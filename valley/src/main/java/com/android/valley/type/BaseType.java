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
public abstract class BaseType<T> {

    public abstract BaseType setCacheManager(CacheManager<T> cacheManager);

   public  abstract BaseType setCallback(HttpListener<T> callback);

   public  abstract boolean cancel();
}
