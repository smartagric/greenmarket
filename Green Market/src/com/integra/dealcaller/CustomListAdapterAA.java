package com.integra.dealcaller;

import java.util.HashMap;
import android.animation.Animator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;

public class CustomListAdapterAA extends BaseAdapter implements OnClickListener, OnScrollListener{
	private Activity activity;
	private LayoutInflater inflater;
	private static Map<String, String> params;
	private SharedPreferences prefs;
	private static String vid=null;
	private static String thumbnail=null;
	private static Intent i=null;
	String url = "http://activetalkgh.com/android_connect/get_video.php";
	private List<MovieAA> movieItems;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = CustomListAdapterAA.class.getSimpleName();
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/followed_talkers.php";
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	
	// Animation
		Animation bounce;

	public CustomListAdapterAA(Activity activity, List<MovieAA> movieItems) {
		this.activity = activity;
		this.movieItems = movieItems;
	}

	
	

	public int getCount() {
		return movieItems.size();
	}

	public Object getItem(int location) {
		return movieItems.get(location);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row_a, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		RoundedImageVieww thumbNail = (RoundedImageVieww) convertView
				.findViewById(R.id.thumbnail);
		//thumbNail.setOnClickListener(this);
		
		
		bounce = AnimationUtils.loadAnimation(activity,R.anim.bounce);
		
		// set animation listener
		bounce.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView rating = (TextView) convertView.findViewById(R.id.rating);
		TextView genre = (TextView) convertView.findViewById(R.id.genre);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
		
		final ImageButton follow = (ImageButton) convertView.findViewById(R.id.imageButtonfollow);
		follow.setOnClickListener(this);
	
		

		// getting movie data for the row
		final MovieAA m = movieItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
		//thumbNail.setVisibility(View.VISIBLE);
		//thumbNail.refreshDrawableState();
		
		// title
		title.setText(m.getTitle());
		
	
		
		// Check for empty rating 
				if (m.getRating()>0) {
					rating.setText(String.valueOf(m.getRating()+" followers"));
					rating.setVisibility(View.VISIBLE);
				} else {
					rating.setVisibility(View.GONE);
				}
			
				
		// Chcek for empty genre
				if (!TextUtils.isEmpty(m.getGenre())) {
					genre.setText(String.valueOf(m.getGenre()));
					genre.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					genre.setVisibility(View.GONE);
				}
		
		
		
		
		
		// release year
		year.setText(String.valueOf(m.getYear()));
		
		
		
		//thumbNail.setOnClickListener(new OnClickListener() {
			
			//@Override
			//public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				//Intent i = new Intent(activity, TalkerProfile.class);

		         
		     	//i.putExtra("foto3", m.getThumbnailUrl());
		     	
		     	//i.putExtra("covery", m.getThumbnailUrl());
		     	
		     	//i.putExtra("namey", m.getTitle());
		     	
		     	//i.putExtra("email", m.getEmail());
		     	
		         //activity.startActivity(i);
	            
				
			//}
		//});

		
		
		
		
		follow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				follow.startAnimation(bounce);

				//prefs = activity.getSharedPreferences("Chat", 0);
				
				
				//String email = prefs.getString("email", null);

				
	        	  //Building Parameters
	            //params = new HashMap<String, String>();
	            //params.put("query", m.getTitle());
	            //params.put("final_image", m.getThumbnailUrl());
	            //params.put("email", m.getEmail());
	            //params.put("rating", m.getYear());
	            //params.put("genre", m.getGenre());
	            //params.put("follower", email);
	            
	          // new Validate().execute();
				
				 i = new Intent(activity, TalkerProfile.class);

		         
		     	i.putExtra("foto3", m.getThumbnailUrl());
		     	
		     	i.putExtra("covery", m.getThumbnailUrl());
		     	
		     	i.putExtra("namey", m.getTitle());
		     	
		     	i.putExtra("email", m.getEmail());
		     	
		        
	            
	            Map<String, String> paramms = new HashMap<String, String>();


	            paramms.put("query", m.getTitle());
	            
	            //Toast.makeText(activity, m.getTitle(), Toast.LENGTH_LONG).show();
 
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url, paramms, new Response.Listener<JSONObject>() {

    	            public void onResponse(JSONObject response) {
    	            	
    	            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
    	                VolleyLog.d(TAG, "Response: " + response.toString());
    	                if (response != null) {
    	                	
    	                	try {
    					      JSONArray feedArray = response.getJSONArray("feed");
    					
    					      for (int i = 0; i < feedArray.length(); i++) {
    					          JSONObject feedObj = (JSONObject) feedArray.get(i);
    					
    					         
    					         
    					          
    					          
    					       // rating might be null sometimes
    					          vid = feedObj.isNull("video_to_play") ? null : feedObj
    					                  .getString("video_to_play");
    					          
    					          thumbnail = feedObj.isNull("video_thumbnail") ? null : feedObj
    					                  .getString("video_thumbnail");
    		
    								      }
    								
    					      if (!TextUtils.isEmpty(vid)) {
    								
    					    	  //Toast.makeText(activity, vid.toString(), Toast.LENGTH_LONG).show();
    					    	  
    					    	  i.putExtra("vid", vid);
    						     	
    						     i.putExtra("thumbnail", thumbnail);
    					    	  
    					    	  activity.startActivity(i);
    							} else {
    								// status is empty, remove from view
    								Toast.makeText(activity, "Channel not scheduled to stream at this time", Toast.LENGTH_LONG).show();
    							}	   
    					      
    					      // notify data changes to list adapater
    					      //Toast.makeText(activity, vid.toString(), Toast.LENGTH_LONG).show();
    					      
    								  } catch (JSONException e) {
    								      e.printStackTrace();
    								  }
    				                   
    	                  
    	                    
    	                    
    	                }
    	            }
    	        }, new Response.ErrorListener() {

    	            public void onErrorResponse(VolleyError error) {
    	            	
    	       
    	                VolleyLog.d(TAG, "Error: " + error.getMessage());
    	                
    	                
    	             
    	                Toast.makeText(activity, "vid_error", Toast.LENGTH_LONG).show();  
    	              
    	            }
    	        });

    	// Adding request to volley request queue
    	AppController.getInstance().addToRequestQueue(jsObjRequest);
	            
				
			}
		});

		return convertView;
	}




	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	class Follow extends AsyncTask<String, String, String> {
    	 
    	String response = null; 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           
            Toast.makeText(activity, "Starting to react", Toast.LENGTH_LONG).show();
            
        }


        /**
         * Following
         * */
        protected String doInBackground(String... args) {
        	
        	
 
            // making fresh volley request and getting json
            
            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo3, params, new Response.Listener<JSONObject>() {

 			            public void onResponse(JSONObject response) {
 			            	
 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
 			                VolleyLog.d(TAG, "Response: " + response.toString());
 			                if (response != null) {
 			                	
 			                	
 			                	 // check for success tag
 			                    try {
 			                        int success = response.getInt(TAG_SUCCESS);
 			         
 			                        if (success == 1) {
 			                            // successfully created product
 			                            Toast.makeText(activity, "Successfully followed", Toast.LENGTH_LONG).show();
 			                           
         			         
 			                            
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
 			                
 			                
 			                Toast.makeText(activity, "Unable to write", Toast.LENGTH_LONG).show();
 			              
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
           

    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
    		  
    		

        }

 
}



	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

}