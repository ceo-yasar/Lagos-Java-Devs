package com.itquire.lagosjavadevs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * A Custom Adapter to control the ArrayList of {@link Users}
 */

public class UserAdapter extends ArrayAdapter<Users> {
    Context context;
    private final Transformation transformation = new RoundedCornersTransformation(100,10);
    public UserAdapter(Activity context, ArrayList<Users> users) {
        super(context, 0, users);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View userListView = convertView;
        if(userListView == null) {
            userListView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Users} object located at this position in the list
        Users currentUser = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID username
        TextView usernameTextView = (TextView) userListView.findViewById(R.id.username);
        // set the username from getmUsername on the username TextView
        usernameTextView.setText(currentUser.getmUsername());

        //Find the ImageView in the list_item.xml layout with the ID user_icon
        ImageView userImageView = (ImageView)userListView.findViewById(R.id.user_icon);
        //load the image in getmImageResourceId with picasso and set it on the user_icon ImageVIew
        Picasso.with(context).load(currentUser.getmImageResourceId()).placeholder(R.mipmap.avatar_placeholder).resize(250,250).centerCrop().transform(transformation).into(userImageView);

        // Return the whole list item layout (containing 1 TextView and an ImageView)
        // so that it can be shown in the ListView
        return userListView;
    }


}
