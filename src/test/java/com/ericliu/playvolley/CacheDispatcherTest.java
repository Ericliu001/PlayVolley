package com.ericliu.playvolley;

import com.ericliu.playvolley.mock.MockCache;
import com.ericliu.playvolley.mock.WaitableQueue;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by ericliu on 19/10/17.
 */

@RunWith(RobolectricTestRunner.class)
public class CacheDispatcherTest {
    private CacheDispatcher mDispatcher;
    private WaitableQueue mCacheQueue;
    private WaitableQueue mNetworkQueue;
    private MockCache mCache;
//    private MockResponseDelivery mDelivery;
//    private MockRequest mRequest;
}
