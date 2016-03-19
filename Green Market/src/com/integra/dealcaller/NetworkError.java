package com.integra.dealcaller;

import com.integra.dealcaller.NetworkResponse;
import com.integra.dealcaller.VolleyError;

/**
 * Indicates that there was a network error when performing a Volley request.
 */
@SuppressWarnings("serial")
public class NetworkError extends VolleyError {
    public NetworkError() {
        super();
    }

    public NetworkError(Throwable cause) {
        super(cause);
    }

    public NetworkError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}