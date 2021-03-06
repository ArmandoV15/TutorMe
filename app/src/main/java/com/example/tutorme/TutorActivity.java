/**
 * The purpose of this activity is to display in a ListView all of the tutors that are available
 * and allow you to click on their name in order to contact them. The way that this works is that
 * a for loop will step through every part of the Firebase database, and if the user has their
 * preferences set to show that they are available, they will show up online.
 * In order to achieve this, I referenced this youtube video:
 * https://www.youtube.com/watch?v=jEmq1B1gveM&t=453s
 */


package com.example.tutorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final User tempUser = (User)listView.getItemAtPosition(i);

                Intent intent = new Intent(TutorActivity.this, ViewProfileActivity.class);

                intent.putExtra("Name", tempUser.getName());
                intent.putExtra("Year", tempUser.getYear());
                intent.putExtra("Major", tempUser.getMajor());
                intent.putExtra("ID", tempUser.getUser_id());
                Log.d("HELLO", "ID: " + tempUser.getUser_id());
                startActivity(intent);

            }
        });
        userList = new ArrayList<>();
    }

    /**
     This override onStart function is used to add all available users to the list view in out tutorList when the user enters the activity from
     the mainActivity.
     */
    @Override
    protected void onStart() {
        super.onStart();

        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                // step through each member of the database and if the user wants to appear online,
                // add them to the listview
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);

                    if (user != null && user.isAvailable)
                    {
                        userList.add(user);
                    }
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
