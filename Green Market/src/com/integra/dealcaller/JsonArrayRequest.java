package com.integra.dealcaller;

import com.integra.dealcaller.NetworkResponse;
import com.integra.dealcaller.ParseError;
import com.integra.dealcaller.Response;
import com.integra.dealcaller.Response.ErrorListener;
import com.integra.dealcaller.Response.Listener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * A request for retrieving a {@link JSONArray} response body at a given URL.
 */
public class JsonArrayRequest extends JsonRequest<JSONArray> {

    /**
     * Creates a new request.
     * @param url URL to fetch the JSON from
     * @param listener Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public JsonArrayRequest(String url, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}