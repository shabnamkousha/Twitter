package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.twitterclient.TwitterApplication;
import com.codepath.apps.twitterclient.TwitterClientA;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shabnam on 2/25/16.
 */
public class HomeTimelineFragment extends TweetsListFragment{

    private TwitterClientA client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        maxTweetId= Long.valueOf(0);


        client = TwitterApplication.getRestClient();

        populateTimeline(maxTweetId);
    }

    Long maxTweetId;
    //Send request + Fill the list view
    public void populateTimeline(Long page){
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //Log.e("Debug", json.toString());
                ArrayList<Tweet> tweets=new ArrayList<>();

                tweets=Tweet.fromJSONArray(json);
                maxTweetId=Tweet.findMaxId(tweets);
                addAll(tweets);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
            }
        }, page);
    }

}
