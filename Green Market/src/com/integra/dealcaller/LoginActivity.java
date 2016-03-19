package com.integra.dealcaller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.integra.dealcaller.R;
  
public class LoginActivity extends ActionBarActivity {  

	
private ProgressDialog pDialog;
private static final String TAG_SUCCESS = "success";
private CustomRequest jsObjRequest;
private SharedPreferences prefs;
private String pro, cov, ema, username, gcm, gro;
private static Map<String, String> params;
public static final String url = "http://activetalkgh.com/android_connect/green_truelogin2.php";

EditText un,pw;  
TextView error;  
Button ok, Link; 
// flag for Internet connection status
Boolean isInternetPresent = false;
 
// Connection detector class
ConnectionDetector cd;


/** Called when the activity is first created. */  
@Override  
public void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	
	android.support.v7.app.ActionBar actionBar = getSupportActionBar();
	actionBar.hide();
	
	
	
  
setContentView(R.layout.activity_mainny);  

prefs = getSharedPreferences("Chat", 0);

cd = new ConnectionDetector(getApplicationContext());

un=(EditText)findViewById(R.id.loginEmail);  
pw=(EditText)findViewById(R.id.loginPassword); 


int maxWidth = 100;

un.setMaxWidth(maxWidth);
pw.setMaxWidth(maxWidth);



ok=(Button)findViewById(R.id.btnLogin);
Link=(Button)findViewById(R.id.btnLinkToRegisterScreen);




Bundle i = getIntent().getExtras();
if (i != null) {
	  
String reg_id = i.getString("REG_ID");
Toast.makeText(getApplicationContext(), reg_id, Toast.LENGTH_LONG).show();
}


Link.setOnClickListener(new View.OnClickListener() {  
	  
@Override  
  
public void onClick(View v) { 
	
	 Intent dashboard = new Intent(getApplicationContext(), RegisterActivity2.class); //RegisterActivity2
     
     startActivity(dashboard);
	
    
}
});







ok.setOnClickListener(new View.OnClickListener() {  
  
@Override  
  
public void onClick(View v) { 
	
	params = new HashMap<String, String>();
	
	params.put("email", un.getText().toString());
    params.put("password", pw.getText().toString());
    
    
	 // get Internet status
    isInternetPresent = cd.isConnectingToInternet();

    // check for Internet status
    if (isInternetPresent) {
        // Internet Connection is Present
        // make HTTP requests
       
    	
    	new Login().execute();	
	      
	       
    
    } else {
        // Internet connection is not present
        // Ask user to connect to Internet
    	
    	Toast.makeText(getApplicationContext(), "Please Check your internet connection and try again", Toast.LENGTH_LONG).show();
    }
	
}
});
}


//TODO Auto-generated method stub  


//String valid = "1";  
 


class Login extends AsyncTask<String, String, String> {
  	 
	String response = null; 
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Attempting login. Please wait...");
      
        pDialog.setCancelable(true);
        pDialog.show();
        
        
        AppController.getInstance().getRequestQueue().getCache().remove(url);
    }


    /**
     * Following
     * */
    protected String doInBackground(String... args) {
    	
    	

        // making fresh volley request and getting json
        
         jsObjRequest = new CustomRequest(Method.POST, url, params, new Response.Listener<JSONObject>() {

			            public void onResponse(JSONObject response) {
			            	
			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
			              
			                if (response != null) {
			                	
			                	String res=response.toString();  
			                	 // check for success tag
			                    try {
			                        int success = response.getInt(TAG_SUCCESS);
			                        
			                        
			                        
			                
			                        if (success == 1) {
			                         
			                        	JSONArray feedArray = response.getJSONArray("feed");
				                        
				                        for (int i = 0; i < feedArray.length(); i++) {
				                            JSONObject feedObj = (JSONObject) feedArray.get(i);
				                            
				                            pro = feedObj.isNull("profilePic") ? null : feedObj
				                                    .getString("profilePic");
				             
				                            cov = feedObj.isNull("coverPhoto") ? null : feedObj
				                                    .getString("coverPhoto");
				                            
				                            ema = feedObj.isNull("email") ? null : feedObj
				                                    .getString("email");
				                            
				                            username = feedObj.isNull("username") ? null : feedObj
				                                    .getString("username");
				                            
				                            gcm = feedObj.isNull("gcm_regid") ? null : feedObj
				                                    .getString("gcm_regid");
				                            
				                            gro = feedObj.isNull("groupType") ? null : feedObj
				                                    .getString("groupType");
				                            
				                        }
			                            
			                            SharedPreferences.Editor edit = prefs.edit();
			                            edit.putString("loginstatus", "");
			                            edit.putString("loginstatus", "loggedin");
			                            
			                            edit.putString("profile_pic", "");
			                            edit.putString("profile_pic", pro);
			                            
			                            edit.putString("cover_photo", "");
			                            edit.putString("cover_photo", cov);
			                            
			                            edit.putString("email", "");
			                            edit.putString("email", ema);
			                            
			                            edit.putString("username", "");
			                            edit.putString("username", username);
			                            
			                            edit.putString("gcm", "");
			                            edit.putString("gcm", gcm);
			                            
			                            edit.putString("gro", "");
			                            edit.putString("gro", gro);
			                            
			                            edit.commit();

			                            
			                        	Intent dashboard = new Intent(getApplicationContext(), TimeLine.class);
					                     
					                     startActivity(dashboard);
					           		
					                     finish();
			                        }
			                        
					                 else if (success == 2) {
					                         
					                	 Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_LONG) .show(); 
					                	 pDialog.dismiss();
			                            
			                        } else {
			                            
			                        	Toast.makeText(getApplicationContext(), "Oops an error occurred", Toast.LENGTH_LONG) .show();
			                        	pDialog.dismiss();
			                        	
			                        }
			                    } catch (JSONException e) {
			                        e.printStackTrace();
			                    }	
			                   

			                	
			                   
			                  
			                    
			                    
			                }
			            }
			        }, new Response.ErrorListener() {

			            public void onErrorResponse(VolleyError error) {
			            	
			       
			                
			                
			                Toast.makeText(getApplicationContext(), "No Reliable Connection", Toast.LENGTH_LONG).show();
			                pDialog.dismiss();
			              
			            }
			        });
			
			// Adding request to volley request queue
        
			AppController.getInstance().addToRequestQueue(jsObjRequest);
			AppController.getInstance().getRequestQueue().getCache().remove(url);
			
		return response;
      
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once product deleted
       

		//Toast.makeText(getApplicationContext(), "Return check", Toast.LENGTH_LONG) .show();
		  
		

    }
    }

@Override
public void onPause() {
    super.onPause();  // Always call the superclass method first

    //AppController.getInstance().getRequestQueue().getCache().remove(url);
    //jsObjRequest.cancel();
    }



@Override
	public void onStop() {
	super.onStop();  // Always call the superclass method first

	//AppController.getInstance().getRequestQueue().getCache().remove(url);
	//jsObjRequest.cancel();
}
}






