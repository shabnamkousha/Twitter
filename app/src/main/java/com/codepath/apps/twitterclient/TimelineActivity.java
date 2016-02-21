package com.codepath.apps.twitterclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClientA client;
    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;

    Long maxTweetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        maxTweetId= Long.valueOf(0);
        lvTweets=(ListView) findViewById(R.id.lvTweets);
        tweets=new ArrayList<>();
        aTweets=new TweetsArrayAdapter(this, tweets);

        lvTweets.setAdapter(aTweets);

        client = TwitterApplication.getRestClient();
        populateTimeline(maxTweetId);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

    }

    public void customLoadMoreDataFromApi(int offset) {
        populateTimeline(maxTweetId);
    }

    //Send request + Fill the list view
    public void populateTimeline(Long page){
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray  json) {
                //Log.e("Debug", json.toString());
                ArrayList<Tweet> tweets=new ArrayList<>();

                tweets=Tweet.fromJSONArray(json);
                maxTweetId=Tweet.findMaxId(tweets);
                aTweets.addAll(tweets);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
            }
        }, page);
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.timeline, menu);

        return true;
    }

    // Default menu function
    @Override
    public boolean onOptionsItemSelected(MenuItem anItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = anItem.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.composeTweetBtn) {

            Intent i = new Intent(TimelineActivity.this, SendTweetActivity .class);
            startActivityForResult(i, 1);
        }


        return super.onOptionsItemSelected(anItem);
    }

    @Override
    protected void onActivityResult(int aRequestCode, int aResultCode, Intent aDataSet) {
        // REQUEST_CODE is defined above
        if (aResultCode == RESULT_OK && aRequestCode == 1) {
            // Extract name value from result extras
            String stringTweet = aDataSet.getExtras().getString("stringTweet");
            Tweet newTweet=new Tweet();
            newTweet.makeTweet(stringTweet);

            tweets.add(0, newTweet);
            aTweets.notifyDataSetChanged();

        }
    }



}
