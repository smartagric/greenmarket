package com.integra.dealcaller;

import java.io.UnsupportedEncodingException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.integra.dealcaller.VideoControllerView;
import com.integra.dealcaller.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TalkerProfile extends ActionBarActivity implements OnClickListener,SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl
 {

	private List<TalkerItem> feedItems;
	private List<HorizontalFeedItem2> feedItems2;
	private TalkerListAdapter listAdapter;
	private RelativeLayout overlap;
	private static final String TAG = TalkerProfile.class.getSimpleName();
    private ListView listView;
    private TextView moving;
    private boolean	 mFullScreen = true;
    SurfaceView feedVideoView;

    SurfaceHolder vidHolder;
    FrameLayout frame;

    MediaPlayer player;
    VideoControllerView controller;
    
    private ImageView cover;
    private ImageView profile_id, try_view;
    private static String selectedName;
    private static String selectedEmail;
    private TextView texty;
    private static String vid = null;
    private static String thumbnail = null;
    private String URL_FEED = "http://activetalkgh.com/android_connect/timeline.php";
    private ImageButton about, chatroom,schedules;
    private ProgressBar pDialog;
    private EditText inputSearch;

    private Handler mHandler = new Handler();
    
    /** Called when the activity is first created. */
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

setContentView(R.layout.listviewdemo);


feedItems = new ArrayList<TalkerItem>();
feedItems2 = new ArrayList<HorizontalFeedItem2>();


//Getting details from intent
Bundle i = getIntent().getExtras();
if (i != null) {
	  
String selectedPhoto = i.getString("foto3");
String selectedPhoto2 = i.getString("covery");

selectedName = i.getString("namey");

selectedEmail = i.getString("email");

vid = i.getString("vid");
thumbnail = i.getString("thumbnail");


listAdapter = new TalkerListAdapter(this, feedItems);



//ListView listview = (ListView) findViewById(R.id.listorry);
//listview.setAdapter(listAdapter);



moving = (TextView)findViewById(R.id.mylinenews);
cover = (ImageView)findViewById(R.id.imageButton111);
profile_id = (ImageView)findViewById(R.id.imageButton222);
try_view = (ImageView)findViewById(R.id.imageButton100);
texty = (TextView)findViewById(R.id.textView1);
overlap = (RelativeLayout)findViewById(R.id.scroll_view_head);
//pocket = (ImageButton)findViewById(R.id.pocket2);


Display display = getWindowManager().getDefaultDisplay();

int width = display.getWidth(); // ((display.getWidth()*20)/100)
int width2 = width/2;
int height = ((display.getHeight()*35)/100);
int height2 = ((display.getHeight()*24)/100);
int height3 = ((display.getHeight()*50)/100);
RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,height);
RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams(width,height3);

cover.setLayoutParams(parms);


//int compute = height-95;
	int compute = height2;

MarginLayoutParams params = (MarginLayoutParams) overlap.getLayoutParams();
params.width = width; params.leftMargin = 0; params.topMargin = compute;
overlap.setLayoutParams(params);





feedVideoView = (SurfaceView)findViewById(R.id.video_view);
feedVideoView.setOnClickListener(this);

frame = (FrameLayout)findViewById(R.id.videoSurfaceContainer);
frame.setOnClickListener(this);


vidHolder = feedVideoView.getHolder();
vidHolder.addCallback(this);
vidHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
player = new MediaPlayer();
controller = new VideoControllerView(this);


registerForContextMenu(cover);
registerForContextMenu(profile_id);


feedVideoView.setOnTouchListener(new OnTouchListener() {
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		controller.setAnchorView(frame);
        controller.show();
        
		return false;
	}
});


//about.setOnClickListener(new View.OnClickListener() {
	 
   // public void onClick(View arg0) {
        // starting background task to update product
      
   // }
//});


//chatroom.setOnClickListener(new View.OnClickListener() {
	 
   // public void onClick(View arg0) {
        // starting background task to update product
    //   Intent tt = new Intent(TalkerProfile.this, ChatActivity2.class);
    //   startActivity(tt);
   // }
//});


//schedules.setOnClickListener(new View.OnClickListener() {
	 
    //public void onClick(View arg0) {
        // starting background task to update product
     //  Intent tt = new Intent(TalkerProfile.this, TalkerSchedules.class);
     //  tt.putExtra("email", selectedEmail);
     //  startActivity(tt);
   // }
//});




pDialog =(ProgressBar)findViewById(R.id.progressBardemo);
// Showing progress dialog before making http request
pDialog.setVisibility(View.VISIBLE);

pDialog.setProgress(0);




getSupportActionBar().setTitle(selectedName);


Animation movee =  AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
moving.startAnimation(movee);



if(vid!=null) {
	//Toast.makeText(getApplicationContext(), vid.toString(), Toast.LENGTH_LONG).show();
	
	
		}



//Setting image for profile pic

// check for selected photo null
if (selectedPhoto != null) {

//Set Name from previous class
texty.setText(null);
texty.setText(selectedName);









imageLoader.get(selectedPhoto,
		new ImageListener() {

			@Override
			public void onErrorResponse(
					VolleyError arg0) {
				Toast.makeText(
						getApplicationContext(),
						"failed to fetch image",
						Toast.LENGTH_LONG).show();
			}

			@Override
			public void onResponse(
					ImageContainer response,
					boolean arg1) {
				if (response.getBitmap() != null) {
					// load bitmap into imageview
					
					profile_id.setImageBitmap(null);
					profile_id
							.setImageBitmap(response
									.getBitmap());
					
					// hide loader and show set &
					// download buttons
					
					
				}
			}
		});

//setting imaage for video_thumbnail 

if (thumbnail != null) {

	
	
	
	
	imageLoader.get(thumbnail,
			new ImageListener() {

				@Override
				public void onErrorResponse(
						VolleyError arg0) {
					Toast.makeText(
							getApplicationContext(),
							"failed to fetch image",
							Toast.LENGTH_LONG).show();
				}

				@Override
				public void onResponse(
						ImageContainer response,
						boolean arg1) {
					if (response.getBitmap() != null) {
						// load bitmap into imageview
						
					
						
						try_view.setImageBitmap(null);
						try_view.setImageBitmap(response
										.getBitmap());
						// hide loader and show set &
						// download buttons
						
						
					}
				}
			});


}else {
Toast.makeText(getApplicationContext(), "thumbnail is null", Toast.LENGTH_LONG).show();
}


// setting imaage for cover photo 

if (selectedPhoto2 != null) {

	
	
	
	
	imageLoader.get(selectedPhoto2,
			new ImageListener() {

				@Override
				public void onErrorResponse(
						VolleyError arg0) {
					Toast.makeText(
							getApplicationContext(),
							"failed to fetch image",
							Toast.LENGTH_LONG).show();
				}

				@Override
				public void onResponse(
						ImageContainer response,
						boolean arg1) {
					if (response.getBitmap() != null) {
						// load bitmap into imageview
						
						cover.setImageBitmap(null);
						cover.setImageBitmap(response
										.getBitmap());
					
						// hide loader and show set &
						// download buttons
						
						
					}
				}
			});


}else {
Toast.makeText(getApplicationContext(), "SelectedPhoto2 is null", Toast.LENGTH_LONG).show();
}

}}



}




/**String url2 = "http://activetalkgh.com/friends/friends_timeline.php";

Map<String, String> params = new HashMap<String, String>();
params.put("query", selectedName);
params.put("email", selectedEmail);



/**
 * Use this query to display search results like
 * 1. Getting the data from SQLite and showing in listview
 * 2. Making webrequest and displaying the data
 * For now we just display the query only
 */





// making fresh volley request and getting json

/**CustomRequest jsObjRequest = new CustomRequest(Method.POST, url2, params, new Response.Listener<JSONObject>() {

            public void onResponse(JSONObject response) {
            	
            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                	
                	
                	parseJsonFeed(response);
                   
                  
                    
                    
                }
            }
        }, new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
            	
       
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.setVisibility(View.GONE);
                
                Toast.makeText(getApplicationContext(), "Unable to fetch data", Toast.LENGTH_LONG).show();
              
            }
        });

// Adding request to volley request queue
AppController.getInstance().addToRequestQueue(jsObjRequest);



 

       


    
    
    

}



/**
 * Parsing json reponse and passing the data to feed view list adapter
 * */
/**private void parseJsonFeed(JSONObject response) {
    try {
        JSONArray feedArray = response.getJSONArray("feed");

        for (int i = 0; i < feedArray.length(); i++) {
            JSONObject feedObj = (JSONObject) feedArray.get(i);

            TalkerItem item = new TalkerItem();
            
            item.setId(feedObj.getInt("id"));
            
            
            
         // Pic1 might be null sometimes
            String p = feedObj.isNull("username") ? null : feedObj
                    .getString("username");
            
            
            item.setUsername(p);
            
         // Pic2 might be null sometimes
            String p2 = feedObj.isNull("timestamp") ? null : feedObj
                    .getString("timestamp");
            
            
            item.setTimeStamp(p2);
            
            
         // Pic3 might be null sometimes
            String p3 = feedObj.isNull("mobile_number") ? null : feedObj
                    .getString("mobile_number");
            
            
            item.setMobile(p3);  
            
            
            
         // Pic4 might be null sometimes
            String p4 = feedObj.isNull("email") ? null : feedObj
                    .getString("email");
            
            
            item.setEmail(p4);
            
            
         // Pic5 might be null sometimes
            String p5 = feedObj.isNull("school") ? null : feedObj
                    .getString("school");
            
            
            item.setSchool(p5);
         
            
            
            // Pic3 might be null sometimes
               String p6 = feedObj.isNull("work") ? null : feedObj
                       .getString("work");
               
               
               item.setWork(p6);  
               
               
               
            // Pic4 might be null sometimes
               String p7 = feedObj.isNull("residence") ? null : feedObj
                       .getString("residence");
               
               
               item.setResidence(p7);
               
               
            // Pic5 might be null sometimes
               String p8 = feedObj.isNull("country") ? null : feedObj
                       .getString("country");
               
               
               item.setCountry(p8);
            
            

            

            feedItems.add(item);
        }

        // notify data changes to list adapater
        listAdapter.notifyDataSetChanged();
        pDialog.setVisibility(View.GONE);
        
    } catch (JSONException e) {
        e.printStackTrace();
    }
    * */



	



@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	
}





//Long Press

@Override
public void onCreateContextMenu(ContextMenu menu, View v,
    ContextMenuInfo menuInfo) {
  if (v.getId()==R.id.imageButton111) {
	 
	  
    
    String[] menuItems = getResources().getStringArray(R.array.long_array3);
    for (int i = 0; i<menuItems.length; i++) {
      menu.add(Menu.NONE, i, i, menuItems[i]);
   
    }
  }
  
  if (v.getId()==R.id.imageButton222) {
		 
	  
	    
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
  
  Toast.makeText(this, "Selected %s for item %s" + menuItemName, Toast.LENGTH_LONG).show();
  
  return true;
}


@Override
public void onPrepared(MediaPlayer mp) {
	// TODO Auto-generated method stub
	
}


@Override
public void surfaceCreated(SurfaceHolder holder) {
	// TODO Auto-generated method stub
	
	
	mHandler.postDelayed(new Runnable() {
        public void run() {
	
	try {
		
		 //player.setDisplay(holder);
		//    player.prepareAsync();
			     
		
				
		
		
		
				//player = new MediaPlayer();
				
				//player.setDataSource("http://activetalkgh.com/android_connect/uploads/kitkatnew.mp4");
	    		player.setDataSource(vid.toString());
				//player.setDataSource("http://activetalkgh.com/android_connect/uploads/kitkatnew.mp4");
	    		//player.seekTo(100);  
	    		
	    		player.setDisplay(vidHolder);
	    		
	    		feedVideoView.requestFocus(); 
	    		
	    		
	    		
				
	    		player.prepare();
	    		
	            

	           
	            
	    		player.setOnPreparedListener(new OnPreparedListener() {
	    			
	    			
	    			
					@Override
					public void onPrepared(MediaPlayer mp) {
						// TODO Auto-generated method stub
						  controller.setMediaPlayer(new VideoControllerView.MediaPlayerControl() {
							
							@Override
							public void toggleFullScreen() {
								// TODO Auto-generated method stub
								 setFullScreen(isFullScreen());
								
							}
							
							@Override
							public void start() {
								// TODO Auto-generated method stub
								try_view.setVisibility(View.GONE);
								 player.start();
							}
							
							@Override
							public void seekTo(int pos) {
								// TODO Auto-generated method stub
								   player.seekTo(pos);
							}
							
							@Override
							public void pause() {
								// TODO Auto-generated method stub
								player.pause();
							}
							
							@Override
							public boolean isPlaying() {
								// TODO Auto-generated method stub
								return player.isPlaying();
							}
							
							@Override
							public boolean isFullScreen() {
								// TODO Auto-generated method stub
								if(mFullScreen){    
			    			        Log.v("FullScreen", "--set icon full screen--");
			    			        return false;
			    			    }else{
			    			        Log.v("FullScreen", "--set icon small full screen--");
			    			        return true;
			    			    }   
							}
							
							@Override
							public int getDuration() {
								// TODO Auto-generated method stub
								return player.getDuration();
							}
							
							@Override
							public int getCurrentPosition() {
								// TODO Auto-generated method stub
								return player.getCurrentPosition();
							}
							
							@Override
							public int getBufferPercentage() {
								// TODO Auto-generated method stub
								return 0;
							}
							
							@Override
							public boolean canSeekForward() {
								// TODO Auto-generated method stub
								return true;
							}
							
							@Override
							public boolean canSeekBackward() {
								// TODO Auto-generated method stub
								return true;
							}
							
							@Override
							public boolean canPause() {
								// TODO Auto-generated method stub
								return true;
							}
						});
							
							
					        //controller.setAnchorView(frame);
					        //controller.show();
					
						  
						  
					//player.start();
						
						pDialog.setVisibility(View.GONE);
						
						
						
					}
				});
	    		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
	    	}
	    	catch(Exception e){
	    	    e.printStackTrace();
	    	}
	
	
	
		}
		 }, 3000);

	
}
@Override
public void surfaceChanged(SurfaceHolder holder, int format, int width,
		int height) {
	// TODO Auto-generated method stub
	
}


@Override
public void surfaceDestroyed(SurfaceHolder holder) {
	// TODO Auto-generated method stub
	
}





@Override
public void start() {
	// TODO Auto-generated method stub
	player.start();
}


@Override
public void pause() {
	// TODO Auto-generated method stub
	player.pause();
}


@Override
public int getDuration() {
	// TODO Auto-generated method stub
	return player.getDuration();
}


@Override
public int getCurrentPosition() {
	// TODO Auto-generated method stub
	return player.getCurrentPosition();
}


@Override
public void seekTo(int pos) {
	// TODO Auto-generated method stub
	player.seekTo(pos);
}


@Override
public boolean isPlaying() {
	// TODO Auto-generated method stub
	return player.isPlaying();
}


@Override
public int getBufferPercentage() {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public boolean canPause() {
	// TODO Auto-generated method stub
	return true;
}


@Override
public boolean canSeekBackward() {
	// TODO Auto-generated method stub
	return true;
}


@Override
public boolean canSeekForward() {
	// TODO Auto-generated method stub
	return true;
}


@Override
public boolean isFullScreen() {
	// TODO Auto-generated method stub
	
	// TODO Auto-generated method stub
	if(mFullScreen){    
        Log.v("FullScreen", "--set icon full screen--");
        return false;
    }else{
        Log.v("FullScreen", "--set icon small full screen--");
        return true;
    }   
	
}


@Override
public void toggleFullScreen() {
	// TODO Auto-generated method stub
	setFullScreen(isFullScreen());
}


public void setFullScreen(boolean fullScreen){
    fullScreen = false;

    if (mFullScreen)

    {
        Log.v("FullScreen", "-----------Set full screen SCREEN_ORIENTATION_LANDSCAPE------------"); 
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
          int height = displaymetrics.heightPixels;
          int width = displaymetrics.widthPixels;
          android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) feedVideoView .getLayoutParams();
          params.width = width;
          params.height=height;
          params.setMargins(0, 0, 0, 0);
          //set icon is full screen
         mFullScreen = fullScreen;
    }
    else{
        Log.v("FullScreen", "-----------Set small screen SCREEN_ORIENTATION_PORTRAIT------------");             
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);      
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        
         // int height = displaymetrics.heightPixels;
          int height = frame.getHeight();//get height Frame Container video
          int width = displaymetrics.widthPixels;
          android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) feedVideoView.getLayoutParams();
          params.width = width;
          params.height= height;
          params.setMargins(0, 0, 0, 0);
          //set icon is small screen
          mFullScreen = !fullScreen;
    }
	





}

	
}