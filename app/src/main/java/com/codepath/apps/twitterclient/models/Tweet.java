package com.codepath.apps.twitterclient.models;

import android.text.format.Time;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shabnam on 2/18/16.
 */
public class Tweet {
    private String body;
    private long uid; //DB id for the tweet

    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String createdAt;

    public User getUser() {
        return user;
    }

    private User user;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public static Long findMaxId(ArrayList<Tweet> tweets){
        Long min_id=tweets.get(0).getUid();
        for(int i=1; i<tweets.size();i++){
            if(tweets.get(i).getUid()<min_id){
                min_id= tweets.get(i).getUid();
            }
        }
        return min_id;
    }

    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet= new Tweet();
        try{
            tweet.body=jsonObject.getString("text");
            tweet.uid=jsonObject.getLong("id");
            tweet.createdAt=jsonObject.getString("created_at");
            tweet.user=User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets=new ArrayList<>();
        for(int i=0; i<jsonArray.length();i++){
            try {
                JSONObject tweetJson=jsonArray.getJSONObject(i);
                Tweet tweet=Tweet.fromJSON(tweetJson);
                if(tweet!=null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }

        return tweets;
    }

    public void makeTweet(String stringTweet){

        this.setBody(stringTweet);
        Time time = new Time();
        time.setToNow();
        this.setCreatedAt(Long.toString(time.toMillis(false)));
        this.setUid(0);

        User user=new User();
        user.makeUser();
        this.setUser(user);

    }


}
