package com.example.catspotting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class PostCreateActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_post_create);

        catpicwindow = findViewById(R.id.catPicWindow);
        loadImg = findViewById(R.id.button3);
        descriptionBox = findViewById(R.id.editText2);
        tags = findViewById(R.id.editText3);
        missingPet = findViewById(R.id.checkBox);
        knownLocal = findViewById(R.id.checkBox2);
        makePost = findViewById(R.id.button4);
    }

    public void takePic(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    public void makePost(View view) {
        //FILL WITH DATABASE STUFF
        Drawable drawable = catpicwindow.getDrawable();
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
        Bitmap bitmap = bitmapDrawable .getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);

        int unixTime = (int) (System.currentTimeMillis() / 1000L);

        String description = descriptionBox.getText().toString();
        String tagList = tags.getText().toString();
        String[] tagArray = tagList.split(",");

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        String name = LoginActivity.username + unixTime;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(name + ".jpg");

        UploadTask uploadTask = storageRef.putBytes(imageInByte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast t = Toast.makeText(getApplicationContext(), "Server Error",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast t = Toast.makeText(getApplicationContext(), "Posted!",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dRef = database.getReference();

        dRef.child("Posts").child(name).child("Image").setValue(name + ".jpg");
        dRef.child("Posts").child(name).child("Description").setValue(description);
        dRef.child("Posts").child(name).child("Username").setValue(LoginActivity.username);
        dRef.child("Posts").child(name).child("Time").setValue(unixTime);
        dRef.child("Posts").child(name).child("Loves").setValue(1);
        dRef.child("Posts").child(name).child("Nopes").setValue(0);
        dRef.child("Posts").child(name).child("Location").child("Latitude").setValue(location.getLatitude());
        dRef.child("Posts").child(name).child("Location").child("Longitude").setValue(location.getLongitude());
        dRef.child("Posts").child(name).child("Tags").setValue(new ArrayList<String>(Arrays.asList(tagArray)));

        System.out.println("I made it to the end");

        Intent intent = new Intent(this, EndlessScrollActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                catpicwindow.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            //idk...
            Toast.makeText(this, "You haven't picked an image",Toast.LENGTH_LONG).show();
        }
    }
}
