package com.integra.dealcaller;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.integra.dealcaller.R;
import com.integra.dealcaller.AndroidMultiPartEntity.ProgressListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
 
public class UploadActivity2 extends Activity {
    // LogCat tag
    private static final String TAG = UploadActivity2.class.getSimpleName();
 
    private ProgressBar progressBar;
    private String filePath = null;
    private TextView txtPercentage;
    public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/share_photo.php";
    private static final String TAG_SUCCESS = "success";
    private ImageView imgPreview;
    private SharedPreferences prefs;
    private VideoView vidPreview;
    private Button btnUpload;
    long totalSize = 0;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload2);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage2);
        btnUpload = (Button) findViewById(R.id.btnUpload2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        imgPreview = (ImageView) findViewById(R.id.imgPreview2);
        vidPreview = (VideoView) findViewById(R.id.videoPreview2);
 
        
        
        
        
 
        // Receiving the data from previous activity
        Intent i = getIntent();
 
        // image or video path that is captured in previous activity
        filePath = i.getStringExtra("filePath");
 
        // boolean flag to identify the media type, image or video
        boolean isImage = i.getBooleanExtra("isImage", true);
 
        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
 
        btnUpload.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // uploading the file to server
                new UploadFileToServer().execute();
                
                new Share().execute();
                
                
            }
        });
 
    }
 
    /**
     * Displaying captured image/video on the screen
     * */
    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            vidPreview.setVisibility(View.GONE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
 
            imgPreview.setImageBitmap(bitmap);
        } else {
            imgPreview.setVisibility(View.GONE);
            vidPreview.setVisibility(View.VISIBLE);
            vidPreview.setVideoPath(filePath);
            // start playing
            vidPreview.start();
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
 
            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
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
 
                File sourceFile = new File(filePath);
 
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
 
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
        	
        	prefs = getSharedPreferences("Chat", 0);
      		
      		
      		String username = prefs.getString("username", null);
      		String email = prefs.getString("email", null);
      		String profile = prefs.getString("profile_pic", null);

        	 // Building Parameters
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", username);
            params.put("final_image", SharePhoto.final_image);
            params.put("status", SharePhoto.status);
            params.put("profilePic", profile);
            params.put("fromEmail", email);
            params.put("postType", SharePhoto.postType);
 
            // making fresh volley request and getting json
            
            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo3, params, new Response.Listener<JSONObject>() {

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