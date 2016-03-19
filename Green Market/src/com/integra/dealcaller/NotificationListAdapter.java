package com.integra.dealcaller;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnGenericMotionListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.integra.dealcaller.R;
import com.integra.dealcaller.FeedListAdapter2.ShareMethod;
import com.integra.dealcaller.ListViewAdapter.Holder;
import com.squareup.picasso.Picasso;


public class NotificationListAdapter extends BaseAdapter implements OnClickListener, OnTouchListener {	
	private Activity activity;
	private Resources res;
	private LayoutInflater inflater;
	private List<NotificationItem> feedItems;
	private List<Movie> movieList = new ArrayList<Movie>();
	private List<HorizontalFeedItem> feedItemx;
	private HorizontalFeedListAdapter hfi;
	private HorizontalListView listView_main;
	private MainInflateAdapter adapter_main;
	private static String[] dataObjects;
	private ViewFlipper viewFlipper;
	private static ViewFlipper viewFlipper2;
	private BaseAdapter mAdapter;
	static Bitmap b1,b2,b3,b4,b5;
	private ViewHolder viewHolder;
	private int position;
	private String foto = null;
	private String foto2 = null;
	private String foto3 = null;
	private LruBitmapCache imageCache;

	private String URL_FEED5 = "http://activetalkgh.com/ads/1.png";
	private static final String URL_FEED2 = "http://activetalkgh.com/talkers/prayer_religion.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = NotificationListAdapter.class.getSimpleName();
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/shared_post.php";
	public static final String url_share_photo4 = "http://activetalkgh.com/android_connect/expressing.php";
	private static String postType;
	
	private String URL_FEED = "http://activetalkgh.com/android_connect/commitment_page_detail2.php";
	
    
	private static String ExpressionType1;
	private static String ExpressionType2;
	private static String ExpressionType3;
	private static String ExpressionType4;
	private static String ExpressionType5;
	
	private static Map<String, String> params2;
	
	private static Map<String, String> paramsExp1;
	private static Map<String, String> paramsExp2;
	private static Map<String, String> paramsExp3;
	private static Map<String, String> paramsExp4;
	private static Map<String, String> paramsExp5;
	
	private static String forEmail;
	private static String forUsername;
	public static String covery = null;
	
	private int imageArra[] = { R.drawable.talkpage_icon, R.drawable.talker1,
			R.drawable.bill, R.drawable.talker1,
			R.drawable.bill, R.drawable.home,
			R.drawable.house, R.drawable.bill };
  
    

	float lastX = 0;
    
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	
	public NotificationListAdapter() {
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
	

	public NotificationListAdapter(Activity activity, List<NotificationItem> feedItems) {
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
	
	public int getItemPos(int position) {
		return position;
	}


	 //public int getViewTypeCount() {
		 //return 3;
	 //}

	 
	 
	
	 
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		
		int type = getItemPos(position);
		  
			
			if (inflater == null)
				inflater = (LayoutInflater) activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null)
				convertView = inflater.inflate(R.layout.notifications_row, null);


		if (imageLoader == null) 
			imageLoader = AppController.getInstance().getImageLoader();
		
		viewHolder = new ViewHolder();

		viewHolder.name = (TextView) convertView.findViewById(R.id.name4noti);
		viewHolder.name.setOnClickListener(this);

		viewHolder.timestamp = (TextView) convertView.findViewById(R.id.timestamp4noti);
		
		
		
		viewHolder.statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg4noti);
		
		
		viewHolder.profilePic2 = (NetworkImageView) convertView.findViewById(R.id.profilePic44noti);
		viewHolder.profilePic2.setOnClickListener(this);
		
		viewHolder.profilePic = (RoundedImageVieww) convertView.findViewById(R.id.profilePic4noti);
		viewHolder.profilePic.setOnClickListener(this);
		
		
		
		convertView.setTag(viewHolder);
		
		imageCache = new LruBitmapCache();
		final NotificationItem item = feedItems.get(position);
		
		
		
		viewHolder.name.setText(item.getName());
		
		

		// Converting timestamp into x ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		viewHolder.timestamp.setText(timeAgo);

		// Chcek for empty status message
		if (!TextUtils.isEmpty(item.getStatus())) {
			viewHolder.statusMsg.setText(item.getStatus());
			viewHolder.statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			viewHolder.statusMsg.setVisibility(View.GONE);
		}
		
		
		
		
		
		
		// user profile pic
		viewHolder.profilePic.setImageUrl(item.getProfilePic(), imageLoader);
		
		
		// Feed image
				if(item.getImge() == null){
		        	
					
					
					viewHolder.profilePic2.setVisibility(View.GONE);
					
					 
		        }else{
		      
		        	//viewHolder.profilePic2.setImageUrl(item.getImge(), imageLoader);
		        	//viewHolder.profilePic2.setVisibility(View.VISIBLE);
			
		        
		        }

		
		
		
		
		
		
		
		viewHolder.profilePic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

	        	//Intent i = new Intent(activity, FriendsProfile.class);

	            
	        	//i.putExtra("foto3", item.getProfilePic());
	        	
	        	//i.putExtra("covery", item.getProfilePic());
	        	
	        	//i.putExtra("namey", item.getName());
	        	
	            //activity.startActivity(i);
	          
	            
	        
	           
				
			}
		});
		
		
		
		
		  
		viewHolder = (ViewHolder)convertView.getTag();
		 //ends here
		return convertView;
	}

	
	
	
	
	
	

    public void onClick(View v) {
    	
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

	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product deleted
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}
	 
	 
	 ///Expression1 asynctask
	 
	 class Expression1_method extends AsyncTask<String, String, String> {
     	 
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
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp1, new Response.Listener<JSONObject>() {

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

	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product deleted
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}

	 
	 ///Expression2 Async task
	 
	 class Expression2_method extends AsyncTask<String, String, String> {
     	 
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
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp2, new Response.Listener<JSONObject>() {

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

	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product deleted
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}

	 
	 ///Expression3 async task
	 
	 class Expression3_method extends AsyncTask<String, String, String> {
     	 
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
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp3, new Response.Listener<JSONObject>() {

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

	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product deleted
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}

	 
	 ///Expression4 AsyncTask
	 
	 class Expression4_method extends AsyncTask<String, String, String> {
     	 
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
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp4, new Response.Listener<JSONObject>() {

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

	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product deleted
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}

	 
	 ///Expression5 AsyncTask
	 
	 class Expression5_method extends AsyncTask<String, String, String> {
     	 
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
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp5, new Response.Listener<JSONObject>() {

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

	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product deleted
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}

	 /* Simple class for multi-type content */
	    public class Content {
	        private static final int TYPE_TEXT = 0;
	        private static final int TYPE_IMAGESET = 1;
	        private static final int TYPE_COUNT = TYPE_IMAGESET + 1;
	        private String mString;
	        private int mType;
	        
	        public Content(String s, int type) {
	            mString = s;
	            mType = type;
	        }
	        
	        
	        public String getString() { return mString; }
	        public int getType() { return mType; }
	    }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	static class ViewHolder {
     
         TextView timestamp;
         TextView name;
         
         TextView statusMsg;
    
         NetworkImageView profilePic2;
         RoundedImageVieww profilePic;
        
         
    }



	


	
	private void setFlipperImage(ArrayList<String> actorsList) {

	   


		 for(int i=0;i<actorsList.size();i++){
		 Log.i("Set Filpper Called", actorsList.get(i).toString()+"");
		 ImageView image = new ImageView(activity);
		// image.setBackgroundResource(res);
		Picasso.with(activity)
		.load(actorsList.get(i).toString())
		.placeholder(R.drawable.ic_launcher)
		.error(R.drawable.ic_launcher)
		.into(image);
		 viewFlipper2.addView(image);
		 }
		}

	 //ParseJsonFeedd
    private void parseJsonFeedd(JSONObject response) {
        try {
       	 
       	 movieList.clear();
       	 adapter_main.notifyDataSetChanged();
       	 
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                Movie movie = new Movie();
                movie.setTitle(feedObj.getString("title"));
                movie.setThumbnailUrl(feedObj.getString("image"));
              
                movie.setYear(feedObj.getString("groupType"));
                movie.setEmail(feedObj.getString("email"));
             
                
             // rating might be null sometimes
                int rat = feedObj.isNull("rating") ? null : feedObj
                        .getInt("rating");
                
                
                movie.setRating(rat);

             // Genre might be null sometimes
                String gen = feedObj.isNull("genre") ? null : feedObj
                        .getString("genre");
                
                
                movie.setGenre(gen);

                // adding movie to movies array
                movieList.add(movie);

            }
 
            // notify data changes to list adapater
            adapter_main.notifyDataSetChanged();
            //pb.setVisibility(View.GONE);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
 	
	}

}
