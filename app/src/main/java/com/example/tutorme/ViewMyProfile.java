package com.example.tutorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ViewMyProfile extends AppCompatActivity {

    private EditText myNameEditText, myEmailEditText, myMajorEditText, myYearEditText;
    private Switch availableSwitch;
    private Button submitChangesButton;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbRef;
    private String parent;
    private static final String TAG = "ViewMyProfileTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_profile);

        final FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

        //final String userID = user.getUid();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);

                    if (user.getUser_id().equals(fbUser.getUid()))
                    {

                        myNameEditText.setText(user.getName());
                        myEmailEditText.setText(user.getEmail());
                        myMajorEditText.setText(user.getMajor());
                        myYearEditText.setText(user.getYear());
                        parent = userSnapshot.getKey();
                        Toast.makeText(ViewMyProfile.this, parent, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        User userProfile = new User();



        submitChangesButton = (Button) findViewById(R.id.saveChangesButton);
        myNameEditText = (EditText) findViewById(R.id.myNameEditText);
        myEmailEditText = (EditText) findViewById(R.id.myEmailEditText);
        myMajorEditText = (EditText) findViewById(R.id.myMajorEditText);
        myYearEditText = (EditText) findViewById(R.id.yearEditText);
        availableSwitch = (Switch) findViewById(R.id.availableSwitch);

        Intent intent = getIntent();



        submitChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference("Users");
                User user = new User(
                        myNameEditText.getText().toString(),
                        myEmailEditText.getText().toString(),
                        fbUser.getUid(),
                        myMajorEditText.getText().toString(),
                        myYearEditText.getText().toString(),
                        availableSwitch.isChecked()
                );

                Map<String, Object> userUpdates = new HashMap<>();
                userUpdates.put(parent,user);
                updateRef.updateChildren(userUpdates);


                finish();
            }
        });




    }
}
