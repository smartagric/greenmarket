package com.integra.dealcaller;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;

public class HorizontalAdapter3 extends BaseAdapter implements OnClickListener{	
	private Activity activity;
	private LayoutInflater inflater;
	private List<FeedItem2> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public HorizontalAdapter3(Activity activity, List<FeedItem2> feedItems) {
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
			convertView = inflater.inflate(R.layout.feed_item2, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.name2);
		name.setOnClickListener(this);

		TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp2);
		
		TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg2);
		
		TextView url = (TextView) convertView.findViewById(R.id.txtUrl2);
		url.setOnClickListener(this);
		
		NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic2);
		profilePic.setOnClickListener(this);
		
		Button comment = (Button) convertView.findViewById(R.id.ButtonComment2);
		comment.setOnClickListener(this);
		
		Button share = (Button) convertView.findViewById(R.id.ButtonShare2);
		share.setOnClickListener(this);
		


		FeedItem2 item = feedItems.get(position);

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

		
		// user profile pic
		profilePic.setImageUrl(item.getProfilePic(), imageLoader);

		
		

		return convertView;
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.profilePic2) {
			Intent dashboard = new Intent(activity, CommitmentPageDetail.class);
			activity.startActivity(dashboard);
		} else if (id == R.id.ButtonComment2) {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			// get the layout inflater
            LayoutInflater inflater = activity.getLayoutInflater();
			// inflate and set the layout for the dialog
            // pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.comment_dialog, null))
             
            // action buttons
            .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // your sign in code here
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // remove the dialog from the screen
                }
            })
            .show();
		} else if (id == R.id.ButtonShare2) {
			Intent dashboard5 = new Intent(activity, ImageViewer.class);
			activity.startActivity(dashboard5);
		} else {
		}
	
    }

	public Object getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

}