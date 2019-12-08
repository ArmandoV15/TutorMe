package com.example.tutorme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ViewMyProfile extends AppCompatActivity {

    private EditText myNameEditText, myEmailEditText, myMajorEditText, myYearEditText;
    private Switch availableSwitch;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        //mDatabase = FirebaseDatabase.getInstance().getReference("Users").child();


        myNameEditText = (EditText) findViewById(R.id.myNameEditText);
        myEmailEditText = (EditText) findViewById(R.id.myEmailEditText);
        myMajorEditText = (EditText) findViewById(R.id.myMajorEditText);
        myYearEditText = (EditText) findViewById(R.id.yearEditText);
        availableSwitch = (Switch) findViewById(R.id.availableSwitch);



    }
}
