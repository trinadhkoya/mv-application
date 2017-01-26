package com.android.valley;

/**
 * Created by trinadhkoya on 26/01/17.
 */

public interface CacheManagerInterface<T> {
    public void addDataToCache(String key,T data);
    public void clearDataFromCache(String key);
    public T getDataFromCache(String key);
    public void dropUnusedDataFromCache();

}
