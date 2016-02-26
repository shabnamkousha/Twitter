package com.codepath.apps.twitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClientA extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "grE6SsfuB3bn7B61ca48izDg1";       // Change this
	public static final String REST_CONSUMER_SECRET = "k4728EBJDWFqM4K91T65QJTeqq6bVXNKl7OXxOChJBqG2J0ymA"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClientA(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	public void getHomeTimeline(AsyncHttpResponseHandler handler, Long maxId){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params=new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		if(maxId!=Long.valueOf(0)) {
			params.put("max_id", maxId-1);
		}

		client.get(apiUrl, params, handler);

	}

	public void composeTweet(AsyncHttpResponseHandler handler, String tweetText){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params=new RequestParams();
		params.put("status", tweetText);

		client.post(apiUrl, params, handler);
		Log.e("DEBUG", "HERE");
	}

	public void getMentionsTimeline(JsonHttpResponseHandler handler, Long maxId) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params=new RequestParams();
		params.put("count", 25);
		//params.put("since_id", 1);
		//if(maxId!=Long.valueOf(0)) {
			//params.put("max_id", maxId-1);
		//}

		client.get(apiUrl, params, handler);
		Log.e("DEBUG", "HERE");

	}
}