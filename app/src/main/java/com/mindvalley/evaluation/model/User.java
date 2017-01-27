package com.mindvalley.evaluation.model;

/**
 * Created by trinadhkoya on 25/01/17.
 */

public class User {

    /*
    I want to display the below data in my view
     */
    public String name;
    public String userName;
    public String profileImage;


    /*
    getters and setters for the above references
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
