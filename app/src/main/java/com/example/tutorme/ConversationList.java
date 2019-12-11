package com.example.tutorme;

import android.app.Activity;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class ConversationList extends ArrayAdapter<Message> {
    private Activity context;
    private List<Message> userList;

    public ConversationList(Activity context, List<Message> userList) {
        super(context, R.layout.listview_conversation, userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listview_conversation, null, true);

        final TextView nameTextView = (TextView) listViewItem.findViewById(R.id.senderTextView);
        TextView messageTextView = (TextView) listViewItem.findViewById(R.id.messageTextView);


        final Message message = userList.get(position);


        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren())
                {
                    User user = userSnapshot.getValue(User.class);

                    if (message.getSender().equals(user.getUser_id()))
                    {
                        nameTextView.setText("Sent From: " + user.getName());
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        messageTextView.setText(message.getTextMessage());



        return listViewItem;
    }
}
