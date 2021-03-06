package com.integra.dealcaller;
// w  ww .  j  av  a  2 s. c  o  m
import java.io.File;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.integra.dealcaller.DefaultRetryPolicy;
import com.integra.dealcaller.ExecutorDelivery;
import com.integra.dealcaller.FilePart;
import com.integra.dealcaller.NetworkResponse;
import com.integra.dealcaller.Request;
import com.integra.dealcaller.Response;
import com.integra.dealcaller.StringPart;
import com.integra.dealcaller.Response.ErrorListener;
import com.integra.dealcaller.Response.Listener;
import com.integra.dealcaller.Response.LoadingListener;
import com.integra.dealcaller.UploadMultipartEntity.ProgressListener;

/**
 * A request for making a Multi Part request
 * 
 * @param <T>
 *            Response expected
 */
public abstract class MultiPartRequest<T> extends Request<T> {

    private static final String         PROTOCOL_CHARSET = "utf-8";

    /**
     * Listener object for delivering the response
     */
    private Listener<T>                 mListener;

    private UploadMultipartEntity mMultipartEntity;
    /**
     * Default connection timeout for Multipart requests
     */
    public static final int TIMEOUT_MS = 60000;

    public MultiPartRequest(int method, String url, Listener<T> listener, ErrorListener errorlistener, LoadingListener loadingListener) {

        super(method, url, errorlistener);
        mListener = listener;
        
        mMultipartEntity = new UploadMultipartEntity();
        
        final ExecutorDelivery delivery = new ExecutorDelivery(new Handler(Looper.getMainLooper()));
        setLoadingListener(loadingListener);
        if (loadingListener != null) {
            mMultipartEntity.setListener(new ProgressListener() {
                long time = SystemClock.uptimeMillis();
                long count = -1;
                
                @Override
                public void transferred(long num) {
                    if (count == -1) {
                        count = mMultipartEntity.getContentLength();
                    }
                    // LogUtils.d("bacy", "upload->" + count + ",num->" + num);
                    long thisTime = SystemClock.uptimeMillis();
                    if (thisTime - time >= getRate() || count == num) {
                        time = thisTime;
                        delivery.postLoading(MultiPartRequest.this, count, num);
                    }
                }
            });
        }
        setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public String getBodyContentType() {
        return mMultipartEntity.getContentType().getValue();
    }

    @Override
    abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

    @Override
    protected void deliverResponse(T response) {

        mListener.onResponse(response);
    }

    /**
     * Get the protocol charset
     */
    public String getProtocolCharset() {
        
        return PROTOCOL_CHARSET;
    }
    
    public void addPart(String key, String value) {
        StringPart part = new StringPart(key, value, PROTOCOL_CHARSET);
        mMultipartEntity.addPart(part);
    }
    
    public void addPart(String key, File file) {
        FilePart part = new FilePart(key, file, null, null);
        mMultipartEntity.addPart(part);
    }

    public UploadMultipartEntity getMultipartEntity() {
        return mMultipartEntity;
    }
    
}