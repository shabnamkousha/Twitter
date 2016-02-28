package com.codepath.apps.twitterclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterclient.fragments.MentionsTimelineFragment;
import com.codepath.apps.twitterclient.fragments.TweetsListFragment;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {


    private TweetsListFragment fragmentTweetsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(0xFF00ACED);

        ViewPager vpPager=(ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabStrip= (PagerSlidingTabStrip) findViewById(R.id.tabs);

        tabStrip.setViewPager(vpPager);


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
        if (id == R.id.miProfile) {

            Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
            i.putExtra("screen_name", "shabkou");
            startActivityForResult(i, 2);
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


        }
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] { "Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0) {
                return new HomeTimelineFragment();
            } else if(position==1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

    }

}
