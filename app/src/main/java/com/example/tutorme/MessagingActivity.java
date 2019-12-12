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

        /**
         arrayAdapter used to display messages sent in the listView of this activity
         */
        listView = (ListView) findViewById(R.id.listView);
        chatMessageList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<Message>(
                this,
                android.R.layout.simple_list_item_1,
                chatMessageList
        );
        listView.setAdapter(arrayAdapter);

        /**
         onClickListener used to send the message typed in the editText too another user while also storing it in Firebase.
         Also does a check to make sure the user is not sending an empty message.
         */
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

        /**
         This override method is used to update the messages that are able to be read as users send them to one another.
         */
        reference = FirebaseDatabase.getInstance().getReference("Users").child(receiverID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                readMessages(senderID, receiverID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     Method used inside send buttons onClickListener to send the message to a specific user
     * @param sender The current senders ID
     * @param receiver The receivers ID
     * @param message The contents of the message
     */
        private void sendMessage (String sender, String receiver, String message){
            reference = FirebaseDatabase.getInstance().getReference();
            Message chatMessage = new Message(sender, receiver, message);
            reference.child("Chats").push().setValue(chatMessage);
        }

    /**
     This is the method used the the addValueEventListener that allows new messages to be readable for both users
     * @param senderID ID of the sender
     * @param receiveID ID of the receiver
     */
    private void readMessages(final String senderID, final String receiveID){
            reference = FirebaseDatabase.getInstance().getReference("Chats");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chatMessageList.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Message message = snapshot.getValue(Message.class);
                            if (message.getReceiver().equals(senderID) && message.getSender().equals(receiveID)||
                                    message.getReceiver().equals(receiveID) && message.getSender().equals(senderID)) {
                                chatMessageList.add(message);
                            }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
