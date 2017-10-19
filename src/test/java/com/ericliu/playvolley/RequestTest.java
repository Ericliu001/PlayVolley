package com.ericliu.playvolley;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
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

    @Test
    public void urlParsing() throws Exception {
        UrlParseRequest nullUrl = new UrlParseRequest(null);
        assertEquals(0, nullUrl.getTrafficStatsTag());

        UrlParseRequest emptyUrl = new UrlParseRequest("");
        assertEquals(0, emptyUrl.getTrafficStatsTag());

        UrlParseRequest noHost = new UrlParseRequest("http:///");
        assertEquals(0, noHost.getTrafficStatsTag());

        UrlParseRequest badProtocol = new UrlParseRequest("bad:http://foo");
        assertEquals(0, badProtocol.getTrafficStatsTag());

        UrlParseRequest goodProtocal = new UrlParseRequest("http://foo");
        assertTrue(0 != goodProtocal.getTrafficStatsTag());
    }

    private static class TestRequest extends Request<Object> {
        private final Priority priority;

        public TestRequest(Priority priority) {
            super(Method.GET, "", null);
            this.priority = priority;
        }

        @Override
        public Priority getPriority() {
            return priority;
        }
    }

    private static class UrlParseRequest extends Request<Object> {
        public UrlParseRequest(String url) {
            super(Method.GET, url, null);
        }
    }
}
