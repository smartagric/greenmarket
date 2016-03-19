package com.integra.dealcaller;

import android.os.AsyncTask;





import android.os.Bundle
;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.SupportMenuInflater;
import android.text.style.TextAppearanceSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.integra.dealcaller.PagerSlidingTabStrip;
import com.integra.dealcaller.BadgeView;
import com.integra.dealcaller.R;


public class TimeLine extends AppCompatActivity  {
	public static BadgeView txtCount;
	public static BadgeView txtCount2;
	public static BadgeView txtCount3;
	public static BadgeView txtCount4;
	public static BadgeView txtCount5;
	private SharedPreferences prefs;
	private View view1;
	private View view2;
	private View view3;
	private View view4;
	private View view5;
	private LinearLayout lin;
	
	Animation animSlideUp;
	
	// action bar
    private android.support.v7.app.ActionBar actionbar;
    
    
   
   
    

ViewPager viewpager;
	FragmentPageAdapter ft;
	

    
	

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);
 
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
 
        MenuItem searchItem = menu.findItem(R.id.action_search);
 
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
 
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
 
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        // searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
 
        return super.onCreateOptionsMenu(menu);
    }
	

	
		
	
	
	

	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
		
		
		int id = item.getItemId();
		if (id == R.id.action_skip) {
			//Intent ng = new Intent(getApplicationContext(), FindFriends.class);
        	//startActivity(ng);
		
		
		} else if (id == R.id.action_create) {
			
			Intent ng = new Intent(getApplicationContext(), CreateNewShowcase.class);
			
			ng.putExtra("holder_id", "0246212975");
			ng.putExtra("name", "Example Trading Ltd.");  
        	//iv.putExtra("chattingFrom", "Junior");
        	//iv.putExtra("chattingToName", m.getTitle());
        	//iv.putExtra("chattingToDeviceID", m.getGCMREG());
        	ng.putExtra("cover_photo", "http://activetalkgh.com/json/1.jpg");
        	
        	startActivity(ng);
        	
			
		} else if (id == R.id.action_update) {
			
			Intent ng = new Intent(getApplicationContext(), AddItemToShowcase.class);
			
			ng.putExtra("holder_id", "0246212975");
			ng.putExtra("name", "Example Trading Ltd.");  
        	//iv.putExtra("chattingFrom", "Junior");
        	//iv.putExtra("chattingToName", m.getTitle());
        	//iv.putExtra("chattingToDeviceID", m.getGCMREG());
        	ng.putExtra("cover_photo", "http://activetalkgh.com/json/1.jpg");
        	
        	startActivity(ng);
		
		
        	
		}else{
			
		}
		
            return super.onOptionsItemSelected(item);
        }
    
	
	
	
	
	
	/**
     * Async task to load the data from server
     * **/
    private class SyncData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // set the progress bar view
            
        }
 
        @Override
        protected String doInBackground(String... params) {
            // not making real request in this demo
            // for now we use a timer to wait for sometime
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(String result) {
            
        }
    };
 


 
    /**
     * Launching new activity
     * */
    private void Reset() {
    	
        
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("loginstatus", "loggedout");
        edit.commit();
 
    }

	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		
		savedInstanceState = null;
	    
		super.onCreate(savedInstanceState);
		actionbar = getSupportActionBar();
	
		//actionbar.hide();
		actionbar.setTitle("Home");
	
		actionbar.setIcon(R.drawable.ic_action_cloud);
        setContentView(R.layout.tablayout);
      
		
		
		
		
		prefs = getSharedPreferences("Chat", 0);
		
		viewpager= (ViewPager) findViewById(R.id.pager);
		ft = new FragmentPageAdapter(getSupportFragmentManager());
		viewpager.setOffscreenPageLimit(5);
		viewpager.setAdapter(ft);
		
		
		// Bind the tabs to the ViewPager
		 PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		 
		 tabs.setViewPager(viewpager);
		 
		 		 
		 
		 


		
	
		
		
		
		
		
		 //1st Tab custom layout
		LayoutInflater inflater = LayoutInflater.from(this);
		view1 = inflater.inflate(R.layout.tabicon, null);
		txtCount = (BadgeView) view1.findViewById(R.id.txtCount);
		
		txtCount.setText("5");
		txtCount.setVisibility(View.GONE);
		
 
		
		//2nd Tab custom layout
		LayoutInflater inflater2 = LayoutInflater.from(this);
		view2 = inflater2.inflate(R.layout.tabicon2, null);
		txtCount2 = (BadgeView) view2.findViewById(R.id.txtCount2);
		txtCount2.setText("4");
		txtCount2.setVisibility(View.GONE);
		
		
		
		//3rd Tab custom layout
		LayoutInflater inflater3 = LayoutInflater.from(this);
		view3 = inflater3.inflate(R.layout.tabicon3, null);
		txtCount3 = (BadgeView) view3.findViewById(R.id.txtCount3);
		txtCount3.setText("3");
		txtCount3.setVisibility(View.VISIBLE);
		
		
		//4th Tab custom layout
		LayoutInflater inflater4 = LayoutInflater.from(this);
		view4 = inflater4.inflate(R.layout.tabicon4, null);
		txtCount4 = (BadgeView) view4.findViewById(R.id.txtCount4);
		txtCount4.setText("2");
		txtCount4.setVisibility(View.GONE);
		
		//5th Tab custom layout
		LayoutInflater inflater5 = LayoutInflater.from(this);
		view5 = inflater5.inflate(R.layout.tabicon5, null);
		txtCount5 = (BadgeView) view5.findViewById(R.id.txtCount5);
		txtCount5.setText("1");
		txtCount5.setVisibility(View.GONE);
		
	
		//actionbar.addTab(actionbar.newTab().setCustomView(view1).setTabListener(this).setTag("Slide Line"));
		//actionbar.addTab(actionbar.newTab().setCustomView(view2).setTabListener(this).setTag("You asked"));
		//actionbar.addTab(actionbar.newTab().setCustomView(view3).setTabListener(this).setTag("You"));
		//actionbar.addTab(actionbar.newTab().setCustomView(view4).setTabListener(this).setTag("Friends"));
		//actionbar.addTab(actionbar.newTab().setCustomView(view5).setTabListener(this).setTag("Help Page"));
		
		
		
	
		
		 

	       
		tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			public void onPageSelected(int arg0) {
				//actionbar.setSelectedNavigationItem(arg0);
	
				
			}
			
			
			
			public void onPageScrolled(int arg0, float arg1, int arg2, int arg3, int arg4) {
				// TODO Auto-generated method stub
				
			}
			
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	


	

   



	

				
				
	public void requestBackup() {
		   BackupManager bm = new BackupManager(this);
		   bm.dataChanged();
		 }			
				
				
	
	@Override
	   public void onStop() {
	       super.onStop();  // Always call the superclass method first

	       requestBackup();
	       }				
	
	}



