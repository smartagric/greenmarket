package com.integra.dealcaller;

import java.io.UnsupportedEncodingException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class CommitmentPageDetail2 extends ActionBarActivity implements OnClickListener {

	private List<FeedItem4> feedItems;
	private FeedListAdapter4 listAdapter;
	private static final String TAG = TimeLineFragment.class.getSimpleName();
    private ListView listView;
    private ImageButton helpy;
    private static String holder_id,service;
    private String URL_FEED = "http://activetalkgh.com/android_connect/sme_postups.php";
    private ProgressBar pb;
    private EditText inputSearch;
    private CustomRequest jsObjRequest;
    private static String cover_photo;
    private static String name;
    
    private TextView concerning, title;
    private static String status;
    private static String verification;
    private static String nesting_institution;
    private ImageView groupie2;
    
   
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.commitment_page_detail);
	//getSupportActionBar().hide();
	
	
	feedItems = new ArrayList<FeedItem4>();
    
	 
    listAdapter = new FeedListAdapter4(this, feedItems);
    
    
    
    NestedListView listview = (NestedListView) findViewById(R.id.listviewt2);
	listview.setAdapter(listAdapter);
	
	helpy = (ImageButton)findViewById(R.id.btnAddExpense);
	
	pb = (ProgressBar)findViewById(R.id.progressBarcpd);
	
	groupie2 = (ImageView)findViewById(R.id.imageButton122);
	concerning = (TextView)findViewById(R.id.lblExpenseCancel);
	title = (TextView)findViewById(R.id.teks4);
	
	registerForContextMenu(groupie2);
	

	
	 Bundle i = getIntent().getExtras();
     if (i != null) {
   	  
     cover_photo = i.getString("cover_photo");
     name = i.getString("name");
     holder_id = i.getString("holder_id");
     service = i.getString("service");
     //status = i.getString("status");
     //verification = i.getString("verification");
     //nesting_institution = i.getString("nesting_institution");
     
     
     
     }
     
     getSupportActionBar().setTitle(name);
     
     concerning.setText("Phone: "+ holder_id);
     title.setText(service);
     
     
	int progress = 0;
	
	 pb.setVisibility(View.VISIBLE);			
     pb.setProgress(progress);
     
     helpy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			
				AlertDialog.Builder builder = new AlertDialog.Builder(CommitmentPageDetail2.this);
		        builder.setMessage("Call "+name+"?");
		        builder.setIcon(R.drawable.giving_hand);
		        builder.setTitle("Confirm...");
		        builder .setCancelable(false)
		          .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               //MyActivity.this.finish();
		        	   
		        	   try {
		                    Intent callIntent = new Intent(Intent.ACTION_CALL);
		                    callIntent.setData(Uri.parse("tel:"+holder_id.toString()));
		                    startActivity(callIntent);
		                    
		                } catch (ActivityNotFoundException activityException) {
		                    //Log.e("Calling a Phone Number", "Call failed", activityException);
		                }
			        	
		           }
		       })
		       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               dialog.cancel();
		           }
		       });
		        
		        
		        AlertDialog alertdialog = builder.create();
		        alertdialog.show();
	        	
	        	
	        	
	        
	            
				
			}
		});
    

     // Retrieves an image specified by the URL, displays it in the UI.
       
    
        ImageRequest request = new ImageRequest(cover_photo, new Response.Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap bitmap) {
				// TODO Auto-generated method stub
				groupie2.setImageBitmap(bitmap);
			}
            
        	
        }, 0, 0, null, 
        
        new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            	
               
            }
            
        });
     // Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(request);
    
    
        
        

     Map<String, String> params = new HashMap<String, String>();
     
     
     params.put("holder_id", holder_id);
     
     
     
     
     /**
      * Use this query to display search results like
      * 1. Getting the data from SQLite and showing in listview
      * 2. Making webrequest and displaying the data
      * For now we just display the query only
      */
     
     // making fresh volley request and getting json
     
      jsObjRequest = new CustomRequest(Method.POST, URL_FEED, params, new Response.Listener<JSONObject>() {

		            public void onResponse(JSONObject response) {
		            	
		            //Toast.makeText(getApplicationContext(),"Response: " + response.toString(), Toast.LENGTH_LONG).show();
		                VolleyLog.d(TAG, "Response: " + response.toString());
		                if (response != null) {
		                	
		                	
		                	parseJsonFeed(response);
		                   
		                  
		                    
		                    
		                }
		            }
		        }, new Response.ErrorListener() {

		            public void onErrorResponse(VolleyError error) {
		            	
		       
		                VolleyLog.d(TAG, "Error: " + error.getMessage());
		                pb.setVisibility(View.GONE);
		                
		                Toast.makeText(getApplicationContext(), "Unable to fetch data", Toast.LENGTH_LONG).show();
		              
		            }
		        });
		
		// Adding request to volley request queue
		AppController.getInstance().addToRequestQueue(jsObjRequest);

     

  
     
     
            
        
        
    
    
	
	
}

       



    



/**
 * Parsing json reponse and passing the data to feed view list adapter
 * */
private void parseJsonFeed(JSONObject response) {
    try {
        JSONArray feedArray = response.getJSONArray("feed");

        for (int i = 0; i < feedArray.length(); i++) {
            JSONObject feedObj = (JSONObject) feedArray.get(i);

            FeedItem4 item = new FeedItem4();
            
            item.setId(feedObj.getInt("id"));
            
            String name = feedObj.isNull("name") ? null : feedObj.getString("name");
            item.setName(name);

            String nest = feedObj.isNull("price_range") ? null : feedObj.getString("price_range");
            item.setNesting(nest);
            
            
            String status = feedObj.isNull("category_description") ? null : feedObj.getString("category_description");
            item.setStatus(status);
            
            
         // Pic1 might be null sometimes
            String p = feedObj.isNull("photo1") ? null : feedObj
                    .getString("photo1");
            
            item.setProfilePic(p);
          
            // Pic1 might be null sometimes
            String p_n = feedObj.isNull("photo1_cost") ? null : feedObj
                    .getString("photo1_cost");
            
            item.setProfilePic_name(p_n);
            
            // Pic1 might be null sometimes
            String p_d = feedObj.isNull("photo1_description") ? null : feedObj
                    .getString("photo1_description");
            
            item.setProfilePic_description(p_d);
            
            
         // Pic2 might be null sometimes
            String p2 = feedObj.isNull("photo2") ? null : feedObj
                    .getString("photo2");
            
            
            item.setProfilePic2(p2);
            
         // Pic1 might be null sometimes
            String p2_n = feedObj.isNull("photo2_cost") ? null : feedObj
                    .getString("photo2_cost");
            
            item.setProfilePic2_name(p2_n);
            
            // Pic1 might be null sometimes
            String p2_d = feedObj.isNull("photo2_description") ? null : feedObj
                    .getString("photo2_description");
            
            item.setProfilePic2_description(p2_d);
            
            
            
         // Pic3 might be null sometimes
            String p3 = feedObj.isNull("photo3") ? null : feedObj
                    .getString("photo3");
            
            
            item.setProfilePic3(p3); 
            
            
         // Pic1 might be null sometimes
            String p3_n = feedObj.isNull("photo3_cost") ? null : feedObj
                    .getString("photo3_cost");
            
            item.setProfilePic3_name(p3_n);
            
            // Pic1 might be null sometimes
            String p3_d = feedObj.isNull("photo3_description") ? null : feedObj
                    .getString("photo3_description");
            
            item.setProfilePic3_description(p3_d);
            
            
            
            
            
         // Pic4 might be null sometimes
            String p4 = feedObj.isNull("photo4") ? null : feedObj
                    .getString("photo4");
            
            
            item.setProfilePic4(p4);
            
            
         // Pic1 might be null sometimes
            String p4_n = feedObj.isNull("photo4_cost") ? null : feedObj
                    .getString("photo4_cost");
            
            item.setProfilePic4_name(p4_n);
            
            // Pic1 might be null sometimes
            String p4_d = feedObj.isNull("photo4_description") ? null : feedObj
                    .getString("photo4_description");
            
            item.setProfilePic4_description(p4_d);
            
            
         // Pic5 might be null sometimes
            String p5 = feedObj.isNull("photo5") ? null : feedObj
                    .getString("photo5");
            
            
            item.setProfilePic5(p5);
            
         // Pic1 might be null sometimes
            String p5_n = feedObj.isNull("photo5_cost") ? null : feedObj
                    .getString("photo5_cost");
            
            item.setProfilePic5_name(p5_n);
            
            // Pic1 might be null sometimes
            String p5_d = feedObj.isNull("photo5_description") ? null : feedObj
                    .getString("photo5_description");
            
            item.setProfilePic5_description(p5_d);
         
          

            
            
           

            

            feedItems.add(item);
        }

        // notify data changes to list adapater
        listAdapter.notifyDataSetChanged();
        pb.setVisibility(View.GONE);
        
    } catch (JSONException e) {
        e.printStackTrace();
    }













	
}








	private static String[] dataObjects = new String[] { "Text #1", "Text #2",
	"Text #3" };






	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
//Long Press
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.imageButton122) {
		 
		  
	    
	    String[] menuItems = getResources().getStringArray(R.array.long_array3);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	   
	    }
	  }
	  
	 
		  
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
	  String[] menuItems = getResources().getStringArray(R.array.long_array3);
	  String menuItemName = menuItems[menuItemIndex];
	  
	  Toast.makeText(this, "Selected %s for item %s" + menuItemName, Toast.LENGTH_LONG).show();
	  
	  return true;
	}

	
	
	

	
	@Override
	   public void onPause() {
	       super.onPause();  // Always call the superclass method first

	       
	       }



	@Override
		public void onStop() {
			super.onStop();  // Always call the superclass method first

			jsObjRequest.cancel();
}
	}