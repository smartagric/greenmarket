package com.integra.dealcaller;

import android.content.Intent;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.integra.dealcaller.CircularAdapter;
import com.integra.dealcaller.DetailActivity;
import com.integra.dealcaller.ExtraArgumentKeys;
import com.integra.dealcaller.ImagesUrls;
import com.integra.dealcaller.ListBuddiesLayout;
import com.integra.dealcaller.R;
import com.integra.dealcaller.ScrollConfigOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ListBuddiesFragment extends Fragment implements OnClickListener,OnTouchListener {
    private static final String TAG = ListBuddiesFragment.class.getSimpleName();
    int mMarginDefault;
    int[] mScrollConfig;
    private boolean isOpenActivities;
    private CircularAdapter mAdapterLeft;
    private CircularAdapter mAdapterRight;
    private CustomListAdapter mmAdapterLeft;
    private CustomListAdapterAA mmAdapterRight;
    @InjectView(R.id.listbuddies)
    public static ListBuddiesLayout mListBuddies;
    private List<String> mImagesLeft = new ArrayList<String>();
    private List<String> mImagesRight = new ArrayList<String>();
    private ObservableListView firstlist,secondlist;
    private List<Movie> movieList = new ArrayList<Movie>();
    private List<MovieAA> movieListaa = new ArrayList<MovieAA>();
    private String URL_FEED = "http://activetalkgh.com/talkers/channels1.php";
    private String URL_FEED2 = "http://activetalkgh.com/talkers/channels2.php";
    private ProgressBar pb;
    private int progress;
    private  JsonObjectRequest movieReq,movieReq2;
    private List<String> mmImagesLeft = new ArrayList<String>();
    private List<String> mmImagesRight = new ArrayList<String>();

    public static ListBuddiesFragment newInstance(boolean isOpenActivitiesActivated) {
        ListBuddiesFragment fragment = new ListBuddiesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ExtraArgumentKeys.OPEN_ACTIVITES.toString(), isOpenActivitiesActivated);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //isOpenActivities = getArguments().getBoolean(ExtraArgumentKeys.OPEN_ACTIVITES.toString(), false);
       mMarginDefault = getResources().getDimensionPixelSize(R.dimen.default_margin_between_lists);
        mScrollConfig = getResources().getIntArray(R.attr.scrollFaster);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);

        pb = (ProgressBar)rootView.findViewById(R.id.progressBar);
        //If we do this we need to uncomment the container on the xml layout
        createListBuddiesLayoutDinamically(rootView);
        
        
        mListBuddies.setListeners();
       
      
        mImagesLeft.addAll(Arrays.asList(ImagesUrls.imageUrls_left));
        mImagesRight.addAll(Arrays.asList(ImagesUrls.imageUrls_right));
        
        
        
        //mAdapterLeft = new CircularAdapter(getActivity(), getResources().getDimensionPixelSize(R.dimen.item_height_small), mImagesLeft);
       
        //mAdapterRight = new CircularAdapter(getActivity(), getResources().getDimensionPixelSize(R.dimen.item_height_tall), mImagesRight);
        
        mmAdapterLeft = new CustomListAdapter(getActivity(), movieList);
        mmAdapterRight = new CustomListAdapterAA(getActivity(), movieListaa);
        
        firstlist = (ObservableListView)rootView.findViewById(R.id.list_left);
        secondlist = (ObservableListView)rootView.findViewById(R.id.list_right);
        mListBuddies.setAdapters(mmAdapterLeft, mmAdapterRight);
        
        
        progress = 0;	
        pb.setVisibility(View.VISIBLE);			
        pb.setProgress(progress);      
        
        
        //firstlist.setAdapter(mmAdapterLeft);
        //secondlist.setAdapter(mmAdapterRight);
        
        
        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                	
                	movieList.clear();
               	 	mmAdapterLeft.notifyDataSetChanged();
               	
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
 
        }
        
        
     // We first check for cached request
        Cache cache2 = AppController.getInstance().getRequestQueue().getCache();
        Entry entry2 = cache2.get(URL_FEED2);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry2.data, "UTF-8");
                try {
                	
                	movieListaa.clear();
               	
               	 	mmAdapterRight.notifyDataSetChanged();
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
                    	   Toast.makeText(getActivity(),"Response: " + "Error", Toast.LENGTH_LONG).show();
                  
                           VolleyLog.d(TAG, "Error: " + error.getMessage());
                           pb.setVisibility(View.GONE);
                         
                       }
                   });

           // Adding request to volley request queue
           AppController.getInstance().addToRequestQueue(movieReq);
           
           
           
           
           // Creating volley request obj
           // making fresh volley request and getting json
              movieReq2 = new JsonObjectRequest(Method.GET,
                      URL_FEED2, null, new Response.Listener<JSONObject>() {

                          public void onResponse(JSONObject response) {
                          	
                          //Toast.makeText(getActivity(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
                              VolleyLog.d(TAG, "Response: " + response.toString());
                              if (response != null) {
                              	
                              	
                              	parseJsonFeed2(response);
                                 
                                
                                  
                                  
                              }
                          }
                      }, new Response.ErrorListener() {

                          public void onErrorResponse(VolleyError error) {
                       	   //Toast.makeText(getActivity(),"Response: " + "Error", Toast.LENGTH_LONG).show();
                     
                              VolleyLog.d(TAG, "Error: " + error.getMessage());
                              pb.setVisibility(View.GONE);
                            
                          }
                      });

              // Adding request to volley request queue
              AppController.getInstance().addToRequestQueue(movieReq2);
       
        return rootView;
    }

    private void createListBuddiesLayoutDinamically(View rootView) {
        mListBuddies = new ListBuddiesLayout(getActivity());
        resetLayout();
        //Once the container is created we can add the ListViewLayout into it
        ((FrameLayout)rootView.findViewById(R.id.listbuddies_container)).addView(mListBuddies);
    }

   // @Override
    //public void onBuddyItemClicked(AdapterView<?> parent, View view, int buddy, int position, long id) {
       // if (isOpenActivities) {
           // Intent intent = new Intent(getActivity(), DetailActivity.class);
           // intent.putExtra(DetailActivity.EXTRA_URL, getImage(buddy, position));
           // startActivity(intent);
       // } else {
          //  Resources resources = getResources();
           // Toast.makeText(getActivity(), resources.getString(R.string.list) + ": " + buddy + " " + resources.getString(R.string.position) + ": " + position, Toast.LENGTH_SHORT).show();
       // }
    //}

    private String getImage(int buddy, int position) {
        return buddy == 0 ? ImagesUrls.imageUrls_left[position] : ImagesUrls.imageUrls_right[position];
    }

    public void setGap(int value) {
        mListBuddies.setGap(value);
    }

    public void setSpeed(int value) {
        mListBuddies.setSpeed(value);
    }

    public void setDividerHeight(int value) {
        mListBuddies.setDividerHeight(value);
    }

    public void setGapColor(int color) {
        mListBuddies.setGapColor(color);
    }

    public void setAutoScrollFaster(int option) {
        mListBuddies.setAutoScrollFaster(option);
    }

    public void setScrollFaster(int option) {
        mListBuddies.setManualScrollFaster(option);
    }

    public void setDivider(Drawable drawable) {
        mListBuddies.setDivider(drawable);
    }

    public void setOpenActivities(Boolean openActivities) {
        this.isOpenActivities = openActivities;
    }

    public void resetLayout() {
        mListBuddies.setGap(mMarginDefault)
                .setSpeed(ListBuddiesLayout.DEFAULT_SPEED)
                .setDividerHeight(mMarginDefault)
                .setGapColor(getResources().getColor(R.color.black))
                .setAutoScrollFaster(mScrollConfig[ScrollConfigOptions.RIGHT.getConfigValue()])
                .setManualScrollFaster(mScrollConfig[ScrollConfigOptions.LEFT.getConfigValue()])
                .setDivider(getResources().getDrawable(R.drawable.divider));
    }
    
    
    
    private void parseJsonFeed(JSONObject response) {
        try {
      	  
      	  	movieList.clear();
       	 mmAdapterLeft.notifyDataSetChanged();
     
       	
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
            mmAdapterLeft.notifyDataSetChanged();
      
           	
            pb.setVisibility(View.GONE);
            
            //pb.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    
    private void parseJsonFeed2(JSONObject response) {
        try {
      	  
      	  	movieListaa.clear();
       	
       	mmAdapterRight.notifyDataSetChanged();
       	
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                MovieAA movie = new MovieAA();
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
            
                movieListaa.add(movie);

            }
 
            // notify data changes to list adapater
         
           	mmAdapterRight.notifyDataSetChanged();
           	
            pb.setVisibility(View.GONE);
            
            //pb.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    
    
    @Override
	   public void onPause() {
	       super.onPause();  // Always call the superclass method first

	       	movieReq.cancel();
	      
	       }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}