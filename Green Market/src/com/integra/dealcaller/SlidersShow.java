package com.integra.dealcaller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.integra.dealcaller.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class SlidersShow extends Activity{

	
	 private String URL_FEED = "http://activetalkgh.com/android_connect/commitment_page_detail2.php";
		ArrayList<String> actorsList;
		
		ViewFlipper viewFlipper;
		

		@Override
		protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_sliders_show);
		    
		    viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
		    
		    
		    
		    actorsList = new ArrayList<String>();
		    
		 
		  
		   
				     
		     // making fresh volley request and getting json
	            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
	                    URL_FEED, null, new Response.Listener<JSONObject>() {
	 
	                        public void onResponse(JSONObject response) {
	                         
	                            if (response != null) {
	                                parseJsonFeed(response);

	                         
	                            }
	                            
	                            
	                        }
	                    }, new Response.ErrorListener() {
	 
	                        public void onErrorResponse(VolleyError error) {
	                            Toast.makeText(getApplicationContext(), "failed to get images", Toast.LENGTH_LONG).show();
	                          
	                      
	                        }
	                    });
	 
	            // Adding request to volley request queue
	            AppController.getInstance().addToRequestQueue(jsonReq);
		     

		  
		     
		     
		            
		        
		        
		    
		    
			
			
		 
		    
		    

		    // handler to set duration and to upate animation
		    final Handler mHandler = new Handler();

		    // Create runnable for posting
		    final Runnable mUpdateResults = new Runnable() {
		        public void run() {
		            AnimateandSlideShow();
		        }
		    };

		    int delay = 500;
		    int period = 4000;

		    Timer timer = new Timer();
		    timer.scheduleAtFixedRate(new TimerTask() {

		        public void run() {
		            mHandler.post(mUpdateResults);
		        }
		    }, delay, period);



		}

	
		  
		  
		  
		  private void setFlipperImage(ArrayList<String> actorsList) {

		 for(int i=0;i<actorsList.size();i++){
		 Log.i("Set Filpper Called", actorsList.get(i).toString()+"");
		 ImageView image = new ImageView(getApplicationContext());
		// image.setBackgroundResource(res);
		Picasso.with(SlidersShow.this)
		.load(actorsList.get(i).toString())
		.placeholder(R.drawable.ic_launcher)
		.error(R.drawable.ic_launcher)
		.into(image);
		 viewFlipper.addView(image);
		 }
		}
		  
		  

/**
 * Parsing json reponse and passing the data to feed view list adapter
 * */
private void parseJsonFeed(JSONObject response) {
    try {
        JSONArray feedArray = response.getJSONArray("feed");

        for (int i = 0; i < feedArray.length(); i++) {
            JSONObject feedObj = (JSONObject) feedArray.get(i);

            
            
            
            
            
         // Pic1 might be null sometimes
            String p = feedObj.isNull("photo1") ? null : feedObj
                    .getString("photo1");
            
            actorsList.add(p);
            
            // Pic2 might be null sometimes
            String p2 = feedObj.isNull("photo2") ? null : feedObj
                    .getString("photo2");
            
            actorsList.add(p2);
     
         // Pic3 might be null sometimes
            String p3 = feedObj.isNull("photo3") ? null : feedObj
                    .getString("photo3");
            
            actorsList.add(p3);
            
         // Pic4 might be null sometimes
            String p4 = feedObj.isNull("photo4") ? null : feedObj
                    .getString("photo4");
            
            actorsList.add(p4);

            // Pic5 might be null sometimes
            String p5 = feedObj.isNull("photo5") ? null : feedObj
                    .getString("photo5");
            
            actorsList.add(p5);
   
        }

        setFlipperImage( actorsList);
       
        
    } catch (JSONException e) {
        e.printStackTrace();
    }
}



		// method to show slide show
		 private void AnimateandSlideShow() {
		 viewFlipper.showNext();
		 }}

