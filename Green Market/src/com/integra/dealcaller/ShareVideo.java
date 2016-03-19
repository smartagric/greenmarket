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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;

import com.integra.dealcaller.R;
import com.integra.dealcaller.AndroidMultiPartEntity.ProgressListener;
import com.integra.dealcaller.SharePhoto.Share;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

public class ShareVideo extends ActionBarActivity {
	
	private Uri fileUri; // file url to store image/video
	
		private static final int REQUEST_CODE = 100;
	    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	     
	    public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;
	    
	    private static final String TAG = ShareVideo.class.getSimpleName();
	    
	    private static final String TAG_SUCCESS = "success";
	    private SharedPreferences prefs;
	    public static final String url_share_photo2 = "http://activetalkgh.com/android_connect/share_video.php";
	    private VideoView bigvideo;
	    public static String name = null;
	    public static String image = null;
	    public static String final_image = null;
	    public static String status ;
	    public EditText InputStatus;
	    public static String profilePic;
	    public static String from_email;
	    public static String postType;
	    
	    private ImageView to_choose;
	    private String videoPath = null;
	    private Button share_inside;
	    private ProgressBar progressBar;
	    long totalSize = 0;
	
	private ImageView capture2;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_video);
        
        getSupportActionBar().setTitle("Video up and Share!");
        
        
        prefs = getSharedPreferences("Chat", 0);
  		
  		
  		String username = prefs.getString("username", null);
  		String email = prefs.getString("email", null);
  		String profile = prefs.getString("profile_pic", null);
     
        
        progressBar = (ProgressBar)findViewById(R.id.progressinsidepage2);
        InputStatus= (EditText)findViewById(R.id.say2);
        to_choose = (ImageView)findViewById(R.id.button_to_choosevideo);
        
        bigvideo = (VideoView)findViewById(R.id.BigVideo);
        
        
        to_choose.setOnClickListener(new OnClickListener() {
       		
       		@Override
       		public void onClick(View arg0) {
       			
       			Intent intent = new Intent();
       	        intent.setType("video/*");
       	        intent.setAction(Intent.ACTION_GET_CONTENT);
       	        intent.addCategory(Intent.CATEGORY_OPENABLE);
       	        startActivityForResult(intent, REQUEST_CODE);
       		}});
        
        
         
         //To be sent over to database
         
        name = username;
        status= InputStatus.getText().toString();
        profilePic= profile;
        from_email = email; 
        postType = "video";

        
        capture2= (ImageView) findViewById(R.id.capture_video);
        
        
        capture2.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View arg0) {
 			
 			recordVideo();
 		
 
 		}
        });
        
        
        
       
        
        

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
     * Launching camera app to record video
     */
    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
  
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
  
        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
  
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
                                                            // name
  
        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
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
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                 
            	Uri selectedVideoURI = data.getData();
            	
            	videoPath = getRealPathFromURI(selectedVideoURI);
            	
            	Toast.makeText(getApplicationContext(), videoPath.toString(), Toast.LENGTH_LONG).show();
            	
            	if(videoPath!=null){
            	
            	bigvideo.setVideoPath(videoPath);
				bigvideo.start();
            	}  
				
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
         
        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                 
                // video successfully recorded
                // launching upload activity
                launchUploadActivity2(true);
             
            } else if (resultCode == RESULT_CANCELED) {
                 
                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
             
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
     
    private void launchUploadActivity2(boolean isImage){
        Intent i = new Intent(ShareVideo.this, UploadActivity2.class);
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
        image="VID_"+timeStamp+".mp4";
        final_image = "http://activetalkgh.com/android_connect/uploads/"+image;
        
        
        if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
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
            
            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo2, params, new Response.Listener<JSONObject>() {

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
   
    

    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }
 
        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);
 
            // updating progress bar value
            progressBar.setProgress(progress[0]);
 
            
        }
 
        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }
 
        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
 
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);
 
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new ProgressListener() {
 
                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
 
                File sourceFile = new File(videoPath);
 
                // Adding file data to http body
                entity.addPart("video", new FileBody(sourceFile));
 
                // Extra parameters if you want to pass to server
                entity.addPart("website",
                        new StringBody("www.activetalkgh.com"));
                entity.addPart("email", new StringBody("abc@gmail.com"));
 
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
 
                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
 
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }
 
            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
 
            return responseString;
 
        }
 
        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);
 
            // showing the server response in an alert dialog
            showAlert(result);
 
            int resultcode = 1;
           
            super.onPostExecute(result);
            
            
        }
 
    }
 
    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        
                    	finish();
                        return;
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    
    
    
    

 
}
    
    
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else { 
            cursor.moveToFirst(); 
            int idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA); 
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    
    @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	       getMenuInflater().inflate(R.menu.actionbar5, menu);
	           return true;
	   }
	   
	   
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        int itemId = item.getItemId();
			if (itemId == R.id.action_post) {
				
				new UploadFileToServer().execute();
	   			
	   			 new Share().execute();
	   			 
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
	    }   
}






