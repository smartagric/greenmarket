package com.integra.dealcaller;

import java.io.UnsupportedEncodingException;
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
import com.integra.dealcaller.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class EditPocketProfile extends ActionBarActivity implements OnClickListener{
	
	private String URL_FEED = "http://activetalkgh.com/android_connect/editpocket.php";
	private String URL_FEED2 = "http://activetalkgh.com/android_connect/Update_test.php";
	private static final String TAG = EditPocketProfile.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private ProgressBar pb;
    private Integer progress;
    private Map<String, String> params;
    private com.android.volley.toolbox.NetworkImageView coverr;
    private com.android.volley.toolbox.NetworkImageView cute;
    private EditText mobile_init;
    private EditText email_init;
    private com.integra.dealcaller.ImageLoader mImageLoader;
    private EditText school_init;
    private EditText work_init;
    private EditText residence_init;
    private Button save;
    private Button discard;
    
    private SharedPreferences prefs;
    private String mobile;
    private String email;
    private String school;
    private String work;
    private String residence;
    
    
    /** Called when the activity is first created. */
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pocket_profile);
        
        prefs = getSharedPreferences("Chat", 0);
        getSupportActionBar().setTitle("Edit your pocket profile");
        
        pb=(ProgressBar)findViewById(R.id.progressBarEditPocketer);
        
       
        
        
        coverr=(com.android.volley.toolbox.NetworkImageView)findViewById(R.id.pocketPhotoedit);
        cute=(com.android.volley.toolbox.NetworkImageView)findViewById(R.id.pocketPhotoedit77);
        
        
        coverr.setDefaultImageResId(R.drawable.null_cover);
        cute.setDefaultImageResId(R.drawable.null_profile);
        
        
        
        mobile_init = (EditText)findViewById(R.id.ButtonEdit3);
       
        
        email_init = (EditText)findViewById(R.id.ButtonEdit4);
        
        
        school_init = (EditText)findViewById(R.id.ButtonEdit5);
     
        
        work_init = (EditText)findViewById(R.id.ButtonEdit6);
      
        
        residence_init = (EditText)findViewById(R.id.ButtonEdit7);
    
        
       
        
        save = (Button)findViewById(R.id.Sav);
        discard = (Button)findViewById(R.id.Dis);
        
        ///
        
        String url_small = prefs.getString("cover_photo", null);
        String url_big = prefs.getString("profile_pic", null);
        
        registerForContextMenu(coverr);
        registerForContextMenu(cute);

         progress=0;
         
     
     
      // We first check for cached request
         Cache cache = AppController.getInstance().getRequestQueue().getCache();
         Entry entry = cache.get(url_small);
         if (entry != null) {
             // fetch the data from cache
             try {
                 String data = new String(entry.data, "UTF-8");
                 try {
                 	
                 coverr.setImageUrl(url_small, imageLoader);
                 	
                     parseJsonFeed(new JSONObject(data));
                
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
             }}
  
         coverr.setImageUrl(url_small, imageLoader);
         
         
         
         // We first check for cached request
         Cache cache2 = AppController.getInstance().getRequestQueue().getCache();
         Entry entry2 = cache2.get(url_big);
         if (entry2 != null) {
             // fetch the data from cache
             try {
                 String data = new String(entry2.data, "UTF-8");
                 try {
                 	
                	 cute.setImageUrl(url_big, imageLoader);
                 	
                     parseJsonFeed(new JSONObject(data));
                
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
             }
         }
             
         cute.setImageUrl(url_big, imageLoader);
         
               
         
       new Fetcher().execute();
         
                
 }
         
         
    
    
 //Long Press
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.pocketPhotoedit) {
		 
		  
	    
	    String[] menuItems = getResources().getStringArray(R.array.long_array4);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	   
	    }
	  }
	  
	  if (v.getId()==R.id.pocketPhotoedit77) {
			 
		  
		    
		    String[] menuItems = getResources().getStringArray(R.array.long_array4);
		    for (int i = 0; i<menuItems.length; i++) {
		      menu.add(Menu.NONE, i, i, menuItems[i]);
		   
		    }
		  }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
	  String[] menuItems = getResources().getStringArray(R.array.long_array4);
	  String menuItemName = menuItems[menuItemIndex];
	  
	  Toast.makeText(this, "Selected %s for item %s" + menuItemName, Toast.LENGTH_LONG).show();
	  
	  return true;
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
                
                String  mobile_no = prefs.getString("phonenumber", null);
                mobile_init.setHint(mobile_no);
                
                String  email = prefs.getString("email", null);
                email_init.setHint(email);
                
                String school =  feedObj.getString("school");
                school_init.setHint(school);
                
                String work =  feedObj.getString("work");
                work_init.setHint(work);
                
                String residence =  feedObj.getString("residence");
                residence_init.setHint(residence);
                
 
               
            }
 
            // notify data changes to list adapater
           
            pb.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
        
    }     
        
        
 
        private class Update extends AsyncTask<String, String, String> {
         	 
        	String response = null; 
            /**
             * Before starting background thread Show Progress Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               
                mobile = mobile_init.getText().toString();
                email = email_init.getText().toString();
                school = school_init.getText().toString();
                work = work_init.getText().toString();
                residence = residence_init.getText().toString();   
                
            }


            /**
             * Deleting product
             * */
            protected String doInBackground(String... args) {
            	
            	

            	 // Building Parameters
                Map<String, String> params = new HashMap<String, String>();
                
                params.put("person_email", prefs.getString("email", null));
                params.put("mobile_number", mobile);
                params.put("email", email);
                params.put("school", school);
                params.put("work", work);
                params.put("residence", residence);
                
     
                // making fresh volley request and getting json
                
                CustomRequest jsObjRequest = new CustomRequest(Method.POST, URL_FEED2, params, new Response.Listener<JSONObject>() {

     			            public void onResponse(JSONObject response) {
     			            	
     			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
     			                VolleyLog.d(TAG, "Response: " + response.toString());
     			                if (response != null) {
     			                	
     			                	
     			                	 // check for success tag
     			                    try {
     			                        int success = response.getInt(TAG_SUCCESS);
     			         
     			                        if (success == 1) {
     			                            // successfully created product
     			                            Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_LONG).show();
     			                           
             			         
     			                            
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

        private class Fetcher extends AsyncTask<String, String, String> {
        	 
         	String response = null; 
             /**
              * Before starting background thread Show Progress Dialog
              * */
             @Override
             protected void onPreExecute() {
                 super.onPreExecute();
                
                 pb.setProgress(progress);
         	 
         	 
         	
       
         // Building Parameters
         params = new HashMap<String, String>();
         params.put("email", prefs.getString("email", null));
                 
             }


             /**
              * Fetching data
              * */
             protected String doInBackground(String... args) {
             	
             	

              // making fresh volley request and getting json
         
         CustomRequest jsObjRequest = new CustomRequest(Method.POST, URL_FEED, params, new Response.Listener<JSONObject>() {

 			            public void onResponse(JSONObject response) {
 			            	
 			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
 			                VolleyLog.d(TAG, "Response: " + response.toString());
 			                if (response != null) {
 			                	
 			                	
 			                	
 			                	parseJsonFeed(response);
 			                	 // check for success tag
 			                   
 			                    
 			                }
 			            }
 			        }, new Response.ErrorListener() {

 			            public void onErrorResponse(VolleyError error) {
 			            	
 			       
 			                VolleyLog.d(TAG, "Error: " + error.getMessage());
 			                
 			                
 			                Toast.makeText(getApplicationContext(), "Could not fetch data", Toast.LENGTH_LONG).show();
 			              pb.setVisibility(View.GONE);
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
        
@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	
}

		

}

