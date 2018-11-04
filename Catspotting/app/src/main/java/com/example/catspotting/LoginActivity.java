package com.example.catspotting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View v) {
        // Code here executes on main thread after user presses button

        EditText usernameView = (EditText)findViewById(R.id.usernameText);
        String username = usernameView.getText().toString();
        EditText passwordView = (EditText)findViewById(R.id.passwdText);
        final String password = passwordView.getText().toString();

        final LoginActivity me = this;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(username);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String dataPassword = dataSnapshot.child("Password").getValue(String.class);
                    if (password.equals(dataPassword)) {
                        Intent intent = new Intent(me, EndlessScrollActivity.class);
                        startActivity(intent);
                    } else {
                        Toast t = Toast.makeText(getApplicationContext(), "Incorrect Password",
                                Toast.LENGTH_SHORT);
                        t.show();
                    }
                } else {
                    Toast t = Toast.makeText(getApplicationContext(), "That User Cannot Be Found",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast t = Toast.makeText(getApplicationContext(), "That User Cannot Be Found",
                        Toast.LENGTH_SHORT);
                t.show();
            }
            });


    }

    public void cancel(View v) {
        // Code here executes on main thread after user presses button
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
