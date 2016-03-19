package com.integra.dealcaller;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.integra.dealcaller.R;

public class AnimationTour extends ActionBarActivity {
	
	 Activity context;
	   AnimatorSet set;
	   ImageView imgView;
	   int imgResources[]={R.drawable.baby, R.drawable.bill, R.drawable.talker1, R.drawable.otabil};
	   int index=0;
	   SharedPreferences prefs;
	   
	   protected void onCreate(Bundle savedInstanceState) {
	   //TODO Auto-generated method stub
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.animation_tour);
	   context=this;
	   
	   prefs = getSharedPreferences("Chat", 0);
		
		
		String username = prefs.getString("username", null);

	   
	   getSupportActionBar().setTitle("Welcome"+ username+"!");
	 
	  
	   }
	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	       getMenuInflater().inflate(R.menu.actionbar2, menu);
	           return true;
	   }
	   
	   
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        int itemId = item.getItemId();
			if (itemId == R.id.action_skip) {
				finish();
				Intent ng = new Intent(getApplicationContext(), TimeLine.class);
				startActivity(ng);
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
	    }
	   
	   public void onStart(){
	   super.onStart();  
	   imgView=(ImageView)findViewById(R.id.image_animator);
	   
	   set = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.slideshow);
	   
	     set.setTarget(imgView);    
	     
	       set.addListener(new AnimatorListenerAdapter(){         
	     public void onAnimationEnd(Animator animator){
	     //repeat animation
	       
	        if(index<imgResources.length)
	        {
	        imgView.setImageResource(imgResources[index]);
	        index++;      
	     set.start();
	        }
	     }
	    
	    
	      });
	   set.start();
	  
	  
   		
	   }
	  
	} 


