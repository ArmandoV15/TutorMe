/**
 * This activity serves as the main menu for our app. All it contains is 4 buttons
 * that reroute you to other sections of the application
 */
package com.example.tutorme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Button seeTutorsBtn = (Button) findViewById(R.id.findTutorsButton);
        Button inboxBtn = (Button) findViewById(R.id.inboxButton);
        Button profileBtn = (Button) findViewById(R.id.profileButton);
        Button logoutBtn = (Button) findViewById(R.id.logoutButton);

        /**
         onClickListener used to take you form the main activity to our tutor list
         that displays our current list of tutors.
         */
        seeTutorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TutorActivity.class);
                startActivity(intent);
            }
        });

        /**
         onClickListener used to take you form the main activity to our viewMessages activity
         where you can view the messages you have sent.
         */
        inboxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewMessages.class);
                startActivity(intent);
            }
        });

        /**
         onClickListener used to take you form the main activity to our viewMyProfileActivity where you can view your personal account details and
         also edit those details.
         */
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewMyProfile.class);
                startActivity(intent);
            }
        });

        /**
         onClickListener used to sign you out of your account and take you back ato the sign in page.
         */
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }
}
