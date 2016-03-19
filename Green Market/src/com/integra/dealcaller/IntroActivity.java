package com.integra.dealcaller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.integra.dealcaller.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class IntroActivity extends ActionBarActivity{

	
	 private String URL_FEED = "http://activetalkgh.com/android_connect/commitment_page_detail2.php";
		ArrayList<String> actorsList;
		
	private ViewPagerAdapter mAdapter;
		private AutoScrollViewPager mPager;
		private CirclePageIndicator mIndicator;
		private int selectedIndex; 
		private Handler mHandler = new Handler();
		Boolean mPageEnd;
		protected static final int[] ICONS = new int[] {
            R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4
    };
		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.intro_activity);
		    getSupportActionBar().hide();
		 
		    
		 
		    mAdapter = new ViewPagerAdapter(this, ICONS);

	        mPager = (AutoScrollViewPager)findViewById(R.id.pager);
	       
	        mPager.setAdapter(mAdapter);
	      
	        
	        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
	        mIndicator.setViewPager(mPager);
	        
	        mIndicator.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int i) {
					// TODO Auto-generated method stub
					if(i == ICONS.length- 1){
						
						 mHandler.postDelayed(new Runnable() {
					            public void run() {
					                doStuff();
					            }
					        }, 4000);
					}
					
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
					
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        
	       //mPager.startAutoScroll(7000);
	       
	        
	        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this, IntroActivity.class));
	        
	        
	       
		    
		}
		
		
		
		
		
		
	    

	

	private void doStuff() {
		
		
		//mPager.stopAutoScroll();
		Intent iv = new Intent(getApplicationContext(), MainActivity2.class);
		startActivity(iv);
		
		finish();
    }
		
		
		}