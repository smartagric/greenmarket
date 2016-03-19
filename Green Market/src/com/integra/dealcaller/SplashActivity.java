package com.integra.dealcaller;



import java.util.ArrayList;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.integra.dealcaller.R;

public class SplashActivity extends ActionBarActivity {
	private static final String TAG = SplashActivity.class.getSimpleName();
	private static final String TAG_FEED = "feed", TAG_ENTRY = "entry",
			TAG_GPHOTO_ID = "gphoto$id", TAG_T = "$t",
			TAG_ALBUM_TITLE = "title";
	private ProgressBar pDialog;
	SharedPreferences prefs;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		
		pDialog = (ProgressBar)findViewById(R.id.progressBarSplash);

		prefs = getSharedPreferences("Chat", 0);
		
		
		String reggy = prefs.getString("REG_ID", null);
		
		
		 if(reggy !=null){
	        	
			 new LoggingIn().execute();	
	            
		 }else{
			
			
			finish();
			
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						
						Intent dashboardt = new Intent(getApplicationContext(), MainActivity2.class);
			            
			            startActivity(dashboardt);
					

					} catch (Exception e) {
						Log.d(TAG, "Exception : " + e.getMessage());
					}
				}
			};

			thread.start();
        	
			
		 }
	

	}
	
	class LoggingIn extends AsyncTask<String, String, String> {
		 
		String response = null; 
	    /**
	     * Before starting background thread Show Progress Dialog
	     * */
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        pDialog.setVisibility(View.VISIBLE);
	        
	        pDialog.setProgress(0);
	        
	       
	        
	    }


	    /**
	     * Deleting product
	     * */
	    protected String doInBackground(String... args) {
	    	
	    	 

	        // Check for success tag
	    	try { 
	    		
	    		String tt = "tt";
	    		String tt2 = "tt";
	    		
	    		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();  
	    		postParameters.add(new BasicNameValuePair("username", tt));  
	    		postParameters.add(new BasicNameValuePair("password", tt2)); 
	    		
	    		
	    		
	    		response = CustomHttpClient.executeHttpPost("http://activetalkgh.com/android_connect/truelogin.php", postParameters);  //Enetr Your remote PHP,ASP, Servlet file link  
	    		String res=response.toString();  
	    		// res = res.trim();  
	    		res= res.replaceAll("\\s+","");  
	    		//error.setText(res); 
	    		//Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG) .show();
	    		
	    	
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
			return response;

	      
	    }

	    /**
	     * After completing background task Dismiss the progress dialog
	     * **/
	    protected void onPostExecute(String file_url) {
	        // dismiss the dialog once product deleted
	       
	        
	        String res=response.toString();  
	 
		
			
	        if(res.contains("true")) {

	            
	        	 Intent dashboard = new Intent(getApplicationContext(), TimeLine.class);
	             
	             startActivity(dashboard);
	   		
	        }
	        
			 
			else{ 
			
			
			Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_LONG) .show();
			
			Intent dashboard2 = new Intent(getApplicationContext(), LoginActivity.class);
            
            startActivity(dashboard2);

			  
			}
	        
	        pDialog.setVisibility(View.GONE);
	   	
	   		}

	    }

	
	

	}
