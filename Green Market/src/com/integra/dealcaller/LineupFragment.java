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
import com.integra.dealcaller.PullToRefreshBase;
import com.integra.dealcaller.PullToRefreshBase.OnLastItemVisibleListener;
import com.integra.dealcaller.PullToRefreshBase.OnRefreshListener;
import com.integra.dealcaller.TimeLineFragment.GetDataTask;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LineupFragment extends Fragment implements OnClickListener, OnTouchListener {
	
	private static final String TAG = LineupFragment.class.getSimpleName();
	 
    // Movies json url
    private static final String URL_FEED = "http://activetalkgh.com/android_connect/smes.php";
    private static final String URL_FEED2 = "http://activetalkgh.com/android_connect/smes.php";
    private static ProgressBar pb;
    private  static List<LineupMovie> movieList;
   
    private static TextView no_friends;
    private static HorizontalListView listView_main;
	private static TextView no_internet;
    public static ListView listView;
    private static ProgressBar pb2;
    private static LineupListAdapter2 adapter_main;
    private static LineupListAdapter adapter;
    public static RelativeLayout over;
    static Boolean isInternetPresent = false;
    static ConnectionDetector cd;
    private EditText txtSearch;
    int progress = 0;
    
    @Override
    public View onCreateView(LayoutInflater inflater,
    ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.lineup_fragment, container, false);
    	
        cd = new ConnectionDetector(getActivity());
    	
 
    	
    	 listView = (ListView) view.findViewById(R.id.listree);
    	 over= (RelativeLayout)view.findViewById(R.id.over);
    	 movieList = new ArrayList<LineupMovie>();
    	 
         adapter = new LineupListAdapter(getActivity(), movieList);
         no_internet = (TextView)view.findViewById(R.id.no_internet);
         no_friends = (TextView)view.findViewById(R.id.no_friends);
         
         listView.setAdapter(adapter);
         
        
         
         
         
         
         
         txtSearch=(EditText)view.findViewById(R.id.txtysearch);
         
         
         //add text changed listener to the EditText
         txtSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
				int textlength = cs.length();
		           ArrayList<LineupMovie> tempArrayList = new ArrayList<LineupMovie>();
		           
		           for(LineupMovie c: movieList){
		              if (textlength <= c.getTitle().length()) {
		                 if (c.getTitle().toLowerCase().contains(cs.toString().toLowerCase())) {
		                    tempArrayList.add(c);
		                 }
		              }
		           }
		           adapter = new LineupListAdapter(getActivity(), tempArrayList);
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
      
      
      
     ////////////////////populate that of horizontal list
      
      
      
      
      
      
         
         
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

	           
	                
	                } else {
	                    // Internet connection is not present
	                    // Ask user to connect to Internet
	                	
	                	no_internet.setVisibility(View.VISIBLE);
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
      
                     LineupMovie movie = new LineupMovie();
                     movie.setTitle(feedObj.getString("channel_name"));
                     
                  // Profile pic might be null sometimes
                     String prof = feedObj.isNull("profilePic") ? null : feedObj
                             .getString("profilePic");
                    
                    
                  
                     movie.setThumbnailUrl(prof);
                     
                     String sme_id = feedObj.isNull("sme_id") ? null : feedObj
                             .getString("sme_id");
                  
                     movie.setSME_id(sme_id);
                     
                     
                     movie.setLocation((feedObj.getString("service")));
                    
                     
                  
                     
                     movie.setTicker(feedObj.getString("location"));
                     
                    

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





@Override
public boolean onTouch(View arg0, MotionEvent arg1) {
	// TODO Auto-generated method stub
	return false;
}

}























