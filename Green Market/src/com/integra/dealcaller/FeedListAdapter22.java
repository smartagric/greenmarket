package com.integra.dealcaller;

import java.io.ByteArrayOutputStream;

import java.io.UnsupportedEncodingException;
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
import android.content.SharedPreferences;
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
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
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
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
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
import com.integra.dealcaller.TimeLineFragment.GetDataTask;
import com.squareup.picasso.Picasso;


public class FeedListAdapter22 extends BaseAdapter implements OnClickListener, OnTouchListener {	
	private Activity activity;
	private Resources res;
	private LayoutInflater inflater;
	private List<FeedItem22> feedItems;
	private List<Movie_main> movieList = new ArrayList<Movie_main>();
	private List<HorizontalFeedItem> feedItemx;
	private HorizontalFeedListAdapter hfi;
	private HorizontalListView listView_main;
	private MainInflateAdapter adapter_main;
	private SharedPreferences prefs;
	private static String[] dataObjects;
	
	private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    
    
	private TextView expressions, boo;
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
	private static final String URL_FEED2 = "http://activetalkgh.com/android_connect/helpees_timeline.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = FeedListAdapter22.class.getSimpleName();
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/shared_post.php";
	public static final String url_share_photo4 = "http://activetalkgh.com/android_connect/expressing.php";
	public static final String url_share_photo5 = "http://activetalkgh.com/android_connect/expressing2.php";
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
	int counter = 0;
	private static String forEmail;
	private static String forUsername;
	public static String covery = null;
	
	private int imageArra[] = { R.drawable.talkpage_icon, R.drawable.talker1,
			R.drawable.bill, R.drawable.talker1,
			R.drawable.bill, R.drawable.home,
			R.drawable.house, R.drawable.bill };
  
    
	float oldTouchValue;
	float lastX = 0;
    
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	
	public FeedListAdapter22() {
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
	

	public FeedListAdapter22(Activity activity, List<FeedItem22> feedItems) {
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
		
		
		
		viewHolder = new ViewHolder();
		

		  
		  if (type == 4) {
			 
			  
			  	
					if (imageLoader == null)
						imageLoader = AppController.getInstance().getImageLoader();
	

         		    convertView = inflater.inflate(R.layout.main_inflate, parent, false);
         		    
         		  adapter_main = new MainInflateAdapter(activity,movieList);
         			listView_main = (HorizontalListView)convertView.findViewById(R.id.listee_main);
         			listView_main.setAdapter(adapter_main);
         			
         			
         			
         		// Gesture detection
         	       // gestureDetector = new GestureDetector(activity, new MyGestureDetector());
         	        //gestureListener = new View.OnTouchListener() {
         	         //   public boolean onTouch(View v, MotionEvent event) {
         	            	
         	          //  	v.getParent().requestDisallowInterceptTouchEvent(false);
         	            	
         	           //     return gestureDetector.onTouchEvent(event);
         	               
         	          //  }
         	       // };
         	        
         			
         	       //listView_main.setOnTouchListener(gestureListener);
         	        		
         			
        
         			listView_main.setOnTouchListener(new OnTouchListener() {
         				
         	   			@Override
         	   	        public boolean onTouch(View view, MotionEvent motionEvent) {
         	   			TimeLineFragment.listView.getParent().requestDisallowInterceptTouchEvent(true);
         	   	            
         	   	            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
         	   	            case MotionEvent.ACTION_UP:
         	   	            TimeLineFragment.listView.getParent().requestDisallowInterceptTouchEvent(false);
         	   	                
         	   	                
         	   	            float currentX = motionEvent.getX();
          	               if (oldTouchValue < currentX)
          	               {
          	                   // swiped left
          	            	   
          	            	 TimeLineFragment.listView.getParent().requestDisallowInterceptTouchEvent(true);
          	            	view.getParent().requestDisallowInterceptTouchEvent(true);
          	               }
          	               if (oldTouchValue > currentX )
          	               {
          	                   //swiped right
          	            	   
          	            	 TimeLineFragment.listView.getParent().requestDisallowInterceptTouchEvent(true);
          	            	 view.getParent().requestDisallowInterceptTouchEvent(true);
          	               }
          		             
         	   	             
         	   	                break;
         	   	                
         	   	         case MotionEvent.ACTION_DOWN:
         	   	        	 
         	   	        	TimeLineFragment.listView.getParent().requestDisallowInterceptTouchEvent(false);
         		                break;   
         	   	            
         	   	            }
         	   	            return false;
         	   	        }
         			});

         			
         			
                
         			
         			
         			 // We first check for cached request
         	        Cache cache = AppController.getInstance().getRequestQueue().getCache();
         	        Entry entry = cache.get(URL_FEED2);
         	        if (entry != null) {
         	            // fetch the data from cache
         	            try {
         	                String data = new String(entry.data, "UTF-8");
         	                try {
         	                	
         	                	movieList.clear();
         	                	adapter_main.notifyDataSetChanged();
         	                	
         	                    parseJsonFeedd(new JSONObject(data));
         	               
         	                } catch (JSONException e) {
         	                    e.printStackTrace();
         	                }
         	            } catch (UnsupportedEncodingException e) {
         	                e.printStackTrace();
         	            }
         	 
         	        } 
         	       
         			
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
                                    	
                                    	Toast.makeText(activity,"Error getting feeds", Toast.LENGTH_LONG).show();
                                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                                        //pb.setVisibility(View.GONE);

                                    }
                                });
             
                        // Adding request to volley request queue
                        AppController.getInstance().addToRequestQueue(jsonReq2);
                    
                    
		  } else{
			  
			convertView = inflater.inflate(R.layout.feed_item22, null);
		

		if (imageLoader == null) 
			imageLoader = AppController.getInstance().getImageLoader();
		
		viewHolder = new ViewHolder();

		viewHolder.name = (TextView) convertView.findViewById(R.id.name4);
		viewHolder.name.setOnClickListener(this);

		viewHolder.timestamp = (TextView) convertView.findViewById(R.id.timestamp4);
		
		viewHolder.comments = (TextView) convertView.findViewById(R.id.comments4);
		
		viewHolder.expressions = (TextView) convertView.findViewById(R.id.expressions4);
		
		expressions = (TextView) convertView.findViewById(R.id.expressions4);
		boo = (TextView) convertView.findViewById(R.id.boo);
		
		
		viewHolder.boo = (TextView) convertView.findViewById(R.id.boo);
		
		viewHolder.lesson = (TextView) convertView.findViewById(R.id.txtLesson);
		
		viewHolder.statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg4);
		
		viewHolder.url = (TextView) convertView.findViewById(R.id.txtUrl4);
		viewHolder.url.setOnClickListener(this);
		
		viewHolder.profilePic = (NetworkImageView3) convertView.findViewById(R.id.profilePic4);
		viewHolder.profilePic.setOnClickListener(this);
		
		viewHolder.feedImageView = (com.integra.dealcaller.NetworkImageView2) convertView.findViewById(R.id.feedImage14);
		viewHolder.feedImageView.setOnClickListener(this);
		
		viewHolder.feedVideoView = (VideoView) convertView.findViewById(R.id.video_view);
		viewHolder.feedVideoView=new VideoView(activity);

		viewHolder.emotion = (ImageButton) convertView.findViewById(R.id.ButtonEmotion4);
		viewHolder.emotion.setOnClickListener(this);
		
		viewHolder.comment = (Button) convertView.findViewById(R.id.ButtonComment4);
		viewHolder.comment.setOnClickListener(this);
		
		viewHolder.share = (Button) convertView.findViewById(R.id.ButtonShare4);
		viewHolder.share.setOnClickListener(this);
		
		convertView.setTag(viewHolder);
		
		imageCache = new LruBitmapCache();
		final FeedItem22 item = feedItems.get(position);
		
		
		
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
		
		
		// Chcek for empty status message
				if (!TextUtils.isEmpty(item.getLesson())) {
					viewHolder.lesson.setText("Lesson : "+ item.getLesson());
					viewHolder.lesson.setVisibility(View.VISIBLE);
				} else {
					// status is empty, remove from view
					viewHolder.lesson.setVisibility(View.GONE);
				}
		
		
		// Check for empty comments 
				if (item.getComments()>0) {
					viewHolder.comments.setText("comments: "+ item.getComments());
				} else {
					//viewHolder.comments.setVisibility(View.GONE);
					viewHolder.comments.setText("No Comments yet");
				}
		
		// Check for empty expressions 
				if (item.getExp()>0) {
					viewHolder.expressions.setText("Yo!: "+item.getExp());
				} else {
					//viewHolder.expressions.setVisibility(View.GONE);
					viewHolder.expressions.setText("Yo!: 0");
				}

				
				// Check for empty expressions 
				if (item.getBoos()>0) {
					viewHolder.boo.setText("Boos: "+item.getBoos());
				} else {
					//viewHolder.expressions.setVisibility(View.GONE);
					viewHolder.boo.setText("Boo: 0");
				}

		// Checking for null feed url
		if (item.getUrl() != null) {
			
			viewHolder.url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
					+ item.getUrl() + "</a> "));
			
			viewHolder.url.setVisibility(View.VISIBLE);
		} else {
			// url is null, remove from the view
			viewHolder.url.setVisibility(View.GONE);
		}

		
		
		
		
		// user profile pic
		viewHolder.profilePic.setImageUrl(item.getProfilePic(), imageLoader);
		
		


		// Feed image
		if(item.getImge() != null){
        	
			
			
			
			viewHolder.feedVideoView.setVisibility(View.GONE);
        	viewHolder.feedImageView.setImageUrl(item.getImge(), imageLoader);
        	viewHolder.feedImageView.setVisibility(View.VISIBLE);
			
			
			//MediaController mc = new MediaController(activity);
			//mc.setAnchorView(viewHolder.feedVideoView);
			//mc.setMediaPlayer(viewHolder.feedVideoView);
			//viewHolder.feedVideoView.setMediaController(mc);
			//viewHolder.feedVideoView.setVideoURI(Uri.parse(fg));		
			//viewHolder.feedVideoView.setVisibility(View.VISIBLE);

			//viewHolder.feedVideoView.requestFocus();
			//viewHolder.feedVideoView.start();
			 
        }else{
        	
        	String fg = item.getImge().toString();
			viewHolder.feedImageView.setVisibility(View.GONE);
        
        }
		
		
		
		viewHolder.comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

	        	Intent iv = new Intent(activity, CommentDialog.class);
	        
	           
	        	iv.putExtra("foto2", item.getImge());
	        	iv.putExtra("username", item.getName());
	        	iv.putExtra("email", item.getEmail());
		
	            activity.startActivity(iv);
	            
	           
	            
				
			}
		});
		
		
		viewHolder.profilePic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

	          
	            
	        
	           
				
			}
		});
		
		
		viewHolder.url.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+item.getUrl()));
 				 activity.startActivity(browserIntent);
				
			}
		});

		
		viewHolder.share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				postType = "SharedPost";
				
				prefs = activity.getSharedPreferences("Chat", 0);
				
				
				String username = prefs.getString("username", null);
				String profile = prefs.getString("profile_pic", null);
				String email = prefs.getString("email", null);
				

	        	 // Building Parameters
	            params2 = new HashMap<String, String>();
	            
	            params2.put("name", username);
	            params2.put("final_image", item.getImge());
	            params2.put("status", username+" shared "+item.getName()+"'s post :"+item.getStatus());
	            params2.put("profilePic", profile);
	            params2.put("fromEmail", email);
	            params2.put("postType", postType);
	            params2.put("forUsername", item.getName());
	            params2.put("forEmail", item.getEmail());

	            //Performing Share method
	            
	            new ShareMethod().execute();
	        
	           
				
			}
		});

		viewHolder.feedImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

	        	Intent i = new Intent(activity, ImageViewer.class);
	        
	           
	        	i.putExtra("foto", item.getImge());
		
	            activity.startActivity(i);
	            
	           
	            
				
			}
		});
		
		
		viewHolder.emotion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
		
				
				//final String [] items = new String[] {"Like", "Congratulate","Laugh", "Feeling Motivated","Sympathize"};
				final String [] items = new String[] {"Yo!", "boo"};
				
	            //final Integer[] icons = new Integer[] {R.drawable.like, R.drawable.congrats,R.drawable.laugh, R.drawable.motivated,R.drawable.sympathize};
	            
	            final Integer[] icons = new Integer[] {R.drawable.like, R.drawable.sympathize};

	            ListAdapter adapter = new ArrayAdapterWithIcon(activity, items, icons);

	            
	            
	            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	            
	           builder.setTitle("your expression...").setAdapter(adapter, new DialogInterface.OnClickListener() {
	                    
	        	   public void onClick(DialogInterface dialog, int arg0 ) {
	        		   
	        		   
	        		   
	        		   switch (arg0) {
	                   case 0:
	                	   //int i = item.getExp();
                   		//int ii = i+1;

                   		//Toast.makeText(activity, Integer.toString(ii), Toast.LENGTH_LONG).show();
                   		
                   		//expressions.setText("");
                          // expressions.setText("Yo!:" + Integer.toString(ii));
                          // expressions.setVisibility(View.VISIBLE);
                          // expressions.refreshDrawableState();
                           
                      // ExpressionType1 = "Like";
       				
                       //prefs = activity.getSharedPreferences("Chat", 0);
       				
       				
       				//String username = prefs.getString("username", null);
       				//String profile = prefs.getString("profile_pic", null);
       				//String email = prefs.getString("email", null);

       	        	 // Building Parameters
       	            paramsExp1 = new HashMap<String, String>();
       	            
       	            //paramsExp1.put("name", username);
       	            //paramsExp1.put("image_url", item.getImge());
       	            //paramsExp1.put("fromEmail", email);
       	            //paramsExp1.put("expressionType", ExpressionType1);
       	            paramsExp1.put("timestamp", item.getTimeStamp());
       	            paramsExp1.put("forEmail", item.getEmail());
       	            
       	            new Expression1_method().execute();
       	            
	                break;
	                       
	                       
	                   case 1:
	                	   int iv = item.getBoos();
                   		int iiv = iv+1;
                   		
                   		//boo.setText("");
                           //boo.setText("Boo:" + Integer.toString(iiv));
                           //boo.setVisibility(View.VISIBLE);
                           //boo.refreshDrawableState();
                           
                           //ExpressionType2 = "Congratulate";
                           
                           //prefs = activity.getSharedPreferences("Chat", 0);
           				
           				
           				//String usernamev = prefs.getString("username", null);
           				//String profilev = prefs.getString("profile_pic", null);
           				//String emailv = prefs.getString("email", null);

	        	        	 // Building Parameters
	        	            paramsExp2 = new HashMap<String, String>();
	        	            
	        	            //paramsExp2.put("name", usernamev);
	        	            //paramsExp2.put("image_url", item.getImge());
	        	            //paramsExp2.put("fromEmail", emailv);
	        	            //paramsExp2.put("expressionType", ExpressionType2);
	        	            paramsExp2.put("timestamp", item.getTimeStamp());
	        	            paramsExp2.put("forEmail", item.getEmail());
	        	            
	        	            new Expression2_method().execute();
	                       break;
	               }
	                    	
	                    	
	                    	
	                    	
	                    }}).show();
	           
	            
				
			}
		});
		
		  
		viewHolder = (ViewHolder)convertView.getTag();
		  } //ends here
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
	           
	            
	            //Toast.makeText(activity, "Starting to share", Toast.LENGTH_LONG).show();
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
	 			                           
	 			                          new GetDataTask();
	 			                            
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
	 			                
	 			                
	 			                Toast.makeText(activity, "Unable to yo!", Toast.LENGTH_LONG).show();
	 			              
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
	           

	    		//Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	        	

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
	           
	            
	            //Toast.makeText(activity, "Starting to share", Toast.LENGTH_LONG).show();
	        }


	        /**
	         * Deleting product
	         * */
	        protected String doInBackground(String... args) {
	        	
	        		            // making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo5, paramsExp2, new Response.Listener<JSONObject>() {

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
	 			                           
	 			                            new GetDataTask();
	 			                            
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
	 			                
	 			                
	 			                Toast.makeText(activity, "Unable to boo!", Toast.LENGTH_LONG).show();
	 			              
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
	           

	    		//Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

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
         ViewFlipper flipper;
         TextView timestamp;
         TextView name;
         TextView comments;
         TextView expressions;
         TextView statusMsg;
         TextView lesson;
         TextView boo;
         TextView url = null;
         FeedImageView inlayFeed;
         NetworkImageView3 profilePic;
         com.integra.dealcaller.NetworkImageView2 feedImageView = null;
         VideoView feedVideoView;
         ImageButton emotion;
         Button comment;
         Button share;
		
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
 
                Movie_main item = new Movie_main();
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
    
    
    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                   
                	//left swipe
                	
                    
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    
                	//Right swipe
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

            @Override
        public boolean onDown(MotionEvent e) {
              return true;
        }
    }

}
