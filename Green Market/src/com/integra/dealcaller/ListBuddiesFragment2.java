package com.integra.dealcaller;

import android.content.Intent;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ListBuddiesFragment2 extends Fragment  {
    private static final String TAG = ListBuddiesFragment2.class.getSimpleName();
    int mMarginDefault;
    int[] mScrollConfig;
    private boolean isOpenActivities;
    private CircularAdapter mAdapterLeft;
    private CircularAdapter mAdapterRight;
    private CustomListAdapter6 mmAdapterLeft;
    private CustomListAdapter6 mmAdapterRight;
    @InjectView(R.id.listbuddies2)
    ListBuddiesLayout2 mListBuddies;
    private List<String> mImagesLeft = new ArrayList<String>();
    private List<String> mImagesRight = new ArrayList<String>();
    private ObservableListView firstlist,secondlist;
    private List<Movie6> movieList = new ArrayList<Movie6>();
    private String URL_FEED = "http://activetalkgh.com/talkers/health.php";
    private  JsonObjectRequest  movieReq;
    private ProgressBar pb;
    private int progress;
    private List<String> mmImagesLeft = new ArrayList<String>();
    private List<String> mmImagesRight = new ArrayList<String>();

    public static ListBuddiesFragment2 newInstance(boolean isOpenActivitiesActivated) {
        ListBuddiesFragment2 fragment = new ListBuddiesFragment2();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ExtraArgumentKeys.OPEN_ACTIVITES.toString(), isOpenActivitiesActivated);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOpenActivities = getArguments().getBoolean(ExtraArgumentKeys.OPEN_ACTIVITES.toString(), false);
       mMarginDefault = getResources().getDimensionPixelSize(R.dimen.default_margin_between_lists);
        mScrollConfig = getResources().getIntArray(R.attr.scrollFaster);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        ButterKnife.inject(this, rootView);

        pb = (ProgressBar)rootView.findViewById(R.id.progressBar2);


        //If we do this we need to uncomment the container on the xml layout
        createListBuddiesLayoutDinamically(rootView);
        mListBuddies.setListeners();
        mImagesLeft.addAll(Arrays.asList(ImagesUrls.imageUrls_left));
        mImagesRight.addAll(Arrays.asList(ImagesUrls.imageUrls_right));
        
        
        
        //mAdapterLeft = new CircularAdapter(getActivity(), getResources().getDimensionPixelSize(R.dimen.item_height_small), mImagesLeft);
       
        //mAdapterRight = new CircularAdapter(getActivity(), getResources().getDimensionPixelSize(R.dimen.item_height_tall), mImagesRight);
        
        mmAdapterLeft = new CustomListAdapter6(getActivity(), movieList);
        mmAdapterRight = new CustomListAdapter6(getActivity(), movieList);
        
        firstlist = (ObservableListView)rootView.findViewById(R.id.list_left2);
        secondlist = (ObservableListView)rootView.findViewById(R.id.list_right2);
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
                           //pb.setVisibility(View.GONE);
                         
                       }
                   });

           // Adding request to volley request queue
           AppController.getInstance().addToRequestQueue(movieReq);
       
        return rootView;
    }

    private void createListBuddiesLayoutDinamically(View rootView) {
        mListBuddies = new ListBuddiesLayout2(getActivity());
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
       	mmAdapterRight.notifyDataSetChanged();
       	
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                Movie6 movie = new Movie6();
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

	       	//movieReq.cancel();
	      
	       }
}