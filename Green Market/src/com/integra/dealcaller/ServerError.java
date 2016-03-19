package com.integra.dealcaller;

import com.integra.dealcaller.NetworkResponse;
import com.integra.dealcaller.VolleyError;

/**
 * Indicates that the error responded with an error response.
 */
@SuppressWarnings("serial")
public class ServerError extends VolleyError {
    public ServerError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public ServerError() {
        super();
    }
}