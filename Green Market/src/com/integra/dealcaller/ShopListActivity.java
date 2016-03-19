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
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ShopListActivity extends ActionBarActivity implements AnimationListener, OnClickListener{

	
	 private String URL_FEED = "http://activetalkgh.com/android_connect/commitment_page_detail2.php";
		ArrayList<String> actorsList;
		
	private ViewPagerAdapter mAdapter;
		private AutoScrollViewPager mPager;
		private CirclePageIndicator mIndicator;
		private int selectedIndex;
		private Button conti;
		public static String foto,foto_name, foto_des,
		foto2_name, foto2_des,
		foto3_name, foto3_des,
		foto4_name, foto4_des,
		foto5_name, foto5_des,
		foto2,foto3,foto4,foto5;
		private FragmentPageAdapter2 ft;
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
		 
		    
	        mPager = (AutoScrollViewPager)findViewById(R.id.pager);
		 
		   
	        mPager.setAdapter(mAdapter);
	   
	        ft = new FragmentPageAdapter2(getSupportFragmentManager());
	        mPager.setOffscreenPageLimit(2);
	        mPager.setAdapter(ft);
	        
	        
	        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
	        mIndicator.setViewPager(mPager);
	        
	        
	        Bundle i = getIntent().getExtras();
	        if (i != null) {
	      	  
	        foto = i.getString("foto");
	        foto_name = i.getString("foto_name");
	        foto_des = i.getString("foto_des");
	        
	        foto2 = i.getString("foto2");
	        foto2_name = i.getString("foto2_name");
	        foto2_des = i.getString("foto2_des");
	        
	        foto3 = i.getString("foto3");
	        foto3_name = i.getString("foto3_name");
	        foto3_des = i.getString("foto3_des");
	        
	        foto4 = i.getString("foto4");
	        foto4_name = i.getString("foto4_name");
	        foto4_des = i.getString("foto4_des");
	        
	        
	        foto5 = i.getString("foto5");
	        foto5_name = i.getString("foto5_name");
	        foto5_des = i.getString("foto5_des");
	        
	        
	        
	        
	        }
	       
	        
	        mIndicator.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int i) {
					// TODO Auto-generated method stub
					
					
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
	       
	        
	        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this, ShopListActivity.class));
	        
	        
	       
		    
		}
		
		
		
		
		
		
	    

	

	private void doStuff() {
		
		
		//mPager.stopAutoScroll();
		
		
    }










	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub
		
		//doStuff();
	}



	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}










	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
		
		
		}