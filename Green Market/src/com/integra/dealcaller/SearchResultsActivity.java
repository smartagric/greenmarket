package com.integra.dealcaller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.integra.dealcaller.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class SearchResultsActivity extends ActionBarActivity {
 
    private TextView txtQuery;
 
    private static final String TAG = SearchResultsActivity.class.getSimpleName();
    
    
    public static final String ITEM_NAME = "itemName";
    
    // flag for Internet connection status
    Boolean isInternetPresent = false;
     
    // Connection detector class
    ConnectionDetector cd;
    
    private static ProgressBar pb;
    private static List<LineupMovie> searchList = new ArrayList<LineupMovie>();
    private ListView listView;
    private static SearchListAdapter adapter;
    private android.support.v7.app.ActionBar actionBar;
    private static TextView searching;

	private TextView noInternet;

	private TextView noResults;
    int progress = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_return_layout);
 
        getSupportActionBar().setTitle("Search Results");
        
        cd = new ConnectionDetector(getApplicationContext());
        
 
        
        
        
        
        
        listView = (ListView)findViewById(R.id.listeesearch);
        adapter = new SearchListAdapter(this, searchList);
        listView.setAdapter(adapter);
        
        searching = (TextView)findViewById(R.id.searching);
 
        noInternet = (TextView)findViewById(R.id.no_internet);
        noResults = (TextView)findViewById(R.id.no_results);
        
        pb=(ProgressBar)findViewById(R.id.progressSearch);
        

        progress = 0;
       
     			
     			
     				
         pb.setVisibility(View.VISIBLE);
         searching.setVisibility(View.VISIBLE);
         pb.setProgress(progress);
        
         
         handleIntent(getIntent());
         
         
        listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				
				
				
			}
        	 
         });  
        
    }    
    
    
    
  

    private static void parseJsonFeed(JSONObject response) {
        try {
       	 
       	 searchList.clear();
       	 adapter.notifyDataSetChanged();
       	 
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                LineupMovie movie = new LineupMovie();
               
                movie.setTitle(feedObj.getString("channel_name"));
                
             // Profile pic might be null sometimes
                String prof = feedObj.isNull("profilePic") ? null : feedObj
                        .getString("profilePic");
               
               
             
                movie.setThumbnailUrl(prof);
                
                String sme_id = feedObj.isNull("sme_id") ? null : feedObj
                        .getString("sme_id");
             
                movie.setSME_id(sme_id);
                
                
                movie.setLocation((feedObj.getString("service")));
               
                
             
                
                movie.setTicker(feedObj.getString("location"));
                
               

                // adding movie to movies array
                
                
                searchList.add(movie);

            }
 
            // notify data changes to list adapater
            
           
            adapter.notifyDataSetChanged();
            
            pb.setVisibility(View.GONE);
            searching.setVisibility(View.GONE);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
 	
	}
    
 
    @Override
    protected void onNewIntent(Intent intent) {
       setIntent(intent);
        handleIntent(intent);
    }
 
    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            
            
            String url = "http://activetalkgh.com/android_connect/search2.php";
            
            Map<String, String> params = new HashMap<String, String>();
            
            
            params.put("query", query);
            
            
            
            
            /**
             * Use this query to display search results like
             * 1. Getting the data from SQLite and showing in listview
             * 2. Making webrequest and displaying the data
             * For now we just display the query only
             */
            
            // making fresh volley request and getting json
            
            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url, params, new Response.Listener<JSONObject>() {

			            public void onResponse(JSONObject response) {
			            	
			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
			                VolleyLog.d(TAG, "Response: " + response.toString());
			                if (response != null) {
			                	
			                	
			                	parseJsonFeed(response);
			                   
			                  
			                    
			                    
			                }
			            }
			        }, new Response.ErrorListener() {

			            public void onErrorResponse(VolleyError error) {
			            	
			       
			                //VolleyLog.d(TAG, "Error: " + error.getMessage());
			                pb.setVisibility(View.GONE);
			                searching.setVisibility(View.GONE);
			                
			             // get Internet status
			                isInternetPresent = cd.isConnectingToInternet();

			                // check for Internet status
			                if (isInternetPresent) {
			                    // Internet Connection is Present
			                    // make HTTP requests
			                	
			                	
			                	noResults.setVisibility(View.VISIBLE);
								
								return;
			                	
			           
			                
			                } else {
			                    // Internet connection is not present
			                    // Ask user to connect to Internet
			                	
			                	noInternet.setVisibility(View.VISIBLE);
			                }
			                
			              
			            }
			        });
			
			// Adding request to volley request queue
			AppController.getInstance().addToRequestQueue(jsObjRequest);

			AppController.getInstance().getRequestQueue().getCache().remove(url);

         
            
            }
           
        }
 
    }

