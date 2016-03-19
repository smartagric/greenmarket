package com.integra.dealcaller;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;

public class TalkerListAdapter extends BaseAdapter implements OnClickListener {	
	private Activity activity;
	private LayoutInflater inflater;
	private List<TalkerItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public TalkerListAdapter(Activity activity, List<TalkerItem> feedItems) {
		this.activity = activity;
		this.feedItems = feedItems;
	}


	public int getCount() {
		return feedItems.size();
	}

	
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	public long getItemId(int position) {
		return position;
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.talker_profile_layout, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.namen);
		name.setOnClickListener(this);

		TextView timestamp = (TextView) convertView.findViewById(R.id.timestampn);
		
		TextView mobile = (TextView) convertView.findViewById(R.id.mobile);
		
		TextView work = (TextView) convertView.findViewById(R.id.worker);
		
		TextView school = (TextView) convertView.findViewById(R.id.schooler);
		
		TextView email = (TextView) convertView.findViewById(R.id.emailer);
		
		
		TextView residence = (TextView) convertView.findViewById(R.id.residence);
		
		TextView country = (TextView) convertView.findViewById(R.id.country);
		
		
		
		final TalkerItem item = feedItems.get(position);
		
		
		
		
		// Check for empty verification status
				if (!TextUtils.isEmpty(item.getUsername())) {
					name.setText(item.getUsername());
					name.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					name.setText("not updated yet");
				}

				
				
				// Check for empty verification status
				if (!TextUtils.isEmpty(item.getTimeStamp())) {
					//Converting timestamp into x ago format
					CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
							Long.parseLong(item.getTimeStamp()),
							System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
					timestamp.setText(timeAgo);
				} else {
					// status is empty, remove from view
					timestamp.setVisibility(View.GONE);
				}
				
				
				// Check for empty verification status
				if (!TextUtils.isEmpty(item.getMobile())) {
					mobile.setText(item.getMobile());
					mobile.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					mobile.setText("not updated yet");
				}
	
				// Check for empty verification status
				if (!TextUtils.isEmpty(item.getEmail())) {
					email.setText(item.getEmail());
					email.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					email.setText("not updated yet");
				}

				
				// Check for empty verification status
				if (!TextUtils.isEmpty(item.getSchool())) {
					school.setText(item.getSchool());
					school.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					school.setText("not updated yet");
				}
				
				
				// Check for empty verification status
				if (!TextUtils.isEmpty(item.getWork())) {
					work.setText(item.getWork());
					work.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					work.setText("not updated yet");
				}

				
				// Check for empty verification status
				if (!TextUtils.isEmpty(item.getResidence())) {
					residence.setText(item.getResidence());
					residence.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					residence.setText("not updated yet");
				}
				
				
				
				// Check for empty verification status
				if (!TextUtils.isEmpty(item.getCountry())) {
					country.setText(item.getCountry());
					country.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					country.setText("not updated yet");
				}
				

		

	
		
			
			

		
		
			
						



		
		
		return convertView;
	}

	
	

    

	public Object getFilter() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
