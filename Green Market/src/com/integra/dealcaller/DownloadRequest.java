package com.integra.dealcaller;
//  w  ww. java 2 s. c  om
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.integra.dealcaller.AuthFailureError;
import com.integra.dealcaller.DefaultRetryPolicy;
import com.integra.dealcaller.Response.ErrorListener;
import com.integra.dealcaller.Response.Listener;
import com.integra.dealcaller.Response.LoadingListener;

public class DownloadRequest extends StringRequest {

    public DownloadRequest(String url, Listener<String> listener, ErrorListener errorListener,
            LoadingListener loadingListener) {
        super(Method.GET, url, listener, errorListener, loadingListener);
        // ??????????????????????????retry????
        setRetryPolicy(
                new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private String target;
    private boolean isResume;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isResume() {
        return isResume;
    }

    public void setResume(boolean isResume) {
        this.isResume = isResume;
    }
    
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        File file = new File(target);
        final long fileLen = file.length();
        if (isResume && fileLen > 0) {
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Range", "bytes="+fileLen+"-");
            return headers;
        }
        return super.getHeaders();
    }
    
    
    /**
     * ???????????????????????????????????????????
     * stopDownload
     * @since 3.5
     */
    public void stopDownload() {
        cancel();
    }
}