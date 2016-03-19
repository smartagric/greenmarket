package com.integra.dealcaller;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.integra.dealcaller.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NewPost extends ActionBarActivity {
	private ImageView capture1;
	private Uri fileUri; // file url to store image/video
	
	 private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	  
	     
	    public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;
	    public static final String url_share_photo = "http://activetalkgh.com/android_connect/share_photo.php";
	    private static final String TAG = SharePhoto.class.getSimpleName();
	    private static final String TAG_SUCCESS = "success";
	    public static String name = null;
	    public static String image = null;
	    private SharedPreferences prefs;
	    public static String final_image = null;
	    public static String status;
	    public EditText InputStatus;
	    public static String profilePic;
	    public static String from_email;
	    public static String postType;
	    private ImageView bigimage;
	    private ImageView to_choose;
	    
	    
	    private static final int REQUEST_CODE = 200;
	    private Bitmap bitmap;
	    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);
 
        prefs = getSharedPreferences("Chat", 0);
  		
  		
  		String username = prefs.getString("username", null);
  		String email = prefs.getString("email", null);
  		String profile = prefs.getString("profile_pic", null);
        
        InputStatus= (EditText)findViewById(R.id.saysomething3);
        bigimage = (ImageView)findViewById(R.id.BigImage3);
        to_choose = (ImageView)findViewById(R.id.button_to_choose3);
         
         //To be sent over to database
         
        name = username;
        status= InputStatus.getText().toString();
        profilePic= profile;
        from_email = email; 
        postType = "photo";
         
         getSupportActionBar().setTitle("Capture for your audience");
  
        capture1= (ImageView) findViewById(R.id.capture_photooo);
        
        
        capture1.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View arg0) {
 			
 			 captureImage();
 			
 			
 			
 		}
 		
 		
 	});
        
     
        to_choose.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0) {
    			
    			Intent intent = new Intent();
    	        intent.setType("image/*");
    	        intent.setAction(Intent.ACTION_GET_CONTENT);
    	        intent.addCategory(Intent.CATEGORY_OPENABLE);
    	        startActivityForResult(intent, REQUEST_CODE);
    		}});

 	
 	
   
         
   
         // Checking camera availability
         if (!isDeviceSupportCamera()) {
             Toast.makeText(getApplicationContext(),
                     "Sorry! Your device doesn't support camera",
                     Toast.LENGTH_LONG).show();
             // will close the app if the device does't have camera
             finish();
         }
     }
   
     /**
      * Checking device has camera hardware or not
      * */
     private boolean isDeviceSupportCamera() {
         if (getApplicationContext().getPackageManager().hasSystemFeature(
                 PackageManager.FEATURE_CAMERA)) {
             // this device has a camera
             return true;
         } else {
             // no camera on this device
             return false;
         }
     }
   
     /**
      * Launching camera app to capture image
      */
     private void captureImage() {
         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
   
         fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
   
         intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
   
         // start the image capture Intent
         startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
     }
      
    
     /**
      * Here we store the file url as it will be null after returning from camera
      * app
      */
     @Override
     protected void onSaveInstanceState(Bundle outState) {
         super.onSaveInstanceState(outState);
   
         // save file url in bundle as it will be null on screen orientation
         // changes
         outState.putParcelable("file_uri", fileUri);
     }
   
     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
         super.onRestoreInstanceState(savedInstanceState);
   
         // get the file url
         fileUri = savedInstanceState.getParcelable("file_uri");
     }
   
      
   
     /**
      * Receiving activity result method will be called after closing the camera
      * */
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         // if the result is capturing Image
         if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
             if (resultCode == RESULT_OK) {
                  
                 // successfully captured the image
                 // launching upload activity
                 launchUploadActivity(true);
                  
                  
             } else if (resultCode == RESULT_CANCELED) {
                  
                 // user cancelled Image capture
                 Toast.makeText(getApplicationContext(),
                         "User cancelled image capture", Toast.LENGTH_SHORT)
                         .show();
              
             } else {
                 // failed to capture image
                 Toast.makeText(getApplicationContext(),
                         "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                         .show();
             }
          
         } else if (requestCode == REQUEST_CODE) {
             if (resultCode == RESULT_OK) {
             	try {
                     // We need to recyle unused bitmaps
                 	Toast.makeText(getApplicationContext(), "Say Hello", Toast.LENGTH_LONG).show();
                     if (bitmap != null) {
                         bitmap.recycle();
                     }
                     InputStream stream = getContentResolver().openInputStream(
                             data.getData());
                     bitmap = BitmapFactory.decodeStream(stream);
                     stream.close();
                     bigimage.setImageBitmap(bitmap);
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
              
                 } catch (IOException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}} else if (resultCode == RESULT_CANCELED) {
                  
                 // user cancelled recording
                 Toast.makeText(getApplicationContext(),
                         "You cancelled the operation", Toast.LENGTH_SHORT)
                         .show();
              
             } else {
                 // failed to record video
                 Toast.makeText(getApplicationContext(),
                         "Error in Operation", Toast.LENGTH_SHORT)
                         .show();
             }
         }
     }
      
     private void launchUploadActivity(boolean isImage){
         Intent i = new Intent(NewPost.this, UploadActivity.class);
         i.putExtra("filePath", fileUri.getPath());
         i.putExtra("isImage", isImage);
         startActivity(i);
     }
       
     /**
      * ------------ Helper Methods ----------------------
      * */
   
     /**
      * Creating file uri to store image/video
      */
     public Uri getOutputMediaFileUri(int type) {
         return Uri.fromFile(getOutputMediaFile(type));
     }
   
     /**
      * returning image / video
      */
     private static File getOutputMediaFile(int type) {
   
         // External sdcard location
         File mediaStorageDir = new File(
                 Environment
                         .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                 Config.IMAGE_DIRECTORY_NAME);
   
         // Create the storage directory if it does not exist
         if (!mediaStorageDir.exists()) {
             if (!mediaStorageDir.mkdirs()) {
                 Log.d(TAG, "Oops! Failed create "
                         + Config.IMAGE_DIRECTORY_NAME + " directory");
                 return null;
             }
         }
   
         // Create a media file name
         String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                 Locale.getDefault()).format(new Date());
         
         File mediaFile;
         image="IMG_"+timeStamp+".jpg";
         final_image = "http://activetalkgh.com/android_connect/uploads/"+image;
         
         if (type == MEDIA_TYPE_IMAGE) {
             mediaFile = new File(mediaStorageDir.getPath() + File.separator
                     + image);
             
             
             
         } else {
             return null;
         }
   
         return mediaFile;
         
         
         
    
        
     
 }
     
     class Share extends AsyncTask<String, String, String> {
       	 
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
         	
         	 

         	 // Building Parameters
             Map<String, String> params = new HashMap<String, String>();
             params.put("name", name);
             params.put("final_image", final_image);
             params.put("status", InputStatus.getText().toString());
             params.put("profilePic", profilePic);
             params.put("fromEmail", from_email);
             params.put("postType", postType);
  
             // making fresh volley request and getting json
             
             CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo, params, new Response.Listener<JSONObject>() {

  			            public void onResponse(JSONObject response) {
  			            	
  			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
  			                VolleyLog.d(TAG, "Response: " + response.toString());
  			                if (response != null) {
  			                	
  			                	
  			                	 // check for success tag
  			                    try {
  			                        int success = response.getInt(TAG_SUCCESS);
  			         
  			                        if (success == 1) {
  			                            // successfully created product
  			                            Toast.makeText(getApplicationContext(), "Successfully shared", Toast.LENGTH_LONG).show();
  			                           
          			         
  			                            
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
     
   
 }




