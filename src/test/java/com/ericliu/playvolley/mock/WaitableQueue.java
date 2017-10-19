package com.ericliu.playvolley.mock;

import com.ericliu.playvolley.Request;
import com.ericliu.playvolley.Response;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by ericliu on 19/10/17.
 */

public class WaitableQueue extends PriorityBlockingQueue<Request<?>> {
    private final Request<?> mStopRequest = new MagicStopRequest();
    private final Semaphore mStopEvent = new Semaphore(0);

    public void waitUntilEmpty(long timeoutMillis) throws InterruptedException, TimeoutException {
        add(mStopRequest);
        if (!mStopEvent.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS)) {
            throw new TimeoutException();
        }
    }

    @Override
    public Request<?> take() throws InterruptedException {
        final Request<?> item = super.take();
        if (item == mStopRequest) {
            mStopEvent.release();
            return take();
        }
        return item;
    }

    private final static class MagicStopRequest extends Request<Object> {
        public MagicStopRequest() {
            super(Method.GET, "", null);
        }

        @Override
        public Priority getPriority() {
            return Priority.LOW;
        }
    }
}
