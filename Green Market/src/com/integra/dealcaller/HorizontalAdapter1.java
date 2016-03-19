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

public class HorizontalAdapter1 extends BaseAdapter  {	
	private Activity activity;
	private LayoutInflater inflater;
	private List<HorizontalFeedItem1> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public HorizontalAdapter1(Activity activity, List<HorizontalFeedItem1> feedItems) {
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
			convertView = inflater.inflate(R.layout.horizontal_feed_item1, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.namehf);
	

		TextView timestamp = (TextView) convertView.findViewById(R.id.timestamphf);
		
		TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsghf);
		
		TextView url = (TextView) convertView.findViewById(R.id.txtUrl5);
	
		
		NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePichf);
		
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImagehf);
		

		HorizontalFeedItem1 item = feedItems.get(position);
		
		

		name.setText(item.getName());

		// Converting timestamp into x ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		timestamp.setText(timeAgo);

		// Chcek for empty status message
		if (!TextUtils.isEmpty(item.getStatus())) {
			statusMsg.setText(item.getStatus());
			statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			statusMsg.setVisibility(View.GONE);
		}

		// Checking for null feed url
		if (item.getUrl() != null) {
			url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
					+ item.getUrl() + "</a> "));

			// Making url clickable
			url.setMovementMethod(LinkMovementMethod.getInstance());
			url.setVisibility(View.VISIBLE);
		} else {
			// url is null, remove from the view
			url.setVisibility(View.GONE);
		}

		// user profile pic
		profilePic.setImageUrl(item.getProfilePic(), imageLoader);

		// Feed image
		if (item.getImge() != null) {
			feedImageView.setImageUrl(item.getImge(), imageLoader);
			feedImageView.setVisibility(View.VISIBLE);
			feedImageView
					.setResponseObserver(new FeedImageView.ResponseObserver() {
						public void onError() {
							
						}

						public void onSuccess() {
							
						}
					});
		} else {
			feedImageView.setVisibility(View.GONE);
		}
		
		
		return convertView;
	}


    

	public Object getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

}
