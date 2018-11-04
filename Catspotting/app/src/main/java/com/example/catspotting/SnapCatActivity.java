package com.example.catspotting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SnapCatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_cat);

        //items on the screen
        ImageButton photobutton = (ImageButton) findViewById(R.id.takePicButton);
        Button noImage = (Button) findViewById(R.id.button2);
        Button findSimilarCats = (Button) findViewById(R.id.button);

    }
}
