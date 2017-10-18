package com.ericliu.playvolley;

/**
 * Created by ericliu on 16/10/17.
 */

public abstract class Request<T> implements Comparable<Request<T>> {
    private int mSequence;

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
}
