package com.integra.dealcaller;
//from  www.j  a va 2s.  c o  m
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.protocol.HTTP;

public class UrlEncodingHelper {

    public static String encode(final String content, final String encoding) {
        try {
            return URLEncoder.encode(
                content, 
                encoding != null ? encoding : HTTP.DEFAULT_CONTENT_CHARSET
            );
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }
    
}