package com.example.tutorme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView nameTextView, majorTextView, yearTextView, messageButton;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent intent = getIntent();

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        yearTextView = (TextView) findViewById(R.id.yearTextView);
        majorTextView = (TextView) findViewById(R.id.majorTextView);
        messageButton = findViewById(R.id.button);


        nameTextView.setText(intent.getStringExtra("Name"));
        yearTextView.setText("Education Level: " + intent.getStringExtra("Year"));
        majorTextView.setText("Major: " + intent.getStringExtra("Major"));
        userId = intent.getStringExtra("ID");

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, MessagingActivity.class);
                intent.putExtra("ID", userId);
                startActivity(intent);
            }
        });


    }
}
