package com.integra.dealcaller;


import static com.integra.dealcaller.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.integra.dealcaller.CommonUtilities.EXTRA_MESSAGE;
import static com.integra.dealcaller.CommonUtilities.SENDER_ID;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.integra.dealcaller.R;
import com.integra.dealcaller.WakeLocker;


import java.io.IOException;


public class MainActivity2 extends ActionBarActivity {

	private int progressStatus = 0;
	public static TextView textView;
	private Handler handler = new Handler();
	public static ProgressBar progressBar;
	public static ImageView box;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static Button lets_go;
    String SENDER_ID = "436724293287";


    static final String TAG = "L2C";

    GoogleCloudMessaging gcm;
    SharedPreferences prefs;
    PrefManager preffy;
    Context context;
    String regid;
    String fail;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        
        
        prefs = getSharedPreferences("Chat", 0);
        context = getApplicationContext();
        
        
        
        progressBar = (ProgressBar) findViewById(R.id.progressBarflatter);
        textView = (TextView)findViewById(R.id.txtPercentage2);
        box = (ImageView)findViewById(R.id.imageview_flatter);
        
        lets_go = (Button)findViewById(R.id.continu);
        
        lets_go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(lets_go.getText()=="OK, later..."){
				finish();
			}else{
				Intent bv = new Intent(getApplicationContext(), TimeLine.class);
				startActivity(bv);
				finish();
			}
			}
		});
         
        
        
       
      
        
      
        //registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
        
        
        
        
       if(checkPlayServices()){

    	
            new Register().execute();

        }else{
            Toast.makeText(getApplicationContext(),"This device is not supported",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                Toast.makeText(context, "This device is not supported",  Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }


    private class Register extends AsyncTask<String, String, String> {

    	  @Override
          protected void onPreExecute() {
              super.onPreExecute();
             
            
      						
      			progressBar.setProgress(0);
      			
      	
              
    	  }
    	  
        @Override
        protected String doInBackground(String... args) {
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                    
                    gcm.unregister();
                    regid = gcm.register(SENDER_ID);
                    
                    Log.e("RegId",regid);

                  

                }

                return  regid;
                

            } catch (IOException ex) {
                Log.e("Error", ex.getMessage());
                
            fail = ex.getMessage().toString();
                return fail;

            }
        }
        @Override
        protected void onPostExecute(String json) {
           
        	//Toast.makeText(context, fail,  Toast.LENGTH_LONG).show();
        	//Toast.makeText(context, "First attempt:" +regid,  Toast.LENGTH_LONG).show();
        	
        	

        	
        	
        	if(fail!=null) {

                //Android 2.2 gmc bug http://stackoverflow.com/questions/19269607/google-cloud-messaging-register-authentication-failed

                //fall back to old deprecated GCM client library

        		 // Get GCM registration id
                final String regId = GCMRegistrar.getRegistrationId(getApplicationContext());
         
                // Check if regid already presents
                if (regId.equals("")) {
                    // Registration is not present, register now with GCM  
                	
                	try {
						
                		GCMRegistrar.register(getApplicationContext(), SENDER_ID);
					} catch (Exception e) {
						// TODO: handle exception
						//Toast.makeText(getApplicationContext(), "failed to do deprecated registration", Toast.LENGTH_LONG).show();
					}
                    
                	//Determine what to do with the regid
                    //Toast.makeText(getApplicationContext(), regId, Toast.LENGTH_LONG).show();
                    
                   
        			
                } else {
                    // Device is already registered on GCM
                    //if (GCMRegistrar.isRegisteredOnServer(getApplicationContext())) {
                        // Skips registration.             
                        //Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), regId, Toast.LENGTH_LONG).show();
                        
                        GCMRegistrar.unregister(getApplicationContext());
                        
                        try {
    						
                    		GCMRegistrar.register(getApplicationContext(), SENDER_ID);
    					} catch (Exception e) {
    						// TODO: handle exception
    						//Toast.makeText(getApplicationContext(), "failed to do deprecated registration2", Toast.LENGTH_LONG).show();
    						
    						
    					}
                        
                        //Toast.makeText(getApplicationContext(), regId, Toast.LENGTH_LONG).show();
                

                   }	
        	
        	
        	
                
        }else{
        	
        }
    
        	 
        	 	
        	
        	
        	
        
        }
    
}
    
    /**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
		WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message
						
			//Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	
	@Override
	protected void onDestroy() {
		
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			//Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
	
	
	public void Forward(){
		
		
	}
}
