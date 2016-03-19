package com.integra.dealcaller;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class WebInterface {

	Context mComtext;
	public static String price = "0";
	WebInterface(Context c){
		this.mComtext = c;
	}
	
	
	@JavascriptInterface
	public String getPrice(){
		return WebInterface.price;
	}

}
