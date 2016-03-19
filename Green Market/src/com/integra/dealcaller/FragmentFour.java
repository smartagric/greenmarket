package com.integra.dealcaller;



import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.integra.dealcaller.R;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class FragmentFour extends Fragment implements OnClickListener{
 
      ImageView ivIcon;
      TextView tvItemName;
      
      private static final String TAG = FragmentFour.class.getSimpleName();
 
      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
      public static final String ITEM_NAME = "itemName";
      private JsonObjectRequest jsonReq;
      private static final String URL_FEED = "http://activetalkgh.com/talkers/business_career.php";
      private ProgressBar pb;
      private View view;
      private List<Movie4> movieList = new ArrayList<Movie4>();
      private ListView listView;
      private CustomListAdapter4 adapter;
      private EditText txtSearch;
      PagerContainer mContainer;
      AutoScrollViewPager pager;
      int progress = 0;
      
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
    	  
    	 
    	  
          // Inflate the layout for this fragment
    	  
    	  
    	  
    	  view = inflater.inflate(R.layout.fragment_layout_four, container, false);
        
 
            listView = (ListView)view.findViewById(R.id.listee4);
            
        
            adapter = new CustomListAdapter4(getActivity(), movieList);
            listView.setAdapter(adapter);
     
            pb=(ProgressBar)view.findViewById(R.id.progressBar4);
          

           
       
         
            
            
            
            progress = 0;
           
         			
         			
         				
             pb.setVisibility(View.VISIBLE);			
             pb.setProgress(progress);
             //pager.startAutoScroll();
     
            
             final Movie4 item = new Movie4();
             
             
             
           
             
             
             
             listView.setOnItemClickListener(new OnItemClickListener() {

     			public void onItemClick(AdapterView<?> arg0, View view, int position,
     					long id) {
     				
     				Intent i = new Intent(getActivity(), TalkerProfile.class);

     	            
     	        	i.putExtra("foto3", item.getThumbnailUrl());
     	        	
     	        	i.putExtra("covery", item.getThumbnailUrl());
     	        	
     	        	i.putExtra("namey", item.getTitle());
     	        	
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
               jsonReq = new JsonObjectRequest(Method.GET,
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
                           	
                           	Toast.makeText(getActivity(),"Error getting feeds ", Toast.LENGTH_LONG).show();
                               VolleyLog.d(TAG, "Error: " + error.getMessage());
                               pb.setVisibility(View.GONE);
                               
                           }
                       });
    
               // Adding request to volley request queue
               AppController.getInstance().addToRequestQueue(jsonReq);
               
			return view;
     
            
            
              
           
           }
         
         
         private void parseJsonFeed(JSONObject response) {
             try {
            	 
            	 movieList.clear();
            	 adapter.notifyDataSetChanged();
            	 
                 JSONArray feedArray = response.getJSONArray("feed");
      
                 for (int i = 0; i < feedArray.length(); i++) {
                     JSONObject feedObj = (JSONObject) feedArray.get(i);
      
                     Movie4 movie = new Movie4();
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
                 adapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
                
             } catch (JSONException e) {
                 e.printStackTrace();
             }
         
      	
     	}
         
         
         
         

      
          public boolean onCreateOptionsMenu(Menu menu) {
              // Inflate the menu; this adds items to the action bar if it is present.
              getActivity().getMenuInflater().inflate(R.menu.main, (Menu) menu);
              return true;
      	}

      	

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		   public void onPause() {
		       super.onPause();  // Always call the superclass method first

		     jsonReq.cancel();
		       }
      }