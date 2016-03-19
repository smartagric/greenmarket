package com.integra.dealcaller;

import java.io.File;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.integra.dealcaller.Network;
import com.integra.dealcaller.RequestQueue;

@TargetApi(Build.VERSION_CODES.FROYO)
public class Volley {

    /** Default on-disk cache directory. */
    private static final String DEFAULT_CACHE_DIR = "volley";

    /**
     * Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
     *
     * @param context A {@link Context} to use for creating the cache dir.
     * @param stack An {@link HttpStack} to use for the network, or null for default.
     * @return A started {@link RequestQueue} instance.
     */
    public static RequestQueue newRequestQueue(Context context, HttpStack stack) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = null;
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack(userAgent);
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskLruBasedCache(cacheDir), network);
        queue.start();

        return queue;
    }
    
    /**
     * chenbo add ????Http????????????????????????
     * ??????????
     * newNoCacheRequestQueue
     * @param context
     * @param stack
     * @return
     * @since 3.5
     */
    public static RequestQueue newNoCacheRequestQueue(Context context) {
        String userAgent = null;
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }

        HttpStack stack = null;
        if (Build.VERSION.SDK_INT >= 9) {
            stack = new HurlStack(userAgent);
        } else {
            // Prior to Gingerbread, HttpUrlConnection was unreliable.
            // See:
            // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
            stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new NoCache(), network);
        queue.start();

        return queue;
    }

    /**
     * Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
     *
     * @param context A {@link Context} to use for creating the cache dir.
     * @return A started {@link RequestQueue} instance.
     */
    public static RequestQueue newRequestQueue(Context context) {
        return newRequestQueue(context, null);
    }
    
    public static File getDefaultCacheFile(Context context) {
        return new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
    }
}