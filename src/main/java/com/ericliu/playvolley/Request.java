package com.ericliu.playvolley;

/**
 * Created by ericliu on 16/10/17.
 */

public abstract class Request<T> implements Comparable<Request<T>> {
    public Object getTag() {
        return null;
    }

    public void cancel() {

    }

    public void setRequestQueue(final RequestQueue requestQueue) {

    }

    public void setSequence(final int sequenceNumber) {

    }

    public void addMarker(final String s) {

    }
}
