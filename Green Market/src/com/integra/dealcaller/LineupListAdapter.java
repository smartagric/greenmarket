package com.integra.dealcaller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;
import com.integra.dealcaller.FeedListAdapter.ViewHolder;

public class LineupListAdapter extends BaseAdapter implements OnClickListener, OnTouchListener{
	private Activity activity;
	private LayoutInflater inflater;
	private List<LineupMovie> movieItems;
	private SharedPreferences prefs;
	private static final String URL_FEED2 = "http://activetalkgh.com/android_connect/trending.php";
	private RelativeLayout relate;
	private String username;
	public static HorizontalListView listView_main;
	public static RoundedImageVieww thumbNail;
	private static LineupListAdapter2 adapter_main;
	private static ProgressBar pb2;
	private JsonObjectRequest jsonReq2;
	private ConnectionDetector cd;
	static Boolean isInternetPresent = false;
	 private  static List<LineupMovie> movieList2;
	   
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = LineupListAdapter.class.getSimpleName();



	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public LineupListAdapter(Activity activity, List<LineupMovie> movieItems) {
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
	
	
	public int getItemPos(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		
		int type = getItemPos(position);
		
		
		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		cd = new ConnectionDetector(activity);
		
		if (type == 0) {
			 
			  
		  	
			if (imageLoader == null)
				imageLoader = AppController.getInstance().getImageLoader();

				
		    convertView = inflater.inflate(R.layout.horizontal_inflate, parent, false);
		    
		    adapter_main = new LineupListAdapter2(activity,movieList2);
	         listView_main = (HorizontalListView)convertView.findViewById(R.id.listee_main2);
				final TextView noInternet = (TextView)convertView.findViewById(R.id.no_internet2);
				final TextView  noResults = (TextView)convertView.findViewById(R.id.no_results2);
				 movieList2 = new ArrayList<LineupMovie>();
		        pb2=(ProgressBar)convertView.findViewById(R.id.progressee);
		        
		        listView_main.setAdapter(adapter_main);  
		        
		    listView_main.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					LineupFragment.over.getParent().requestDisallowInterceptTouchEvent(true);
					return false;
				}
			});
		    
		    pb2.setProgress(0);
			
			noInternet.setOnClickListener(this);
			noResults.setOnClickListener(this);
			
	
			 // We first check for cached request
	      com.android.volley.Cache cache2 = AppController.getInstance().getRequestQueue().getCache();
	      Entry entry2 = cache2.get(URL_FEED2);
	      if (entry2 != null) {
	          // fetch the data from cache
	          try {
	              String data = new String(entry2.data, "UTF-8");
	              try {
	              	
	              	movieList2.clear();
	             	 	adapter_main.notifyDataSetChanged();
	                  parseJsonFeed2(new JSONObject(data));
	              } catch (JSONException e) {
	                  e.printStackTrace();
	              }
	          } catch (UnsupportedEncodingException e) {
	              e.printStackTrace();
	          }

	      } 
	   
	   
			
			 // Creating volley request obj
	    // making fresh volley request and getting json
	        jsonReq2 = new JsonObjectRequest(Method.GET,
	               URL_FEED2, null, new Response.Listener<JSONObject>() {

	                   public void onResponse(JSONObject response) {
	                   	
	                   	//Toast.makeText(getActivity(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	                       VolleyLog.d(TAG, "Response: " + response.toString());
	                       if (response != null) {
	                       	
	                           parseJsonFeed2(response);
	                        
	                           
	                           
	                       }
	                   }
	               }, new Response.ErrorListener() {

	                   public void onErrorResponse(VolleyError error) {
	                  	 
	                  	
	                       pb2.setVisibility(View.GONE);
	                    
			         
			                
			             // get Internet status
			                isInternetPresent = cd.isConnectingToInternet();

			                // check for Internet status
			                if (isInternetPresent) {
			                    // Internet Connection is Present
			                    // make HTTP requests
			                	
	 
			                        noResults.setVisibility(View.VISIBLE);
			                        
			                } else {
			                    // Internet connection is not present
			                    // Ask user to connect to Internet
			                	
			                	noInternet.setVisibility(View.VISIBLE);
			                }
	                       

	                   }
	               });

	       // Adding request to volley request queue
	       AppController.getInstance().addToRequestQueue(jsonReq2);
		    
		    
			
	 } else{
		 convertView = inflater.inflate(R.layout.lineup_row, null);
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		thumbNail = (RoundedImageVieww) convertView
				.findViewById(R.id.thumbnail00);
		TextView title = (TextView) convertView.findViewById(R.id.title00);
		
		relate = (RelativeLayout)convertView.findViewById(R.id.relate);
		TextView rating = (TextView) convertView.findViewById(R.id.rating00);
		TextView ticker = (TextView) convertView.findViewById(R.id.ticker00);
		TextView day = (TextView) convertView.findViewById(R.id.day00);
		TextView title_init = (TextView) convertView.findViewById(R.id.titleinitials00);
		
		prefs = activity.getSharedPreferences("Chat", 0);

		username = prefs.getString("username", null);
	

		// getting movie data for the row
		final LineupMovie m = movieItems.get(position);

		
					
					// thumbnail image
					//thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
				
		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
		//viewHolder.thumbNail.setBackgroundColor(Color.parseColor("#f95c17"));
		
		
		// title
		//viewHolder.title.setTextColor(Color.parseColor(viewHolder.randomColorName));
	
		//String initials = m.getTitle().substring(0, 2);
		//title_init.setText(initials);
		
		//title.setTextColor(Color.parseColor("#639e31"));
	
		title.setText(m.getTitle());
	
		
		// rating
		rating.setText(String.valueOf(m.getLocation()));
		
		ticker.setText(m.getTicker());
		
		
		

		
		thumbNail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				//thumbNail.destroyDrawingCache();
				//thumbNail.buildDrawingCache();
				
				Intent iv = new Intent(activity, CommitmentPageDetail2.class);
				
				iv.putExtra("holder_id", m.getSME_id());
				iv.putExtra("name", m.getTitle());  
	        	iv.putExtra("service", m.getLocation());
	        	//iv.putExtra("chattingToName", m.getTitle());
	        	//iv.putExtra("chattingToDeviceID", m.getGCMREG());
	        	iv.putExtra("cover_photo", m.getThumbnailUrl());
		
	            activity.startActivity(iv);
			}
		});

		relate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				

				//thumbNail.destroyDrawingCache();
				//thumbNail.buildDrawingCache();
				
				Intent iv = new Intent(activity, CommitmentPageDetail2.class);
				
				iv.putExtra("holder_id", m.getSME_id());
				iv.putExtra("name", m.getTitle());  
				iv.putExtra("service", m.getLocation());
	        	//iv.putExtra("chattingToName", m.getTitle());
	        	//iv.putExtra("chattingToDeviceID", m.getGCMREG());
	        	iv.putExtra("cover_photo", m.getThumbnailUrl());
		
	            activity.startActivity(iv);
			}
		});

	 }
		  
		return convertView;
	}
		
	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	 private static void parseJsonFeed2(JSONObject response) {
         try {
        	 
        	 movieList2.clear();
        	 adapter_main.notifyDataSetChanged();
        	 
             JSONArray feedArray = response.getJSONArray("feed");
  
             for (int i = 0; i < feedArray.length(); i++) {
                 JSONObject feedObj = (JSONObject) feedArray.get(i);
  
                 LineupMovie movie = new LineupMovie();
                 movie.setTitle(feedObj.getString("product_name"));
                 
                 // Profile pic might be null sometimes
                    String prof = feedObj.isNull("profilePic") ? null : feedObj
                            .getString("profilePic");
                   
                   
                 
                    movie.setThumbnailUrl(prof);
                    
                    String sme_id = feedObj.isNull("sme_id") ? null : feedObj
                            .getString("sme_id");
                 
                    movie.setSME_id(sme_id);
                    
                    
                    movie.setLocation((feedObj.getString("description")));
                   
                    
                 
                    
                    movie.setTicker(feedObj.getString("price"));

                 // adding movie to movies array
                 
                 
                 movieList2.add(movie);

             }
  
             // notify data changes to list adapater
             
            
             adapter_main.notifyDataSetChanged();
             listView_main.refreshDrawableState();
             
             pb2.setVisibility(View.GONE);
          
             
         } catch (JSONException e) {
             e.printStackTrace();
         }
     
  	
 	}




	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}


}