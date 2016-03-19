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
import com.integra.dealcaller.LineupFragment.GetFriends;
import com.integra.dealcaller.R;
import com.integra.dealcaller.AndroidMultiPartEntity.ProgressListener;
import com.integra.dealcaller.RegisterActivity2.RegisterPerson;
import com.integra.dealcaller.TimeLineFragment.GetDataTask;
import com.integra.dealcaller.crop.Crop;
import com.integra.dealcaller.crop.CropImageActivity;

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
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CreateNewShowcase extends ActionBarActivity  {
	
	private ImageView capture1;
	private Uri fileUri; // file url to store image/video
	
	 private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	  
	     
	    public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;
	    public static final String url_update = "http://activetalkgh.com/android_connect/create_store.php";
	    
	    private static final String TAG = CreateNewShowcase.class.getSimpleName();
	    private static final String TAG_SUCCESS = "success";
	    public static String name = null;
	    public static String image = null;
	    public static String final_image = null;
	    private static String filePath;
	    private static String fileName;
	    public static String status;
	    private SharedPreferences prefs;
	    public EditText business_name,description,location,phone,retype,password,email;
	    public static String profilePic;
	    public static String from_email;
	    private ProgressBar pb;
	    public static String postType;
	    private com.integra.dealcaller.RecyclingImageView bigimage;
	    private ImageView to_choose;
	    private Button create;
	    private static  Map<String, String> params;
	 
	    private static final int REQUEST_CODE = 200;
	    private Bitmap bitmap;
	    long totalSize = 0;
	    
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_showcase);
        getSupportActionBar().setTitle("Create a new Store");
        prefs = getSharedPreferences("Chat", 0);
  		
       
       business_name= (EditText)findViewById(R.id.saysomething);
       description= (EditText)findViewById(R.id.saysomething2);
       location= (EditText)findViewById(R.id.saysomething3);
       phone= (EditText)findViewById(R.id.saysomething_fon);
       retype= (EditText)findViewById(R.id.saysomething_veri);
       password= (EditText)findViewById(R.id.saysomething_pass);
       email= (EditText)findViewById(R.id.saysomething_email);
     
       
       create = (Button)findViewById(R.id.btnCreateProduct2);
       bigimage = (com.integra.dealcaller.RecyclingImageView)findViewById(R.id.BigImage);
       to_choose = (ImageView)findViewById(R.id.button_to_choose);
       pb = (ProgressBar) findViewById(R.id.progressinsidepage);
       
       
       // Create a media file name
       final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",                            
               Locale.getDefault()).format(new Date());
       
       
       create.setOnClickListener(new OnClickListener() {
		
    	   
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			if (create.getText().toString().contentEquals("Store created...Exit..")){
	    		   finish();
	    	   }else{
	    		   
	    	   create.setEnabled(false);
			image="IMG_"+timeStamp+".jpg";
	        final_image = "http://activetalkgh.com/android_connect/uploads/"+image;
	        
			params = new HashMap<String, String>();
            params.put("location", location.getText().toString());
            
            params.put("description", description.getText().toString());
            params.put("business_name", business_name.getText().toString());
            params.put("phone_number", phone.getText().toString());
            params.put("retype", retype.getText().toString());
            params.put("password", password.getText().toString());
           
            params.put("email", email.getText().toString());
            params.put("image", final_image);
			
            Toast.makeText(getApplicationContext(), business_name.getText().toString(), Toast.LENGTH_LONG).show();
			new UploadFileToServer().execute();
   			
	    	   }
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
  
     
  
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
        	 
                       
            beginCrop(result.getData());
            
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
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
				
				
	   			 
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
	    }
 
	   
	  
	  
	   
	   public static String getFileName() {
			return fileName;
		}

		public static void setFileName(String filePath) {
			CreateNewShowcase.fileName = filePath;
		}
		
		
		
		
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
		        //image="IMG_"+timeStamp+".jpg";
		        //final_image = "http://activetalkgh.com/android_connect/uploads/"+image;
		        
		        if (type == MEDIA_TYPE_IMAGE) {
		            mediaFile = new File(mediaStorageDir.getPath() + File.separator
		                    + image);
		            
		            
		            
		        } else {
		            return null;
		        }
		  
		        return mediaFile;
 
		}
		    
		    
		    
		    private void beginCrop(Uri source) {
		        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
		        Crop.of(source, destination).asSquare().start(this);
		       
		    }

		    private void handleCrop(int resultCode, Intent result) {
		        if (resultCode == RESULT_OK) {
		        	
		       
		     
		            bigimage.setImageURI(Crop.getOutput(result));
		            
		          
		            
		            
				              
		            
		           
		            
		           
		        } else if (resultCode == Crop.RESULT_ERROR) {
		            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
		        }
		    }
		    
		    
		    
		    
		    
		    /**
		     * Uploading the file to server
		     * */
		    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		        @Override
		        protected void onPreExecute() {
		            // setting progress bar to zero
		            pb.setProgress(0);
		            super.onPreExecute();
		        }
		 
		        @Override
		        protected void onProgressUpdate(Integer... progress) {
		            // Making progress bar visible
		            pb.setVisibility(View.VISIBLE);
		 
		            // updating progress bar value
		            pb.setProgress(progress[0]);
		 
		            
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
		            
		                
		                
		                File sourceFile = new File(CropImageActivity.saveUri.getPath());

		                // Adding file data to http body
		                entity.addPart("image", new FileBody(sourceFile));
		 
		                // Extra parameters if you want to pass to server
		                entity.addPart("website",new StringBody("www.activetalkgh.com"));
		                entity.addPart("email", new StringBody("abc@gmail.com"));
		                entity.addPart("chosen", new StringBody(image));
		                
		                totalSize = entity.getContentLength();
		                httppost.setEntity(entity);
		 
		                // Making server call
		                HttpResponse response = httpclient.execute(httppost);
		                HttpEntity r_entity = response.getEntity();
		 
		                int statusCode = response.getStatusLine().getStatusCode();
		                if (statusCode == 200) {
		                    // Server response
		                    responseString = EntityUtils.toString(r_entity);
		         /////////////////////////////////////////////////now update the strings to db
		                    
		                    
		                    
		                    new Update().execute();
		                    
		                } else {
		                    responseString = "Error occurred! Http Status Code: "
		                            + statusCode;
		                    Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
		                    create.setEnabled(true);
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
		            //Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
		 
		            int resultcode = 1;
		           
		            pb.setVisibility(View.GONE);
		            super.onPostExecute(result);
		            
		            
		        }
		 
		    }
		    
		    
		    
		    
		    
		    class Update extends AsyncTask<String, String, String> {
		      	 
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
		        	
		        	
		            // making fresh volley request and getting json
		            
		            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_update, params, new Response.Listener<JSONObject>() {

		 			            public void onResponse(JSONObject response) {
		 			            	
		 			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
		 			                VolleyLog.d(TAG, "Response: " + response.toString());
		 			                if (response != null) {
		 			                	
		 			                	
		 			                	 // check for success tag
		 			                    try {
		 			                        int success = response.getInt(TAG_SUCCESS);
		 			         
		 			                        if (success == 1) {
		 			                            // successfully created product
		 			                            Toast.makeText(getApplicationContext(), "Successfully created", Toast.LENGTH_LONG).show();
		 			                      
		 			                            pb.setVisibility(View.GONE);
		 			                           create.setBackgroundColor(Color.parseColor("#3498db"));
		 			                           create.setText("Store created...Exit..");
		 			                          create.setEnabled(true);
		 			                     new GetFriends();
		 			                            
		 			                        } else {
		 			                            // failed to create product
		 			                        	 create.setEnabled(true);
		 			                        }
		 			                    } catch (JSONException e) {
		 			                        e.printStackTrace();
		 			                       create.setEnabled(true);
		 			                    }	
		 			                   
		 			                  
		 			                    
		 			                    
		 			                }
		 			            }
		 			        }, new Response.ErrorListener() {

		 			            public void onErrorResponse(VolleyError error) {
		 			            	
		 			       
		 			                VolleyLog.d(TAG, "Error: " + error.getMessage());
		 			                
		 			                
		 			                Toast.makeText(getApplicationContext(), "could not create", Toast.LENGTH_LONG).show();
		 			               create.setEnabled(true);
		 			              
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
		           

		    		  
		    		

		        }

		        



		    } 
		    
	
}




