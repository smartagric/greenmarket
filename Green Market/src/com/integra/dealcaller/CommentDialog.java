package com.integra.dealcaller;

import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.integra.dealcaller.R;
import com.integra.dealcaller.SharePhoto.Share;
import com.integra.dealcaller.UpdateStatus.Update_Status;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class CommentDialog extends ActionBarActivity {
	
	private ProgressBar pbLoader;
	private FeedItem selectedPhoto;
	private ImageView fullImageView;
	private List<FeedItem> feedItems;
	private SharedPreferences prefs;
	private static String URL_FEED = "http://activetalkgh.com/android_connect/timeline.php";
    private JsonObjectRequest jsonReq;
    private NestedListView listView;
    private static CommentListAdapter adapter;
    private static List<CommentItem> movieList;
	private static Map<String, String> params;
	public static final String TAG_SEL_IMAGE = "selectedImage";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = CommentDialog.class.getSimpleName();
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/comment.php";
	private FeedListAdapter feedy;
	private EditText comment;
	
	
	 /** Called when the activity is first created. */
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.comment_dialog);
	        getSupportActionBar().setTitle("Comment on this post");
	        
	        prefs = getSharedPreferences("Chat", 0);
			
			
			String username = prefs.getString("username", null);
			String email = prefs.getString("email", null);
	        
	        int progress = 0;
	        		listView = (NestedListView) findViewById(R.id.listViewc);
	               fullImageView = (ImageView) findViewById(R.id.viewer);
	               comment = (EditText)findViewById(R.id.commentthis);
	               
	 
	        feedy = new FeedListAdapter(this, feedItems);
	        
	        
	        
	        movieList = new ArrayList<CommentItem>();
	        adapter = new CommentListAdapter(this, movieList);
	        listView.setAdapter(adapter);
	        
	        
	        
	        
	        Bundle i = getIntent().getExtras();
            if (i != null) {
          	  
            String selectedPhoto = i.getString("foto2");
            String for_username = i.getString("username");
            String for_email = i.getString("email");
            String from_username = username;
            String from_email = email;
            String commentty = comment.getText().toString();

        
            
            
	 		
            // Building Parameters
            params = new HashMap<String, String>();
            
            params.put("from_username", from_username);
           
            params.put("from_email", from_email);
            params.put("for_username", for_username);
            params.put("for_email", for_email);
            params.put("image_url", selectedPhoto);
            params.put("comment", commentty);
            
            
           
            
            // check for selected photo null
	 		if (selectedPhoto != null) {
	 			

	 			imageLoader.get(selectedPhoto,
	 					new ImageListener() {

	 						@Override
	 						public void onErrorResponse(
	 								VolleyError arg0) {
	 							Toast.makeText(
	 									getApplicationContext(),
	 									"failed to fetch image",
	 									Toast.LENGTH_LONG).show();
	 						}

	 						@Override
	 						public void onResponse(
	 								ImageContainer response,
	 								boolean arg1) {
	 							if (response.getBitmap() != null) {
	 								// load bitmap into imageview
	 								
	 								fullImageView.setImageBitmap(null);
	 								fullImageView
	 										.setImageBitmap(response
	 												.getBitmap());
	 								
	 								// hide loader and show set &
	 								// download buttons
	 							
	 								
	 							}
	 						}
	 					});

	 			
	 		}
	 		
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
	                    	
	                    	Toast.makeText(getApplicationContext(),"Could not fetch comments by people", Toast.LENGTH_LONG).show();
	                        VolleyLog.d(TAG, "Error: " + error.getMessage());
	                       
	                    
	                    }
	                });

	        // Adding request to volley request queue
	        AppController.getInstance().addToRequestQueue(jsonReq);
	    }
	     }
	 
	    
	 
	 
	 class Comment_on_this extends AsyncTask<String, String, String> {
	   	 
	    	String response = null; 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            Toast.makeText(getApplicationContext(), "Starting to react", Toast.LENGTH_LONG).show();
	            
	        }


	        /**
	         * Following
	         * */
	        protected String doInBackground(String... args) {
	        	
	        	
	 
	            // making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo3, params, new Response.Listener<JSONObject>() {

	 			            public void onResponse(JSONObject response) {
	 			            	
	 			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	 			                VolleyLog.d(TAG, "Response: " + response.toString());
	 			                if (response != null) {
	 			                	
	 			                	
	 			                	 // check for success tag
	 			                    try {
	 			                        int success = response.getInt(TAG_SUCCESS);
	 			         
	 			                        if (success == 1) {
	 			                            // successfully created product
	 			                            Toast.makeText(getApplicationContext(), "Successfully followed", Toast.LENGTH_LONG).show();
	 			                           
	         			         
	 			                            
	 			                        } else {
	 			                            // failed to create product
	 			                        }
	 			                    } catch (JSONException e) {
	 			                        e.printStackTrace();
	 			                    }	
	 			                   
	 			                  
	 			                    
	 			                    
	 			                }
	 			            }
	 			        }, new Response.ErrorListener() {

	 			            public void onErrorResponse(VolleyError error) {
	 			            	
	 			       
	 			                VolleyLog.d(TAG, "Error: " + error.getMessage());
	 			                
	 			                
	 			                Toast.makeText(getApplicationContext(), "Unable to write", Toast.LENGTH_LONG).show();
	 			              
	 			            }
	 			        });
	 			
	 			// Adding request to volley request queue
	 			AppController.getInstance().addToRequestQueue(jsObjRequest);
				return response;
	          
	        }

	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product deleted
	           

	    		Toast.makeText(getApplicationContext(), "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}
	 
	 

	 @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	       getMenuInflater().inflate(R.menu.actionbar5, menu);
	           return true;
	   }
	   
	   
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        int itemId = item.getItemId();
			if (itemId == R.id.action_post) {
				
				new Comment_on_this().execute();
 
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
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
	 
	                CommentItem item = new CommentItem();
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
	       
	        
	           
	            
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	    }

}



