package com.example.catspotting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class EndlessScrollActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endless_scroll);

        getData();
    }

    private void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Posts");
        Query myQuery = myRef.orderByChild("Time").limitToLast(100);
        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedList<DataSnapshot> postList = new LinkedList<>();
                double myLat = 33.775 * Math.PI / 180;
                double myLong = 84.396 * Math.PI / 180;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    double latitude = postSnapshot.child("Location").child("Latitude").getValue(Double.class);
                    double longitude = postSnapshot.child("Location").child("Longitude").getValue(Double.class);
                    latitude *= Math.PI / 180;
                    longitude *= Math.PI / 180;
                    double dLat = Math.abs(latitude - myLat);
                    double dLong = Math.abs(longitude - myLong);
                    double a = Math.pow(Math.sin(dLat/2), 2) + Math.cos(myLat)*Math.cos(latitude)*
                            Math.pow(Math.sin(dLong/2),2);
                    double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    double distance = 3959*c;
                    if (distance <= 100) {
                        postList.addFirst(postSnapshot);
                    }
                }

                ArrayList<DataSnapshot> postList2 = new ArrayList<>(postList);
                createRecycler(postList2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("loadPost:onCancelled" + databaseError.toException());
                // ...
            }
        });
    }

    private void createRecycler(ArrayList<DataSnapshot> postList) {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //String[] myDataset = {"20180317_132504_001.jpg", "20180323_162849.jpg"};
        //String[] myDataset = {"h4R0yylGVIWLbV5UThsS"};
        //int[] myDataset = {0, 1, 2, 3};
        // specify an adapter (see also next example)
        mAdapter = new MyListAdapter(postList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
