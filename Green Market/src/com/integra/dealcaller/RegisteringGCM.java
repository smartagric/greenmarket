package com.integra.dealcaller;

import java.io.IOException;




import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.integra.dealcaller.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisteringGCM extends ActionBarActivity {
	
	public static String TAG = "RegisteringGCM";
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	String user_name = "";

	private static String save_reg_id = "http://activetalkgh.com/android_connect/save_reg_id.php";
	private int progress;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	String SENDER_ID = "436724293287";
	String API_KEY = "AIzaSyAO6Dai3ztXle8KR9WQPDNEKJ9Wfiud220";

	TextView mDisplay;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	private TextView detect;

	String regid;
	ListView list;
	EditText username;

	Context context;
	
	ProgressBar pb;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registering_gcm);
        
       
        	pb=(ProgressBar)findViewById(R.id.progressBarflat);
        	detect = (TextView)findViewById(R.id.textDetector);
        	
        	progress = 0;
        	
        	 prefs = getSharedPreferences("Chat", 0);
             context = getApplicationContext();
             if(!prefs.getString("REG_FROM","").equals("")){
             	
             	Intent tr = new Intent(getApplicationContext(), ChatActivity2.class);
             	startActivity(tr);
             	
             }else  if(!prefs.getString("REG_ID", "").equals("")){
             	
             	Toast.makeText(getApplicationContext(),"You are registered already"+prefs.getString("REG_ID", ""),Toast.LENGTH_SHORT).show();
             	
             	Intent tr = new Intent(getApplicationContext(), ChatActivity2.class);
             	startActivity(tr);

             }else if(checkPlayServices()){

                 new Register().execute();

             }else{
                 //device not supported
             }
         }
	
	private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
            	final DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        finish();
                    }
                };
                final Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                        resultCode, this, 10, cancelListener
                );
                errorDialog.show();
            
                
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }else if (resultCode == ConnectionResult.SERVICE_MISSING ||
                resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED ||
                resultCode == ConnectionResult.SERVICE_DISABLED) {
         Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1);
         dialog.show();
     }
        
        
        return true;
    }


    private class Register extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                    regid = gcm.register(SENDER_ID);
                    Log.e("RegId",regid);

                    prefs = getSharedPreferences("Chat", 0);
                    
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("registration_id", regid.toString());
                    edit.commit();

                 
                
                 
                }
                
                return  regid;
                

            } catch (IOException ex) {
                Log.e("Error", ex.getMessage());
             
              
               //unable to register
                return "Fails";

            }
        }
        @Override
        protected void onPostExecute(String json) {
        
        
      
       
        
        }
    }
    
    
    private class RegisterPerson extends AsyncTask<String, String, String> {
      	 
    	String response = null; 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisteringGCM.this);
            pDialog.setMessage("Registeration in progress...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        
        protected String doInBackground(String... args) {
        	
        	 

        	 // Building Parameters
            Map<String, String> params = new HashMap<String, String>();
            params.put("reg_id", regid.toString());
            
           
            
            // making fresh volley request and getting json
            
            CustomRequest jsObjRequest = new CustomRequest(Method.POST, save_reg_id, params, new Response.Listener<JSONObject>() {

 			            public void onResponse(JSONObject response) {
 			            	
 			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
 			                VolleyLog.d(TAG, "Response: " + response.toString());
 			                if (response != null) {
 			                	
 			                	
 			                	 // check for success tag
 			                    try {
 			                        int success = response.getInt(TAG_SUCCESS);
 			         
 			                        if (success == 1) {
 			                            // successfully created product
 			                            Intent i = new Intent(getApplicationContext(), TimeLine.class);
 			                            startActivity(i);
 			                           
     			                            if (success == 3) {
         			                            // successfully created product
         			                            Toast.makeText(getApplicationContext(), "gcm stored already", Toast.LENGTH_LONG).show();
         			         
 			                            
 			                        } else {
 			                            // failed to create product
 			                        }}
 			                    } catch (JSONException e) {
 			                        e.printStackTrace();
 			                    }	
 			                   
 			                  
 			                    
 			                    
 			                }
 			            }
 			        }, new Response.ErrorListener() {

 			            public void onErrorResponse(VolleyError error) {
 			            	
 			       
 			                VolleyLog.d(TAG, "Error: " + error.getMessage());
 			                pDialog.dismiss();
 			                
 			                Toast.makeText(getApplicationContext(), "Unable to save", Toast.LENGTH_LONG).show();
 			              
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
}