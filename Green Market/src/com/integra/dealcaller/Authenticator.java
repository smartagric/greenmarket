package com.integra.dealcaller;

import com.integra.dealcaller.AuthFailureError;

/**
 * An interface for interacting with auth tokens.
 */
public interface Authenticator {
    /**
     * Synchronously retrieves an auth token.
     *
     * @throws AuthFailureError If authentication did not succeed
     * @throws com.android.volley.AuthFailureError 
     * @throws com.android.volley.AuthFailureError 
     */
    public String getAuthToken() throws com.integra.dealcaller.AuthFailureError;

    /**
     * Invalidates the provided auth token.
     */
    public void invalidateAuthToken(String authToken);
}