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

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.integra.dealcaller.R;
import com.integra.dealcaller.AndroidMultiPartEntity.ProgressListener;
import com.integra.dealcaller.RegisterActivity2.RegisterPerson;
import com.integra.dealcaller.TimeLineFragment.GetDataTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AddItemToShowcase extends ActionBarActivity  {
	
	private ImageView capture1;
	private Uri fileUri; // file url to store image/video
	
	 private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	  
	     
	    public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;
	    public static final String url_share_photo = "http://activetalkgh.com/android_connect/share_photo.php";
	    private static final String TAG = AddItemToShowcase.class.getSimpleName();
	    private static final String TAG_SUCCESS = "success";
	    public static String name = null;
	    public static String image1 = null;
	    public static String image2 = null;
	    public static String image3 = null;
	    public static String image4 = null;
	    public static String image5 = null;
	    public static String final_image1 = null;
	    public static String final_image2 = null;
	    public static String final_image3 = null;
	    public static String final_image4 = null;
	    public static String final_image5 = null;
	    private static String filePath1,filePath2, filePath3,filePath4,filePath5;
	    private static String fileName;
	    public static String status;
	    private SharedPreferences prefs;
	    private Button cancel, add;
	    public EditText InputStatus;
	    public static String profilePic;
	    public static String from_email;
	    private ProgressBar progressBar;
	    public static String postType;
	    private RoundedImageVieww2 bigimage;
	    private com.integra.dealcaller.RecyclingImageView bigimage1,bigimage2,bigimage3,bigimage4,bigimage5;
	    private ImageButton to_choose;
	    private ImageButton to_choose2,to_choose3,to_choose4,to_choose5;
	   
	 
	    private static final int REQUEST_CODE = 200;
	    private static final int REQUEST_CODE2 = 300;
	    private static final int REQUEST_CODE3 = 400;
	    private static final int REQUEST_CODE4 = 500;
	    private static final int REQUEST_CODE5 = 600;
	    private Bitmap bitmap;
	    long totalSize = 0;
	    
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_to_showcase);
        
        prefs = getSharedPreferences("Chat", 0);
  		
  		
  		String username = prefs.getString("username", null);
  		String email = prefs.getString("email", null);
  		String profile = prefs.getString("profile_pic", null);
       
       
       InputStatus= (EditText)findViewById(R.id.saysomething);
       cancel = (Button)findViewById(R.id.btnCreateProduct);
       add = (Button)findViewById(R.id.btnCreateProduct2);
       bigimage = (RoundedImageVieww2)findViewById(R.id.BigImage);
       bigimage1 = (com.integra.dealcaller.RecyclingImageView )findViewById(R.id.selected_image1);
       bigimage2 = (com.integra.dealcaller.RecyclingImageView)findViewById(R.id.selected_image2);
       bigimage3 = (com.integra.dealcaller.RecyclingImageView)findViewById(R.id.selected_image3);
       bigimage4 = (com.integra.dealcaller.RecyclingImageView)findViewById(R.id.selected_image4);
       bigimage5 = (com.integra.dealcaller.RecyclingImageView)findViewById(R.id.selected_image5);
       
       
       to_choose = (ImageButton)findViewById(R.id.bu1);
       to_choose2 = (ImageButton)findViewById(R.id.bu2);
       to_choose3 = (ImageButton)findViewById(R.id.bu3);
       to_choose4 = (ImageButton)findViewById(R.id.bu4);
       to_choose5 = (ImageButton)findViewById(R.id.bu5);
       progressBar = (ProgressBar) findViewById(R.id.progressinsidepage);
       
       
       
       //Receiving Intents from other apps
       
       
   
   		
     
       
        
        //To be sent over to database
        
       name = username;
       status= InputStatus.getText().toString();
       profilePic= profile;
       from_email = email; 
       postType = "photo";
        
        getSupportActionBar().setTitle("Add items to your Store");
 
       capture1= (ImageView) findViewById(R.id.capture_photo);
       
       
       
       add.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Toast.makeText(getApplicationContext(), "You will be allowed to add items when showcase group is in operation", Toast.LENGTH_LONG).show();
			
			//new UploadFileToServer().execute();
   			
  			 //new Share().execute();
		}
	});
     
       
       
       cancel.setOnClickListener(new OnClickListener() {
   		
   		@Override
   		public void onClick(View arg0) {
   			// TODO Auto-generated method stub
   			
   			finish();
   		}
   	});
       
       
       
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
   	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
   	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
   	        startActivityForResult(intent, REQUEST_CODE);
   		}});

       to_choose2.setOnClickListener(new OnClickListener() {
      		
      		@Override
      		public void onClick(View arg0) {
      			
      			Intent intent = new Intent();
      	        intent.setType("image/*");
      	        intent.setAction(Intent.ACTION_GET_CONTENT);
      	        intent.addCategory(Intent.CATEGORY_OPENABLE);
      	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
      	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
      	        startActivityForResult(intent, REQUEST_CODE2);
      		}});
       to_choose3.setOnClickListener(new OnClickListener() {
      		
      		@Override
      		public void onClick(View arg0) {
      			
      			Intent intent = new Intent();
      	        intent.setType("image/*");
      	        intent.setAction(Intent.ACTION_GET_CONTENT);
      	        intent.addCategory(Intent.CATEGORY_OPENABLE);
      	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
      	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
      	        startActivityForResult(intent, REQUEST_CODE3);
      		}});
       to_choose4.setOnClickListener(new OnClickListener() {
      		
      		@Override
      		public void onClick(View arg0) {
      			
      			Intent intent = new Intent();
      	        intent.setType("image/*");
      	        intent.setAction(Intent.ACTION_GET_CONTENT);
      	        intent.addCategory(Intent.CATEGORY_OPENABLE);
      	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
      	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
      	        startActivityForResult(intent, REQUEST_CODE4);
      		}});
       to_choose5.setOnClickListener(new OnClickListener() {
      		
      		@Override
      		public void onClick(View arg0) {
      			
      			Intent intent = new Intent();
      	        intent.setType("image/*");
      	        intent.setAction(Intent.ACTION_GET_CONTENT);
      	        intent.addCategory(Intent.CATEGORY_OPENABLE);
      	        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
      	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
      	        startActivityForResult(intent, REQUEST_CODE5);
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
                //launchUploadActivity(true);
                 
                 
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
            	
            	Uri selectedImageURI = data.getData();
            	
            	filePath1 = getRealPathFromURI(selectedImageURI);
            	
            	image1 = image1;
            	final_image1 = final_image1;
            	
            	try {
                    // We need to recyle unused bitmaps
                	//Toast.makeText(getApplicationContext(), filePath.toString(), Toast.LENGTH_LONG).show();
                    if (bitmap != null) {
                        //bitmap.recycle();
                    }
                    InputStream stream = getContentResolver().openInputStream(
                            data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 50, 60, true);
                    stream.close();
                    
                    
                    bigimage1.setImageBitmap(bMapScaled);
                    to_choose.setVisibility(View.GONE);
                    
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
             
                } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}} else if (resultCode == RESULT_CANCELED) {
                 
                // user cancelled recording
                //Toast.makeText(getApplicationContext(),
                  //      "You cancelled the operation", Toast.LENGTH_SHORT)
                  //      .show();
             
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Error in Operation", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        
        else if (requestCode == REQUEST_CODE2) {
            if (resultCode == RESULT_OK) {
            	
            	Uri selectedImageURI = data.getData();
            	
            	filePath2 = getRealPathFromURI(selectedImageURI);
            	
            	image2 = image1;
            	final_image2 = final_image1;
            	
            	try {
                    // We need to recyle unused bitmaps
                	//Toast.makeText(getApplicationContext(), filePath.toString(), Toast.LENGTH_LONG).show();
                    if (bitmap != null) {
                        //bitmap.recycle();
                    }
                    InputStream stream = getContentResolver().openInputStream(
                            data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 50, 60, true);
                    stream.close();
                    
                    
                    bigimage2.setImageBitmap(bMapScaled);
                    to_choose2.setVisibility(View.GONE);
                    
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
             
                } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}} else if (resultCode == RESULT_CANCELED) {
                 
                // user cancelled recording
                //Toast.makeText(getApplicationContext(),
                 //       "You cancelled the operation", Toast.LENGTH_SHORT)
                 //       .show();
             
            } else {
                // failed to record video
               // Toast.makeText(getApplicationContext(),
                 //       "Error in Operation", Toast.LENGTH_SHORT)
                  //      .show();
            }
        }
        
        else if (requestCode == REQUEST_CODE3) {
            if (resultCode == RESULT_OK) {
            	
            	Uri selectedImageURI = data.getData();
            	
            	filePath3 = getRealPathFromURI(selectedImageURI);
            	image3 = image1;
            	final_image3 = final_image1;
            	try {
                    // We need to recyle unused bitmaps
                	//Toast.makeText(getApplicationContext(), filePath.toString(), Toast.LENGTH_LONG).show();
                    if (bitmap != null) {
                        //bitmap.recycle();
                    }
                    InputStream stream = getContentResolver().openInputStream(
                            data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 50, 60, true);
                    stream.close();
                    
                    
                    bigimage3.setImageBitmap(bMapScaled);
                    to_choose3.setVisibility(View.GONE);
                    
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
             
                } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}} else if (resultCode == RESULT_CANCELED) {
                 
                // user cancelled recording
                //Toast.makeText(getApplicationContext(),
                 //       "You cancelled the operation", Toast.LENGTH_SHORT)
                   //     .show();
             
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Error in Operation", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        
        else if (requestCode == REQUEST_CODE4) {
            if (resultCode == RESULT_OK) {
            	
            	Uri selectedImageURI = data.getData();
            	
            	filePath4 = getRealPathFromURI(selectedImageURI);
            	image4 = image1;
            	final_image4 = final_image1;
            	
            	try {
                    // We need to recyle unused bitmaps
                	//Toast.makeText(getApplicationContext(), filePath.toString(), Toast.LENGTH_LONG).show();
                    if (bitmap != null) {
                        //bitmap.recycle();
                    }
                    InputStream stream = getContentResolver().openInputStream(
                            data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 50, 60, true);
                    stream.close();
                    
                    
                    bigimage4.setImageBitmap(bMapScaled);
                    to_choose4.setVisibility(View.GONE);
                    
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
             
                } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}} else if (resultCode == RESULT_CANCELED) {
                 
                // user cancelled recording
                //Toast.makeText(getApplicationContext(),
                  //      "You cancelled the operation", Toast.LENGTH_SHORT)
                  //      .show();
             
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Error in Operation", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        
        else if (requestCode == REQUEST_CODE5) {
            if (resultCode == RESULT_OK) {
            	
            	Uri selectedImageURI = data.getData();
            	
            	filePath5 = getRealPathFromURI(selectedImageURI);
            	image5 = image1;
            	final_image5 = final_image1;
            	try {
                    // We need to recyle unused bitmaps
                	//Toast.makeText(getApplicationContext(), filePath.toString(), Toast.LENGTH_LONG).show();
                    if (bitmap != null) {
                        //bitmap.recycle();
                    }
                    InputStream stream = getContentResolver().openInputStream(
                            data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 50, 60, true);
                    stream.close();
                    
                    
                    bigimage5.setImageBitmap(bMapScaled);
                    to_choose5.setVisibility(View.GONE);
                    
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
             
                } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}} else if (resultCode == RESULT_CANCELED) {
                 
                // user cancelled recording
                //Toast.makeText(getApplicationContext(),
                  //      "You cancelled the operation", Toast.LENGTH_SHORT)
                   //     .show();
             
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Error in Operation", Toast.LENGTH_SHORT)
                        .show();
            }
        }
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
        image1="IMG_"+timeStamp+".jpg";
        final_image1 = "http://activetalkgh.com/android_connect/uploads/"+image1;
        
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + image1);
            
            
            
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
            params.put("final_image", final_image1);
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
 			                           
 			                          new GetDataTask();
 			                            
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
 
            
                
                File sourceFile = new File(filePath1);
                
               

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
 
                // Extra parameters if you want to pass to server
                entity.addPart("website",new StringBody("www.activetalkgh.com"));
                entity.addPart("email", new StringBody("abc@gmail.com"));
                entity.addPart("chosen", new StringBody(image1));
                
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
           
            progressBar.setVisibility(View.GONE);
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
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
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
				
				//new UploadFileToServer().execute();
	   			
	   			 //new Share().execute();
	   			 
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
	    }
 
	   
	  
	  
	   
	   public static String getFileName() {
			return fileName;
		}

		public static void setFileName(String filePath) {
			AddItemToShowcase.fileName = filePath;
		}
}




