package com.integra.dealcaller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

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
import android.view.GestureDetector;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;
import com.integra.dealcaller.FeedListAdapter.ViewHolder;


public class LineupListAdapter2 extends BaseAdapter implements OnClickListener {
	private Activity activity;
	private LayoutInflater inflater;
	private List<LineupMovie> movieItems;
	private SharedPreferences prefs;
	private RelativeLayout relate;
	private String username;
	public static RoundedImageVieww thumbNail;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = LineupListAdapter2.class.getSimpleName();
 
	private String h,n,s,c, t;


	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public LineupListAdapter2(Activity activity, List<LineupMovie> movieItems) {
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
		final GestureDetector mGestureDetector = new GestureDetector(activity, new MyGestureDetector());
		
		int type = getItemPos(position);
		
		
		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.lineup_row2, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		thumbNail = (RoundedImageVieww) convertView
				.findViewById(R.id.thumbnail00);
		TextView title = (TextView) convertView.findViewById(R.id.title00);
		
		relate = (RelativeLayout)convertView.findViewById(R.id.relate);
		TextView rating = (TextView) convertView.findViewById(R.id.rating00);
		TextView ticker = (TextView) convertView.findViewById(R.id.ticker00);
		
		
		
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
		
		
		//title.setTextColor(Color.parseColor("#" + m.getColor()));
	
		title.setText(m.getTitle());
	
		
		// rating
		rating.setText(String.valueOf(m.getLocation()));
		
		ticker.setText(m.getTicker());
		
		
		
			//now import ontouch listener into view
		
		thumbNail.setOnTouchListener(new OnTouchListener()
		 {

		     @Override
		     public boolean onTouch(View v, MotionEvent event)
		     {
		    	 
		    	 
					
		    	 h = m.getSME_id();
		    	 n = m.getTitle();
		    	 s = m.getLocation();
		    	 t = m.getTicker();
		    	 c = m.getThumbnailUrl();
					
			
		    	
		         return mGestureDetector.onTouchEvent(event);
		     }
		 });

		

		  
		return convertView;
	}
		


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

	private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
	 {
	     public boolean onSingleTapUp(MotionEvent e) {
	         // ---Call it directly---
	    	 
	    	 Intent iv = new Intent(activity, OneItemShop.class);
				
				iv.putExtra("photo", c);
				iv.putExtra("description", s);  
	        	iv.putExtra("price", t);
	        	iv.putExtra("sme_id", h);
	        	//iv.putExtra("chattingToDeviceID", m.getGCMREG());
	        	//iv.putExtra("cover_photo", c);
		
	            activity.startActivity(iv);
	            
	         return false;
	     }

	     public void onLongPress(MotionEvent e) {
	     }

	     public boolean onDoubleTap(MotionEvent e) {
	         return false;
	     }

	     public boolean onDoubleTapEvent(MotionEvent e) {
	         return false;
	     }

	     public boolean onSingleTapConfirmed(MotionEvent e) {
	         return false;

	     }

	     public void onShowPress(MotionEvent e) {
	         
	     }

	     public boolean onDown(MotionEvent e) {            
	         // Must return true to get matching events for this down event.
	         return true;
	     }

	     public boolean onScroll(MotionEvent e1, MotionEvent e2, final float distanceX, float distanceY) {
	         return super.onScroll(e1, e2, distanceX, distanceY);
	     }        

	     @Override
	     public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	         // do something
	         return super.onFling(e1, e2, velocityX, velocityY);
	     }
	 }

}