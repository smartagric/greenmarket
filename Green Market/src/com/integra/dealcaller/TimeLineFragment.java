 package com.integra.dealcaller;

import java.io.UnsupportedEncodingException;





import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.animation.Animation.AnimationListener;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import com.integra.dealcaller.BadgeView;
import com.integra.dealcaller.R;
import com.integra.dealcaller.EditProductActivity.SaveProductDetails;
import com.integra.dealcaller.FeedListAdapter.Content;
import com.integra.dealcaller.PullToRefreshBase.OnLastItemVisibleListener;
import com.integra.dealcaller.PullToRefreshBase.OnRefreshListener;
import com.integra.dealcaller.PullToRefreshBase.State;



import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnHoverListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Filter;
import android.widget.AdapterView.OnItemClickListener;

public class TimeLineFragment extends Fragment implements OnClickListener,OnTouchListener{
	
	
	 
    // Movies json url
	private static final String TAG = TimeLineFragment.class.getSimpleName();
    public static PullToRefreshListView listView;
    public static FeedListAdapter listAdapter;
    private JsonObjectRequest jsonReq,jsonReqqy;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private static List<FeedItem> feedItems;
   
    
   
    private HorizontalFeedListAdapter listAdapterx;
    private List<HorizontalFeedItem> feedItemx;
    private int actionBarHeight;
    private static String URL_FEED = "http://activetalkgh.com/android_connect/timeline.php";
    private String URL_FEED3 = "http://activetalkgh.com/android_connect/flagsreal.php";
    private String URL_FEEDqq = "http://activetalkgh.com/android_connect/helpees_timeline.php";
    private String URL_FEED4 = "http://activetalkgh.com/android_connect/commitment_page_detail2.php";
    private String URL_FEED5 = "http://activetalkgh.com/ads/1.png";
    private static final String TAG_SUCCESS = "success";
    private EditText inputSearch;
    private TextView mRefreshViewText;
    static ArrayList<String> actorsList;
    static String p1,p2,p3,p4,p5;
    private String jsonResponse;
    private PopupWindow pw;
    private ImageView con;
    private TextView mResultText;
    private View view;
    private ViewPager mViewPager;
    private ImageButton btn5;
    private LinearLayout lin;
    private String timeStamp;
    static Bitmap inlayBitmap;
    private CharSequence timeAgo;
    private ArrayList<Content> mData = new ArrayList<Content>();
    
    Point p;
 // action bar
    private android.support.v7.app.ActionBar actionBar;
    
 
    Animation animSlideUp;
    Animation animSlideDown;
    
    private static ProgressBar pb;
    
    private Integer progress;
    
    
    Button btn;
    Button btn2;
    ImageButton btn3;
    
    
    
    // flag for Internet connection status
    Boolean isInternetPresent = false;
     
    // Connection detector class
    ConnectionDetector cd;
    
    
    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater,
    ViewGroup container, Bundle savedInstanceState)
    {
    	
      view = inflater.inflate(R.layout.timeline_fragment, container, false);
        
        cd = new ConnectionDetector(getActivity());
       
    
     animSlideUp = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in);
     animSlideDown = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_out);

    
		 // Adding a View Badger
		
     	con= (ImageView)view.findViewById(R.id.connection_lost);
        btn = (Button) view.findViewById(R.id.buttoso);
        btn.setOnClickListener(this);  
      
        btn2 = (Button) view.findViewById(R.id.buttoso2);
        btn2.setOnClickListener(this);  
        
        btn5 = (ImageButton) view.findViewById(R.id.buttoso5);
        btn5.setOnClickListener(this);
        
        
        
        btn3 = (ImageButton) view.findViewById(R.id.buttoso3);
        btn3.setOnClickListener(this);  
        
         
        
        lin = (LinearLayout)view.findViewById(R.id.lin);
        
        listView = (PullToRefreshListView)view.findViewById(R.id.listok);
        
        //listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
       // listView.setStackFromBottom(true);
       
        
        feedItems = new ArrayList<FeedItem>();
        
        
        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        
        
        listView.setAdapter(listAdapter);
        
        
        actorsList = new ArrayList<String>();
    
      
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        
        int width = display.getWidth(); // ((display.getWidth()*20)/100)
       
        int height = display.getHeight()*(10/100);
       
        
       
        lin.setMinimumHeight(100);
        
        
        //boolean pauseOnScroll = true; // or true
        //boolean pauseOnFling = true; // or false

        //BitmapTools tools = new BitmapTools(getActivity());
        
      //PauseOnScrollListener listen = new PauseOnScrollListener(tools, pauseOnScroll, pauseOnFling);
      //listView.setOnScrollListener(listen);

        
            
        listView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView listView, int scrollState) {
                // Pause disk cache access to ensure smoother scrolling
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    
                	disableTouchTheft(listView);
                } else {
                
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    // TODO Auto-generated method stub
            }
        });
        
       
      
        
        timeStamp = new SimpleDateFormat("yyyyMMddHHmmss",
                Locale.getDefault()).format(new Date());
        
        
        //converting to time ago
        timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(timeStamp),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        
        
        
        animSlideUp.setAnimationListener(new AnimationListener() {
    		
    		@Override
    		public void onAnimationStart(Animation arg0) {
    			// TODO Auto-generated method stub
    			
    		}
    		
    		@Override
    		public void onAnimationRepeat(Animation arg0) {
    			// TODO Auto-generated method stub
    			
    		}
    		
    		@Override
    		public void onAnimationEnd(Animation arg0) {
    			// TODO Auto-generated method stub
    			
    		}
    	});
        
        
       
            
        
        
        animSlideDown.setAnimationListener(new AnimationListener() {
    		
    		@Override
    		public void onAnimationStart(Animation arg0) {
    			// TODO Auto-generated method stub
    			
    		}
    		
    		@Override
    		public void onAnimationRepeat(Animation arg0) {
    			// TODO Auto-generated method stub
    			
    		}
    		
    		@Override
    		public void onAnimationEnd(Animation arg0) {
    			// TODO Auto-generated method stub
    			
    		}
    	});
        
        
        
        listView.setOnScrollListener(new OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {

                if(mLastFirstVisibleItem<firstVisibleItem)
                {
                    Log.i("SCROLLING DOWN","TRUE");
                    

                    if(lin.isShown()){
                    lin.startAnimation(animSlideDown);
                    lin.setVisibility(View.GONE);
                    btn.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                 
                    btn5.setVisibility(View.GONE);
                    }
                }
                if(mLastFirstVisibleItem>firstVisibleItem)
                {
                    Log.i("SCROLLING UP","TRUE");
                      

                    if(!lin.isShown()){
                        lin.setVisibility(View.VISIBLE);
                        btn.setVisibility(View.VISIBLE);
                        btn2.setVisibility(View.VISIBLE);
                        btn3.setVisibility(View.VISIBLE);
                       
                        
                        btn5.setVisibility(View.VISIBLE);
                        lin.startAnimation(animSlideUp);
                        } 
                }
                mLastFirstVisibleItem=firstVisibleItem;

            }
        });
        
        

		// Set a listener to be invoked when the list should be refreshed.
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				new GetDataTask();
			}
		});

		// Add an end-of-list listener
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				//Toast.makeText(getActivity(), "End of feeds!", Toast.LENGTH_SHORT).show();
			}
		});

		

		/**
		 * Add Sound Event Listener
		 */
		//SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(getActivity());
		//soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		//soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		//soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		//listView.setOnPullEventListener(soundListener);

		
	
		
        
        
       
        
        
        
       
        
        feedItemx = new ArrayList<HorizontalFeedItem>();
        
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
        View vview = layoutInflater.inflate(R.layout.horizontal_listview1, null); 

			
			
		    
		    
		    
		    
		    
		    
		    
        pb=(ProgressBar)view.findViewById(R.id.progressBar1);
        

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
       
        
        
        ImageRequest request2 = new ImageRequest(URL_FEED5, new Response.Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap bitmap) {
				// TODO Auto-generated method stub
				inlayBitmap = bitmap;
			}
            
        	
        }, 0, 0, null, 
        
        new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            
        
            }
            
        });
     // Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(request2);  
        
        
        
        
      //making fresh volley request and getting json for viewflipper
        //JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                //URL_FEED4, null, new Response.Listener<JSONObject>() {

                   //public void onResponse(JSONObject response) {
                     
                        //if (response != null) {
                            //parseJsonFeed3(response);

                     
                       //}
                        
                        
                    //}
                //}, new Response.ErrorListener() {

                    //public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), "failed to get images", Toast.LENGTH_LONG).show();
                      
                  
                    //}
                //});

       // Adding request to volley request queue
      //AppController.getInstance().addToRequestQueue(jsonReq);
        
        
      
      
      
        
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
                        	
                        	Toast.makeText(getActivity(),"Error, Error, Error, Error pleaseeeee ", Toast.LENGTH_LONG).show();
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                           
                            pb.setVisibility(View.GONE);
                        }
                    });
 
            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
  
         
           ///////////////////////////////
            
            
            
          
            
        
        
		return view;
		
        
    }  

           
    
    
    public static class GetDataTask  {

       
        	//Request to get updates
        	JsonObjectRequest jsonReqqy = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {
 
                        public void onResponse(JSONObject response) {
                        	
                        	//Toast.makeText(getActivity(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
                            VolleyLog.d(TAG, "Response: " + response.toString());
                            
                          
                            
                            
                            if (response != null) {
                       
                            	parseJsonFeed(response);
                            	listAdapter.notifyDataSetChanged();
                            	
                               
                                
                            }
                        }
                    }, new Response.ErrorListener() {
 
                        public void onErrorResponse(VolleyError error) {
                        	
                        	//Toast.makeText(getActivity(),"Unable to refresh", Toast.LENGTH_LONG).show();
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                           
                         // Call onRefreshComplete when the list has been refreshed.
                			listView.onRefreshComplete();
                        }
                    
                        
                    });
 
        	{
        	 // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReqqy);
			
           

            
                    
        }
}
  
    
 
    
    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private static void parseJsonFeed(JSONObject response) {
        try {
        	
        	feedItems.clear();
        	listAdapter.notifyDataSetChanged();
       	 
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));
 
                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                
                // Status might be null sometimes
                String stat = feedObj.isNull("status") ? null : feedObj
                        .getString("status");
                
                item.setStatus(stat);
                
             // Status might be null sometimes
                String lesson = feedObj.isNull("lesson") ? null : feedObj
                        .getString("lesson");
                
                item.setLesson(lesson);
                
                
             // Profile pic might be null sometimes
                String prof = feedObj.isNull("profilePic") ? null : feedObj
                        .getString("profilePic");
                
                item.setProfilePic(prof);
                
                
             // Comments might be null sometimes
                int com = feedObj.isNull("no_of_comments") ? null : feedObj
                        .getInt("no_of_comments");
                
                item.setComments(com);
                
             // Expressions might be null sometimes
                int expr = feedObj.isNull("no_of_expressions") ? null : feedObj
                        .getInt("no_of_expressions");
                
                
                item.setExp(expr);
                
                
             // Expressions might be null sometimes
                int boo = feedObj.isNull("no_of_boos") ? null : feedObj
                        .getInt("no_of_boos");
                
                
                item.setBoos(boo);
                
                item.setTimeStamp(feedObj.getString("timeStamp"));
                item.setEmail(feedObj.getString("from_email"));
 
                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);
 
               
                feedItems.add(item);
            }
 
            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
            listView.onRefreshComplete();
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
 	
	}
    
    
        
    
    public void onClick(View w) {
		// TODO Auto-generated method stub
		
		
		int id = w.getId();
		if (id == R.id.buttoso) {
			Intent iv = new Intent(this.getActivity(), SharePhoto.class);
			startActivity(iv);
		} else if (id == R.id.buttoso2) {
			//Intent vv = new Intent(this.getActivity(), ShareVideo.class);
			//startActivity(vv);
			Toast.makeText(getActivity(), "not fully coded yet", Toast.LENGTH_LONG).show();
		} else if (id == R.id.buttoso3) {
			//Intent vv2 = new Intent(this.getActivity(), EmoActivity.class);   //Originally should be UpdateStatus.class
			//startActivity(vv2);
		
			
		} else if (id == R.id.buttoso5) {
			
			Intent i = new Intent(this.getActivity(), ChatHeadService.class);   //Originally should be UpdateStatus.class
		
			i.putExtra("text", "Hello! Samuel");
     	   this.getActivity().startService(i);
		}
    }
	
    
    @Override
	public void onResume() {
        super.onResume();
        
     // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests
           
        
        	
 	      
 	       
        
        } else if(!con.isShown()){
            // Internet connection is not present
            // Ask user to connect to Internet
       
        	
        	con.setVisibility(View.VISIBLE);
        
        
        	LayoutInflater layoutInflater 
            = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	
           View popupView = layoutInflater.inflate(R.layout.popup2, null);  
                    final PopupWindow popupWindow2 = new PopupWindow(
                      popupView, 
                      LayoutParams.FILL_PARENT,  
                            LayoutParams.WRAP_CONTENT);  
                    
                    ImageButton btnDismiss = (ImageButton)popupView.findViewById(R.id.dismiss2);
                    TextView tekx = (TextView)popupView.findViewById(R.id.popup_text2);
                    
                    btnDismiss.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
             // TODO Auto-generated method stub
             popupWindow2.dismiss();
            }});
                     
                    
                    
                    popupWindow2.setOutsideTouchable(true);
                  
                    
                    tekx.setText("    Check your internet connection");
                    popupWindow2.showAtLocation(popupView, Gravity.NO_GRAVITY, -30, 10);
                    
                    popupWindow2.setOutsideTouchable(true);
                    
                    
        
      	       
                
        }else if (con.isShown()){
        	
        }else{
        	
        }
    }
    
    
  
    private void parseJsonFeedqq(JSONObject response) {
        try {
        	
        	
       	 	
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                HorizontalFeedItem item = new HorizontalFeedItem();
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
                
                
                feedItemx.add(item);
            }
 
            
           
            
            
            pb.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
 
    
    
    }
    
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if ( view != null) {
            boolean wasProcessed = view.onTouchEvent(ev);

            if (!wasProcessed) {
                view = null;
            }

            return wasProcessed;
        }

        return super.getActivity().dispatchTouchEvent(ev);
    }




	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

   
	public static void disableTouchTheft(View view) {
	    view.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View view, MotionEvent motionEvent) {
	            view.getParent().requestDisallowInterceptTouchEvent(true);
	            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
	            case MotionEvent.ACTION_UP:
	                view.getParent().requestDisallowInterceptTouchEvent(false);
	                break;
	            }
	            return false;
	        }
	    });
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
	
	
	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */
	private void parseJsonFeed3(JSONObject response) {
	    try {
	        JSONArray feedArray = response.getJSONArray("feed");

	        for (int i = 0; i < feedArray.length(); i++) {
	            JSONObject feedObj = (JSONObject) feedArray.get(i);

	            
	            
	            
	            
	            
	         // Pic1 might be null sometimes
	           p1 = feedObj.isNull("photo1") ? null : feedObj
	                    .getString("photo1");
	            
	            actorsList.add(p1);
	            
	            // Pic2 might be null sometimes
	            p2 = feedObj.isNull("photo2") ? null : feedObj
	                    .getString("photo2");
	            
	            actorsList.add(p2);
	     
	         // Pic3 might be null sometimes
	            p3 = feedObj.isNull("photo3") ? null : feedObj
	                    .getString("photo3");
	            
	            actorsList.add(p3);
	            
	         // Pic4 might be null sometimes
	            p4 = feedObj.isNull("photo4") ? null : feedObj
	                    .getString("photo4");
	            
	            actorsList.add(p4);

	            // Pic5 might be null sometimes
	            p5 = feedObj.isNull("photo5") ? null : feedObj
	                    .getString("photo5");
	            
	            actorsList.add(p5);
	   
	        }

	        
	        //setFlipperImage( actorsList);
	       
	        
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	}


}   
    
    
 
 

