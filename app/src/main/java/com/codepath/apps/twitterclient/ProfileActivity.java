package com.codepath.apps.twitterclient;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.fragments.UserTimelineFragment;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

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
        String screen_name=getIntent().getStringExtra("screen_name");

        client.getUserInfo(screen_name, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user=User.fromJson(response);
                getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#00ACED\"> @" + user.getScreenName()+"</font>"));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffffffff));
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(true);

                populateProfileHeader(user);

            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
            }
        });

        if(savedInstanceState==null) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screen_name);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    private  void populateProfileHeader(User user) {
        TextView tvName= (TextView) findViewById(R.id.tvFullName);
        TextView tvTagLine= (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowing= (TextView) findViewById(R.id.tvFollowing);
        TextView tvFollowers= (TextView) findViewById(R.id.tvFollowers);
        ImageView ivProfileImage=(ImageView)  findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagLine.setText(user.getTagLine());
        tvFollowing.setText(user.getFollowersCount() + " Followers");
        tvFollowers.setText(user.getFriendsCount()+ " Following");
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);


    }
}
