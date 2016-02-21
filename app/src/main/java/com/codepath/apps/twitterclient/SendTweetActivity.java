package com.codepath.apps.twitterclient;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class SendTweetActivity extends AppCompatActivity {
    private TwitterClientA client;
    EditText tweetMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tweetMessage= (EditText) findViewById(R.id.etTweet);
        client = TwitterApplication.getRestClient();

    }

    public void tweetClicked (View view){

        String stringTweet=tweetMessage.getText().toString();

        client.composeTweet(new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                String stringTweet=tweetMessage.getText().toString();
                Log.e("Debug", stringTweet);
                Intent data = new Intent();

                data.putExtra("stringTweet", stringTweet);


                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pa

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
            }
        }, stringTweet);
    }

}
