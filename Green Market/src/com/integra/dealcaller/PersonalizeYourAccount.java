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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.integra.dealcaller.R;
import com.integra.dealcaller.AndroidMultiPartEntity.ProgressListener;

public class PersonalizeYourAccount extends ActionBarActivity implements OnClickListener {
	
	private Button save;
	private static String url_register = "http://activetalkgh.com/android_connect/personalize.php";
	private static String TAG = "RegisterActivity";
	private static final int REQUEST_CODE = 200;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	JSONParser jsonParser = new JSONParser();
	private static String filePath = null;
	private static String filePath2 = null;
	private static final String TAG_SUCCESS = "success";
	private Bitmap bitmap;
	private Utils3 utils;
	private ProgressBar progressBar;
	public static String image = null;
    public static String final_image = null;
    public static String image2 = null;
    public static String final_image2 = null;
    long totalSize = 0;
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	PrefManager prefs;

	private ImageView cover;
	private RoundedImageView pocketPhoto;
	private ProgressDialog pDialog;
	private EditText fullname, username, residence, code, phone_number,school,work;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalize_your_account);
        
        getSupportActionBar().setTitle("Personalize your profile!");
        
        utils = new Utils3(this);
        
        
        
        progressBar = (ProgressBar) findViewById(R.id.progresspersonalize);
        // Edit Text
        save =(Button)findViewById(R.id.ButtonSavery);
        cover = (ImageView)findViewById(R.id.coverfotory);
        cover.setOnClickListener(this);
        
        pocketPhoto=(RoundedImageView)findViewById(R.id.pocketPhotoeditry);
        fullname = (EditText) findViewById(R.id.full_namery);
        username = (EditText) findViewById(R.id.usernamery);
        residence = (EditText) findViewById(R.id.residencery);
        code = (EditText) findViewById(R.id.codery);
        phone_number = (EditText) findViewById(R.id.phone_numberry);
        school = (EditText) findViewById(R.id.schoolry);
        work = (EditText) findViewById(R.id.workry);
        
        
        final String fullnamey = fullname.getText().toString();
        final String usernamey = username.getText().toString();
        final String phone_numbey = phone_number.getText().toString();
        
     
        registerForContextMenu(pocketPhoto);
      
        
       
       


        
 
        // button click event
        save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// Check if user filled the form
				if(fullnamey.trim().length() > 0 && usernamey.trim().length() > 0 && phone_numbey.trim().length() > 0  ){
					
					
					new UploadFileToServer1().execute();
					new UploadFileToServer2().execute();
					new Personalize().execute();
					finish();
					
				}else{
					// user doen't filled that data
					// ask him to fill the form
					Toast.makeText(getApplicationContext(), "One of the reqired fields are missing", Toast.LENGTH_LONG).show();
				}
				
				
				
			}
 
                   });
        
     
 
    }
	
	
	
	
        class Personalize extends AsyncTask<String, String, String> {
       	 
        	String response = null; 
            /**
             * Before starting background thread Show Progress Dialog
             * */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(PersonalizeYourAccount.this);
                pDialog.setMessage("Registeration in progress...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }


            
            protected String doInBackground(String... args) {
            	
            	 

            	 // Building Parameters
                Map<String, String> params = new HashMap<String, String>();
                
                params.put("cover", final_image);
                params.put("pocketPhoto", final_image2);
                params.put("fullname", fullname.getText().toString());
                params.put("username", username.getText().toString());
                params.put("residence", residence.getText().toString());
                params.put("code", code.getText().toString());
                params.put("phone_number", phone_number.getText().toString());
                params.put("school", school.getText().toString());
                params.put("work", work.getText().toString());
               
                
                // making fresh volley request and getting json
                
                CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_register, params, new Response.Listener<JSONObject>() {

     			            public void onResponse(JSONObject response) {
     			            	
     			            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
     			                VolleyLog.d(TAG, "Response: " + response.toString());
     			                if (response != null) {
     			                	
     			                	
     			                	 // check for success tag
     			                    try {
     			                        int success = response.getInt(TAG_SUCCESS);
     			         
     			                        if (success == 1) {
     			                            // successfully created product
     			                            Intent i = new Intent(getApplicationContext(), AnimationTour.class);
     			                            startActivity(i);
     			                           
         			                            if (success == 3) {
             			                            // successfully created product
             			                            Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_LONG).show();
             			         
     			                            
     			                        } else {
     			                            // failed to create product
     			                        }}
     			                    } catch (JSONException e) {
     			                        e.printStackTrace();
     			                    }	
     			                   
     			                  
     			                    
     			                    
     			                }
     			            }
     			        }, new Response.ErrorListener() {

     			            public void onErrorResponse(VolleyError error) {
     			            	
     			       
     			                VolleyLog.d(TAG, "Error: " + error.getMessage());
     			                pDialog.dismiss();
     			                
     			                Toast.makeText(getApplicationContext(), "Unable to update credentials", Toast.LENGTH_LONG).show();
     			              
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
        
        
        //Long Press
        
        @Override
    	public void onCreateContextMenu(ContextMenu menu, final View v,
    	    ContextMenuInfo menuInfo) {
    	  if (v.getId()==R.id.pocketPhotoeditry) {
    		 
    		  
    	    
    	    String[] menuItems = getResources().getStringArray(R.array.long_array2);
    	    for (int i = 0; i<menuItems.length; i++) {
    	      menu.add(Menu.NONE, i, i, menuItems[i]);
    	   
    	    }
    	  }
    	  
    	  if (v.getId()==R.id.coverfotory) {
     		 
    		  
      	    
      	    String[] menuItems = getResources().getStringArray(R.array.long_array2plus);
      	    for (int i = 0; i<menuItems.length; i++) {
      	      menu.add(Menu.NONE, i, i, menuItems[i]);
      	   
      	    }
      	  }
    	  
    	     	}
    	
    	@Override
    	public boolean onContextItemSelected(MenuItem item) {
    	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    	  int menuItemIndex = item.getItemId();
    	  String[] menuItems = getResources().getStringArray(R.array.long_array2);
    	  String[] menuItems2 = getResources().getStringArray(R.array.long_array2plus);
    	  
    	  String menuItemName = menuItems[menuItemIndex];
    	
    	  String menuItemName2 = menuItems2[menuItemIndex];
    	

    	  if (menuItemIndex==0){
              
     		 Intent intent = new Intent();
     	        intent.setType("image/*");
     	        intent.setAction(Intent.ACTION_GET_CONTENT);
     	        intent.addCategory(Intent.CATEGORY_OPENABLE);
     	        startActivityForResult(intent, REQUEST_CODE); 
            
             
          
     	}
     	
     	if (menuItemIndex==1){
             
     		 Bitmap bitmap = ((BitmapDrawable) pocketPhoto.getDrawable())
     					.getBitmap();
     		  
     		  utils.saveImageToSDCard(bitmap);
             
          
     	
  		
  		}
    	  
		return true;
		
    	}
    	  
    	  
    	  
    	


    	/**
         * Receiving activity result method will be called after closing the camera
         * */
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // if the result is capturing Image
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                	
                	Uri selectedImageURI = data.getData();
                	
                	filePath = getRealPathFromURI(selectedImageURI);
                	
                	try {
                        // We need to recyle unused bitmaps
                    	Toast.makeText(getApplicationContext(), filePath.toString(), Toast.LENGTH_LONG).show();
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        InputStream stream = getContentResolver().openInputStream(
                                data.getData());
                        bitmap = BitmapFactory.decodeStream(stream);
                        stream.close();
                        cover.setImageBitmap(bitmap);
                        
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                 
                    } catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
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
                
             
                }} else if (requestCode == REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                	
                	Uri selectedImageURI = data.getData();
                	
                	filePath2 = getRealPathFromURI2(selectedImageURI);
                	
                	try {
                        // We need to recyle unused bitmaps
                    	Toast.makeText(getApplicationContext(), filePath2.toString(), Toast.LENGTH_LONG).show();
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        InputStream stream = getContentResolver().openInputStream(
                                data.getData());
                        bitmap = BitmapFactory.decodeStream(stream);
                        stream.close();
                        pocketPhoto.setImageBitmap(bitmap);
                        
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
            
            
         // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            
            
            image="IMG_"+timeStamp+".jpg";
            final_image = "http://activetalkgh.com/android_connect/uploads/"+image;
            
            return result;
        }
        
        
        private String getRealPathFromURI2(Uri contentURI) {
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
            
            
         // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            
            
            image2="IMG_"+timeStamp+".jpg";
            final_image2 = "http://activetalkgh.com/android_connect/uploads/"+image2;
            
            return result;
        }
        
  
        
          
            
            /**
             * Uploading the file2 to server
             * */
            private class UploadFileToServer2 extends AsyncTask<Void, Integer, String> {
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
         
                        File sourceFile = new File(filePath2);
         
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
                
                /**
                 * Uploading the file 1 to server
                 * */
                private class UploadFileToServer1 extends AsyncTask<Void, Integer, String> {
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


				@Override
				public void onClick(View b) {
					// TODO Auto-generated method stub
					
					
					 int id = b.getId();
					if (id == R.id.coverfotory) {
						alertSimpleListView();
					} else {
					}
				    
					
				}            
                
				
				public void alertSimpleListView() {
					 
				    /*
				     * WebView is created programatically here.
				     *
				     * @Here are the list of items to be shown in the list
				     */
				    final CharSequence[] items = { "Change", "Download"};
				 
				    AlertDialog.Builder builder = new AlertDialog.Builder(PersonalizeYourAccount.this);
				   
				    builder.setItems(items, new DialogInterface.OnClickListener() {
				    	
				        public void onClick(DialogInterface dialog, int item) {
				 
				        	if (item==0){
	                            
				        		 Intent intent = new Intent();
				        	        intent.setType("image/*");
				        	        intent.setAction(Intent.ACTION_GET_CONTENT);
				        	        intent.addCategory(Intent.CATEGORY_OPENABLE);
				        	        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE); 
	                           
	                            
	                         
	                    	}
	                    	
				        	if (item==1){
	                            
				        		 Bitmap bitmap = ((BitmapDrawable) cover.getDrawable())
				        					.getBitmap();
				        		  
				        		  utils.saveImageToSDCard(bitmap);
	                            
	                         
	                    	}
				        	
				            // will toast your selection
				        	Toast.makeText(getApplicationContext(), "Name: " + items[item], Toast.LENGTH_LONG).show();
				            
				            dialog.dismiss();
				 
				        }
				    }).show();
				}
        }





