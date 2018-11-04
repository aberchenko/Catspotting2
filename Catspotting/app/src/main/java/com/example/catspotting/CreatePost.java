package com.example.catspotting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreatePost extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;


    ImageView catpicwindow;
    Button loadImg;
    EditText descriptionBox;
    EditText tags;
    Button makePost;
    CheckBox missingPet;
    CheckBox knownLocal;
    Image catimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("I arrived!");

        catpicwindow = findViewById(R.id.catPicWindow);
        loadImg = findViewById(R.id.button3);
        descriptionBox = findViewById(R.id.editText2);
        tags = findViewById(R.id.editText3);
        missingPet = findViewById(R.id.checkBox);
        knownLocal = findViewById(R.id.checkBox2);
        makePost = findViewById(R.id.button4);

        System.out.println("I'm still here");

        /*loadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePic();
            }
        });
        System.out.println("In the middle");
        makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePost();
            }
        });
        System.out.println("Apparently I'm still around");*/
    }





}
