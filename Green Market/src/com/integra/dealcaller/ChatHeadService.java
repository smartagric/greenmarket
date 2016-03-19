package com.integra.dealcaller;


import static com.integra.dealcaller.CommonUtilities.EXTRA_MESSAGE;
import static com.integra.dealcaller.CommonUtilities.SENDER_ID;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
 
import com.google.android.gcm.GCMRegistrar;
import com.integra.dealcaller.R;
 
public class ChatHeadService extends Service {

	  private WindowManager windowManager;
	  private LayoutInflater inflater;
	  private View chatHead;
	  private LinearLayout lout;
	

	  @Override public IBinder onBind(Intent intent) {
	    // Not used
	    return null;
	  }

	  @Override public void onCreate() {
	    super.onCreate();

	    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
	    inflater = LayoutInflater.from(this);
	   
	  }
	  
	  
	    
	    
	    @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {

	        chatHead = inflater.inflate(R.layout.service_chat_head, null);

	        
	        TextView txt_text = (TextView) chatHead.findViewById(R.id.txt_text);

	       
	        txt_text.setText(intent.getStringExtra("text"));

	        chatHead.findViewById(R.id.btn_dismiss).setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                windowManager.removeView(chatHead);
	            }
	        });  
	    
	   
	    final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
	        WindowManager.LayoutParams.WRAP_CONTENT,
	        WindowManager.LayoutParams.WRAP_CONTENT,
	        WindowManager.LayoutParams.TYPE_PHONE,
	        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
	        PixelFormat.TRANSLUCENT);

	    params.gravity = Gravity.TOP | Gravity.LEFT;
	    params.x = 0;
	    params.y = 100;

	    windowManager.addView(chatHead, params);
	    
	    
	    chatHead.setOnTouchListener(new View.OnTouchListener() {
	    	  private int initialX;
	    	  private int initialY;
	    	  private float initialTouchX;
	    	  private float initialTouchY;

	    	  @Override public boolean onTouch(View v, MotionEvent event) {
	    	    switch (event.getAction()) {
	    	      case MotionEvent.ACTION_DOWN:
	    	        initialX = params.x;
	    	        initialY = params.y;
	    	        initialTouchX = event.getRawX();
	    	        initialTouchY = event.getRawY();
	    	        return true;
	    	      case MotionEvent.ACTION_UP:
	    	        return true;
	    	      case MotionEvent.ACTION_MOVE:
	    	        params.x = initialX + (int) (event.getRawX() - initialTouchX);
	    	        params.y = initialY + (int) (event.getRawY() - initialTouchY);
	    	        windowManager.updateViewLayout(chatHead, params);
	    	        return true;
	    	    }
	    	    return false;
	    	  }
	    	});
	    return super.onStartCommand(intent, flags, startId);

	  }

	  @Override
	  public void onDestroy() {
	    super.onDestroy();
	    if (chatHead != null) windowManager.removeView(chatHead);
	  }
	}