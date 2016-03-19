package com.integra.dealcaller;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
 
public class LruBitmapCache2 extends LruCache<String, Bitmap> implements
        ImageCache {
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
 
        return cacheSize;
    }
 
    public LruBitmapCache2() {
        this(getDefaultLruCacheSize());
    }
 
    public LruBitmapCache2(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }
 
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }
 
    public Bitmap getBitmap(String url) {
        return get(url);
    }
 
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
