package com.codepath.apps.twitterclient;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.fragments.UserTimelineFragment;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Shabnam on 2/18/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List <Tweet>tweets) {
        super(context, 0 ,tweets);
    }

    //Viewholder pattern
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet=getItem(position);
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent, false);
        }
        final ImageView ivProfileImage=(ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView  tvUsername=(TextView) convertView.findViewById(R.id.tvUsername);
        TextView  tvName=(TextView) convertView.findViewById(R.id.tvName);
        TextView  tvBody=(TextView) convertView.findViewById(R.id.tvBody);
        TextView  tvCreated=(TextView) convertView.findViewById(R.id.tvCreated);

        String timeDiff=getRelativeTimeAgo(tweet.getCreatedAt());
        tvCreated.setText(timeDiff);

        String at="@";
        tvUsername.setText( at.concat(tweet.getUser().getScreenName()));
        tvName.setText( tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent);
        ivProfileImage.setTag(tvUsername.getText());
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ProfileActivity.class);
                intent.putExtra("screen_name", (String) v.getTag());
                v.getContext().startActivity(intent);
            }
        });
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        return convertView;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
