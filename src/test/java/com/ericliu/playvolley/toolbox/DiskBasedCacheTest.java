package com.ericliu.playvolley.toolbox;

import com.ericliu.playvolley.Cache;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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
    private Cache cache;

    @Before
    public void setUp() throws Exception {
        // Intialize empty cache
        cache = new DiskBasedCache(temporaryFolder.getRoot(), MAX_SIZE);
        cache.initialize();
    }
}
