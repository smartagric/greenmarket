package com.integra.dealcaller;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;



public class WebPaymentLocal extends ActionBarActivity {

	 WebView WebViewWithCSS;
	 @SuppressLint("JavascriptInterface")
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.web_payment_local);

	 WebViewWithCSS = (WebView)findViewById(R.id.webView1);
	 WebViewWithCSS.addJavascriptInterface(new WebInterface(this), "Android");
	 
	 WebViewWithCSS.getSettings().setLoadWithOverviewMode(true);
	 WebViewWithCSS.getSettings().setUseWideViewPort(true);
	 
	 WebSettings webSetting = WebViewWithCSS.getSettings();
	 webSetting.setJavaScriptEnabled(true);
	 
	 WebViewWithCSS.setWebViewClient(new WebViewClient());
	 WebViewWithCSS.loadUrl("file:///android_asset/vodafone.html"); 
	//WebViewWithCSS.loadUrl("http://activetalkgh.com/android_connect/uplay_scorers.php");
	 }

	 private class WebViewClient extends android.webkit.WebViewClient
	 {
	 @Override
	 public boolean shouldOverrideUrlLoading(WebView view, String url) 
	 {
	 return super.shouldOverrideUrlLoading(view, url);
	 }
 }
	 
}