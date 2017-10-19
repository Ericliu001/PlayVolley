package com.ericliu.playvolley.toolbox;

import com.ericliu.playvolley.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by ericliu on 19/10/17.
 */
@RunWith(RobolectricTestRunner.class)
public class CacheTest {

    @Test
    public void publicMethods() throws Exception {
        assertNotNull(Cache.class.getMethod("get", String.class));
        assertNotNull(Cache.class.getMethod("put", String.class, Cache.Entry.class));
        assertNotNull(Cache.class.getMethod("initialize"));
        assertNotNull(Cache.class.getMethod("invalidate", String.class, boolean.class));
        assertNotNull(Cache.class.getMethod("remove", String.class));
        assertNotNull(Cache.class.getMethod("clear"));
    }
}
