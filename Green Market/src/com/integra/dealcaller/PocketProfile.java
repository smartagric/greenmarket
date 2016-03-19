package com.integra.dealcaller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.integra.dealcaller.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PocketProfile extends ActionBarActivity {
	private String URL_FEED = "http://activetalkgh.com/android_connect/editpocket.php";
	private static final String TAG = PocketProfile.class.getSimpleName();
	private ProgressBar pb;
	private Integer progress;
	private SharedPreferences prefs;
		private TextView mobile_init;
	    private TextView email_init;
	    private TextView school_init;
	    private TextView work_init;
	    private TextView residence_init;
	
	private RoundedImageView pocket;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocket_profile);
 
        getSupportActionBar().setTitle("Pocket Profile");
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        
        pocket = (RoundedImageView)findViewById(R.id.pocketPhoto);
        
        pb=(ProgressBar)findViewById(R.id.progressBarPorket);
        
        
        registerForContextMenu(pocket);
        
        
        mobile_init = (TextView)findViewById(R.id.vv1);
                
        email_init = (TextView)findViewById(R.id.vv2);
                
        school_init = (TextView)findViewById(R.id.vv3);
                
        work_init = (TextView)findViewById(R.id.vv4);
     
        residence_init = (TextView)findViewById(R.id.vv5);
        
        
        
        
        
        progress=0;
        
        pb.setProgress(progress);
        
        
        
        
        
        
        
        
        Bundle i = getIntent().getExtras();
        if (i != null) {
      	  
        String selectedPhoto = i.getString("foto3");
        String passed_email = i.getString("person_email");
        
        
        if (passed_email!=null){
        	//do something
        	
        	prefs = getSharedPreferences("Chat", 0);
			String username = prefs.getString("username", null);
	  		String email = prefs.getString("email", null);
	  		String profile = prefs.getString("profile_pic", null);
             
             // Building Parameters
             Map<String, String> params = new HashMap<String, String>();
             params.put("email", passed_email);
             

             // making fresh volley request and getting json
             
             CustomRequest jsObjRequest = new CustomRequest(Method.POST, URL_FEED, params, new Response.Listener<JSONObject>() {

     			            public void onResponse(JSONObject response) {
     			            	
     			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
     			                VolleyLog.d(TAG, "Response: " + response.toString());
     			                if (response != null) {
     			                	
 
     			                	parseJsonFeed(response);
     			                	 // check for success tag
     			                   
     			                    
     			                }
     			            }
     			        }, new Response.ErrorListener() {

     			            public void onErrorResponse(VolleyError error) {
     			            	
     			       
     			                VolleyLog.d(TAG, "Error: " + error.getMessage());
     			                
     			                
     			                Toast.makeText(getApplicationContext(), "Unable to write", Toast.LENGTH_LONG).show();
     			              
     			            }
     			        });
     			
     			// Adding request to volley request queue
     			AppController.getInstance().addToRequestQueue(jsObjRequest);
     	
           
         
        }
        	
		
		
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
							
							pocket.setImageBitmap(null);
							pocket.setImageBitmap(response
											.getBitmap());
							
							// hide loader and show set &
							// download buttons
							
							
						}
					}
				});

    }
}

 //Long Press
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.pocketPhoto) {
		 
		  
	    
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
	
	
	/**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                
              
                
                String  mobile_no = feedObj.getString("mobile_number");
                mobile_init.setText(mobile_no);
                
                String  email = feedObj.getString("email");
                email_init.setText(email);
                
                String school =  feedObj.getString("school");
                school_init.setText(school);
                
                String work =  feedObj.getString("work");
                work_init.setText(work);
                
                String residence =  feedObj.getString("residence");
                residence_init.setText(residence);
                
 
               
            }
 
            // notify data changes to list adapater
           
            pb.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
        
    }     
    

}



