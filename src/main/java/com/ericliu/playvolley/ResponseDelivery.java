package com.ericliu.playvolley;

import com.ericliu.playvolley.Request;
import com.ericliu.playvolley.Response;
import com.ericliu.playvolley.VolleyError;

/**
 * Created by ericliu on 16/10/17.
 */

public interface ResponseDelivery {

    /**
     * Parses a response from network or cache, and delivers it.
     */
    void postResponse(Request<?> request, Response<?> response);

    /**
     * Parses a response from network or cache, and delivers it.
     * The provided runnable will be executed after delivery.
     */
    void postResponse(Request<?> request, Response<?> response, Runnable runnable);

    /**
     * Posts an error for the given request.
     */
    void postError(Request<?> request, VolleyError error);
}
