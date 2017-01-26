package com.android.valley.utils;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.LruCache;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by trinadhkoya on 26/01/17.
 */

public class CacheManager<T> extends LruCache<String, T> implements CacheManagerInterface<T> {


    HashMap<String, Long> cacheHitTimestamp = new HashMap<>();  //timestamp to check expiry so unused items can be removed
    private final int timeout = 5 * 1000 * 60; // if cache is not used for 5 minutes, will expire and evicted

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public CacheManager(int maxSize) {
        super(maxSize);
    }

    @Override
    public void addDataToCache(String key, T data) {
        if (getDataFromCache(key) == null) {
            synchronized (this) {
                put(key, data);
                cacheHitTimestamp.put(key, SystemClock.uptimeMillis());
            }
        }


    }

    @Override
    public void clearDataFromCache(String key) {
        if (getDataFromCache(key) != null) {
            remove(key);
        }

    }

    @Override
    public T getDataFromCache(String key) {
        synchronized (this) {
            cacheHitTimestamp.put(key, SystemClock.uptimeMillis());
            dropUnusedDataFromCache();
        }
        return get(key);

    }

    @Override
    public void dropUnusedDataFromCache() {
        Map<String, T> items = snapshot();
        for (String key : items.keySet()) {
            long cacheTime = cacheHitTimestamp.get(key);
            if (cacheTime + timeout < SystemClock.uptimeMillis()) {
                remove(key);
            }
        }

    }

    @Override
    protected int sizeOf(String key, T returnedData) {
        int size;
        if (returnedData instanceof JSONArray) {
            size = ((JSONArray) returnedData).toString().length();
        } else if (returnedData instanceof JSONObject) {
            size = ((JSONObject) returnedData).toString().length();
        } else {
            size = ((Bitmap) returnedData).getByteCount();
        }

        return size / 1024;
    }
}
