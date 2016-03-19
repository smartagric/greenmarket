package com.integra.dealcaller;

import com.integra.dealcaller.NetworkResponse;
import com.integra.dealcaller.Request;
import com.integra.dealcaller.Response;
import com.integra.dealcaller.Response.ErrorListener;
import com.integra.dealcaller.Response.Listener;
import com.integra.dealcaller.Response.LoadingListener;

import java.io.UnsupportedEncodingException;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 */
public class StringRequest extends Request<String> {
    private final Listener<String> mListener;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(int method, String url, Listener<String> listener,
            ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }
    
    public StringRequest(int method, String url, Listener<String> listener,
            ErrorListener errorListener, LoadingListener loadingListener) {
        super(method, url, errorListener, loadingListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(String url, Listener<String> listener, ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        } catch (NullPointerException e) {
            parsed = "";
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}