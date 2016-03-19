 package com.integra.dealcaller;

import java.io.UnsupportedEncodingException;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.integra.dealcaller.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CommitmentFragment extends Fragment implements OnClickListener, OnTouchListener{
	
	
	 
    // Movies json url
	private static final String TAG = CommitmentFragment.class.getSimpleName();
    private NestedListView listView;
    private FeedListAdapter2 listAdapter;
    private List<FeedItem2> feedItems;
    private static final String URL_FEED2 = "http://activetalkgh.com/android_connect/helpees_timeline.php";
    private String URL_FEED = "http://activetalkgh.com/android_connect/helpees_timeline.php";
    private ProgressBar pb;
    private JsonObjectRequest jsonReq;
    private List<Movie_main2> movieList; 
    private Button link;
    private HorizontalListView listView_main;
	private MainInflateAdapter2 adapter_main;
    private Button touched;
    private ImageView groupie;
    
    float lastX = 0;
    float oldTouchValue;
    
    
    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater,
    ViewGroup container, Bundle savedInstanceState)
    {
    	
        View view = inflater.inflate(R.layout.commitment_fragment, container, false);
        listView_main = (HorizontalListView)view.findViewById(R.id.listee_main2);
        listView = (NestedListView)view.findViewById(R.id.listry);
        listView.setStackFromBottom(false);
        
        
        movieList = new ArrayList<Movie_main2>();
        feedItems = new ArrayList<FeedItem2>();
 
        listAdapter = new FeedListAdapter2(getActivity(), feedItems);
        pb = (ProgressBar)view.findViewById(R.id.progressBarcf);
        
        
        adapter_main = new MainInflateAdapter2(getActivity(),movieList);
			
			
			
			
        
        touched= (Button)view.findViewById(R.id.TouchedBySituation);
        groupie = (ImageView)view.findViewById(R.id.imageButton100);
        
        
        listView.setAdapter(listAdapter);
        
        
         
        listView_main.setAdapter(adapter_main);
        
        
        listView_main.setOnTouchListener(new OnTouchListener() {
				
	   			@Override
	   	        public boolean onTouch(View view, MotionEvent motionEvent) {
	   	            view.getParent().requestDisallowInterceptTouchEvent(true);
	   	            
	   	            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
	   	            //case MotionEvent.ACTION_UP:
	   	                //view.getParent().requestDisallowInterceptTouchEvent(false);
	   	                
	   	         case MotionEvent.ACTION_MOVE:     
	   	            float currentX = motionEvent.getX();
	               if (oldTouchValue < currentX)
	               {
	                   // swiped left
	            	   
	            	  view.getParent().requestDisallowInterceptTouchEvent(true);
	               }
	               if (oldTouchValue > currentX )
	               {
	                   //swiped right
	            	   
	            	  view.getParent().requestDisallowInterceptTouchEvent(true);
	               }
		             
	   	             
	   	                break;
	   	                
	   	         //case MotionEvent.ACTION_DOWN:
	   	        	 
	   	        // view.getParent().requestDisallowInterceptTouchEvent(false);
		              //  break;   
	   	            
	   	            }
	   	            return false;
	   	        }
			});
        
        
        
        
        
        registerForContextMenu(groupie);
        
        //final CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.viewPager2);
        
        //LinearLayout surr  = (LinearLayout)view.findViewById(R.id.surrounding2);
  		
         
         //ImagePagerAdapter3 adapter = new ImagePagerAdapter3(getActivity());
 	
 		//viewPager.setAdapter(adapter);
 		//viewPager.setCurrentItem(1);
 		
 		//viewPager.setClipToPadding(false);
 		//viewPager.setPadding(70,0,70,0);
 
 		
 		
 		//viewPager.setOnTouchListener(this);
 
 		
 		//surr.setOnTouchListener(new OnTouchListener() {
			
 			//@Override
 	       // public boolean onTouch(View view, MotionEvent motionEvent) {
 	         //   view.getParent().requestDisallowInterceptTouchEvent(false);
 	         //   switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
 	          //  case MotionEvent.ACTION_UP:
 	          //      view.getParent().requestDisallowInterceptTouchEvent(false);
 	             
 	           //     break;
 	                
 	         //case MotionEvent.ACTION_DOWN:
	            //    view.getParent().requestDisallowInterceptTouchEvent(false);
	             
	            //    break;   
 	            
 	           // }
 	           // return false;
 	       // }
		// });

 		 
 	

 		//viewPager.setOnTouchListener(new OnTouchListener() {
		
 			//@Override
 	        //public boolean onTouch(View view, MotionEvent motionEvent) {

 			
 	            //switch (motionEvent.getAction()& MotionEvent.ACTION_MASK ) {
 	            //case MotionEvent.ACTION_UP:
 	                //view.getParent().requestDisallowInterceptTouchEvent(false);
 	             
 	                //break;
 	                
 	         //case MotionEvent.ACTION_DOWN:
	                //view.getParent().requestDisallowInterceptTouchEvent(false);
	                //lastX = motionEvent.getX();
	                //break;  
	          
 	      //case MotionEvent.ACTION_MOVE:

 	        //if (lastX > motionEvent.getX()) {
 	        	//view.getParent().requestDisallowInterceptTouchEvent(true);
 	        	
 	        //} else if(lastX < motionEvent.getX()){
 	        	//view.getParent().requestDisallowInterceptTouchEvent(true);
 	        	
 	        //}else{
 	        	//view.getParent().requestDisallowInterceptTouchEvent(false);
 	        //}

 	        //lastX = motionEvent.getX();
 	        //break;
 	            
 	            //}
 	            //return false;
 	        //}
 			
 			
		//});
        
        
        
        touched.setOnClickListener(new OnClickListener() {

        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int id = v.getId();
				if (id == R.id.TouchedBySituation) {
					alertWebView2();
				} else {
				}
			
		    }
 	 
         });   
				
	

        
		int progress = 0;
		
		
		pb.setVisibility(View.VISIBLE);			
	     pb.setProgress(progress);
	     
	
	     
	  // We first check for cached request
	        Cache cache2 = AppController.getInstance().getRequestQueue().getCache();
	        Entry entry2 = cache2.get(URL_FEED2);
	        if (entry2 != null) {
	            // fetch the data from cache
	            try {
	                String data = new String(entry2.data, "UTF-8");
	                try {
	                	
	                	movieList.clear();
	               	 	adapter_main.notifyDataSetChanged();
	                    parseJsonFeed(new JSONObject(data));
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
	 
	        } 
	     
	     adapter_main = new MainInflateAdapter2(getActivity(),movieList);
			listView_main = (HorizontalListView)view.findViewById(R.id.listee_main2);
			listView_main.setAdapter(adapter_main);
			
			 // Creating volley request obj
          // making fresh volley request and getting json
             JsonObjectRequest jsonReq2 = new JsonObjectRequest(Method.GET,
                     URL_FEED2, null, new Response.Listener<JSONObject>() {
  
                         public void onResponse(JSONObject response) {
                         	
                         	//Toast.makeText(getActivity(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
                             VolleyLog.d(TAG, "Response: " + response.toString());
                             if (response != null) {
                             	
                                 parseJsonFeedd(response);
                              
                                 
                                 
                             }
                         }
                     }, new Response.ErrorListener() {
  
                         public void onErrorResponse(VolleyError error) {
                         	
                         	Toast.makeText(getActivity(),"Error getting feeds", Toast.LENGTH_LONG).show();
                             VolleyLog.d(TAG, "Error: " + error.getMessage());
                             //pb.setVisibility(View.GONE);

                         }
                     });
  
             // Adding request to volley request queue
             AppController.getInstance().addToRequestQueue(jsonReq2);
             
             
             
      
 
        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                	
                	feedItems.clear();
               	 	listAdapter.notifyDataSetChanged();
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
 
        } 
            // making fresh volley request and getting json
             jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {
 
                        public void onResponse(JSONObject response) {
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
            AppController.getInstance().addToRequestQueue(jsonReq);
  
         
		
        
        
        
		return view;
        
 
           }
 
    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
        	
        	feedItems.clear();
       	 	listAdapter.notifyDataSetChanged();
       	 	
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                FeedItem2 item = new FeedItem2();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));
 
           
                
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));
                item.setHolder_ID(feedObj.getString("holder_id"));
                
                // coverphoto might be null sometimes
                String cov = feedObj.isNull("cover_photo") ? null : feedObj
                        .getString("cover_photo");
                item.setCoverPhoto(cov);
                
                item.setNestingInstitution(feedObj.getString("nesting_institution"));
                
                
                String ver = feedObj.isNull("verification_status") ? null : feedObj
                        .getString("verification_status");
                
                item.setVerificationStatus(ver);
                
                
                feedItems.add(item);
            }
 
            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
 
    
    
    }
    private void setContentView(int TimeLineFragment) {
		// TODO Auto-generated method stub

	

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
 
	public void alertWebView() {
		 
	    // WebView is created programatically here.
	    WebView myWebView = new WebView(getActivity());
	    myWebView.loadUrl("http://activetalkgh.com/android_connect/timeline.php");
	 
	    /*
	     * This part is needed so it won't ask the user to open another browser.
	     */
	    myWebView.setWebViewClient(new WebViewClient() {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);
	            return true;
	        }
	    });
	 
	    new AlertDialog.Builder(getActivity()).setView(myWebView)
	            .setTitle("Help Page?")
	            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	               
	                public void onClick(DialogInterface dialog, int id) {
	 
	                    dialog.cancel();
	 
	                }
	 
	            }).show();
	}  
	    
	    public void alertWebView2() {
			 
		    // WebView is created programatically here.
		    WebView myWebView = new WebView(getActivity());
		    myWebView.loadUrl("http://activetalkgh.com/android_connect/timeline.php");
		 
		    /*
		     * This part is needed so it won't ask the user to open another browser.
		     */
		    myWebView.setWebViewClient(new WebViewClient() {
		        @Override
		        public boolean shouldOverrideUrlLoading(WebView view, String url) {
		            view.loadUrl(url);
		            return true;
		        }
		    });
		 
		    new AlertDialog.Builder(getActivity()).setView(myWebView)
		            .setTitle("About Help Page")
		            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		               
		                public void onClick(DialogInterface dialog, int id) {
		 
		                    dialog.cancel();
		 
		                }
		 
		            }).show();
		    
		    
	}
	    
	  //Long Press
	    
	    @Override
		public void onCreateContextMenu(ContextMenu menu, View v,
		    ContextMenuInfo menuInfo) {
		  if (v.getId()==R.id.imageButton100) {
			 
			  
		    
		    String[] menuItems = getResources().getStringArray(R.array.long_array3);
		    for (int i = 0; i<menuItems.length; i++) {
		      menu.add(Menu.NONE, i, i, menuItems[i]);
		   
		    }
		  }
		  
		 
			  
		}
		
		@Override
		public boolean onContextItemSelected(MenuItem item) {
		  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		  int menuItemIndex = item.getItemId();
		  String[] menuItems = getResources().getStringArray(R.array.long_array3);
		  String menuItemName = menuItems[menuItemIndex];
		  
		  Toast.makeText(getActivity(), "Selected %s for item %s" + menuItemName, Toast.LENGTH_LONG).show();
		  
		  return true;
		}

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			return false;
		}
		
		
		
		
		@Override
		   public void onPause() {
		       super.onPause();  // Always call the superclass method first

		      
		       }



		@Override
			public void onStop() {
			super.onStop();  // Always call the superclass method first

	         jsonReq.cancel();  
	}
		
		
		 //ParseJsonFeedd
	    private void parseJsonFeedd(JSONObject response) {
	        try {
	       	 
	       	 movieList.clear();
	       	 adapter_main.notifyDataSetChanged();
	       	 
	            JSONArray feedArray = response.getJSONArray("feed");
	 
	            for (int i = 0; i < feedArray.length(); i++) {
	                JSONObject feedObj = (JSONObject) feedArray.get(i);
	 
	                Movie_main2 item = new Movie_main2();
	                item.setId(feedObj.getInt("id"));
	                item.setName(feedObj.getString("name"));
	 
	           
	                
	                item.setStatus(feedObj.getString("status"));
	                item.setProfilePic(feedObj.getString("profilePic"));
	                item.setTimeStamp(feedObj.getString("timeStamp"));
	                item.setHolder_ID(feedObj.getString("holder_id"));
	                
	                // coverphoto might be null sometimes
	                String cov = feedObj.isNull("cover_photo") ? null : feedObj
	                        .getString("cover_photo");
	                item.setCoverPhoto(cov);
	                
	                item.setNestingInstitution(feedObj.getString("nesting_institution"));
	                
	                
	                String ver = feedObj.isNull("verification_status") ? null : feedObj
	                        .getString("verification_status");
	                
	                item.setVerificationStatus(ver);
	                movieList.add(item);

	            }
	 
	            // notify data changes to list adapater
	            adapter_main.notifyDataSetChanged();
	            //pb.setVisibility(View.GONE);
	            
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	    
	 	
		}
}
