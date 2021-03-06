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
import android.view.View;
import android.view.View.OnClickListener;
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

public class SearchListAdapter extends BaseAdapter implements OnClickListener {
	private Activity activity;
	private LayoutInflater inflater;
	private List<LineupMovie> movieItems;
	private SharedPreferences prefs;
	private static final String URL_FEED2 = "http://activetalkgh.com/android_connect/smes.php";
	private RelativeLayout relate;
	private String username;
	private HorizontalListView listView_main;
	public static RoundedImageVieww thumbNail;
	private static LineupListAdapter2 adapter_main;
	private static ProgressBar pb2;
	private JsonObjectRequest jsonReq2;
	private ConnectionDetector cd;
	static Boolean isInternetPresent = false;
	 private  static List<LineupMovie> movieList2;
	   
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = SearchListAdapter.class.getSimpleName();



	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public SearchListAdapter(Activity activity, List<LineupMovie> movieItems) {
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
	
		String initials = m.getTitle().substring(0, 2);
		title_init.setText(initials);
		
		title.setTextColor(Color.parseColor("#639e31"));
	
		title.setText(m.getTitle());
	
		
		// rating
		rating.setText(String.valueOf(m.getLocation()));
		
		ticker.setText(m.getTicker());
		
		//day.setBackgroundColor(Color.parseColor("#" + m.getColor()));
		day.setText(m.getDay());
		

		
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
                 movie.setTitle(feedObj.getString("channel_name"));
                 
              // Profile pic might be null sometimes
                 String prof = feedObj.isNull("profilePic") ? null : feedObj
                         .getString("profilePic");
                
                 String col = feedObj.isNull("color_string") ? null : feedObj
                         .getString("color_string");
              
                 movie.setThumbnailUrl(prof);
                 
                 String sme_id = feedObj.isNull("sme_id") ? null : feedObj
                         .getString("sme_id");
              
                 movie.setSME_id(sme_id);
                 
                 
                 movie.setLocation((feedObj.getString("service")));
                
                 movie.setColor(col);
              
                 
                 movie.setTicker(feedObj.getString("ticker"));
                 
                 movie.setDay(feedObj.getString("day"));

                 // adding movie to movies array
                 
                 
                 movieList2.add(movie);

             }
  
             // notify data changes to list adapater
             
            
             adapter_main.notifyDataSetChanged();
             
             pb2.setVisibility(View.GONE);
          
             
         } catch (JSONException e) {
             e.printStackTrace();
         }
     
  	
 	}


}