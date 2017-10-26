package com.ericliu.playvolley.toolbox;

import com.ericliu.playvolley.Cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by ericliu on 19/10/17.
 */

public class DiskBasedCache implements Cache {
    public DiskBasedCache(final File root, final int maxSize) {

    }

    public static void writeString(final ByteArrayOutputStream baos, final String s) {
        throw new UnsupportedOperationException();
    }

    static void writeInt(final ByteArrayOutputStream outputStream, final int n) {
        outputStream.write((n >> 0) & 0xFF);
        outputStream.write((n >> 8) & 0xFF);
        outputStream.write((n >> 16) & 0xFF);
        outputStream.write((n >> 24) & 0xFF);
    }

    static int readInt(final ByteArrayInputStream inputStream) {
        throw new UnsupportedOperationException();
    }

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
