package com.integra.dealcaller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.google.android.gcm.GCMRegistrar;
import com.integra.dealcaller.R;

public class ChatActivity2 extends ActionBarActivity {

	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	Utils2 utils;
	private String URL_FEED4 = "http://activetalkgh.com/android_connect/commitment_page_detail2.php";
	static String TAG = "GCM DEMO";
	String user_name;
	String regid;
	private static String chattingToName,chattingToName2, chattingToDeviceID, little, firstMessage, chattingToDeviceID2;
	private static String regIde;
	private android.support.v7.app.ActionBar actionBar;
	private SharedPreferences prefs;
	String SENDER_ID = "436724293287";
	private static String API_KEY = "AIzaSyAO6Dai3ztXle8KR9WQPDNEKJ9Wfiud220"; // this is the old API key, new one is in the notepad file

	EditText edtMessage;
	ListView chatLV;
	DBOperation dbOperation;
	
	ChatListAdapter chatAdapater;
	ArrayList<ChatPeople> ChatPeoples;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity2);

		prefs = getSharedPreferences("Chat", 0);
		
		utils = new Utils2(this);

		chatLV = (ListView) findViewById(R.id.listViewchat);
		chatLV.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		chatLV.setStackFromBottom(true);
		
		
		edtMessage = (EditText) findViewById(R.id.editText_message);

		ChatPeoples = new ArrayList<ChatPeople>();
		actionBar = getSupportActionBar();
		
		
		
		Bundle b = getIntent().getExtras();

		if (b != null) {
			//user_name = b.getString("chattingFrom");
			chattingToName = b.getString("chattingToName");
			chattingToDeviceID = b.getString("chattingToDeviceID");
			
			chattingToName2 = b.getString("chattingTo");
			chattingToDeviceID2= b.getString("chattingToDeviceID2");
			//regIde = b.getString("myregistrationID");
			
			
			Log.i(TAG, "Chat From : " + user_name + " >> Chatting To : "
					+ chattingToName);
			
			
			
		little = b.getString("little_icon");
		
			
		 if(chattingToName !=null){
			 actionBar.setTitle(chattingToName);
	            }else{
	            	actionBar.setTitle(chattingToName2);
	            }
			
			//Bitmap bmap = CustomListAdapter00.thumbNail.getDrawingCache();

		    //Drawable d = new BitmapDrawable(getResources(), bmap);

			//actionBar.setIcon(d);

		}

		
		
		

		dbOperation = new DBOperation(this);
		dbOperation.createAndInitializeTables();

		populateChatMessages();

		
		
		registerReceiver(mHandleMessageReceiver, new IntentFilter("CHAT_MESSAGE_RECEIVED"));
	}

	private void populateChatMessages() {

		getData();
		
		
		
		if (ChatPeoples.size() > 0) {
			chatAdapater = new ChatListAdapter(this, ChatPeoples);
			chatLV.setAdapter(chatAdapater);
		}

	}

	void clearMessageTextBox() {

		edtMessage.clearFocus();
		edtMessage.setText("");

		hideKeyBoard(edtMessage);

	}

	private void hideKeyBoard(EditText edt) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
	}

	void addToDB(ChatPeople curChatObj) {

		ChatPeople people = new ChatPeople();
		ContentValues values = new ContentValues();
		values.put(people.getPERSON_NAME(), curChatObj.getPERSON_NAME());
		values.put(people.getPERSON_CHAT_MESSAGE(),
				curChatObj.getPERSON_CHAT_MESSAGE());
		values.put(people.getPERSON_DEVICE_ID(),
				curChatObj.getPERSON_DEVICE_ID());
		values.put(people.getPERSON_CHAT_TO_FROM(),
				curChatObj.getPERSON_CHAT_TO_FROM());
		values.put(people.getPERSON_EMAIL(), "demo_email@email.com");
		dbOperation.open();
		long id = dbOperation.insertTableData(people.getTableName(), values);
		dbOperation.close();
		if (id != -1) {
			Log.i(TAG, "Succesfully Inserted");
		}

		populateChatMessages();
	}

	void getData() {

		ChatPeoples.clear();

		///Populate based on sender's ID not receipient
		
		 if(chattingToDeviceID !=null){
			 Cursor cursor = dbOperation.getDataFromTable(chattingToDeviceID, chattingToName);
			 
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					do {
						// Log.i(TAG,
						// "Name = " + cursor.getString(0) + ", Message = "
						// + cursor.getString(1) + " Device ID = "
						// + cursor.getString(2));

						ChatPeople people = addToChat(cursor.getString(0),
								cursor.getString(1), cursor.getString(3));
						
						ChatPeoples.add(people);
						actionBar.setTitle(people.getPERSON_NAME());
					} while (cursor.moveToNext());
				}
				cursor.close();
				
	            }else{
	            	Cursor cursor = dbOperation.getDataFromTable(chattingToDeviceID2, chattingToName);
	            	if (cursor.getCount() > 0) {
	        			cursor.moveToFirst();
	        			do {
	        				// Log.i(TAG,
	        				// "Name = " + cursor.getString(0) + ", Message = "
	        				// + cursor.getString(1) + " Device ID = "
	        				// + cursor.getString(2));

	        				ChatPeople people = addToChat(cursor.getString(0),
	        						cursor.getString(1), cursor.getString(3));
	        				
	        				ChatPeoples.add(people);
	        				actionBar.setTitle(people.getPERSON_NAME());
	        			} while (cursor.moveToNext());
	        		}
	        		cursor.close();
	            }
		
		
		
	
		
		

	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			
			Bundle b = intent.getExtras();

			String message = b.getString("message");
			String to = b.getString("chattingTo");
			String from = b.getString("chattingFrom");
			String regid = b.getString("registrationIDs");
			String regide = b.getString("myregistrationID");
			
			//Toast.makeText(getApplicationContext(), message + to + from + regide, Toast.LENGTH_LONG).show();

			Log.i(TAG, " Received in Activity " + message + ", NAME = "
					+ chattingToName + ", dev ID = " + chattingToDeviceID);

			// this demo this is the same device
			
			
			if(chattingToName !=null){
				ChatPeople curChatObj = addToChat(chattingToName, message,
						"Received");
				
				addToDB(curChatObj); // adding to db
				
	            }else{
	            	ChatPeople curChatObj = addToChat(chattingToName2, message,
	        				"Received");
	            	
	            	addToDB(curChatObj); // adding to db
	            }
			
			
		
			
			
			

			
			populateChatMessages();
			chatAdapater.notifyDataSetChanged();

		}
	};

	public void sendMessage() {

		final String messageToSend = edtMessage.getText().toString().trim();

		
		
  		String username = prefs.getString("username", null);
  		String regIde = prefs.getString("gcm", null);
  		

		if (messageToSend.length() > 0) {

		
			Log.i(TAG, "sendMessage");

			 Map<String, String> params = new HashMap<String, String>();
	            params.put("message", messageToSend);
	            
	            if(chattingToDeviceID !=null){
	            params.put("registrationIDs", chattingToDeviceID);
	            params.put("myregistrationID", regIde);
	            }else{
	            	params.put("registrationIDs", chattingToDeviceID2);
	            	params.put("myregistrationID", regIde);	
	            }
	            
	            
	            
	            
	            
	            
	            if(chattingToName !=null){
	            	params.put("chattingTo", chattingToName);
		            }else{
		            	params.put("chattingTo", chattingToName2);
		            }
	            
	            params.put("chattingFrom", username);
	            params.put("apiKey", API_KEY);
	            
	            //Toast.makeText(getApplicationContext(), chattingToDeviceID2 + "" + chattingToName2, Toast.LENGTH_LONG).show();
	            
	String urrl = utils.getCurrentIPAddress() + "GCM/gcm_engine.php";
	//String urrl = utils.getCurrentIPAddress() + "gcm_server_php/send_message.php";


	// making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, urrl, params, new Response.Listener<JSONObject>() {

	 			            public void onResponse(JSONObject response) {
	 			            	
	 			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	 			                VolleyLog.d(TAG, "Response: " + response.toString());
	 			                if (response != null) {
	 			                	
	 			                	
	 			                	 // check for success tag
	 			                    //try {
	 			                       // int success = response.getInt(TAG_SUCCESS);
	 			         
	 			                        //if (success == 1) {
	 			                            // successfully created product
	 			                            //Toast.makeText(getApplicationContext(), "Successfully shared", Toast.LENGTH_LONG).show();
	 			                           
	         			         
	 			                            
	 			                        //} else {
	 			                            // failed to create product
	 			                       // }
	 			                    //} catch (JSONException e) {
	 			                        //e.printStackTrace();
	 			                   // }	
	 			                   
	 			                  
	 			                    
	 			                    
	 			                }
	 			            }
	 			        }, new Response.ErrorListener() {

	 			            public void onErrorResponse(VolleyError error) {
	 			            	
	 			       
	 			                VolleyLog.d(TAG, "Error: " + error.getMessage());
	 			                
	 			                
	 			                Toast.makeText(getApplicationContext(), "Unable to send", Toast.LENGTH_LONG).show();
	 			              
	 			            }
	 			        });
	 			
	 			// Adding request to volley request queue
	 			AppController.getInstance().addToRequestQueue(jsObjRequest);
	 			
				return;
	          
	        
		}

	}

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mHandleMessageReceiver);
	}

	ChatPeople addToChat(String personName, String chatMessage, String toOrFrom) {

		//Log.i(TAG, "inserting : " + personName + ", " + chatMessage + ", "
				//+ toOrFrom + " , " + chattingToDeviceID);
		
		ChatPeople curChatObj = new ChatPeople();
		
		if(personName !=null){
			curChatObj.setPERSON_NAME(personName);
            }else{
            	curChatObj.setPERSON_NAME(chattingToName2);
            }
		//curChatObj.setPERSON_NAME(personName);
		
		curChatObj.setPERSON_CHAT_MESSAGE(chatMessage);
		curChatObj.setPERSON_CHAT_TO_FROM(toOrFrom);
		
		if(chattingToDeviceID !=null){
            curChatObj.setPERSON_DEVICE_ID(chattingToDeviceID);
            }else{
            	curChatObj.setPERSON_DEVICE_ID(chattingToDeviceID2);
            }
		
		//curChatObj.setPERSON_DEVICE_ID(chattingToDeviceID);
		
		
		curChatObj.setPERSON_EMAIL("demo@gmail.com");

		return curChatObj;

	}

	public void onClick(final View view) {

		if (view == findViewById(R.id.send)) {

			ChatPeople curChatObj = addToChat(chattingToName, edtMessage
					.getText().toString().trim(), "Sent");
			addToDB(curChatObj); // adding to db

			sendMessage();

			clearMessageTextBox();

		}

	}
}
