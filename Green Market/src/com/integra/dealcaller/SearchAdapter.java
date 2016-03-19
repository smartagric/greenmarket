package com.integra.dealcaller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;


public class SearchAdapter extends BaseAdapter implements OnClickListener{
	private Activity activity;
	private LayoutInflater inflater;
	private List<SearchFeed> searchItems;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = SearchAdapter.class.getSimpleName();
	private static Map<String, String> params;
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/send_friend_request.php";
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public SearchAdapter(Activity activity, List<SearchFeed> searchItems) {
		this.activity = activity;
		this.searchItems = searchItems;
	}

	
	

	public int getCount() {
		return searchItems.size();
	}

	public Object getItem(int location) {
		return searchItems.get(location);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.search_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnailsearch);
		thumbNail.setOnClickListener(this);
		
		TextView title = (TextView) convertView.findViewById(R.id.titlesearch);
		TextView rating = (TextView) convertView.findViewById(R.id.ratingsearch);
		TextView genre = (TextView) convertView.findViewById(R.id.genresearch);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYearsearch);
	
		
		ImageButton sendrequest = (ImageButton) convertView.findViewById(R.id.imageButton1000);
		sendrequest.setOnClickListener(this);

		// getting movie data for the row
		final SearchFeed m = searchItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
	
		
		// title
		title.setText(m.getTitle());
		
		
		
		if (m.getRating()>0) {
			rating.setText(String.valueOf(m.getRating())+"mutual friends");
			rating.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			rating.setVisibility(View.GONE);
		}
		
	
		
		
		if (!TextUtils.isEmpty(m.getGenre())) {
			genre.setText(String.valueOf(m.getGenre()));
			genre.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			genre.setVisibility(View.GONE);
		}
		
		
		if (!TextUtils.isEmpty(m.getYear())) {
			year.setText(String.valueOf(m.getYear()));
			year.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			year.setVisibility(View.GONE);
		}
		
		
		
		
		sendrequest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				PrefManager pp = new PrefManager(activity);

	        	 // Building Parameters
	            Map<String, String> params = new HashMap<String, String>();
	            params.put("from_username", pp.getUserName());
	            params.put("from_email", pp.getEmail());
	            params.put("to_username", m.getTitle());
	            params.put("to_email", m.getEmail());
	            
	           
	            new SendFriendRequest().execute();
				
			}
		});

		return convertView;
	}

	
	

class SendFriendRequest extends AsyncTask<String, String, String> {
    	 
    	String response = null; 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           
            
            
        }


        /**
         * Following
         * */
        protected String doInBackground(String... args) {
        	
        	
 
            // making fresh volley request and getting json
            
            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo3, params, new Response.Listener<JSONObject>() {

 			            public void onResponse(JSONObject response) {
 			            	
 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
 			                VolleyLog.d(TAG, "Response: " + response.toString());
 			                if (response != null) {
 			                	
 			                	
 			                	 // check for success tag
 			                    try {
 			                        int success = response.getInt(TAG_SUCCESS);
 			         
 			                        if (success == 1) {
 			                            // successfully created product
 			                            Toast.makeText(activity, "Successfully followed", Toast.LENGTH_LONG).show();
 			                           
         			         
 			                            
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

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
           

    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
    		  
    		

        }

 
}




@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	
}
}