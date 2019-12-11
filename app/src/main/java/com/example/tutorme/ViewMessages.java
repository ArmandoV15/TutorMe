package com.example.tutorme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewMessages extends AppCompatActivity {

    DatabaseReference dbRef;
    ListView listView;
    List<Message> conversationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

        conversationsList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.messagesList);
        dbRef = FirebaseDatabase.getInstance().getReference("Chats");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Message message = (Message)listView.getItemAtPosition(i);

                Toast.makeText(ViewMessages.this, message.getSender(), Toast.LENGTH_SHORT).show();
            }
        });

    }


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // get a reference to the MenuInflater
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.messages_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void startMessageActivity() {
        Intent intent = new Intent(this, MessagingActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sendMessage:
                startMessageActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
