package com.example.tutorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessagingActivity extends AppCompatActivity {

    DatabaseReference reference;
    List<Message> chatMessageList;
    ArrayAdapter<Message> arrayAdapter;
    ListView listView;
    String msg;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        Button send = findViewById(R.id.sendButton);
        final EditText message = findViewById(R.id.editText);

        Intent intent = getIntent();
        final String receiverID= intent.getStringExtra("ID");
        final String senderID = user.getUid();

        listView = (ListView) findViewById(R.id.listView);
        chatMessageList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<Message>(
                this,
                android.R.layout.simple_list_item_1,
                chatMessageList
        );
        listView.setAdapter(arrayAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = message.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(senderID, receiverID, msg);
                    chatMessageList.add(new Message(senderID, receiverID, msg));
                    arrayAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MessagingActivity.this, "Enter a message", Toast.LENGTH_SHORT).show();
                }
                message.setText("");
            }
        });

    }
        private void sendMessage (String sender, String receiver, String message){
            reference = FirebaseDatabase.getInstance().getReference();
            Message chatMessage = new Message(sender, receiver, message);
            reference.child("Chats").push().setValue(chatMessage);
        }
    }
