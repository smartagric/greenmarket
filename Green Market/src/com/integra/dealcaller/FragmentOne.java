package com.integra.dealcaller;



import java.io.UnsupportedEncodingException;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.integra.dealcaller.R;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class FragmentOne extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
      
      private static final String TAG = FragmentOne.class.getSimpleName();
 
      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
      public static final String ITEM_NAME = "itemName";
      private String URL_FEED = "http://activetalkgh.com/talkers/health.php";
      private View view;
      private JsonObjectRequest movieReq;
      private ProgressBar pb;
      private List<Movie> movieList = new ArrayList<Movie>();
      private ListView listView;
      private CustomListAdapter adapter;
      private EditText txtSearch;
      AutoScrollViewPager pager;
     
      
      PagerContainer mContainer;
 
      int progress = 0;
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
    	  
    	 
          // Inflate the layout for this fragment
    	
    	    
    	  view = inflater.inflate(R.layout.fragment_layout_one, container, false);
        

 
            listView = (ListView)view.findViewById(R.id.listee);
            
            
           
            
            adapter = new CustomListAdapter(getActivity(), movieList);
            listView.setAdapter(adapter);
           
            pb=(ProgressBar)view.findViewById(R.id.progressBar);
            
            
            
            
            mContainer = (PagerContainer)view.findViewById(R.id.pager_container);

            pager = mContainer.getViewPager();
            ImagePagerAdapter adapter2 = new ImagePagerAdapter(getActivity());
            pager.setAdapter(adapter2);
            //Necessary or the pager will only have one extra page to show
            // make this at least however many pages you can see
            pager.setOffscreenPageLimit(adapter2.getCount());
           
          

            
            

            progress = 0;
           
         			
         			
         				
             pb.setVisibility(View.VISIBLE);			
             pb.setProgress(progress);
             //pager.startAutoScroll();
            
             
             final Movie item = new Movie();
             
             
            
             
           
             
             listView.setOnItemClickListener(new OnItemClickListener() {

     			public void onItemClick(AdapterView<?> arg0, View view, int position,
     					long id) {
     				
     				Intent i = new Intent(getActivity(), TalkerProfile.class);

     	            
     	        	i.putExtra("foto3", item.getThumbnailUrl());
     	        	
     	        	i.putExtra("covery", item.getThumbnailUrl());
     	        	
     	        	i.putExtra("namey", item.getTitle());
     	        	
     	        	i.putExtra("email", item.getEmail());
     	        	
     	            startActivity(i);
     				
     			}
             	 
              });  
            
           
            

            // We first check for cached request
            Cache cache = AppController.getInstance().getRequestQueue().getCache();
            Entry entry = cache.get(URL_FEED);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                    	
                    	movieList.clear();
                   	 	adapter.notifyDataSetChanged();
                        parseJsonFeed(new JSONObject(data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
     
            } 
           
            // Creating volley request obj
         // making fresh volley request and getting json
            movieReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {
 
                        public void onResponse(JSONObject response) {
                        	
                        //Toast.makeText(getActivity(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
                            VolleyLog.d(TAG, "Response: " + response.toString());
                            if (response != null) {
                            	
                            	
                            	parseJsonFeed(response);
                               
                              
                                
                                
                            }
                        }
                    }, new Response.ErrorListener() {
 
                        public void onErrorResponse(VolleyError error) {
                        	
                   
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            pb.setVisibility(View.GONE);
                          
                        }
                    });
 
            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(movieReq);
            
            
            
			return view;
  
         
            
            
                   
        }
      
  
      private void parseJsonFeed(JSONObject response) {
          try {
        	  
        	  	movieList.clear();
         	 	adapter.notifyDataSetChanged();
         	 
              JSONArray feedArray = response.getJSONArray("feed");
   
              for (int i = 0; i < feedArray.length(); i++) {
                  JSONObject feedObj = (JSONObject) feedArray.get(i);
   
                  Movie movie = new Movie();
                  movie.setTitle(feedObj.getString("title"));
                  movie.setThumbnailUrl(feedObj.getString("image"));
                  
               // rating might be null sometimes
                  int rat = feedObj.isNull("rating") ? null : feedObj
                          .getInt("rating");
                  
                  
                  movie.setRating(rat);

               // Genre might be null sometimes
                  String gen = feedObj.isNull("genre") ? null : feedObj
                          .getString("genre");
                  
                  
                  movie.setGenre(gen);
                  
                
                  movie.setYear(feedObj.getString("groupType"));
                  movie.setEmail(feedObj.getString("email"));
 

                  // adding movie to movies array
              
                  movieList.add(movie);

              }
   
              // notify data changes to list adapater
              adapter.notifyDataSetChanged();
              pb.setVisibility(View.GONE);
          } catch (JSONException e) {
              e.printStackTrace();
          }
      
   	
  	}
      
      
  
  


      

       
       
   public void onResume(){
	   super.onResume();
	   //adapter.notifyDataSetChanged();
   }
     
   @Override
   public void onPause() {
       super.onPause();  // Always call the superclass method first

       movieReq.cancel();
       }
   
   
	 
	   
	   
	   
   }