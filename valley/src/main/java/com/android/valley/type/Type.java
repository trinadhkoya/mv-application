package com.android.valley.type;

import com.android.valley.listener.HttpListener;
import com.android.valley.utils.CacheManager;

/**
 * Created by trinadhkoya on 26/01/17.
 */

/**
 *
 * @param <T>
 */
public abstract class Type<T> {

    abstract Type setCacheManager(CacheManager<T> cacheManager);

    abstract Type setCallback(HttpListener<T> callback);

    abstract boolean cancel();
}
