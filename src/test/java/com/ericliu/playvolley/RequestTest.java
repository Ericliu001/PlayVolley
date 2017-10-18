package com.ericliu.playvolley;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertTrue;

/**
 * Created by ericliu on 18/10/17.
 */

@RunWith(RobolectricTestRunner.class)
public class RequestTest {

    @Test
    public void compareTo() throws Exception {
        int sequence = 0;

        TestRequest low = new TestRequest(Request.Priority.LOW);
        low.setSequence(sequence++);

        TestRequest low2 = new TestRequest(Request.Priority.LOW);
        low2.setSequence(sequence++);

        TestRequest high = new TestRequest(Request.Priority.HIGH);
        high.setSequence(sequence++);

        TestRequest immediate = new TestRequest(Request.Priority.IMMEDIATE);
        immediate.setSequence(sequence++);

        // high priority requests are lesser so that they will be sorted to the front;
        assertTrue(low.compareTo(high) > 0);
        assertTrue(high.compareTo(low) < 0);

        assertTrue(low.compareTo(low2) < 0);
        assertTrue(low2.compareTo(low) > 0);

        assertTrue(low.compareTo(immediate) > 0);
        assertTrue(immediate.compareTo(low) < 0);
    }

    private static class TestRequest extends Request<Object> {
        private final Priority priority;

        public TestRequest(Priority priority) {
            this.priority = priority;
        }

        @Override
        public Priority getPriority() {
            return priority;
        }
    }
}
