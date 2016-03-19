package com.integra.dealcaller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;

public class CustomListAdapter11 extends BaseAdapter implements OnClickListener {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Movie11> movieItems;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = CustomListAdapter11.class.getSimpleName();
	private static Map<String, String> params;
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/followed_talkers.php";
	private static final String URL_FEED2 = "http://activetalkgh.com/talkers/lifestyle_entertainment.php";
	
	private List<Movie11b> movieListb = new ArrayList<Movie11b>();
    private List<Movie11c> movieListc = new ArrayList<Movie11c>();
    static JsonObjectRequest jsonReq2,jsonReq3;
    
    private HorizontalListView listView_b,listView_c,listView_d ;
    private CustomListAdapter11 adapter;
    private CustomListAdapter11b adapter_b;
    private CustomListAdapter11c adapter_c;
    


	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapter11(Activity activity, List<Movie11> movieItems) {
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

	public int getItemPos(int position) {
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		int type = getItemPos(position);
		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (type == 1) {
			 
			  
		  	
			if (imageLoader == null)
				imageLoader = AppController.getInstance().getImageLoader();

		    convertView = inflater.inflate(R.layout.horizontal_inflate11b, parent, false);
		    
		  adapter_b = new CustomListAdapter11b(activity,movieListb);
			listView_b = (HorizontalListView)convertView.findViewById(R.id.listee11b);
			listView_b.setAdapter(adapter_b);
			
			
			 // We first check for cached request
            Cache cache = AppController.getInstance().getRequestQueue().getCache();
            Entry entry = cache.get(URL_FEED2);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                    	
                    	movieListb.clear();
                   	 	adapter_b.notifyDataSetChanged();
                        parseJsonFeedb(new JSONObject(data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
     
            } 
			 // Creating volley request obj
           // making fresh volley request and getting json
              jsonReq2 = new JsonObjectRequest(Method.GET,
                      URL_FEED2, null, new Response.Listener<JSONObject>() {
   
                          public void onResponse(JSONObject response) {
                          	
                          	//Toast.makeText(getActivity(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
                              VolleyLog.d(TAG, "Response: " + response.toString());
                              if (response != null) {
                              	
                                  parseJsonFeedb(response);
                               
                                  
                                  
                              }
                          }
                      }, new Response.ErrorListener() {
   
                          public void onErrorResponse(VolleyError error) {
                          	
                          	Toast.makeText(activity,"Error getting feeds", Toast.LENGTH_LONG).show();
                              VolleyLog.d(TAG, "Error: " + error.getMessage());
                              //pb.setVisibility(View.GONE);

                          }
                      });
   
              // Adding request to volley request queue
              AppController.getInstance().addToRequestQueue(jsonReq2);
		}
              
              else if (type == 2) {
     			 
    			  
      		  	
      			if (imageLoader == null)
      				imageLoader = AppController.getInstance().getImageLoader();

      		    convertView = inflater.inflate(R.layout.horizontal_inflate11c, parent, false);
      		    
      		  adapter_c = new CustomListAdapter11c(activity,movieListc);
      			listView_c = (HorizontalListView)convertView.findViewById(R.id.listee11c);
      			listView_c.setAdapter(adapter_c);
      			
      			
      			 // We first check for cached request
                Cache cache = AppController.getInstance().getRequestQueue().getCache();
                Entry entry = cache.get(URL_FEED2);
                if (entry != null) {
                    // fetch the data from cache
                    try {
                        String data = new String(entry.data, "UTF-8");
                        try {
                        	
                        	movieListc.clear();
                       	 	adapter_c.notifyDataSetChanged();
                            parseJsonFeedc(new JSONObject(data));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
         
                } 
      			 // Creating volley request obj
                  // making fresh volley request and getting json
                     jsonReq3 = new JsonObjectRequest(Method.GET,
                             URL_FEED2, null, new Response.Listener<JSONObject>() {
          
                                 public void onResponse(JSONObject response) {
                                 	
                                 	//Toast.makeText(getActivity(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
                                     VolleyLog.d(TAG, "Response: " + response.toString());
                                     if (response != null) {
                                     	
                                         parseJsonFeedc(response);
                                      
                                         
                                         
                                     }
                                 }
                             }, new Response.ErrorListener() {
          
                                 public void onErrorResponse(VolleyError error) {
                                 	
                                 	Toast.makeText(activity,"Error getting feeds", Toast.LENGTH_LONG).show();
                                     VolleyLog.d(TAG, "Error: " + error.getMessage());
                                     //pb.setVisibility(View.GONE);

                                 }
                             });
          
                     // Adding request to volley request queue
                     AppController.getInstance().addToRequestQueue(jsonReq3);
		    
		   
                     
         
          
	
         		    
 } else{
	  
	
			convertView = inflater.inflate(R.layout.list_row11, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail11);
		//thumbNail.setOnClickListener(this);
		// thumbNail.setOnClickListener(null);
		//thumbNail.setOnTouchListener(this);
		//disableTouchTheft(thumbNail);
		
		TextView title = (TextView) convertView.findViewById(R.id.title11);
		TextView rating = (TextView) convertView.findViewById(R.id.rating11);
		TextView genre = (TextView) convertView.findViewById(R.id.genre11);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear11);
	

		//ImageButton follow = (ImageButton) convertView.findViewById(R.id.imageButtonfollow5);
		//follow.setOnClickListener(this);



		// getting movie data for the row
		final Movie11 m = movieItems.get(position);

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
	
		
//Chcek for empty genre
		if (!TextUtils.isEmpty(m.getGenre())) {
			genre.setText(String.valueOf(m.getGenre()));
			genre.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			genre.setVisibility(View.GONE);
		}

		
		// release year
		year.setText(String.valueOf(m.getYear()));
		
		
		//thumbNail.setOnClickListener(new OnClickListener() {
			
			//@Override
			//public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				//Intent i = new Intent(activity, TalkerProfile.class);

		         
		     	//i.putExtra("foto3", m.getThumbnailUrl());
		     	
		     	//i.putExtra("covery", m.getThumbnailUrl());
		     	
		     	//i.putExtra("namey", m.getTitle());
		     	
		     	//i.putExtra("email", m.getEmail());
		     	
		         //activity.startActivity(i);
	            
				
			//}
		//});

		
		//follow.setOnClickListener(new OnClickListener() {
			
			//@Override
			//public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				//PrefManager pp = new PrefManager(activity);

	        	 // Building Parameters
	            //params = new HashMap<String, String>();
	            //params.put("title", m.getTitle());
	            //params.put("final_image", m.getThumbnailUrl());
	            //params.put("email", m.getEmail());
	            //params.put("rating", m.getYear());
	            //params.put("genre", m.getGenre());
	            //params.put("follower", pp.getEmail());
	            
	           
	            //new Follow().execute();
				
			//}
		//});

		
 }	

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
	
	
	private void disableTouchTheft(View view) {
	    view.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View view, MotionEvent motionEvent) {
	            view.getParent().requestDisallowInterceptTouchEvent(true);
	            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
	            
	            case MotionEvent.ACTION_SCROLL:
	                view.getParent().requestDisallowInterceptTouchEvent(true);
	                break;
	            
	            }
	            return false;
	        }
	    });
	}




	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}



	//ParseJsonFeedb
   private void parseJsonFeedb(JSONObject response) {
       try {
      	 
      	 movieListb.clear();
      	 adapter_b.notifyDataSetChanged();
      	 
           JSONArray feedArray = response.getJSONArray("feed");

           for (int i = 0; i < feedArray.length(); i++) {
               JSONObject feedObj = (JSONObject) feedArray.get(i);

               Movie11b movie = new Movie11b();
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
               movieListb.add(movie);

           }

           // notify data changes to list adapater
           adapter_b.notifyDataSetChanged();
           //pb.setVisibility(View.GONE);
           
       } catch (JSONException e) {
           e.printStackTrace();
       }
   
	
	}	
   
   
   //ParseJsonFeedc
   private void parseJsonFeedc(JSONObject response) {
       try {
      	 
      	 movieListc.clear();
      	 adapter_c.notifyDataSetChanged();
      	 
           JSONArray feedArray = response.getJSONArray("feed");

           for (int i = 0; i < feedArray.length(); i++) {
               JSONObject feedObj = (JSONObject) feedArray.get(i);

               Movie11c movie = new Movie11c();
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
               movieListc.add(movie);

           }

           // notify data changes to list adapater
           adapter_c.notifyDataSetChanged();
           //pb.setVisibility(View.GONE);
           
       } catch (JSONException e) {
           e.printStackTrace();
       }
   
	
	

  
   
	
	}

}