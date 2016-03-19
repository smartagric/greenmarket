package com.integra.dealcaller;



import java.util.ArrayList;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.integra.dealcaller.R;


public class SplashFinal extends ActionBarActivity {
	private static final String TAG = SplashFinal.class.getSimpleName();
	private static final String TAG_FEED = "feed", TAG_ENTRY = "entry",
			TAG_GPHOTO_ID = "gphoto$id", TAG_T = "$t",
			TAG_ALBUM_TITLE = "title";
	private ProgressBar pDialog;
	private Handler mHandler = new Handler();
	
	SharedPreferences prefs;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		
		prefs = getSharedPreferences("Chat", 0);
		pDialog = (ProgressBar)findViewById(R.id.progressBarSplash);
 
		pDialog.setProgress(0);
		
		
		

		mHandler.postDelayed(new Runnable() {
            public void run() {
            	
                doStuff();
            }
        }, 2000);
		
		
		
		
	    }

	

	private void doStuff() {
		
		
		String status = prefs.getString("loginstatus", null);

		if(status=="loggedin"){
			
			Intent iv = new Intent(this, TimeLine.class);
			startActivity(iv);
			finish();
		}else{
		Intent iv = new Intent(this, LoginActivity.class);
		startActivity(iv);
		finish();
		
		}
    }

	}
