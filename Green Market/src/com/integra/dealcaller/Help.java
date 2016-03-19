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
import com.integra.dealcaller.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Help extends ActionBarActivity implements OnClickListener {
	
	 private String URL_FEED = "http://activetalkgh.com/android_connect/Help.php";
	private static String holder_id;
	private TextView Bank;
	private Button Send;
	private TextView BankBranch;
	private SharedPreferences prefs;
	private TextView AccountName;
	private TextView AccountNumber;
	private static String PhoneNumber = "0574932005";
	private static String smsMessage = "From Activetalk";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_dialog_custom_layout);
        
        
        Bank = (TextView)findViewById(R.id.txtBank);
        BankBranch = (TextView)findViewById(R.id.txtBankBranch);
        AccountName = (TextView)findViewById(R.id.txtAccountName);
        AccountNumber = (TextView)findViewById(R.id.txtAccountNumber);
        Send = (Button)findViewById(R.id.btn_login);
        
        
        
        Bundle i = getIntent().getExtras();
        if (i != null) {
      	  
        holder_id = i.getString("holder_id");
      
        } 
        
        
        
        Send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				 sendSmsByManager();
				
			}
		});
        
        
        Map<String, String> params = new HashMap<String, String>(); 
        params.put("holder_id", holder_id);
        
        
        
        
        /**
         * Use this query to display search results like
         * 1. Getting the data from SQLite and showing in listview
         * 2. Making webrequest and displaying the data
         * For now we just display the query only
         */
        
        // making fresh volley request and getting json
        
        CustomRequest jsObjRequest = new CustomRequest(Method.POST, URL_FEED, params, new Response.Listener<JSONObject>() {

		            public void onResponse(JSONObject response) {
		            	
		            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
		                
		                if (response != null) {
		                	
		                	
		                	parseJsonFeed(response);
		                   
		                  
		                    
		                    
		                }
		            }
		        }, new Response.ErrorListener() {

		            public void onErrorResponse(VolleyError error) {
	                
		                Toast.makeText(getApplicationContext(), "Unable to fetch data", Toast.LENGTH_LONG).show();
		              
		            }
		        });
		
		// Adding request to volley request queue
		AppController.getInstance().addToRequestQueue(jsObjRequest);

        

     
        
        
       
        
    }
	
	/**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");
 
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
 
                
                prefs = getSharedPreferences("Chat", 0);
				
				
				String username = prefs.getString("username", null);
				String profile = prefs.getString("profile_pic", null);
				String email = prefs.getString("email", null);
				
                
                String  bank_name = feedObj.getString("bank_name");
                Bank.setText(bank_name);
                
                String  branch = feedObj.getString("branch");
                BankBranch.setText(branch);
                
                String account_name =  feedObj.getString("account_name");
                AccountName.setText(account_name);
                
                String account_number =  feedObj.getString("account_number");
                AccountNumber.setText(account_number);
                
                
                
 
               
            }
 
            // notify data changes to list adapater
           
        
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
        
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}     
    
	public void sendSmsByManager() {
		
			        try {
		
			            // Get the default instance of the SmsManager
		
			            SmsManager smsManager = SmsManager.getDefault();
		
			            smsManager.sendTextMessage(PhoneNumber,
		
			                    null, 
	
			                    smsMessage,
		
			                    null,
		
			                    null);
		
			            Toast.makeText(getApplicationContext(), "Sms sent to 0574932005, 50p will be deducted from your balance",
		
			                    Toast.LENGTH_LONG).show();
		
			        } catch (Exception ex) {
		
			            Toast.makeText(getApplicationContext(),"Your sms has failed...",
		
			                    Toast.LENGTH_LONG).show();
		
			            ex.printStackTrace();
		
			        }
		
			    }
}



