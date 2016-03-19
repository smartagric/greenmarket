package com.integra.dealcaller;




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.integra.dealcaller.R;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GridClass extends ActionBarActivity {
	
	private GridView gridView;
	private ProgressBar pbLoader;
	private static final String TAG = GridClass.class.getSimpleName();
	private SharedPreferences prefs;
	private GridViewAdapter adapter;

	Boolean isInternetPresent = false;
	private int columnWidth;	
	private String selectedAlbumId;
	private List<Wallpaper> photosList;
	public static String foto2 = null;
	private TextView noInternet,noResults;
	private int progress;
	 
     
     private String URL_FEED = "http://activetalkgh.com/android_connect/photos.php";
   
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_grid);
 
        getSupportActionBar().setTitle("Photos");
        
        gridView = (GridView)findViewById(R.id.grid_view);
        noResults = (TextView)findViewById(R.id.no_results);
        
        photosList = new ArrayList<Wallpaper>();
        
        
        progress = 0;
		pbLoader = (ProgressBar)findViewById(R.id.pbLoader);
		pbLoader.setVisibility(View.VISIBLE);
		

		// get Internet status
       
        
        
		pbLoader.setProgress(progress);
	
		prefs = getSharedPreferences("Chat", 0);
		
		
		String username = prefs.getString("username", null);
		String profile = prefs.getString("profile_pic", null);
		String email = prefs.getString("email", null);

				
		
		
		
		
		Map<String, String> params = new HashMap<String, String>();
        
        
        params.put("from_email", email);
        
        
        
        
        /**
         * Use this query to display search results like
         * 1. Getting the data from SQLite and showing in listview
         * 2. Making webrequest and displaying the data
         * For now we just display the query only
         */
        
        // making fresh volley request and getting json
        
        CustomRequest jsObjRequest = new CustomRequest(Method.POST, URL_FEED, params, new Response.Listener<JSONObject>() {

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
		                pbLoader.setVisibility(View.GONE);
		                
		                //Toast.makeText(getApplicationContext(), "Unable to fetch data", Toast.LENGTH_LONG).show();
		                
		                
		                	gridView.setEmptyView(noResults);
		                        //noResults.setVisibility(View.VISIBLE);
		                        		                     
		                	
		                
		              
		            }
		        });
		
		// Adding request to volley request queue
		AppController.getInstance().addToRequestQueue(jsObjRequest);

        

     
      

        
        
		
	
     // Initilizing Grid View
		InitilizeGridLayout();

		// Gridview adapter
				adapter = new GridViewAdapter(this, photosList, columnWidth);

				// setting grid view adapter
				gridView.setAdapter(adapter);
				
				

				// Grid item select listener
				gridView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {

						// On selecting the grid image, we launch fullscreen activity
						Intent i = new Intent(getApplicationContext(),ImageViewer.class);

						// Passing selected image to fullscreen activity
						Wallpaper photo = photosList.get(position);
						
						
					
						 
						i.putExtra("foto", photo.getUrl());
						
						startActivity(i);
					}
				});
		
    }
	
	
	
	/**
	 * Method to calculate the grid dimensions Calculates number columns and
	 * columns width in grid
	 * */
	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				4, r.getDisplayMetrics());

		// Column width
		
		columnWidth = (int) ((getScreenWidth() - ((2 + 1) * padding)) / 2);
				
		// Setting number of grid columns
		
		gridView.setColumnWidth(columnWidth);
		gridView.setNumColumns(2);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);

		// Setting horizontal and vertical padding
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}
	
	
	 private void parseJsonFeed(JSONObject response) {
         try {
             JSONArray feedArray = response.getJSONArray("feed");
  
             for (int i = 0; i < feedArray.length(); i++) {
                 JSONObject feedObj = (JSONObject) feedArray.get(i);
  
                 Wallpaper wallpap = new Wallpaper();
                 
                 wallpap.setUrl((feedObj.getString("image")));
                 wallpap.setPhotoJson((feedObj.getString("image")));
                 

                 // adding movie to movies array
                 photosList.add(wallpap);

             }
  
             // notify data changes to list adapater
             adapter.notifyDataSetChanged();
             pbLoader.setVisibility(View.GONE);
         } catch (JSONException e) {
             e.printStackTrace();
         }
}
	 
	 /*
		 * getting screen width
		 */
		@SuppressWarnings("deprecation")
		public int getScreenWidth() {
			int columnWidth;
			WindowManager wm = (WindowManager) this
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();

			final Point point = new Point();
			try {
				display.getSize(point);
			} catch (java.lang.NoSuchMethodError ignore) {
				// Older device
				point.x = display.getWidth();
				point.y = display.getHeight();
			}
			columnWidth = point.x;
			return columnWidth;
		}
		
		
		public void saveImageToFolder(Bitmap bitmap) {
			
			// create new folder in Internal memory
			
			File myDir = this.getDir("ActiveTalk photos", Context.MODE_PRIVATE); //Creating an internal dir;
			if(!myDir.exists())
			{
			     myDir.mkdirs();
			}     
			
			Random generator = new Random();
			int n = 10000;
			n = generator.nextInt(n);
			String fname = "Wallpaper-" + n + ".jpg";
			File file = new File(myDir, fname);
			if (file.exists())
				file.delete();
			try {
				FileOutputStream out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();
				Toast.makeText(this,"Image saved successflly",
						Toast.LENGTH_SHORT).show();
				Log.d(TAG, "Wallpaper saved to: " + file.getAbsolutePath());

			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this,
						"Failed to save image",
						Toast.LENGTH_SHORT).show();
			}
		}
		
		
		public void setAsWallpaper(Bitmap bitmap) {
			try {
				WallpaperManager wm = WallpaperManager.getInstance(this);

				wm.setBitmap(bitmap);
				Toast.makeText(this,
						"Wallpaper set",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this,
						"Failed to set as Wallpaper",
						Toast.LENGTH_SHORT).show();
			}
		}
}


