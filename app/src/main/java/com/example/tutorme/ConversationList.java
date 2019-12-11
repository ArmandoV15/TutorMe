package com.example.tutorme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView nameTextView = (TextView) listViewItem.findViewById(R.id.senderTextView);
        TextView messageTextView = (TextView) listViewItem.findViewById(R.id.messageTextView);


        Message message = userList.get(position);

        messageTextView.setText(message.getTextMessage());
        nameTextView.setText(message.getSender());


        return listViewItem;
    }
}
