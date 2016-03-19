package com.integra.dealcaller;


import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.integra.dealcaller.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CentralImageViewer extends Activity {
	
	private static final String TAG = CentralImageViewer.class.getSimpleName();
	private ProgressBar pbLoader;
	
	private ImageView fullImageView;
	
	public static final String TAG_SEL_IMAGE = "selectedImage";
	private FeedListAdapter feedy;
	
	
    /** Called when the activity is first created. */
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.central_image_viewer);
        
        pbLoader=(ProgressBar)findViewById(R.id.progressBarCentral);
        
       int progress = 0;
       
              fullImageView = (ImageView) findViewById(R.id.imageViewCentral);
       
       
             
              
              
       
              Bundle i = getIntent().getExtras();
              if (i != null) {
            	  
              String selectedPhoto = i.getString("foto");
            
		
		
		

		// check for selected photo null
		if (selectedPhoto != null) {
			
			


			
			pbLoader.setVisibility(View.VISIBLE);
		       pbLoader.setProgress(progress);
			
			imageLoader.get(selectedPhoto,
					new ImageListener() {

						@Override
						public void onErrorResponse(
								VolleyError arg0) {
							Toast.makeText(
									getApplicationContext(),
									"failed to fetch image",
									Toast.LENGTH_LONG).show();
						}

						@Override
						public void onResponse(
								ImageContainer response,
								boolean arg1) {
							if (response.getBitmap() != null) {
								// load bitmap into imageview
								
								fullImageView.setImageBitmap(null);
								fullImageView
										.setImageBitmap(response
												.getBitmap());
								
								// hide loader and show set &
								// download buttons
								pbLoader.setVisibility(View.GONE);
								
							}
						}
					});

			
		}
    }
    }
}