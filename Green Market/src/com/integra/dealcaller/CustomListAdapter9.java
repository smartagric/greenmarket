package com.integra.dealcaller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;

public class CustomListAdapter9 extends BaseAdapter implements OnClickListener {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Movie9> movieItems;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = CustomListAdapter9.class.getSimpleName();
	private static Map<String, String> params;
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/followed_talkers.php";

	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapter9(Activity activity, List<Movie9> movieItems) {
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
			convertView = inflater.inflate(R.layout.list_row9, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail9);
		thumbNail.setOnClickListener(this);
		
		TextView title = (TextView) convertView.findViewById(R.id.title9);
		TextView rating = (TextView) convertView.findViewById(R.id.rating9);
		TextView genre = (TextView) convertView.findViewById(R.id.genre9);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear9);
		
		
		Button follow = (Button) convertView.findViewById(R.id.imageButtonfollow9);
		follow.setOnClickListener(this);



		
	

		// getting movie data for the row
		final Movie9 m = movieItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
	
		
		// title
		title.setText(m.getTitle());
		
		// Check for empty rating 
		if (m.getRating()>0) {
			rating.setText(String.valueOf(m.getRating()+" followers"));
			rating.setVisibility(View.VISIBLE);
		} else {
			rating.setVisibility(View.GONE);
		}
	
		
// Chcek for empty genre
		if (!TextUtils.isEmpty(m.getGenre())) {
			genre.setText(String.valueOf(m.getGenre()));
			genre.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			genre.setVisibility(View.GONE);
		}

		// release year
		year.setText(String.valueOf(m.getYear()));
		
		
		
		thumbNail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				Intent i = new Intent(activity, TalkerProfile.class);

		         
		     	i.putExtra("foto3", m.getThumbnailUrl());
		     	
		     	i.putExtra("covery", m.getThumbnailUrl());
		     	
		     	i.putExtra("namey", m.getTitle());
		     	
		     	i.putExtra("email", m.getEmail());
		     	
		         activity.startActivity(i);
	            
				
			}
		});


		follow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				PrefManager pp = new PrefManager(activity);

	        	 // Building Parameters
	            params = new HashMap<String, String>();
	            params.put("title", m.getTitle());
	            params.put("final_image", m.getThumbnailUrl());
	            params.put("email", m.getEmail());
	            params.put("rating", m.getYear());
	            params.put("genre", m.getGenre());
	            params.put("follower", pp.getEmail());
	            
	           
	            new Follow().execute();
				
			}
		});

		
		
		

		return convertView;
	}



	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	class Follow extends AsyncTask<String, String, String> {
   	 
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
}