package com.integra.dealcaller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;
import com.integra.dealcaller.FeedListAdapter.ShareMethod;

public class FeedListAdapter2 extends BaseAdapter implements OnClickListener{	
	private Activity activity;
	private LayoutInflater inflater;
	private List<FeedItem2> feedItems;
	private static String postType;
	private SharedPreferences prefs;
	private static Map<String, String> params2;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = FeedListAdapter2.class.getSimpleName();
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/shared_help_post.php";
	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public FeedListAdapter2(Activity activity, List<FeedItem2> feedItems) {
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
		
		
		
		NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic2);
		//profilePic.setOnClickListener(this);
		
		Button Help = (Button) convertView.findViewById(R.id.ButtonComment2);
		Help.setOnClickListener(this);
		
		ImageButton next = (ImageButton) convertView.findViewById(R.id.next);
		next.setOnClickListener(this);
		
		Button share = (Button) convertView.findViewById(R.id.ButtonShare2);
		share.setOnClickListener(this);
		


		final FeedItem2 item = feedItems.get(position);

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
		

		profilePic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//Intent vc = new Intent(activity, CommitmentPageDetail.class);
		        
		           
	        	//vc.putExtra("cover_photo", item.getCoverPhoto());
	        	//vc.putExtra("name", item.getName());
	        	//vc.putExtra("holder_id", item.getHolder_ID());
	        	
	        	//vc.putExtra("status", item.getStatus());
	        	//vc.putExtra("verification", item.getVerificationStatus());
	        	//vc.putExtra("nesting_institution", item.getNestingInstitution());
	        	//vc.putExtra("photo1", item.getPhoto1());
	        	//vc.putExtra("photo2", item.getPhoto2());
	        	//vc.putExtra("photo3", item.getPhoto3());
	        	//vc.putExtra("photo4", item.getPhoto4());
	        	//vc.putExtra("photo5", item.getPhoto5());
	        	
	            //activity.startActivity(vc);

			
	           
				
			}
		});
		
		
		   
		   
		Help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				 
				Intent vc = new Intent(activity, Help.class);
	        	vc.putExtra("holder_id", item.getHolder_ID());
	            activity.startActivity(vc);
	            
				
			}
		});
		
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent vc = new Intent(activity, Help.class);
	        	vc.putExtra("holder_id", item.getHolder_ID());
	            activity.startActivity(vc);
	            
			
	           
				
			}
		});

		
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				postType = "SharedHelpPost";
				
				prefs = activity.getSharedPreferences("Chat", 0);
				
				
				String username = prefs.getString("username", null);
				String profile = prefs.getString("profile_pic", null);
				String email = prefs.getString("email", null);

	        	 // Building Parameters
	            params2 = new HashMap<String, String>();
	            
	            params2.put("name", username);
	         
	            params2.put("status", username+" shared "+item.getName()+"'s Help Request :"+item.getStatus());
	            params2.put("profilePic", profile);
	            params2.put("fromEmail", email);
	            params2.put("postType", postType);
	            params2.put("forUsername", item.getName());
	            params2.put("forEmail", item.getHolder_ID());

	            //Performing Share method
	            
	            new ShareMethod().execute();

	            
				
			}
		});


		return convertView;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		
        	
        default:
            break;
        }
	
    }

	public Object getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	
	 class ShareMethod extends AsyncTask<String, String, String> {
     	 
	    	String response = null; 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            
	            Toast.makeText(activity, "Starting to share", Toast.LENGTH_LONG).show();
	        }


	        /**
	         * Deleting product
	         * */
	        protected String doInBackground(String... args) {
	        	
	        		            // making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo3, params2, new Response.Listener<JSONObject>() {

	 			            public void onResponse(JSONObject response) {
	 			            	
	 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	 			                VolleyLog.d(TAG, "Response: " + response.toString());
	 			                if (response != null) {
	 			                	
	 			                	
	 			                	 // check for success tag
	 			                    try {
	 			                        int success = response.getInt(TAG_SUCCESS);
	 			         
	 			                        if (success == 1) {
	 			                            // successfully created product
	 			                            Toast.makeText(activity, "Successfully shared", Toast.LENGTH_LONG).show();
	 			                           
	         			         
	 			                            
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
}
}