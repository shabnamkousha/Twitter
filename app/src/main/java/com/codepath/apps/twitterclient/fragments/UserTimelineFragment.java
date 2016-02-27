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
 * Created by Shabnam on 2/26/16.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClientA client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        maxTweetId= Long.valueOf(0);


        client = TwitterApplication.getRestClient();

        populateTimeline(maxTweetId);
    }

    Long maxTweetId;


    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userFragment.setArguments(args);
        return userFragment;
    }

    public void populateTimeline(Long page) {
        String screen_name = getArguments().getString("screen_name");
        client.getUserTimeline(screen_name , new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.e("Debug", json.toString());
                ArrayList<Tweet> tweets = new ArrayList<>();

                tweets = Tweet.fromJSONArray(json);
                Long temp = Tweet.findMaxId(tweets);
                setMaxTweetId(temp);
                addAll(tweets);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
            }
        });
    }

}
