package com.integra.dealcaller;

import java.util.HashMap;


import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;


import com.integra.dealcaller.R;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateStatus extends ActionBarActivity implements OnClickListener {
	
	private EditText stat;
	private EditText feeling;
	private EditText location;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = UpdateStatus.class.getSimpleName();
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/share_status.php";
	private static String postType;
	private static Map<String, String> params;
	private ImageView btnforstat;
	private ImageView btnforfeeling;
	private ImageView btnforlocation;
	private ImageButton butto;
	private FrameLayout emo;
	private Button post;
	private Button cancel;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_status);
        
        getSupportActionBar().setTitle("Update your status");
        
        butto = (ImageButton)findViewById(R.id.new_btn);
        emo = (FrameLayout)findViewById(R.id.emojicons);
        
        //stat = (EditText)findViewById(R.id.stat);
        //feeling = (EditText)findViewById(R.id.feeling);
        //location= (EditText)findViewById(R.id.location);
        
        //btnforstat= (ImageView)findViewById(R.id.btnforstat);
        //btnforfeeling= (ImageView)findViewById(R.id.btnforfeeling);
        //btnforlocation= (ImageView)findViewById(R.id.btnforlocation);
        
        
        
        
        //String statusWords = stat.getText().toString();
        //String feelingWords = feeling.getText().toString();
        //String locationWords = location.getText().toString();
        //postType = "Status";
        
        //PrefManager pp = new PrefManager(this);

   	 // Building Parameters
        // Building Parameters
        //params = new HashMap<String, String>();
        
        //params.put("name", pp.getUserName());
       
        //params.put("status", statusWords +"--feeling"+feelingWords+"  @"+locationWords);
        //params.put("profilePic", pp.getprofile());
        //params.put("fromEmail", pp.getEmail());
        //params.put("postType", postType);
        
        
     butto.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			emo.setVisibility(View.VISIBLE);
		}
	});
      
        
    }
	
	class Update_Status extends AsyncTask<String, String, String> {
   	 
    	String response = null; 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           
            Toast.makeText(getApplicationContext(), "Starting to react", Toast.LENGTH_LONG).show();
            
        }


        /**
         * Following
         * */
        protected String doInBackground(String... args) {
        	
        	
 
            // making fresh volley request and getting json
            
            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo3, params, new Response.Listener<JSONObject>() {

 			            public void onResponse(JSONObject response) {
 			            	
 			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
 			                VolleyLog.d(TAG, "Response: " + response.toString());
 			                if (response != null) {
 			                	
 			                	
 			                	 // check for success tag
 			                    try {
 			                        int success = response.getInt(TAG_SUCCESS);
 			         
 			                        if (success == 1) {
 			                            // successfully created product
 			                            Toast.makeText(getApplicationContext(), "Successfully followed", Toast.LENGTH_LONG).show();
 			                           
         			         
 			                            
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
 			                
 			                
 			                Toast.makeText(getApplicationContext(), "Unable to write", Toast.LENGTH_LONG).show();
 			              
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
           

    		Toast.makeText(getApplicationContext(), "Return check", Toast.LENGTH_LONG) .show();
    		  
    		

        }

 
}

	
	 @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	       getMenuInflater().inflate(R.menu.actionbar5, menu);
	           return true;
	   }
	   
	   
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        int itemId = item.getItemId();
			if (itemId == R.id.action_post) {
				
				new Update_Status().execute();
	   			
	   			// new Share().execute();
	   			 
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
	    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	   
	   
	   
}



