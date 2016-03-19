package com.integra.dealcaller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;


public class MyTalkersListAdapter extends BaseAdapter implements OnClickListener {
	private Activity activity;
	private LayoutInflater inflater;
	private List<MovieMyTalkers> movieItems;
	private RelativeLayout relate;
	 TextView title, title_init;
     TextView genre;
     RoundedImageVieww thumbNail;
     String[] colorArray;
     String randomColorName;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public MyTalkersListAdapter(Activity activity, List<MovieMyTalkers> movieItems) {
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
		if (convertView == null){
			convertView = inflater.inflate(R.layout.mytalkers_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
	
		relate = (RelativeLayout)convertView.findViewById(R.id.thumbrelate);
		thumbNail = (RoundedImageVieww) convertView
				.findViewById(R.id.thumbnailmytalkers);
		title = (TextView) convertView.findViewById(R.id.titlemytalkers);
		
		title_init = (TextView) convertView.findViewById(R.id.titleinitials);
		
		genre = (TextView) convertView.findViewById(R.id.genremytalkers);
		
	

		

		
		
			
			// getting movie data for the row
			MovieMyTalkers m = movieItems.get(position);
			
			//Random random = new Random();
			//viewHolder.colorArray = activity.getResources().getStringArray(R.array.color_array); 
			
			
			//viewHolder.randomColorName = viewHolder.colorArray[random.nextInt(viewHolder.colorArray.length)];
			
			
				// thumbnail image
				thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
				//viewHolder.thumbNail.setBackgroundColor(Color.parseColor("#f95c17"));
				
				
				// title
				//viewHolder.title.setTextColor(Color.parseColor(viewHolder.randomColorName));
			
				String initials = m.getTitle().substring(0, 2);
				title_init.setText(initials);
				
			title.setTextColor(Color.parseColor("#" + m.getColor()));
			
			title.setText(m.getTitle());
			
			// rating
			
			
			// genre
			
			genre.setText(String.valueOf(m.getGenre()));
			
			// release year


	
		}
		else
	    {
	        /* We recycle a View that already exists */
	        
	    }
		
		
	
		thumbNail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent vv = new Intent(activity, Notifications.class);
				activity.startActivity(vv);
			}
		});

		
		relate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				
				Intent iv = new Intent(activity, Notifications.class);
	            activity.startActivity(iv);
			}
		});
		
		return convertView;
	}



	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}