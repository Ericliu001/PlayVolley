package com.ericliu.playvolley.toolbox;

import com.ericliu.playvolley.Request;
import com.ericliu.playvolley.Response;
import com.ericliu.playvolley.ResponseDelivery;
import com.ericliu.playvolley.VolleyError;

import java.util.concurrent.Executor;

/**
 * Created by ericliu on 16/10/17.
 */

public class ExecutorDelivery implements ResponseDelivery {

    public ExecutorDelivery(Executor executor) {
    }

    @Override
    public void postResponse(final Request<?> request, final Response<?> response) {

    }

    @Override
    public void postResponse(final Request<?> request, final Response<?> response, final Runnable runnable) {

    }

    @Override
    public void postError(final Request<?> request, final VolleyError error) {

    }
}
