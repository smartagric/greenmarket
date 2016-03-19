package com.integra.dealcaller;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;



public class PrefManager {
	private static final String TAG = PrefManager.class.getSimpleName();

	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "ActiveTalk";

	
	
	// username
	private static final String KEY_USERNAME = "fg";



	// Email
	private static final String KEY_EMAIL = "fg";
	
	
	// GCM Reg_id
		private static final String KEY_GCM = "REG_ID";
		

	//ProfilePic
		private static final String KEY_PROFILE_PIC = "http://activetalkgh.com/json/67.jpg";
	
		
	//ProfilePic
		private static final String KEY_COVER = "http://activetalkgh.com/json/3a.jpg";
		
	//ProfilePic
		private static final String KEY_MOBILE = "0574932005";
	

	public PrefManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

	}

	/**
	 * Storing google username
	 * */
	public void setUsername(String Username) {
		editor = pref.edit();

		editor.putString(KEY_USERNAME, Username);

		// commit changes
		editor.commit();
	}

	public String getUserName() {
		return KEY_USERNAME;
	}

	
	//Storing Mobile Number
	public void setMobileNo(String MobileNo) {
		editor = pref.edit();

		editor.putString(KEY_MOBILE, MobileNo);

		// commit changes
		editor.commit();
	}

	public String getMobileNo() {
		return KEY_MOBILE;
	}
	
	
	/**
	 * Storing profile pic
	 * */
	public void setProfile(String profilePic) {
		editor = pref.edit();

		editor.putString(KEY_PROFILE_PIC, profilePic);

		// commit changes
		editor.commit();
	}

	public String getprofile() {
		return KEY_PROFILE_PIC;
	}

	
	/**
	 * Storing profile pic
	 * */
	public void setCover(String cover) {
		editor = pref.edit();

		editor.putString(KEY_COVER, cover);

		// commit changes
		editor.commit();
	}

	public String getcover() {
		return KEY_COVER;
	}

	
	
	
	/**
	 * Storing google GCM reg_id
	 * */
	public void setReg_id(String reg_id) {
		editor = pref.edit();

		editor.putString(KEY_GCM, reg_id);

		// commit changes
		editor.commit();
	}

	public String getReg_id() {
		return KEY_GCM;
	}


	/**
	 * storing gallery name
	 * */
	public void setEmail(String email) {
		editor = pref.edit();

		editor.putString(KEY_EMAIL, email);

		// commit changes
		editor.commit();
	}

	public String getEmail() {
		return KEY_EMAIL;
	}

	
}