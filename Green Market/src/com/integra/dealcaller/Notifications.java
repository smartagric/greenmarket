package com.integra.dealcaller;
 
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
 
import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.integra.dealcaller.R;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
 

 
 
public class Notifications extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
 
    private static String TAG = Notifications.class.getSimpleName();
 
    private static String URL_TOP_250 = "http://activetalkgh.com/android_connect/top_250.php?offset=";
    private static String URL_FEED = "http://activetalkgh.com/android_connect/dealcaller_comments.php";
    private static JsonObjectRequest jsonReq;
    private static SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private static NotificationListAdapter adapter;
    private static List<NotificationItem> movieList;
 
    // initially offset will be 0, later will be updated while parsing the json
    private static int offSet = 0;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    	actionBar.setTitle("Comments");
    	
    	
        listView = (ListView) findViewById(R.id.listViewn);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
 
        movieList = new ArrayList<NotificationItem>();
        adapter = new NotificationListAdapter(this, movieList);
        listView.setAdapter(adapter);
        
        swipeRefreshLayout.setColorScheme(R.color.myorange, R.color.white, R.color.orange, R.color.white);
        
        swipeRefreshLayout.setMinimumHeight(15);
 
        swipeRefreshLayout.setOnRefreshListener(this);
        
        

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                	
                	movieList.clear();
                	adapter.notifyDataSetChanged();
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
 
        } 
        		 
 
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                    	
                                        swipeRefreshLayout.setRefreshing(true);
 
                                        fetchMovies();
                                    }
                                }
        );
 
    }
 
    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        fetchMovies();
    }
 
    /**
     * Fetching movies json by making http call
     */
    public static void fetchMovies() {
 
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);
 
        // appending offset to url
        String url = URL_TOP_250 + offSet;
        
        String url2 = URL_FEED;
 
     // making fresh volley request and getting json
        jsonReq = new JsonObjectRequest(Method.GET,
                URL_FEED, null, new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {
                    	
                    	//Toast.makeText(getActivity(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
                        VolleyLog.d(TAG, "Response: " + response.toString());
                        if (response != null) {
                        	
                            parseJsonFeed(response);
                           
                            
                            
                            
                        }
                    }
                }, new Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {
                    	
                    	//Toast.makeText(getApplicationContext(),"Unable to get feeds", Toast.LENGTH_LONG).show();
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                       
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }
    
    
    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private static void parseJsonFeed(JSONObject response) {
        try {
        	
        	movieList.clear();
        	adapter.notifyDataSetChanged();
       	 
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                NotificationItem item = new NotificationItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));
 
                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                
                // Status might be null sometimes
                String stat = feedObj.isNull("status") ? null : feedObj
                        .getString("status");
                
                item.setStatus(stat);
                
             // Profile pic might be null sometimes
                String prof = feedObj.isNull("profilePic") ? null : feedObj
                        .getString("profilePic");
                
                item.setProfilePic(prof);
                
                
             // Comments might be null sometimes
                int com = feedObj.isNull("no_of_comments") ? null : feedObj
                        .getInt("no_of_comments");
                
                item.setComments(com);
                
             // Expressions might be null sometimes
                int expr = feedObj.isNull("no_of_expressions") ? null : feedObj
                        .getInt("no_of_expressions");
                
                
                item.setExp(expr);
                
                item.setTimeStamp(feedObj.getString("timeStamp"));
                item.setEmail(feedObj.getString("from_email"));
 
                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);
 
               
                movieList.add(item);
            }
 
            // notify data changes to list adapater
            adapter.notifyDataSetChanged();
       
         // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
 	
	}
 
    @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	       getMenuInflater().inflate(R.menu.actionbar6, menu);
	           return true;
	   }
	   
	   
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        int itemId = item.getItemId();
			if (itemId == R.id.action_add) {
				
				
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
	    }
	
}