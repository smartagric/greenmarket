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
import com.integra.dealcaller.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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



public class CommitmentPageDetail extends ActionBarActivity implements OnClickListener {

	private List<FeedItem4> feedItems;
	private FeedListAdapter4 listAdapter;
	private static final String TAG = TimeLineFragment.class.getSimpleName();
    private ListView listView;
    private ImageButton helpy;
    private static String holder_id;
    private String URL_FEED = "http://activetalkgh.com/android_connect/commitment_page_detail.php";
    private ProgressBar pb;
    private EditText inputSearch;
    private CustomRequest jsObjRequest;
    private static String cover_photo;
    private static String name;
    
    private TextView concerning;
    private static String status;
    private static String verification;
    private static String nesting_institution;
    private ImageView groupie2;
    
   
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.commitment_page_detail);
	getSupportActionBar().hide();
	
	
	feedItems = new ArrayList<FeedItem4>();
    
	 
    listAdapter = new FeedListAdapter4(this, feedItems);
    
    
    
    ListView listview = (ListView) findViewById(R.id.listviewt2);
	listview.setAdapter(listAdapter);
	
	helpy = (ImageButton)findViewById(R.id.btnAddExpense);
	
	pb = (ProgressBar)findViewById(R.id.progressBarcpd);
	
	groupie2 = (ImageView)findViewById(R.id.imageButton122);
	concerning = (TextView)findViewById(R.id.lblExpenseCancel);
	
	
	registerForContextMenu(groupie2);
	

	
	 Bundle i = getIntent().getExtras();
     if (i != null) {
   	  
     cover_photo = i.getString("cover_photo");
     name = i.getString("name");
     holder_id = i.getString("holder_id");
     
     status = i.getString("status");
     verification = i.getString("verification");
     nesting_institution = i.getString("nesting_institution");
     
     
     
     }
     
     //getSupportActionBar().setTitle(name);
     
     concerning.setText("Concerning: "+ name);
     
     
     
	int progress = 0;
	
	 pb.setVisibility(View.VISIBLE);			
     pb.setProgress(progress);
     
     helpy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				 
				Intent vc = new Intent(getApplicationContext(), Help.class);
		        
		           
	        	vc.putExtra("holder_id", holder_id);
	        	
	        	
	        	
	        	
	            startActivity(vc);
	            
				
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
            item.setName(name);

            
            item.setNesting(nesting_institution);
            item.setVerify(verification);
            item.setStatus(status);
            
            
         // Pic1 might be null sometimes
            String p = feedObj.isNull("photo1") ? null : feedObj
                    .getString("photo1");
            
            
            item.setProfilePic(p);
            
         // Pic2 might be null sometimes
            String p2 = feedObj.isNull("photo2") ? null : feedObj
                    .getString("photo2");
            
            
            item.setProfilePic2(p2);
            
            
         // Pic3 might be null sometimes
            String p3 = feedObj.isNull("photo3") ? null : feedObj
                    .getString("photo3");
            
            
            item.setProfilePic3(p3);  
            
            
            
         // Pic4 might be null sometimes
            String p4 = feedObj.isNull("photo4") ? null : feedObj
                    .getString("photo4");
            
            
            item.setProfilePic4(p4);
            
            
         // Pic5 might be null sometimes
            String p5 = feedObj.isNull("photo5") ? null : feedObj
                    .getString("photo5");
            
            
            item.setProfilePic5(p5);
         
            
            
            
            item.setTimeStamp(feedObj.getString("timeStamp"));

            

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