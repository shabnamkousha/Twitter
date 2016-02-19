package com.codepath.apps.twitterclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        ImageView ivProfileImage=(ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView  tvUsername=(TextView) convertView.findViewById(R.id.tvUsername);
        TextView  tvBody=(TextView) convertView.findViewById(R.id.tvBody);
        tvUsername.setText( tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        return convertView;
    }
}
