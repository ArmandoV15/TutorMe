package com.example.tutorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewMessages extends AppCompatActivity {

    DatabaseReference dbRef, name;
    ListView listView;
    List<Message> conversationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

        conversationsList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.messagesList);
        dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        name = FirebaseDatabase.getInstance().getReference("Users");

        /**
         This listView onClickListener is used to take the user to the messaging app when they press on a message they want to reply too.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Message message = (Message)listView.getItemAtPosition(i);

                Intent intent = new Intent(ViewMessages.this,MessagingActivity.class);
                String uid = FirebaseAuth.getInstance().getUid();
                intent.putExtra("ID", uid);
                startActivity(intent);
            }
        });

    }

    /**
     This is where we keep the messages updated int he listView as user send them
     back and forth.
     */
    @Override
    protected void onStart() {
        super.onStart();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                conversationsList.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Message message = userSnapshot.getValue(Message.class);
                    conversationsList.add(message);
                }
                ConversationList adapter = new ConversationList(ViewMessages.this, conversationsList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void startMessageActivity() {
        Intent intent = new Intent(this, MessagingActivity.class);
        startActivity(intent);
    }

}
