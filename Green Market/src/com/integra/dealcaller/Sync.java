package com.integra.dealcaller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;



    

public class Sync extends AsyncTask<String, Void, String> {
	
	
	private Activity activity;
	private List<FeedItem> feedItems; 
	private static final String TAG = TimeLineFragment.class.getSimpleName();
    
    private String URL_FEED = "http://activetalkgh.com/android_connect/timeline.php";
    
    
    public Sync(Activity activity, List<FeedItem> feedItems) {
		this.activity = activity;
		this.feedItems = feedItems;
		
		
	}
    
    {

    
    feedItems = new ArrayList<FeedItem>();
    
	}
        @Override
        protected void onPreExecute() {
            // set the progress bar view
            
        }
 
        @Override
        protected String doInBackground(String... params) {
            // not making real request in this demo
            // for now we use a timer to wait for sometime
        	
        	 // We first check for cached request
            Cache cache = AppController.getInstance().getRequestQueue().getCache();
            Entry entry = cache.get(URL_FEED);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                    	
                    	feedItems.clear();
                    
                        parseJsonFeed(new JSONObject(data));
                   
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
     
            } else {
           
                // making fresh volley request and getting json
                JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                        URL_FEED, null, new Response.Listener<JSONObject>() {
     
                            public void onResponse(JSONObject response) {
                            	
                            	Toast.makeText(activity,"Async task doing its thing!!", Toast.LENGTH_LONG).show();
                                VolleyLog.d(TAG, "Response: " + response.toString());
                                if (response != null) {
                                	
                                    parseJsonFeed(response);
                                   
                                    
                                    
                                }
                            }
                        }, new Response.ErrorListener() {
     
                            public void onErrorResponse(VolleyError error) {
                            	
                            	Toast.makeText(activity,"Error getting feeds", Toast.LENGTH_LONG).show();
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
                               
                                
                            }
                        });
     
                // Adding request to volley request queue
                AppController.getInstance().addToRequestQueue(jsonReq);
      
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
         
        }
			return URL_FEED;
        }
        
        
        @Override
        protected void onPostExecute(String result) {
            
        }
        
        
        private void parseJsonFeed(JSONObject response) {
            try {
            	
            	feedItems.clear();
           	 
           	 
                JSONArray feedArray = response.getJSONArray("feed");
     
                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);
     
                    FeedItem item = new FeedItem();
                    item.setId(feedObj.getInt("id"));
                    item.setName(feedObj.getString("name"));
     
                    // Image might be null sometimes
                    String image = feedObj.isNull("image") ? null : feedObj
                            .getString("image");
                    item.setImge(image);
                    item.setStatus(feedObj.getString("status"));
                    item.setProfilePic(feedObj.getString("profilePic"));
                    item.setTimeStamp(feedObj.getString("timeStamp"));
     
                    // url might be null sometimes
                    String feedUrl = feedObj.isNull("url") ? null : feedObj
                            .getString("url");
                    item.setUrl(feedUrl);
     
                    
                    feedItems.add(item);
                }
     
                // notify data changes to list adapater
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
        
    };
}


