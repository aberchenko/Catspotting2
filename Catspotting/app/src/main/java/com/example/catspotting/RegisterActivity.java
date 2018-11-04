package com.example.catspotting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void cancel(View v) {
        // Code here executes on main thread after user presses button
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void register(View v) {
        // Code here executes on main thread after user presses button
        EditText usernameView = (EditText)findViewById(R.id.usernameText);
        final String username = usernameView.getText().toString();
        EditText passwordView = (EditText)findViewById(R.id.passwdText);
        final String password = passwordView.getText().toString();
        EditText password2View = (EditText)findViewById(R.id.passwd2Text);
        final String password2 = passwordView.getText().toString();
        if (!password.equals(password2)) {
            Toast t = Toast.makeText(getApplicationContext(), "Different Passwords",
                    Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        final RegisterActivity me = this;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(username);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast t = Toast.makeText(getApplicationContext(), "User already exists",
                            Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    database.getReference().child(username).child("Password").setValue(password);
                    Intent intent = new Intent(me, EndlessScrollActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast t = Toast.makeText(getApplicationContext(), "Error Occurred",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }
}
