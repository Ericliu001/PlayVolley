package com.ericliu.playvolley.toolbox;

import com.ericliu.playvolley.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by ericliu on 19/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", sdk = 16)
public class DiskBasedCacheTest {

    private static final int MAX_SIZE = 1 << 20; // 1024 * 1024
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private DiskBasedCache cache;

    @Before
    public void setUp() throws Exception {
        // Intialize empty cache
        cache = new DiskBasedCache(temporaryFolder.getRoot(), MAX_SIZE);
        cache.initialize();
    }

    @After
    public void tearDown() throws Exception {
        cache = null;
    }

    @Test
    public void testEmptyInitialize() throws Exception {
        assertThat(cache.get("key"), is(nullValue()));
    }

    @Test
    public void testPutGetZeroBytes() throws Exception {
        Cache.Entry entry = new Cache.Entry();
        entry.data = new byte[0];
        entry.serverDate = 1234567L;
        entry.lastModified = 13572468L;
        entry.ttl = 9876543L;
        entry.etag = "etag";
        entry.responseHeaders = new HashMap<>();
        entry.responseHeaders.put("fruit", "banana");
        entry.responseHeaders.put("color", "yellow");
        cache.put("my-magical-key", entry);

        assertThatEntriesAreEqual(cache.get("my-magical-key"), entry);
        assertThat(cache.get("unknown-key"), is(nullValue()));
    }

    private void assertThatEntriesAreEqual(final Cache.Entry actual, final Cache.Entry expected) {
        assertEquals(actual.data, expected.data);
        assertEquals(actual.etag, expected.etag);
        assertEquals(actual.lastModified, expected.lastModified);
        assertEquals(actual.responseHeaders, expected.responseHeaders);
        assertEquals(actual.serverDate, expected.serverDate);
        assertEquals(actual.softTtl, expected.softTtl);
        assertEquals(actual.ttl, expected.ttl);
    }

    @Test
    public void serializeInt() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DiskBasedCache.writeInt(baos, 0);
        DiskBasedCache.writeInt(baos, 19791214);
        DiskBasedCache.writeInt(baos, -20050711);
        DiskBasedCache.writeInt(baos, Integer.MIN_VALUE);
        DiskBasedCache.writeInt(baos, Integer.MAX_VALUE);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        assertEquals(DiskBasedCache.readInt(bais), 0);
        assertEquals(DiskBasedCache.readInt(bais), 19791214);
        assertEquals(DiskBasedCache.readInt(bais), -20050711);
        assertEquals(DiskBasedCache.readInt(bais), Integer.MIN_VALUE);
        assertEquals(DiskBasedCache.readInt(bais), Integer.MAX_VALUE);
    }
}
