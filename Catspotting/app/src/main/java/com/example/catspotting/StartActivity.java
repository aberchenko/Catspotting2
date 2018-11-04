package com.example.catspotting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.View;
import android.content.Intent;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /*final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(this, ScrollingActivity.class);

            }
        });*/

        //loadWithGlide();
    }

    public void onClick(View v) {
        // Code here executes on main thread after user presses button
        Intent intent = new Intent(this, EndlessScrollActivity.class);
        startActivity(intent);
    }

    public void loadWithGlide() {
        // [START storage_load_with_glide]
        FirebaseStorage storage = FirebaseStorage.getInstance();
        System.out.println("Storage: " + storage);

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference with an initial file path and name
        StorageReference gsReference = storage.getReferenceFromUrl("gs://catspotting-5ae47.appspot.com/20180317_132504_001.jpg");

        System.out.println("Starting to try.");
        // ImageView in your Activity
        ImageView imageView = findViewById(R.id.imageView);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(this /* context */)
                .load(gsReference)
                .into(imageView);
        // [END storage_load_with_glide]
    }
}
