package com.itquire.lagosjavadevs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * This activity controls the User details page
 */

public class JavaDevProfile extends AppCompatActivity {
    //declaration of user image container
    ImageView profileImage;
    //declaration of username container
    TextView usernameTextView;
   //declaration of container that describe the profile link
    TextView profileLinkTextView;
    //declaration of user profile link container
    TextView userProfileUrlTextView;
    //declaration of a {@link Users} object
    Users users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        //get the data passed from the ListView in the previous activity
        Intent userIntent = getIntent();
        //store the intent in the declared {@Link Users} object
        users = (Users) userIntent.getSerializableExtra("Users");
        //find the ImageView with id user_image
        profileImage = (ImageView) findViewById(R.id.user_Image);
        //load and store the image in the {@link Users}'s  image url into the ImageView
        Picasso.with(this).load(users.getmImageResourceId()).placeholder(R.mipmap.avatar_placeholder).fit().into(profileImage);

        //find the TextView with id user_profile_name
        usernameTextView = (TextView) findViewById(R.id.user_profile_name);
        //store the username from {@link Users} into the TextView
        usernameTextView.setText(users.getmUsername());

        //find the TextView with id profileLinkText
        profileLinkTextView = (TextView) findViewById(R.id.profileLinkText);
        //store the description into the TextView
        profileLinkTextView.setText("Link to Profile: ");
        //find the TextView with id profile_url
        userProfileUrlTextView = (TextView) findViewById(R.id.profile_url);
        //store the url to the user profile page from {@link Users} into the TextView
        userProfileUrlTextView.setText(users.getmUserProfileUrl());
    }

    //action to be performed when the TextView with id profile_url is clicked
    public void ProfileLink (View view){
        //create a new intent and set the action
        Intent profilePage = new Intent(Intent.ACTION_VIEW);
        //set the link into the intent
        profilePage.setData(Uri.parse(users.getmUserProfileUrl()));
        //call the intent
        startActivity(profilePage);
    }
    //action to be performed when the share button is clicked
    public void Share (View view){
        //declare and store the intent content into a String
        String shareContent = "Check out this awesome developer @" + users.getmUsername() +", " + users.getmUserProfileUrl();
        //create a new intent and set the action
        Intent shareUserDetailsIntent = new Intent (Intent.ACTION_SEND);
        //attach the content to the intent
        shareUserDetailsIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        //set the type of intent
        shareUserDetailsIntent.setType("text/plain");
        //call the intent
        startActivity(Intent.createChooser(shareUserDetailsIntent,"Share With"));
    }



}
