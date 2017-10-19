package com.ericliu.playvolley;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by ericliu on 16/10/17.
 */

public abstract class Request<T> implements Comparable<Request<T>> {

    private final int method;
    private final String url;
    private final Response.ErrorListener errorListener;

    /** Default tag for {@link android.net.TrafficStats} */
    private int mDefaultTrafficStatsTag;
    /** Sequence number of this request, used to enforce FIFO ordering */
    private int mSequence;

    public Request(final int method, final String url, final Response.ErrorListener errorListener) {
        this.method = method;
        this.url = url;
        this.errorListener = errorListener;

        mDefaultTrafficStatsTag = findDefaultTrafficStatsTag(url);
    }

    private static int findDefaultTrafficStatsTag(final String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            if (uri != null) {
                String host = uri.getHost();
                if (host != null) {
                    return host.hashCode();
                }
            }
        }
        return 0;
    }

    public int getTrafficStatsTag() {
        return mDefaultTrafficStatsTag;
    }

    public Object getTag() {
        return null;
    }

    public void cancel() {

    }

    public void setRequestQueue(final RequestQueue requestQueue) {

    }

    public void setSequence(final int sequence) {
        mSequence = sequence;
    }

    public void addMarker(final String s) {

    }

    @Override
    public int compareTo(final Request<T> o) {
        final Priority currentPriority = this.getPriority();
        final Priority comparedPriority = o.getPriority();
        return currentPriority == comparedPriority ?
                mSequence - o.mSequence :
                comparedPriority.ordinal() - currentPriority.ordinal();
    }

    /**
     * Return the {@link Priority} of this request; {@link Priority#NORMAL} by default.
     *
     * @return
     */
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    /**
     * Priority values. Requests will be processed from
     * higher priorities to lower priorities, in FIFO order.
     */
    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    /**
     * Supported request methods.
     */
    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }
}
