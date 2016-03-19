package com.integra.dealcaller;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;



import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import com.android.volley.toolbox.ImageLoader;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CustomDialogClass extends Dialog implements
android.view.View.OnClickListener {

public Activity c;
public Dialog d;
Utils2 utils;

public Button yes, no;
public TextView ed1, ed2, ed3,ed4, ed5,ed6,ed7,ed8, tick;
public TextView message;
private static final String TAG_SUCCESS = "success";
public static Map<String, String> params2,params3;
public ProgressBar pp;
private static String currentDate;

public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/donor_info_update.php";

public CustomDialogClass(Activity a) {
super(a);
// TODO Auto-generated constructor stub
this.c = a;
}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.editable_dalog);
		
		
		
		//setTitle("Input Donor Info");
		pp = (ProgressBar)findViewById(R.id.progresser);
		message = (TextView)findViewById(R.id.message);
		ed1 = (TextView)findViewById(R.id.ButtonEdit3);
		ed2 = (TextView)findViewById(R.id.ButtonEdit4);
		
		ed3 = (TextView)findViewById(R.id.ButtonEdit6);
		ed4 = (TextView)findViewById(R.id.ButtonEdit7);
		ed5 = (TextView)findViewById(R.id.ButtonEdit8);
		
		tick = (TextView)findViewById(R.id.ticker);
		
		setCancelable(false);
		
		yes = (Button) findViewById(R.id.Sav);
		no = (Button) findViewById(R.id.Dis);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		currentDate = sdf.format(new Date());
        
        
        
        
       
        tick.setText("Click proceed to begin payment processing" );
        
        
       
        
		yes.setOnClickListener(this);
		no.setOnClickListener(this);
		
		}

			@Override
			public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Sav:
			 
					String code = GetCountryZipCode();
				 	params2 = new HashMap<String, String>();
		            
		           
		            params2.put("phone_number", "");
		            params2.put("date_of_donation", "");
		           
                   	
                   	
		                params3 = new HashMap<String, String>();
			            params3.put("username", "Samuel");
			            params3.put("password", "Samuelofori7");
			            params3.put("to", "");
			            params3.put("from", "");
				       
			            params3.put("message", "");
                 	    
		            
		            
		            new ShareMethod().execute();
			  break;
			case R.id.Dis:
			  dismiss();
			  break;
			default:
			  break;
			}
			
		

}
			
			
			//Share method both saves to db and sends SMS message
			 class ShareMethod extends AsyncTask<String, String, String> {
		     	 
			    	String response = null; 
			        /**
			         * Before starting background thread Show Progress Dialog
			         * */
			        @Override
			        protected void onPreExecute() {
			            super.onPreExecute();
			           
			           pp.setVisibility(View.VISIBLE);
			           pp.setProgress(0);
			           message.setVisibility(View.VISIBLE);
			            message.setText("Saving info to database");
			        }


			        /**
			        
			         * */
			        protected String doInBackground(String... args) {
			        	
			        		            // making fresh volley request and getting json
			            
			            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo3, params2, new Response.Listener<JSONObject>() {

			 			            public void onResponse(JSONObject response) {
			 			            	
			 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
			 			                
			 			                if (response != null) {
			 			                	
			 			                	
			 			                	 // check for success tag
			 			                    try {
			 			                        int success = response.getInt(TAG_SUCCESS);
			 			         
			 			                        if (success == 1) {
			 			                            // successfully created product
			 			                        	pp.setVisibility(View.GONE);
			 			    			          
			 			    			           message.setVisibility(View.VISIBLE);
			 			                        	message.setText("Successfully saved to database");
			 			                        	
			 			               ////////////Sending SMS Message
			 			                        	
			 			                        	
			 			                        	
			 							           
			 			                        			      
			 			                        	
			 			                        	 CustomRequest jsObjRequest = new CustomRequest(Method.POST, "http://activetalkgh.com/android_connect/send_sms.php", params3, new Response.Listener<JSONObject>() {

			 					 			            public void onResponse(JSONObject response) {
			 					 			            	
			 					 			            message.setText(response.toString());
			 					 			               
			 					 			                if (response != null) {
			 					 			                	
			 					 			                	try {
			 								                        int success = response.getInt(TAG_SUCCESS);
			 								                        
			 								                        
			 								         
			 								                        if (success == 1) {
			 								                        	
			 								                        	yes.setVisibility(View.GONE);
								 			       			            
								 			       			            no.setBackgroundColor(Color.parseColor("#31b573"));
								 			     			            
								 			       			            
								 			       			            no.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_grow_fade_in_from_bottom));
								 			       			            no.getAnimation().setDuration(3000);
								 			       			        
									 			       			        no.setText("Close");
								 			       			            no.setTextColor(Color.parseColor("#ffffff"));
				 					 			                  
								 			       			        message.setText("Saved to database and SMS sent");  
			 								                        	
								 			       			    message.setText(response.toString());
			 								                           
			 								                        }else  {
			 										                         
			 								                        	message.setText("Saved to database but SMS sending failed");
			 								                            
			 								                        } 
			 								                    } catch (JSONException e) {
			 								                        e.printStackTrace();
			 								                    }	
			 					 			                	
			 					 			                	
			 					 			                	 
			 					 			                    
			 					 			                }
			 					 			            }
			 					 			        }, new Response.ErrorListener() {

			 					 			            public void onErrorResponse(VolleyError error) {
			 					 			            	
			 					 			            	message.setText("Saved to database but SMS sending failed");
			 					 			              
			 					 			            }
			 					 			        });
			 					 			
			 					 			// Adding request to volley request queue
			 					 			AppController.getInstance().addToRequestQueue(jsObjRequest);
			 					 			
			 			                        	
		 			       			            
		 			       			            
		 			       			            
		 			       			            
			 			       
			 			                            
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
			 			           
			 			                
			 			            	pp.setVisibility(View.GONE);
	 			    			          
	 			    			           message.setVisibility(View.VISIBLE);
			 			               message.setText("Unable to save to database");
			 			              
			 			            }
			 			        });
			 			
			 			// Adding request to volley request queue
			 			AppController.getInstance().addToRequestQueue(jsObjRequest);
						return response;
			          
			        }
		}
			 
			 
			 
			
			 
			 
			 
			 public String GetCountryZipCode(){
				    String CountryID="";
				    String CountryZipCode="";

				    TelephonyManager manager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
				    //getNetworkCountryIso
				    CountryID= manager.getSimCountryIso().toUpperCase();
				    String[] rl=getContext().getResources().getStringArray(R.array.CountryCodes);
				    for(int i=0;i<rl.length;i++){
				        String[] g=rl[i].split(",");
				        if(g[1].trim().equals(CountryID.trim())){
				            CountryZipCode=g[0];
				            break;  
				        }
				    }
				    return CountryZipCode;
				}					
					
					
					
}

