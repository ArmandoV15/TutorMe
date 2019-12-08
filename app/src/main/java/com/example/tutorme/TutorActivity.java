package com.example.tutorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TutorActivity extends AppCompatActivity {

    DatabaseReference usersDatabase;

    ListView listView;
    List<User> userList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        listView = (ListView) findViewById(R.id.listView);
        usersDatabase = FirebaseDatabase.getInstance().getReference("Users");

        userList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);

                    userList.add(user);
                }

                UsersList adapter = new UsersList(TutorActivity.this, userList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
