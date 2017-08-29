package com.itquire.lagosjavadevs;

import java.io.Serializable;

/**
 * An Instance of User
 */

public class Users implements Serializable {
    //declare container to hold the username
    private String mUsername;
    //declare container to hold the user image
    private String mImageResourceId;
    //declare container to hold the user profile url
    private String mUserProfileUrl;

    /**
     * Create a constructor for {@link Users}
     * @param username
     */
    public Users(String username) {
        mUsername = username;
    }


    /**
     * Create a constructor for {@link Users}
     * @param username
     * @param imageResourceId
     * @param profileUrl
     */
    public Users(String username, String imageResourceId, String profileUrl){
        mUsername = username;
        mImageResourceId = imageResourceId;
        mUserProfileUrl = profileUrl;
    }
    //set a getter for the username
    public String getmUsername() {
        return mUsername;
    }

    //set a getter for the user image resource
    public String getmImageResourceId() {
        return mImageResourceId;
    }

    //set a getter for the user profile url
    public String getmUserProfileUrl(){
        return mUserProfileUrl;
    }

}
