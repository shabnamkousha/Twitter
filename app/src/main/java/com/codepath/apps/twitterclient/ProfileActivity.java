package com.codepath.apps.twitterclient;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.codepath.apps.twitterclient.fragments.UserTimelineFragment;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    TwitterClientA client;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        client=TwitterApplication.getRestClient();

        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user=User.fromJson(response);
                getSupportActionBar().setTitle("@"+user.getScreenName());

            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
            }
        });
        String screen_name=getIntent().getStringExtra("screen_name");
        if(savedInstanceState==null) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screen_name);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }
}
