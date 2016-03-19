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

public class CustomListAdapter00 extends BaseAdapter implements OnClickListener {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Movie00> movieItems;
	private SharedPreferences prefs;
	private RelativeLayout relate;
	private String username;
	public static NetworkImageView3 thumbNail;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = CustomListAdapter00.class.getSimpleName();



	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapter00(Activity activity, List<Movie00> movieItems) {
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
			convertView = inflater.inflate(R.layout.list_row00, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		thumbNail = (NetworkImageView3) convertView
				.findViewById(R.id.thumbnail00);
		TextView title = (TextView) convertView.findViewById(R.id.title00);
		
		relate = (RelativeLayout)convertView.findViewById(R.id.relate);
		TextView rating = (TextView) convertView.findViewById(R.id.rating00);
		
		prefs = activity.getSharedPreferences("Chat", 0);

		username = prefs.getString("username", null);
	

		// getting movie data for the row
		final Movie00 m = movieItems.get(position);

		
					
					// thumbnail image
					thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
				
		
	
		
		// title
		title.setText(m.getTitle());
		
		// rating
		rating.setText("Mutual Friends:" + String.valueOf(m.getRating()));
		

		
		thumbNail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				thumbNail.destroyDrawingCache();
				thumbNail.buildDrawingCache();
				
				Intent iv = new Intent(activity, ChatActivity2.class);
 		        
	 	           
	        	iv.putExtra("chattingFrom", "Junior");
	        	iv.putExtra("chattingToName", m.getTitle());
	        	iv.putExtra("chattingToDeviceID", m.getGCMREG());
	        	iv.putExtra("little_icon", m.getThumbnailUrl());
		
	            activity.startActivity(iv);
			}
		});

		relate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				thumbNail.destroyDrawingCache();
				thumbNail.buildDrawingCache();
				
				Intent iv = new Intent(activity, ChatActivity2.class);
 		        
	 	           
	        	iv.putExtra("chattingFrom", "Junior");
	        	iv.putExtra("chattingToName", m.getTitle());
	        	iv.putExtra("chattingToDeviceID", m.getGCMREG());
	        	iv.putExtra("little_icon", m.getThumbnailUrl());
		
	            activity.startActivity(iv);
			}
		});



		return convertView;
	}




	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	


}