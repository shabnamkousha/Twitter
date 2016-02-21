package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shabnam on 2/18/16.
 */
public class User {
    private String name;
    private Long id;
    private String screenName;
    private String profileImageUrl;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static User fromJson(JSONObject  json){
        User u=new User();
        try {
            u.name = json.getString("name");
            u.id=json.getLong("id");
            u.screenName=json.getString("screen_name");
            u.profileImageUrl=json.getString("profile_image_url");

        } catch (JSONException e){
            e.printStackTrace();
        }

        return u;
    }

    public void makeUser(){
        this.setProfileImageUrl("https://pbs.twimg.com/profile_images/701307328591634432/DhYP6321_bigger.jpg");
        this.setScreenName("shabkou");
        this.setName("shabkou");
    }
}

