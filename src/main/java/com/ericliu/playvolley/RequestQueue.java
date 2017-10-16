package com.ericliu.playvolley;

import com.android.volley.*;
import com.android.volley.RequestQueue.RequestFilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ericliu on 11/10/17.
 */

public class RequestQueue {

    /**
     * Call back interface for completed requests.
     */
    public interface RequestFinishedListener {

        /**
         * Called when a request finished processing.
         *
         * @param request
         */
        void onRequestFinished(Request request);
    }

    /** Used for generating monotonically-increasing sequence numbers for requests. */
    private final AtomicInteger mSequenceGenerator = new AtomicInteger();

    /**
     * The set of all requests currently being processed by this RequestQueue.
     * A Request will be in this set if it is waiting in any queue or currently being processed
     * by any dispatcher.
     */
    private final Set<Request<?>> mCurrentRequests = new HashSet<>();

    /** The mCache triage queue. */
    private final PriorityBlockingQueue<Request<?>> mCacheQueue = new PriorityBlockingQueue<>();

    /** The queue of requests that are actually going out to the network. */
    private final PriorityBlockingQueue<Request<?>> mNetworkQueue = new PriorityBlockingQueue<>();

    private final Cache mCache;
    private final Network mNetwork;

    /** The mNetwork dispatchers */
    private final NetworkDispatcher[] mNetworkDispatchers;
    private final ResponseDelivery mDelivery;

    /**
     * The mCache dispatcher
     */
    private CacheDispatcher mCacheDispatcher;

    private final List<RequestFinishedListener> mFinishedListeners = new ArrayList<>();

    public RequestQueue(final Cache cache,
                        final Network network,
                        final int threadPoolSize,
                        final ResponseDelivery delivery) {
        this.mCache = cache;
        this.mNetwork = network;
        mNetworkDispatchers = new NetworkDispatcher[threadPoolSize];
        this.mDelivery = delivery;
    }

    public void start() {
        mCacheDispatcher = new CacheDispatcher(mCacheQueue, mNetworkQueue, mCache, mDelivery);
        mCacheDispatcher.start();

        for (int i = 0; i < mNetworkDispatchers.length; i++) {
            mNetworkDispatchers[i] = new NetworkDispatcher(mNetworkQueue, mNetwork, mCache, mDelivery);
            mNetworkDispatchers[i].start();
        }
    }

    public void stop() {
        if (mCacheDispatcher != null) {
            mCacheDispatcher.quit();
        }

        for (final NetworkDispatcher mNetworkDispatcher : mNetworkDispatchers) {
            if (mNetworkDispatcher != null) {
                mNetworkDispatcher.quit();
            }
        }
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

    public void add(final Request<?> request) {
        // Tag the request as belonging to this queue and add it to the set of current requests.
        mCurrentRequests.add(request);

        request.setSequence(getSequenceNumber());
        request.addMarker("add-to-queue");

        mCacheQueue.add(request);
    }

    private int getSequenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }

    public void addRequestFinishedListener(final RequestFinishedListener listener) {
        mFinishedListeners.add(listener);
    }

    private Cache getCache() {
        return mCache;
    }
}
