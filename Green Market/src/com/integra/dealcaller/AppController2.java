package com.integra.dealcaller;


import android.app.Application;
import android.graphics.Bitmap;

import android.text.TextUtils;
 
import com.integra.dealcaller.ImageLoader;
import com.integra.dealcaller.Request;
import com.integra.dealcaller.RequestQueue;
import com.integra.dealcaller.Volley;
import com.integra.dealcaller.ImageLoader.ImageCache;
 
public class AppController2 extends Application {
 
    public static final String TAG = AppController2.class.getSimpleName();
 
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private ImageCache mLruBitmapCache;
 
    private static AppController2 mInstance;
 
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
 
    public static synchronized AppController2 getInstance() {
        return mInstance;
    }
 
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
 
        return mRequestQueue;
    }
 
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(mRequestQueue, mLruBitmapCache);
        }
 
        return this.mImageLoader;
    }
 
    public ImageCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new ImageCache() {
				
				@Override
				public void putBitmap(String url, Bitmap bitmap) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public Bitmap getBitmap(String url) {
					// TODO Auto-generated method stub
					return null;
				}
			};
        return this.mLruBitmapCache;
    }
 
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
