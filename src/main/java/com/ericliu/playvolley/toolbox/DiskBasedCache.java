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
        int n = 0x0;
        n |= ((inputStream.read() & 0xFF) << 0);
        n |= ((inputStream.read() & 0xFF) << 8);
        n |= ((inputStream.read() & 0xFF) << 16);
        n |= ((inputStream.read() & 0xFF) << 24);
        return n;
    }

    public static void writeLong(final ByteArrayOutputStream outputStream, final long n) {
        outputStream.write((byte) (n >>> 0));
        outputStream.write((byte) (n >>> 8));
        outputStream.write((byte) (n >>> 16));
        outputStream.write((byte) (n >>> 24));
        outputStream.write((byte) (n >>> 32));
        outputStream.write((byte) (n >>> 40));
        outputStream.write((byte) (n >>> 48));
        outputStream.write((byte) (n >>> 56));
    }

    public static long readLong(final ByteArrayInputStream inputStream) {
        long n = 0L;
        n |= ((inputStream.read() & 0xFFL) << 0);
        n |= ((inputStream.read() & 0xFFL) << 8);
        n |= ((inputStream.read() & 0xFFL) << 16);
        n |= ((inputStream.read() & 0xFFL) << 24);
        n |= ((inputStream.read() & 0xFFL) << 32);
        n |= ((inputStream.read() & 0xFFL) << 40);
        n |= ((inputStream.read() & 0xFFL) << 48);
        n |= ((inputStream.read() & 0xFFL) << 56);
        return n;
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
