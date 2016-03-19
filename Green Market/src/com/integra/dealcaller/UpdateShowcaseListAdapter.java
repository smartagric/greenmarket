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

public class UpdateShowcaseListAdapter extends BaseAdapter implements OnClickListener {	
	private Activity activity;
	private LayoutInflater inflater;
	private List<UpdateShowcaseItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public UpdateShowcaseListAdapter(Activity activity, List<UpdateShowcaseItem> feedItems) {
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
			convertView = inflater.inflate(R.layout.nesting_layout, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.namen);
		name.setOnClickListener(this);

		TextView timestamp = (TextView) convertView.findViewById(R.id.timestampn);
		
		TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsgn);
		
		TextView verificationStatus = (TextView) convertView.findViewById(R.id.VerificationStatus);
		
		TextView nestingInstitution = (TextView) convertView.findViewById(R.id.NestingInstitution);
		
		TextView url = (TextView) convertView.findViewById(R.id.txtUrln);
		url.setOnClickListener(this);
		
		NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePicn);
		profilePic.setOnClickListener(this);
		
		NetworkImageView profilePic2 = (NetworkImageView) convertView.findViewById(R.id.profilePicn01);
		profilePic2.setOnClickListener(this);
		
		NetworkImageView profilePic3 = (NetworkImageView) convertView.findViewById(R.id.profilePicn02);
		profilePic3.setOnClickListener(this);
		
		NetworkImageView profilePic4 = (NetworkImageView) convertView.findViewById(R.id.profilePicn03);
		profilePic4.setOnClickListener(this);
		
		NetworkImageView profilePic5 = (NetworkImageView) convertView.findViewById(R.id.profilePicn04);
		profilePic5.setOnClickListener(this);
		
		
		final UpdateShowcaseItem item = feedItems.get(position);
		
		

		name.setText(item.getName());
		nestingInstitution.setText(item.getNesting());
		timestamp.setText(item.getTimeStamp());
		statusMsg.setText(item.getStatus());
		verificationStatus.setText(item.getVerify());
		nestingInstitution.setText(item.getNesting());
		url.setText(item.getUrl());
		
		
		
		
		
		// Check for empty verification status
				if (!TextUtils.isEmpty(item.getVerify())) {
					verificationStatus.setText(item.getVerify());
					verificationStatus.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					verificationStatus.setVisibility(View.GONE);
				}

		

		// Converting timestamp into x ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		timestamp.setText(timeAgo);

	
		
			statusMsg.setText(item.getStatus());
			

		
		
			
						// Check for empty profilePic
						if (!TextUtils.isEmpty(item.getProfilePic())) {
						profilePic.setImageUrl(item.getProfilePic(), imageLoader);
						profilePic.setVisibility(View.VISIBLE);
						} else {
							// status is empty, remove from view
						profilePic.setVisibility(View.GONE);
						}
	
						// Check for empty profilePic
						if (!TextUtils.isEmpty(item.getProfilePic2())) {
							profilePic2.setImageUrl(item.getProfilePic2(), imageLoader);
							profilePic2.setVisibility(View.VISIBLE);
						} else {
							// status is empty, remove from view
							profilePic2.setVisibility(View.GONE);
						}
							
		
						// Check for empty profilePic
						if (!TextUtils.isEmpty(item.getProfilePic3())) {
							profilePic3.setImageUrl(item.getProfilePic3(), imageLoader);
							profilePic3.setVisibility(View.VISIBLE);
						} else {
							// status is empty, remove from view
							profilePic3.setVisibility(View.GONE);
						}
				
						
						// Check for empty profilePic
						if (!TextUtils.isEmpty(item.getProfilePic4())) {
							profilePic4.setImageUrl(item.getProfilePic4(), imageLoader);
							profilePic4.setVisibility(View.VISIBLE);
						} else {
							// status is empty, remove from view
							profilePic4.setVisibility(View.GONE);
						}
						
						
						

						
						// Check for empty profilePic
						if (!TextUtils.isEmpty(item.getProfilePic5())) {
							profilePic5.setImageUrl(item.getProfilePic(), imageLoader);
							profilePic5.setVisibility(View.VISIBLE);
						} else {
							// status is empty, remove from view
							profilePic5.setVisibility(View.GONE);
						}
		
						
			profilePic.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								

					        	Intent i = new Intent(activity, ImageViewer.class);
					        
					           
					        	i.putExtra("foto", item.getProfilePic());
						
					            activity.startActivity(i);
					            
					           
					            
								
							}
						});
			
			
			profilePic2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					

		        	Intent i = new Intent(activity, ImageViewer.class);
		        
		           
		        	i.putExtra("foto", item.getProfilePic2());
			
		            activity.startActivity(i);
		            
		           
		            
					
				}
			});

			
profilePic3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					

		        	Intent i = new Intent(activity, ImageViewer.class);
		        
		           
		        	i.putExtra("foto", item.getProfilePic3());
			
		            activity.startActivity(i);
		            
		           
		            
					
				}
			});


profilePic4.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		

    	Intent i = new Intent(activity, ImageViewer.class);
    
       
    	i.putExtra("foto", item.getProfilePic4());

        activity.startActivity(i);
        
       
        
		
	}
});


profilePic5.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		

    	Intent i = new Intent(activity, ImageViewer.class);
    
       
    	i.putExtra("foto", item.getProfilePic5());

        activity.startActivity(i);
        
       
        
		
	}
});



		
		
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
