package com.ericliu.playvolley.mock;

import com.ericliu.playvolley.Cache;

/**
 * Created by ericliu on 19/10/17.
 */

public class MockCache implements Cache {
    @Override
    public Entry get(final String key) {
        return null;
    }

    @Override
    public void put(final String key, final Entry entry) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void invalidate(final String key, final boolean fullExpire) {

    }

    @Override
    public void remove(final String key) {

    }

    @Override
    public void clear() {

    }
}
