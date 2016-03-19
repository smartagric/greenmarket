package com.integra.dealcaller;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class CustomRequest2 extends Request<JSONObject>{

      private Listener<JSONObject> listener;
      private String params;

      public CustomRequest2(String url, String params,
                Listener<JSONObject> reponseListener, ErrorListener errorListener) {
            super(Method.GET, url, errorListener);
            this.listener = reponseListener;
            this.params = params;
      }

      public CustomRequest2(int method, String url, String params,
                Listener<JSONObject> reponseListener, ErrorListener errorListener) {
            super(method, url, errorListener);
            this.listener = reponseListener;
            this.params = params;
        }

   

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
         try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                return Response.success(new JSONObject(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
    }

}