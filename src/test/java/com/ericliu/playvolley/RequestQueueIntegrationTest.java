package com.ericliu.playvolley;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.NoCache;
import com.ericliu.playvolley.RequestQueue.RequestFinishedListener;
import com.ericliu.playvolley.mock.MockRequest;
import com.ericliu.playvolley.utils.ImmediateResponseDelivery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowSystemClock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by ericliu on 13/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowSystemClock.class})
public class RequestQueueIntegrationTest {

    private ResponseDelivery mDelivery;

    @Mock
    private Network mMockNetwork;

    @Mock
    private RequestFinishedListener<byte[]> mMockListener;
    @Mock
    private RequestFinishedListener<byte[]> mMockListener2;

    @Before
    public void setUp() throws Exception {
        mDelivery = new ImmediateResponseDelivery();
        initMocks(this);
    }

    @Test
    public void add_requestProcessedInCorrectOrder() throws Exception {

        MockRequest lowPriorityRequest = new MockRequest();
        MockRequest highPriorityRequest = new MockRequest();
        lowPriorityRequest.setCacheKey("1");
        highPriorityRequest.setCacheKey("2");

        lowPriorityRequest.setPriority(Request.Priority.LOW);
        highPriorityRequest.setPriority(Request.Priority.HIGH);

        Answer<NetworkResponse> delayAnswer = new Answer<NetworkResponse>() {
            @Override
            public NetworkResponse answer(final InvocationOnMock invocation) throws Throwable {
                Thread.sleep(20);
                return mock(NetworkResponse.class);
            }
        };
        when(mMockNetwork.performRequest(highPriorityRequest)).thenAnswer(delayAnswer);
        when(mMockNetwork.performRequest(lowPriorityRequest)).thenReturn(mock(NetworkResponse.class));

        final RequestQueue requestQueue = new RequestQueue(new NoCache(),
                mMockNetwork,
                1,
                mDelivery);

        requestQueue.addRequestFinishedListener(mMockListener);
        requestQueue.add(lowPriorityRequest);
        requestQueue.add(highPriorityRequest);

        requestQueue.start();

        InOrder inOrder = inOrder(mMockListener);
        inOrder.verify(mMockListener, timeout(10000)).onRequestFinished(highPriorityRequest);
        inOrder.verify(mMockListener, timeout(10000)).onRequestFinished(lowPriorityRequest);
        requestQueue.stop();
    }
}
