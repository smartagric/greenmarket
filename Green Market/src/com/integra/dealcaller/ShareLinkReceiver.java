package com.integra.dealcaller;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.integra.dealcaller.R;
import com.integra.dealcaller.AndroidMultiPartEntity.ProgressListener;
import com.integra.dealcaller.SharePhoto.Share;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ShareLinkReceiver extends ActionBarActivity {
	
	private Uri fileUri; // file url to store image/video
	
		private static final int REQUEST_CODE = 100;
	    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	     
	    public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;
	    
	    private static final String TAG = ShareLinkReceiver.class.getSimpleName();
	    
	    private static final String TAG_SUCCESS = "success";
	    
	    public static final String url_share_photo2 = "http://activetalkgh.com/android_connect/share_video.php";
	    
	    public static String name = null;
	    public static String image = null;
	    public static String final_image = null;
	    public static String status ;
	    public EditText InputStatus;
	    public static String profilePic;
	    public static String from_email;
	    public static String postType;
	    private TextView linktextview,linktextview2;
	    private SharedPreferences prefs;
	    private String videoPath = null;
	 
	    private ProgressBar progressBar;
	    long totalSize = 0;
	
	private ImageView capture2;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_link_receiver);
        
        getSupportActionBar().setTitle("Share");
        
        
        prefs = getSharedPreferences("Chat", 0);
  		
  		
  		String username = prefs.getString("username", null);
  		String email = prefs.getString("email", null);
  		String profile = prefs.getString("profile_pic", null);
     
        
        progressBar = (ProgressBar)findViewById(R.id.progresslinkreceiver);
        InputStatus= (EditText)findViewById(R.id.say2receiver);
       
       
        
        linktextview = (TextView)findViewById(R.id.link_to_be_shared);
        linktextview2 = (TextView)findViewById(R.id.link_to_be_shared2);
        
        
        
        //Receiving Intents from other apps
        //get the received intent
   		final Intent receivedIntent = getIntent();
   		
   		 if(receivedIntent!=null){
   		//get the action
   		String receivedAction = receivedIntent.getAction();
   		//find out what we are dealing with
   		String receivedType = receivedIntent.getType();
   		
   		
   		//make sure it's an action and type we can handle
   		
   		if(receivedAction.equals(Intent.ACTION_SEND)){
   			if(receivedType.startsWith("text/plain")){
   				
   	            	
   	
   				if (receivedAction.equalsIgnoreCase(Intent.ACTION_SEND) && receivedIntent.hasExtra(Intent.EXTRA_TEXT)) {
   					
   				    String s = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
   				    String t = receivedIntent.getStringExtra(Intent.EXTRA_SUBJECT); 
   				    
   				    
   				    
   				 
   				    linktextview.setVisibility(View.VISIBLE);
   				    linktextview.setText(Html.fromHtml("<a href=\"" + s + "\">"
   						+ s + "</a> "));
   				    //linktextview.setText(s); //output: a TextView that holds the URL
   				    
   				 linktextview2.setVisibility(View.VISIBLE);
   				 linktextview2.setText(t);
   				} 
   					
   	            	
   			}else{
   				
   		
   			}
   			}
   		 }      
       
        
        
         
         //To be sent over to database
         
        name = username;
        status= InputStatus.getText().toString();
        profilePic= profile;
        from_email = email; 
        postType = "link";

       
        
        

        
	}

        
  
   
  
    
     
   
  
  
     
  
   
    
      
  
    
    class Share extends AsyncTask<String, String, String> {
     	 
    	String response = null; 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           
            
            
        }


        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {
        	
        	 

        	 // Building Parameters
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", name);
            params.put("final_image", final_image);
            params.put("status", InputStatus.getText().toString());
            params.put("profilePic", profilePic);
            params.put("fromEmail", from_email);
            params.put("postType", postType);
 
            // making fresh volley request and getting json
            
            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo2, params, new Response.Listener<JSONObject>() {

 			            public void onResponse(JSONObject response) {
 			            	
 			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
 			                VolleyLog.d(TAG, "Response: " + response.toString());
 			                if (response != null) {
 			                	
 			                	
 			                	 // check for success tag
 			                    try {
 			                        int success = response.getInt(TAG_SUCCESS);
 			         
 			                        if (success == 1) {
 			                            // successfully created product
 			                            Toast.makeText(getApplicationContext(), "Successfully shared", Toast.LENGTH_LONG).show();
 			                           
         			         
 			                            
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
   
    

 
    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        
                    	finish();
                        return;
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    
    
    
    

 
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
				

	   			
	   			 new Share().execute();
	   			 
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
	    }    
   

}






