/**
 * This is our custom array class that we use in TutorActivity.
 * It displays two textviews one below the other
 */

package com.example.tutorme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UsersList extends ArrayAdapter<User> {
    private Activity context;
    private List<User> userList;

    public UsersList(Activity context, List<User> userList) {
        super(context, R.layout.listview_layout, userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem  = inflater.inflate(R.layout.listview_layout, null, true);

        TextView nameTextView = (TextView) listViewItem.findViewById(R.id.nameTextView);
        TextView yearTextView = (TextView) listViewItem.findViewById(R.id.textViewYear);

        User user = userList.get(position);

        nameTextView.setText(user.getName());
        yearTextView.setText(user.getYear());

        return listViewItem;
    }
}
