package com.integra.dealcaller;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.integra.dealcaller.R;

public class FeedListAdapter3 extends BaseAdapter implements OnClickListener {	
	private Activity activity;
	private LayoutInflater inflater;
	private List<FeedItem3> feedItems;
	
	private int position;
	private String foto = null;
	private String foto2 = null;
	private String foto3 = null;
	private SharedPreferences prefs;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG = FeedListAdapter3.class.getSimpleName();
	public static final String url_share_photo3 = "http://activetalkgh.com/android_connect/shared_post.php";
	public static final String url_share_photo4 = "http://activetalkgh.com/android_connect/expressing.php";
	private static String postType;
	
	private static String ExpressionType1;
	private static String ExpressionType2;
	private static String ExpressionType3;
	private static String ExpressionType4;
	private static String ExpressionType5;
	
	private static Map<String, String> params2;
	
	private static Map<String, String> paramsExp1;
	private static Map<String, String> paramsExp2;
	private static Map<String, String> paramsExp3;
	private static Map<String, String> paramsExp4;
	private static Map<String, String> paramsExp5;
	
	private static String forEmail;
	private static String forUsername;
	public static String covery = null;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public FeedListAdapter3(Activity activity, List<FeedItem3> feedItems) {
		this.activity = activity;
		this.feedItems = feedItems;
	}


	public int getCount() {
		return feedItems.size();
	}

	
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	public long getItemId(int position) {
		return position;
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.feed_item3, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.name3);
		name.setOnClickListener(this);

		TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp3);
		
		TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg3);
		
		TextView comments = (TextView) convertView.findViewById(R.id.comments3);
		
		TextView expressions = (TextView) convertView.findViewById(R.id.expressions3);


		Button emotion = (Button) convertView.findViewById(R.id.ButtonEmotion3);
		emotion.setOnClickListener(this);
		
		Button comment = (Button) convertView.findViewById(R.id.ButtonComment3);
		comment.setOnClickListener(this);
		
		Button share = (Button) convertView.findViewById(R.id.ButtonShare3);
		share.setOnClickListener(this);
		
		
		TextView url = (TextView) convertView.findViewById(R.id.txtUrl3);
		url.setOnClickListener(this);
		
		NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic3);
		profilePic.setOnClickListener(this);
		
		FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage3);
		feedImageView.setOnClickListener(this);

		final FeedItem3 item = feedItems.get(position);
		
	
		name.setText(item.getName());
		


	

		// Converting timestamp into x ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		timestamp.setText(timeAgo);

		// Chcek for empty status message
		if (!TextUtils.isEmpty(item.getStatus())) {
			statusMsg.setText(item.getStatus());
			statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			statusMsg.setVisibility(View.GONE);
		}

		
		// Check for empty comments 
		if (item.getComments()>0) {
			comments.setText("comments: "+ item.getComments());
		} else {
			comments.setVisibility(View.GONE);
		}

       // Check for empty expressions 
		if (item.getExp()>0) {
			expressions.setText("expressions: "+item.getExp());
		} else {
			expressions.setVisibility(View.GONE);
		}

		// Checking for null feed url
		if (item.getUrl() != null) {
			url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
					+ item.getUrl() + "</a> "));

			// Making url clickable
			url.setMovementMethod(LinkMovementMethod.getInstance());
			url.setVisibility(View.VISIBLE);
		} else {
			// url is null, remove from the view
			url.setVisibility(View.GONE);
		}

		// user profile pic
		profilePic.setImageUrl(item.getProfilePic(), imageLoader);

		// Feed image
		if (item.getImge() != null) {
			feedImageView.setImageUrl(item.getImge(), imageLoader);
			feedImageView.setVisibility(View.VISIBLE);
			feedImageView
					.setResponseObserver(new FeedImageView.ResponseObserver() {
						public void onError() {
							
						}

						public void onSuccess() {
							
						}
					});
		} else {
			feedImageView.setVisibility(View.GONE);
		}
		
comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

	        	Intent iv = new Intent(activity, CommentDialog.class);
	        
	           
	        	iv.putExtra("foto2", item.getImge());
	        	iv.putExtra("username", item.getName());
	        	iv.putExtra("email", item.getEmail());
		
	            activity.startActivity(iv);
	            
	           
	            
				
			}
		});
		
		
		profilePic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

	        	
	            
	        
	           
				
			}
		});

		
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				postType = "SharedPost";
				
				prefs = activity.getSharedPreferences("Chat", 0);
				
				
				String username = prefs.getString("username", null);
				String profile = prefs.getString("profile_pic", null);
				String email = prefs.getString("email", null);

	        	 // Building Parameters
	            params2 = new HashMap<String, String>();
	            
	            params2.put("name", username);
	            params2.put("final_image", item.getImge());
	            params2.put("status", username+" shared "+item.getName()+"'s post :"+item.getStatus());
	            params2.put("profilePic", profile);
	            params2.put("fromEmail", email);
	            params2.put("postType", postType);
	            params2.put("forUsername", item.getName());
	            params2.put("forEmail", item.getEmail());

	            //Performing Share method
	            
	            new ShareMethod().execute();
	        
	           
				
			}
		});

		feedImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

	        	Intent i = new Intent(activity, ImageViewer.class);
	        
	           
	        	i.putExtra("foto", item.getImge());
		
	            activity.startActivity(i);
	            
	           
	            
				
			}
		});
		
		
		emotion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				final String [] items = new String[] {"Like", "Congratulate","Laugh", "Feeling Motivated","Sympathize"};
	            final Integer[] icons = new Integer[] {android.R.drawable.ic_menu_gallery, R.drawable.ic_action_camera,android.R.drawable.ic_menu_gallery, 

R.drawable.ic_action_camera,android.R.drawable.ic_menu_gallery};
	            ListAdapter adapter = new ArrayAdapterWithIcon(activity, items, icons);

	            
	            
	            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.AlertDialogCustom));
	            
	           builder.setTitle("your expression...").setAdapter(adapter, new DialogInterface.OnClickListener() {
	                    
	        	   public void onClick(DialogInterface dialog, int arg0 ) {
	                    	
	                    	if (arg0==0){
	                        
	                        
	                        ExpressionType1 = "Like";
	        				
	                        prefs = activity.getSharedPreferences("Chat", 0);
	        				
	        				
	        				String username = prefs.getString("username", null);
	        				String profile = prefs.getString("profile_pic", null);
	        				String email = prefs.getString("email", null);

	        	        	 // Building Parameters
	        	            paramsExp1 = new HashMap<String, String>();
	        	            
	        	            paramsExp1.put("name", username);
	        	            paramsExp1.put("image_url", item.getImge());
	        	            paramsExp1.put("fromEmail", email);
	        	            paramsExp1.put("expressionType", ExpressionType1);
	        	            paramsExp1.put("forUsername", item.getName());
	        	            paramsExp1.put("forEmail", item.getEmail());
	        	            
	        	            new Expression1_method().execute();
	                       
	                    }
	                    	
	                    	if (arg0==1){
	                            
	                            
	                            ExpressionType2 = "Congratulate";
		        				
	                            prefs = activity.getSharedPreferences("Chat", 0);
	            				
	            				
	            				String username = prefs.getString("username", null);
	            				String profile = prefs.getString("profile_pic", null);
	            				String email = prefs.getString("email", null);

		        	        	 // Building Parameters
		        	            paramsExp2 = new HashMap<String, String>();
		        	            
		        	            paramsExp2.put("name", username);
		        	            paramsExp2.put("image_url", item.getImge());
		        	            paramsExp2.put("fromEmail", email);
		        	            paramsExp2.put("expressionType", ExpressionType2);
		        	            paramsExp2.put("forUsername", item.getName());
		        	            paramsExp2.put("forEmail", item.getEmail());
		        	            
		        	            new Expression2_method().execute();
	                            
	                         
	                    	}
	                    	
	                    	if (arg0==2){
	                            
	                    		ExpressionType3 = "Laugh";
		        				
	                    		prefs = activity.getSharedPreferences("Chat", 0);
	            				
	            				
	            				String username = prefs.getString("username", null);
	            				String profile = prefs.getString("profile_pic", null);
	            				String email = prefs.getString("email", null);

		        	        	 // Building Parameters
		        	            paramsExp3 = new HashMap<String, String>();
		        	            
		        	            paramsExp3.put("name", username);
		        	            paramsExp3.put("image_url", item.getImge());
		        	            paramsExp3.put("fromEmail", email);
		        	            paramsExp3.put("expressionType", ExpressionType3);
		        	            paramsExp3.put("forUsername", item.getName());
		        	            paramsExp3.put("forEmail", item.getEmail());
		        	            
		        	            new Expression3_method().execute();
	                            
	                         
	                    	}
	                    	
	                    	if (arg0==3){
	                            
	                    		
	                    		ExpressionType4 = "Feeling Motivated";
		        				
	                    		prefs = activity.getSharedPreferences("Chat", 0);
	            				
	            				
	            				String username = prefs.getString("username", null);
	            				String profile = prefs.getString("profile_pic", null);
	            				String email = prefs.getString("email", null);
	            				

		        	        	 // Building Parameters
		        	            paramsExp4 = new HashMap<String, String>();
		        	            
		        	            paramsExp4.put("name", username);
		        	            paramsExp4.put("image_url", item.getImge());
		        	            paramsExp4.put("fromEmail", email);
		        	            paramsExp4.put("expressionType", ExpressionType4);
		        	            paramsExp4.put("forUsername", item.getName());
		        	            paramsExp4.put("forEmail", item.getEmail());
		        	            
		        	            new Expression4_method().execute();
	                            
	                         
	                    	}
	                    	
	                    	if (arg0==4){
	                    		
	                    		ExpressionType5 = "Sympathize";
		        				
	                    		prefs = activity.getSharedPreferences("Chat", 0);
	            				
	            				
	            				String username = prefs.getString("username", null);
	            				String profile = prefs.getString("profile_pic", null);
	            				String email = prefs.getString("email", null);

		        	        	 // Building Parameters
		        	            paramsExp5 = new HashMap<String, String>();
		        	            
		        	            paramsExp5.put("name", username);
		        	            paramsExp5.put("image_url", item.getImge());
		        	            paramsExp5.put("fromEmail", username);
		        	            paramsExp5.put("expressionType", ExpressionType5);
		        	            paramsExp5.put("forUsername", item.getName());
		        	            paramsExp5.put("forEmail", item.getEmail());
		        	            
		        	            new Expression5_method().execute();
	                            
	                         
	                    	}
	                    }}).show();
	           
	            
				
			}
		});
		

		return convertView;
	}


    public void onClick(View v) {
    	
        switch (v.getId()) {
        
       		
	
        default:
            break;
        }
	
    }
    

	public Object getFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	 class ShareMethod extends AsyncTask<String, String, String> {
     	 
	    	String response = null; 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            
	            Toast.makeText(activity, "Starting to share", Toast.LENGTH_LONG).show();
	        }


	        /**
	         * Deleting product
	         * */
	        protected String doInBackground(String... args) {
	        	
	        		            // making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo3, params2, new Response.Listener<JSONObject>() {

	 			            public void onResponse(JSONObject response) {
	 			            	
	 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	 			                VolleyLog.d(TAG, "Response: " + response.toString());
	 			                if (response != null) {
	 			                	
	 			                	
	 			                	 // check for success tag
	 			                    try {
	 			                        int success = response.getInt(TAG_SUCCESS);
	 			         
	 			                        if (success == 1) {
	 			                            // successfully created product
	 			                            Toast.makeText(activity, "Successfully shared", Toast.LENGTH_LONG).show();
	 			                           
	         			         
	 			                            
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
	 			                
	 			                
	 			                Toast.makeText(activity, "Unable to write", Toast.LENGTH_LONG).show();
	 			              
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
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}
	 
	 
	 ///Expression1 asynctask
	 
	 class Expression1_method extends AsyncTask<String, String, String> {
     	 
	    	String response = null; 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            
	            Toast.makeText(activity, "Starting to share", Toast.LENGTH_LONG).show();
	        }


	        /**
	         * Deleting product
	         * */
	        protected String doInBackground(String... args) {
	        	
	        		            // making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp1, new Response.Listener<JSONObject>() {

	 			            public void onResponse(JSONObject response) {
	 			            	
	 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	 			                VolleyLog.d(TAG, "Response: " + response.toString());
	 			                if (response != null) {
	 			                	
	 			                	
	 			                	 // check for success tag
	 			                    try {
	 			                        int success = response.getInt(TAG_SUCCESS);
	 			         
	 			                        if (success == 1) {
	 			                            // successfully created product
	 			                            Toast.makeText(activity, "Successfully shared", Toast.LENGTH_LONG).show();
	 			                           
	         			         
	 			                            
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
	 			                
	 			                
	 			                Toast.makeText(activity, "Unable to write", Toast.LENGTH_LONG).show();
	 			              
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
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}

	 
	 ///Expression2 Async task
	 
	 class Expression2_method extends AsyncTask<String, String, String> {
     	 
	    	String response = null; 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            
	            Toast.makeText(activity, "Starting to share", Toast.LENGTH_LONG).show();
	        }


	        /**
	         * Deleting product
	         * */
	        protected String doInBackground(String... args) {
	        	
	        		            // making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp2, new Response.Listener<JSONObject>() {

	 			            public void onResponse(JSONObject response) {
	 			            	
	 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	 			                VolleyLog.d(TAG, "Response: " + response.toString());
	 			                if (response != null) {
	 			                	
	 			                	
	 			                	 // check for success tag
	 			                    try {
	 			                        int success = response.getInt(TAG_SUCCESS);
	 			         
	 			                        if (success == 1) {
	 			                            // successfully created product
	 			                            Toast.makeText(activity, "Successfully shared", Toast.LENGTH_LONG).show();
	 			                           
	         			         
	 			                            
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
	 			                
	 			                
	 			                Toast.makeText(activity, "Unable to write", Toast.LENGTH_LONG).show();
	 			              
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
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}

	 
	 ///Expression3 async task
	 
	 class Expression3_method extends AsyncTask<String, String, String> {
     	 
	    	String response = null; 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            
	            Toast.makeText(activity, "Starting to share", Toast.LENGTH_LONG).show();
	        }


	        /**
	         * Deleting product
	         * */
	        protected String doInBackground(String... args) {
	        	
	        		            // making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp3, new Response.Listener<JSONObject>() {

	 			            public void onResponse(JSONObject response) {
	 			            	
	 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	 			                VolleyLog.d(TAG, "Response: " + response.toString());
	 			                if (response != null) {
	 			                	
	 			                	
	 			                	 // check for success tag
	 			                    try {
	 			                        int success = response.getInt(TAG_SUCCESS);
	 			         
	 			                        if (success == 1) {
	 			                            // successfully created product
	 			                            Toast.makeText(activity, "Successfully shared", Toast.LENGTH_LONG).show();
	 			                           
	         			         
	 			                            
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
	 			                
	 			                
	 			                Toast.makeText(activity, "Unable to write", Toast.LENGTH_LONG).show();
	 			              
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
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}

	 
	 ///Expression4 AsyncTask
	 
	 class Expression4_method extends AsyncTask<String, String, String> {
     	 
	    	String response = null; 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            
	            Toast.makeText(activity, "Starting to share", Toast.LENGTH_LONG).show();
	        }


	        /**
	         * Deleting product
	         * */
	        protected String doInBackground(String... args) {
	        	
	        		            // making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp4, new Response.Listener<JSONObject>() {

	 			            public void onResponse(JSONObject response) {
	 			            	
	 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	 			                VolleyLog.d(TAG, "Response: " + response.toString());
	 			                if (response != null) {
	 			                	
	 			                	
	 			                	 // check for success tag
	 			                    try {
	 			                        int success = response.getInt(TAG_SUCCESS);
	 			         
	 			                        if (success == 1) {
	 			                            // successfully created product
	 			                            Toast.makeText(activity, "Successfully shared", Toast.LENGTH_LONG).show();
	 			                           
	         			         
	 			                            
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
	 			                
	 			                
	 			                Toast.makeText(activity, "Unable to write", Toast.LENGTH_LONG).show();
	 			              
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
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}

	 
	 ///Expression5 AsyncTask
	 
	 class Expression5_method extends AsyncTask<String, String, String> {
     	 
	    	String response = null; 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	           
	            
	            Toast.makeText(activity, "Starting to share", Toast.LENGTH_LONG).show();
	        }


	        /**
	         * Deleting product
	         * */
	        protected String doInBackground(String... args) {
	        	
	        		            // making fresh volley request and getting json
	            
	            CustomRequest jsObjRequest = new CustomRequest(Method.POST, url_share_photo4, paramsExp5, new Response.Listener<JSONObject>() {

	 			            public void onResponse(JSONObject response) {
	 			            	
	 			            //Toast.makeText(activity,"Response: " + response.toString(), Toast.LENGTH_LONG).show();
	 			                VolleyLog.d(TAG, "Response: " + response.toString());
	 			                if (response != null) {
	 			                	
	 			                	
	 			                	 // check for success tag
	 			                    try {
	 			                        int success = response.getInt(TAG_SUCCESS);
	 			         
	 			                        if (success == 1) {
	 			                            // successfully created product
	 			                            Toast.makeText(activity, "Successfully shared", Toast.LENGTH_LONG).show();
	 			                           
	         			         
	 			                            
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
	 			                
	 			                
	 			                Toast.makeText(activity, "Unable to write", Toast.LENGTH_LONG).show();
	 			              
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
	           

	    		Toast.makeText(activity, "Return check", Toast.LENGTH_LONG) .show();
	    		  
	    		

	        }

	 
	}


}
