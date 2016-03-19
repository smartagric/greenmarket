package com.integra.dealcaller;

import static com.integra.dealcaller.CommonUtilities.SENDER_ID;
import static com.integra.dealcaller.CommonUtilities.displayMessage;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.google.android.gcm.GCMBaseIntentService;
import com.integra.dealcaller.R;

public class GCMIntentService2 extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService2";
	private static String regID;
	private static String message1, to, from, regId, myregId;
	public static final int NOTIFICATION_ID = 1;
	DBOperation dbOperation;
	public static final String url = "http://activetalkgh.com/android_connect/create_user.php";
    private SharedPreferences prefs;
    private static final String TAG_SUCCESS = "success";
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

    public GCMIntentService2() {
        super(SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        //displayMessage(context, "Your device registred with GCM");
        regID = registrationId;
        
        prefs = getSharedPreferences("Chat", 0);
	       
	        SharedPreferences.Editor edit = prefs.edit();
	        edit.putString("registration_id", regID);
	        
	        edit.commit();
	        
	        
        new Create().execute();
        
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        //displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        //Log.i(TAG, "Received message");
    	
    	dbOperation = new DBOperation(this);
		dbOperation.createAndInitializeTables();
		
        String message = intent.getExtras().getString("message");
        to = intent.getExtras().getString("chattingTo");
        from = intent.getExtras().getString("chattingFrom");
        regId = intent.getExtras().getString("registrationIDs");
        myregId = intent.getExtras().getString("myregistrationID");
         message1 = from;
        
    
         
         ChatPeople curChatObj = addToChat(from, message,
 				"Received");
 		
 			//addToDB(curChatObj); // adding to db

 			
 			

       
		
		
		// notifies user
        sendNotification(message);
        
      
    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, ChatActivity2.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      

    }
    
    
    
    class Create extends AsyncTask<String, String, String> {
     	 
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
        	
        	String email = prefs.getString("email", null); 

        	 // Building Parameters
            Map<String, String> params = new HashMap<String, String>();
            params.put("reg_id", regID);
            params.put("email", email);
            //params.put("password", "Kiti");
            //params.put("profilePic", profilePic);
            //params.put("fromEmail", from_email);
            //params.put("postType", postType);
 
            // making fresh volley request and getting json
            
            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url, params, new Response.Listener<JSONObject>() {

 			            public void onResponse(JSONObject response) {
 			            	
 			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
 			                VolleyLog.d(TAG, "Response: " + response.toString());
 			                if (response != null) {
 			                	
 			                	
 			                	 // check for success tag
 			                    try {
 			                        int success = response.getInt(TAG_SUCCESS);
 			         
 			                        if (success == 1) {
 			                            // successfully created product
 			                            //Toast.makeText(getApplicationContext(), "Successfully shared", Toast.LENGTH_LONG).show();
 			                           
 			                        	MainActivity2.progressBar.setVisibility(View.GONE);
 			                        	MainActivity2.textView.setVisibility(View.GONE);
 			                        	MainActivity2.box.setVisibility(View.VISIBLE);
 			                        	MainActivity2.lets_go.setVisibility(View.VISIBLE);
 			                        	
 			                        	
 			                            
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
 			                
 			                
 			                //Toast.makeText(getApplicationContext(), "Unable to Initialize,try again later", Toast.LENGTH_LONG).show();
 			                MainActivity2.textView.setText("Initialization failed"); 
 			                MainActivity2.textView.setVisibility(View.VISIBLE);
 			               MainActivity2.lets_go.setText("OK, later..."); 
 			              MainActivity2.lets_go.setVisibility(View.VISIBLE); 
 			                
 			            }
 			        });
 			
 			// Adding request to volley request queue
 			AppController.getInstance().addToRequestQueue(jsObjRequest);
 			
 		
			return response;
          
        }
    
    }
    
    
    
 // Put the message into a notification and post it.
 	// This is just one simple example of what you might choose to do with
 	// a GCM message.
 	@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
 	
 	private void sendNotification(String msg) {

 		mNotificationManager = (NotificationManager) this
 				.getSystemService(Context.NOTIFICATION_SERVICE);

 		Intent notifyIntent = new Intent(this, ChatActivity2.class);
 		
 		notifyIntent.putExtra("chattingTo", from);
 		
 		//notifyIntent.putExtra("chattingFrom", from);
 		//notifyIntent.putExtra("chattingToDeviceID", myregId);
 		notifyIntent.putExtra("chattingToDeviceID2", myregId);
 		//notifyIntent.putExtra("myregistrationID", myregId);
 		
 		// Sets the Activity to start in a new, empty task
 		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
 		                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
 		                        
 		// Creates the PendingIntent
 		      PendingIntent contentIntent = PendingIntent.getActivity(this,0, notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);



 		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
 				this).setSmallIcon(R.drawable.ic_launcher)
 				.setContentTitle(message1)
 				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
 				.setContentText(msg);
 		
 		mBuilder.setAutoCancel(true);
 		
 		Uri sound = Uri.parse("android.resource://" + "com.integra.dealcaller/" + R.raw.refreshing_sound);
 	
 		mBuilder.setSound(sound);
 	
 		mBuilder.setContentIntent(contentIntent);
 		
 		
 		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
 	}
 	
 	
 	
 	ChatPeople addToChat(String personName, String chatMessage, String toOrFrom) {

		
		ChatPeople curChatObj = new ChatPeople();
		curChatObj.setPERSON_NAME(personName);
		curChatObj.setPERSON_CHAT_MESSAGE(chatMessage);
		curChatObj.setPERSON_CHAT_TO_FROM(toOrFrom);
		curChatObj.setPERSON_DEVICE_ID(myregId);
		curChatObj.setPERSON_EMAIL("demo@gmail.com");

		return curChatObj;

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

		
	}
}
