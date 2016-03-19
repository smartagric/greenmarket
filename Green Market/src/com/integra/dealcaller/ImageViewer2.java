package com.integra.dealcaller;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract.Columns;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class ImageViewer2 extends Activity implements OnClickListener {
	
	private static final String TAG = ImageViewer2.class.getSimpleName();
	private ProgressBar pbLoader;
	private Wallpaper selectedPhoto;
	private ImageView fullImageView;
	private List<Wallpaper> feedItems;
	public static final String TAG_SEL_IMAGE = "selectedImage";
	private GridViewAdapter feedy;
	private int imageWidth;
	public static String fname = null;
	private Utils3 utils;
	private ShareActionProvider mShareActionProvider;
	private LinearLayout llSetWallpaper;
	private LinearLayout llDownloadWallpaper;

	
	
	
    /** Called when the activity is first created. */
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer2);
        
        pbLoader=(ProgressBar)findViewById(R.id.progressBarViewer2);
        
        llSetWallpaper = (LinearLayout) findViewById(R.id.llSetWallpaper);
		llDownloadWallpaper = (LinearLayout) findViewById(R.id.llDownloadWallpaper);
	

		


		// layout click listeners
		llSetWallpaper.setOnClickListener(this);
		llDownloadWallpaper.setOnClickListener(this);

		// setting layout buttons alpha/opacity
		llSetWallpaper.getBackground().setAlpha(70);
		llDownloadWallpaper.getBackground().setAlpha(70);
        
       int progress = 0;
       
              fullImageView = (ImageView) findViewById(R.id.imageViewwy2);
              utils = new Utils3(this);
       
              feedy = new GridViewAdapter(this, feedItems, imageWidth);
              
              
       
       Intent i = getIntent();
     
		selectedPhoto = (Wallpaper) i.getSerializableExtra(GridClass.foto2);
		
		
		

		// check for selected photo null
		if (selectedPhoto != null) {
			
			

			String url = selectedPhoto.getUrl();
			
			pbLoader.setVisibility(View.VISIBLE);
		       pbLoader.setProgress(progress);
			
			imageLoader.get(url,
					new ImageListener() {

						@Override
						public void onErrorResponse(
								VolleyError arg0) {
							
							pbLoader.setVisibility(View.GONE);
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
    
    /**
	 * Adjusting the image aspect ration to scroll horizontally, Image height
	 * will be screen height, width will be calculated respected to height
	 * */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void adjustImageAspect(int bWidth, int bHeight) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		if (bWidth == 0 || bHeight == 0)
			return;

		int sHeight = 0;

		if (android.os.Build.VERSION.SDK_INT >= 13) {
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			sHeight = size.y;
		} else {
			Display display = getWindowManager().getDefaultDisplay();
			sHeight = display.getHeight();
		}

		int new_width = (int) Math.floor((double) bWidth * (double) sHeight
				/ (double) bHeight);
		params.width = new_width;
		params.height = sHeight;

		Log.d(TAG, "Fullscreen image new dimensions: w = " + new_width
				+ ", h = " + sHeight);

		fullImageView.setLayoutParams(params);
	}

	/**
	 * View click listener
	 * */
	@Override
	public void onClick(View v) {
		Bitmap bitmap = ((BitmapDrawable) fullImageView.getDrawable())
				.getBitmap();
		int id = v.getId();
		if (id == R.id.llDownloadWallpaper2) {
			utils.saveImageToSDCard(bitmap);
		} else if (id == R.id.llSetWallpaper2) {
			utils.setAsWallpaper(bitmap);
		} else {
		}
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate menu resource file.
	    getMenuInflater().inflate(R.menu.actionbar3, menu);

	    // Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.action_share22);

	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();

	
	   mShareActionProvider.setShareIntent(shareImage());

	    return super.onCreateOptionsMenu(menu);
	}

	   
	  
	   
	   
	   private void saveImageToSDCard(Bitmap bitmap) {
			File myDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"ActiveTalk");

			myDir.mkdirs();
			Random generator = new Random();
			int n = 10000;
			n = generator.nextInt(n);
			fname = "Photo-" + n + ".jpg";
			
			File file = new File(myDir, fname);
			if (file.exists())
				file.delete();
			try {
				FileOutputStream out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();
				Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.toast_saved).replace("#","\"" + "ActiveTalk" + "\""),Toast.LENGTH_SHORT).show();
				
				Log.d(TAG, "Wallpaper saved to: " + file.getAbsolutePath());

			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.toast_saved_failed),
						Toast.LENGTH_SHORT).show();
			}
		}  
	
	   
	   
	   private Intent shareImage() {
		    Intent share = new Intent(Intent.ACTION_SEND);
		 
		    // If you want to share a png image only, you can do:
		    // setType("image/png"); OR for jpeg: setType("image/jpeg");
		    share.setType("image/*");
		 
		    // Make sure you put example png image named myImage.png in your
		    // directory
		    String imagePath = Environment.getExternalStorageDirectory()
		            + "/"+ fname;
		 
		    File imageFileToShare = new File(imagePath);
		 
		    Uri uri = Uri.fromFile(imageFileToShare);
		    share.putExtra(Intent.EXTRA_STREAM, uri);
		 
		    startActivity(Intent.createChooser(share, "Share Image!"));
			return share;
		}

}
