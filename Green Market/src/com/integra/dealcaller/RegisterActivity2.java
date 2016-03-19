package com.integra.dealcaller;

import java.io.IOException;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;



import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
 
public class RegisterActivity2 extends ActionBarActivity {
 
	
	
    // Progress Dialog
    private ProgressBar pDialog;
    private static String TAG = "RegisterActivity";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    JSONParser jsonParser = new JSONParser();
    private static Map<String, String> params;
    private static String picker_code;
    private ActionBar actionbar;
    String SENDER_ID = "436724293287";
    GoogleCloudMessaging gcm;
    private TextView tec;
    SharedPreferences prefs;
    SharedPreferences prefs2;
    Context context;
    String regid;
    
    private static CountryPicker picker;
    
    EditText inputEmail;
    EditText inputPassword;
    EditText confirmPassword;
    EditText inputName;
    EditText inputResidence;
   
    EditText inputPhoneNumber;
   
    
 
    // url to create new product
    private static String url_register = "http://activetalkgh.com/android_connect/green_register.php";
    private static Button btnSignUp; 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity2);
        actionbar = getSupportActionBar();
        actionbar.setTitle("Sign Up!");
       
        
        // Edit Text
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        confirmPassword = (EditText) findViewById(R.id.inputPassword2);
        inputName = (EditText) findViewById(R.id.inputName);
        pDialog = (ProgressBar)findViewById(R.id.progressBar_reg);
        tec = (TextView)findViewById(R.id.teks);
     
        inputPhoneNumber = (EditText) findViewById(R.id.inputFon);
        
        

        
        
        
        // Create button
        btnSignUp = (Button) findViewById(R.id.btnCreateProduct);
 
        
        
        
        
        
       
        
       
        
        
        final String code = GetCountryZipCode();
        
        
        // button click event
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            
            
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				btnSignUp.setEnabled(false);

           	 // Building Parameters
                params = new HashMap<String, String>();
               params.put("email", inputEmail.getText().toString());
               params.put("password", inputPassword.getText().toString());
               params.put("username", inputName.getText().toString());
               params.put("phonenumber", inputPhoneNumber.getText().toString());
             
               
               //Toast.makeText(getApplicationContext(), inputEmail.getText().toString()+inputPassword.getText()+inputName.getText().toString()+inputCountry.getText().toString()+inputPhoneNumber.getText().toString(), Toast.LENGTH_LONG).show();
               
               if (btnSignUp.getText()=="Continue"){
            	   
            	 Intent i = new Intent(getApplicationContext(), IntroActivity.class);
                    startActivity(i);
                    
                    finish();
               }else if(btnSignUp.getText()=="OK"){
            	   finish();
               }else{
				
            	   
            	   if (inputEmail.getText().toString().matches("")) {
            		   
            		   tec.setText("You did not enter an email.");
            		   tec.setTextColor(Color.parseColor("#df0024"));
            		   tec.setVisibility(View.VISIBLE);
            		   btnSignUp.setEnabled(true);
            		   
            	   }else if (isEmailValid(inputEmail.getText().toString())==false) {
           			tec.setText("Please enter a valid email address");
           			tec.setTextColor(Color.parseColor("#df0024"));
            		   tec.setVisibility(View.VISIBLE);
            		  btnSignUp.setEnabled(true);

            		}else if (inputPassword.getText().toString().matches("")) {
            			tec.setText("You did not enter a password.");
            			tec.setTextColor(Color.parseColor("#df0024"));
             		   tec.setVisibility(View.VISIBLE);
             		  btnSignUp.setEnabled(true);
             		   
            		}else if(!inputPassword.getText().toString().contentEquals(confirmPassword.getText().toString())){
            			tec.setText("Password mismatch...please retype your password");
            			tec.setTextColor(Color.parseColor("#df0024"));
              		   tec.setVisibility(View.VISIBLE);
              		 btnSignUp.setEnabled(true);
              		 
              		 
            		}else if (inputPassword.getText().toString().length()<6) {
            			tec.setText("Password should be at least 6 characters");
            			tec.setTextColor(Color.parseColor("#df0024"));
             		   tec.setVisibility(View.VISIBLE);
             		  btnSignUp.setEnabled(true);
              		   
            		}else if (inputName.getText().toString().matches("")) {
            			tec.setText("You did not enter your name.");
            			tec.setTextColor(Color.parseColor("#df0024"));
             		   tec.setVisibility(View.VISIBLE);
             		  btnSignUp.setEnabled(true);
             		   
            		}else if (inputPhoneNumber.getText().toString().matches("")) {
            			tec.setText("Please enter your phone number.");
            			tec.setTextColor(Color.parseColor("#df0024"));
             		   tec.setVisibility(View.VISIBLE);
             		  btnSignUp.setEnabled(true);
            		
            		
             		  
            		}else {
            			
            			tec.setText("");
            			new RegisterPerson().execute();
            		   
            		}
				
                 
				// Check if user filled the form
				//if(email.trim().length() > 0 && password.trim().length() > 0 && country.trim().length() > 0  && code.trim().length() > 0){
					
					//new RegisterPerson().execute();
					//finish();
					
				//}else{
					// user doen't filled that data
					// ask him to fill the form
					//Toast.makeText(getApplicationContext(), "Reqired field(s) missing", Toast.LENGTH_LONG).show();
				//}
				
               }
				
			}
 
                   });
    
 
    }
        class RegisterPerson extends AsyncTask<String, String, String> {
       	 
        	String response = null; 
            /**
             * Before starting background thread Show Progress Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog.setVisibility(View.VISIBLE);
                pDialog.setProgress(0);
                tec.setVisibility(View.VISIBLE);
                
            }


            
            protected String doInBackground(String... args) {
            	
            	 

               
                
                // making fresh volley request and getting json
                
                CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_register, params, new Response.Listener<JSONObject>() {

     			            public void onResponse(JSONObject response) {
     			            	
     			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
     			                VolleyLog.d(TAG, "Response: " + response.toString());
     			                if (response != null) {
     			                	
     			                	
     			                	 // check for success tag
     			                    try {
     			                        int success = response.getInt(TAG_SUCCESS);
     			         
     			                        if (success == 1) {
     			                            // successfully created product
     			                        	        
     			                        	Intent dashboard = new Intent(getApplicationContext(), TimeLine.class);
   					                     
     			                        	startActivity(dashboard);
   					           		
     			                        	finish();
     			                            
     			                            //finish();
     			       			                	
     			                       } else if (success == 0) {
    			                            // an error occurred
    			                            //Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_LONG).show();
    			                           tec.setText("Oops! an error occurred...try again later.");
    			                           tec.setTextColor(Color.parseColor("#df0024"));
    			                           pDialog.setVisibility(View.GONE);
    			                           btnSignUp.setEnabled(true);
		                        
     			                           
     			                        } else if (success == 2) {
             			                            // user exists already
             			                            //Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_LONG).show();
     			                        	tec.setText("An account exists with this email");
     			                        	tec.setTextColor(Color.parseColor("#df0024"));
     			                           pDialog.setVisibility(View.GONE);
     			                          btnSignUp.setEnabled(true);
     			                            
     			                        } else {
     			                            // failed to create product
     			                        	tec.setText("Oops! an error occurred...try again later.");
     			                        	tec.setTextColor(Color.parseColor("#df0024"));
     			                           pDialog.setVisibility(View.GONE);
     			                          btnSignUp.setEnabled(true);
     			                        	
     			                        }
     			                    } catch (JSONException e) {
     			                        e.printStackTrace();
     			                    }	
     			                   
     			                  
     			                    
     			                    
     			                }
     			            }
     			        }, new Response.ErrorListener() {

     			            public void onErrorResponse(VolleyError error) {
     			            	
     			       
     			                VolleyLog.d(TAG, "Error: " + error.getMessage());
     			                pDialog.setVisibility(View.GONE);
     			                tec.setVisibility(View.VISIBLE);
     			                tec.setText("failed to register, please try again in few minutes.");
     			               tec.setTextColor(Color.parseColor("#df0024"));
     			                //Toast.makeText(getApplicationContext(), "Unable to register", Toast.LENGTH_LONG).show();
     			               //btnSignUp.setText("OK");
     			              btnSignUp.setEnabled(true);
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
               
 
        		//Toast.makeText(getApplicationContext(), "Return check", Toast.LENGTH_LONG) .show();
        		  
        		

            }

            

 

        }   
        
       
        
        public static boolean isEmailValid(String email) {
            boolean isValid = false;

            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;

            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            }
            return isValid;
        }    
  
        
        public String GetCountryZipCode(){
            String CountryID="";
            String CountryZipCode="";

            TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            //getNetworkCountryIso
            CountryID= manager.getSimCountryIso().toUpperCase();
            String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
            for(int i=0;i<rl.length;i++){
                String[] g=rl[i].split(",");
                if(g[1].trim().equals(CountryID.trim())){
                    CountryZipCode=g[0];
                    break;  
                }
            }
            return CountryZipCode;
        }
       
        
        public void addListenerOnChk() {

        	

        	
        	
}
        
        
      
        
        
        
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
     if (keyCode == KeyEvent.KEYCODE_BACK) {
         // write your code here

    	 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage("All inputs will not be saved, discard activity? ");
	        builder.setIcon(R.drawable.ic_action_upload);
	        //builder.setTitle("Confirm...");
	        builder .setCancelable(false)
	          .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	              finish();
		        	
	           }
	       })
	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               dialog.cancel();
	           }
	       });
	        
	        
	        AlertDialog alertdialog = builder.create();
	        alertdialog.show();
     	}
     	return true;
        }
        
        
     
        
        public static void hideKeyboard(Activity activity) {
    	    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    	    //Find the currently focused view, so we can grab the correct window token from it.
    	    View view = activity.getCurrentFocus();
    	    //If no view currently has focus, create a new one, just so we can grab a window token from it
    	    if (view == null) {
    	        view = new View(activity);
    	    }
    	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    	}
        

        
        @Override
 	   public void onStop() {
 	       super.onStop();  // Always call the superclass method first

 	       if(picker!=null){
 	    	  
 	       }				
 	
 	}

}

