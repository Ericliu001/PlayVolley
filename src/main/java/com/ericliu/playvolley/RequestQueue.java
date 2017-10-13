package com.ericliu.playvolley;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.ResponseDelivery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by ericliu on 11/10/17.
 */

public class RequestQueue {

    /**
     * Call back interface for completed requests.
     *
     * @param <T>
     */
    public interface RequestFinishedListener<T> {

        /**
         * Called when a request finished processing.
         *
         * @param request
         */
        void onRequestFinished(Request<T> request);
    }
    /**
     * The set of all requests currently being processed by this RequestQueue.
     * A Request will be in this set if it is waiting in any queue or currently being processed
     * by any dispatcher.
     */
    private final Set<Request<?>> mCurrentRequests = new HashSet<>();

    /**
     * The cache triage queue.
     */
    private final PriorityBlockingQueue<Request<?>> mCacheQueue = new PriorityBlockingQueue<>();

    private final List<RequestFinishedListener<?>> mFinishedListeners = new ArrayList<>();

    public RequestQueue(final Cache cache,
                        final Network network,
                        final int threadPoolSize,
                        final ResponseDelivery delivery) {

    }

    public void start() {

    }

    public void stop() {

    }

    public void cancelAll(final Object tag) {
        cancelAll(new RequestFilter() {
            @Override
            public boolean apply(final Request<?> request) {
                return request.getTag() == tag;
            }
        });
    }

    public void cancelAll(final RequestFilter filter) {
        for (final Request<?> request : mCurrentRequests) {
            if (filter.apply(request)) {
                request.cancel();
            }
        }
    }

    public <T> void add(final Request<T> request) {
        mCurrentRequests.add(request);
    }

    public <T> void addRequestFinishedListener(final RequestFinishedListener<T> mMockListener) {

    }
}
