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
}

