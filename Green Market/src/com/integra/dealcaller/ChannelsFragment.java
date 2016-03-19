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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChannelsFragment extends Fragment implements OnClickListener {
	
	private static final String TAG = ChannelsFragment.class.getSimpleName();
	 
    // Movies json url
    private static final String URL_FEED = "http://activetalkgh.com/friends/friends.php";
    private static ProgressBar pb;
    private  static List<ChannelsItem> movieList;
    private static TextView no_friends;

	private static TextView no_internet;
    private ListView listView;
    
    private static ChannelsListAdapter adapter;
    static Boolean isInternetPresent = false;
    static ConnectionDetector cd;
    private EditText txtSearch;
    int progress = 0;
    
    @Override
    public View onCreateView(LayoutInflater inflater,
    ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.channels_fragment, container, false);
    	
        cd = new ConnectionDetector(getActivity());
    	
 
    	
    	 listView = (ListView) view.findViewById(R.id.listree);
    	 
    	 movieList = new ArrayList<ChannelsItem>();
         adapter = new ChannelsListAdapter(getActivity(), movieList);
         no_internet = (TextView)view.findViewById(R.id.no_internet);
         no_friends = (TextView)view.findViewById(R.id.no_friends);
         
         listView.setAdapter(adapter);
         registerForContextMenu(listView);
         
         
         
         
         txtSearch=(EditText)view.findViewById(R.id.txtysearch);
         
         
         //add text changed listener to the EditText
         txtSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
				int textlength = cs.length();
		           ArrayList<ChannelsItem> tempArrayList = new ArrayList<ChannelsItem>();
		           
		           for(ChannelsItem c: movieList){
		              if (textlength <= c.getTitle().length()) {
		                 if (c.getTitle().toLowerCase().contains(cs.toString().toLowerCase())) {
		                    tempArrayList.add(c);
		                 }
		              }
		           }
		           adapter = new ChannelsListAdapter(getActivity(), tempArrayList);
		           listView.setAdapter(adapter);
				  
				
			
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
         });
         
         
         
         
        
  
         pb=(ProgressBar)view.findViewById(R.id.progressBar00);
         

         progress = 0;
        
      			
      			
      				
          pb.setVisibility(View.VISIBLE);			
          pb.setProgress(progress);
  
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
                      adapter.notifyDataSetChanged();
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
              } catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
              }
   
          }       
         
         
         
      new GetFriends();
         
         
         return view;
     
     }
   
    
    
    public static class GetFriends  {

        
    	// Creating volley request obj
  // making fresh volley request and getting json
      JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
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
                 	
                 	//Toast.makeText(getActivity(),"Could not populate friends", Toast.LENGTH_LONG).show();
                     VolleyLog.d(TAG, "Error: " + error.getMessage());
                    
                  pb.setVisibility(View.GONE);
                  
               // get Internet status
	                isInternetPresent = cd.isConnectingToInternet();

	                // check for Internet status
	                if (isInternetPresent) {
	                    // Internet Connection is Present
	                    // make HTTP requests
	                	
	                	
	                	//no friends yet
	                	no_friends.setVisibility(View.VISIBLE);
	                	pb.setVisibility(View.GONE);

	           
	                
	                } else {
	                    // Internet connection is not present
	                    // Ask user to connect to Internet
	                	
	                	no_internet.setVisibility(View.VISIBLE);
	                	pb.setVisibility(View.GONE);
	                }
                 }
             });

      {
     // Adding request to volley request queue
     AppController.getInstance().addToRequestQueue(jsonReq);

		
       
    
    }   
                
    }

  
         private static void parseJsonFeed(JSONObject response) {
             try {
            	 
            	 movieList.clear();
            	 adapter.notifyDataSetChanged();
            	 
                 JSONArray feedArray = response.getJSONArray("feed");
      
                 for (int i = 0; i < feedArray.length(); i++) {
                     JSONObject feedObj = (JSONObject) feedArray.get(i);
      
                     ChannelsItem movie = new ChannelsItem();
                     movie.setTitle(feedObj.getString("username"));
                     
                  // Profile pic might be null sometimes
                     String prof = feedObj.isNull("profilePic") ? null : feedObj
                             .getString("profilePic");
                     
                  
                     movie.setThumbnailUrl(prof);
                     
                     
                     movie.setRating((feedObj.getInt("mutualFriends")));
                    

                  
                     
                     movie.setGCMREG(feedObj.getString("gcm_regid"));

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
        getMenuInflater().inflate(R.menu.main, (ViewGroup) menu);
        return true;
	}

	private LayoutInflater getMenuInflater() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.listree) {
		 
		  
	    
	    String[] menuItems = getResources().getStringArray(R.array.long_array);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	   
	    }
	  }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
	  String[] menuItems = getResources().getStringArray(R.array.long_array);
	  String menuItemName = menuItems[menuItemIndex];
	  
	  Toast.makeText(getActivity(), "Selected %s for item %s" + menuItemName, Toast.LENGTH_LONG).show();
	  
	  return true;
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	   public void onPause() {
	       super.onPause();  // Always call the superclass method first

	      
	       }



	@Override
		public void onStop() {
			super.onStop();  // Always call the superclass method first
			
        
}

	
  
  
 

 
  public void searchText(){
  
 
 
  
}

}























