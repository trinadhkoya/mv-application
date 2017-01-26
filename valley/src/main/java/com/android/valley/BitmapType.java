package com.android.valley;

import android.graphics.Bitmap;

import com.android.valley.listener.HttpListener;
import com.android.valley.model.HeaderParameter;
import com.android.valley.model.RequestParameter;

import java.util.ArrayList;

/**
 * Created by trinadhkoya on 26/01/17.
 */
public class BitMapType extends Type<Bitmap> {

    private String mUrl;
    private HttpListener<Bitmap> mliListener;
    private MindValleyHTTP.Method mMethod;
    private ArrayList<RequestParameter> mRequestParameters;
    private ArrayList<HeaderParameter> mHeaderParameters;
    private BitMapRequestTask mTask;
    private CacheManagerInterface<Bitmap> mCacheManager;


    public BitMapType(MindValleyHTTP.Method method, String url, ArrayList<RequestParameter> requestParameters, ArrayList<HeaderParameter> headerParameters) {
        this.mUrl = url;
        this.mMethod = method;
        this.mRequestParameters = requestParameters;
        this.mHeaderParameters = headerParameters;
    }

    @Override
    public BitMapType setCacheManager(CacheManager<Bitmap> cacheManager) {
        this.mCacheManager = cacheManager;
        return this;
    }

    @Override
    public BitMapType setCallback(HttpListener<Bitmap> listener) {
        this.mliListener = listener;
        mliListener.onRequest();
        Bitmap bitmap;
        if (mCacheManager != null) {
            bitmap = mCacheManager.getDataFromCache(mUrl);
            if (bitmap != null) {
                mliListener.onResponse(bitmap);
                return this;
            }
        }
        mTask = new BitMapRequestTask(mMethod, mUrl, mRequestParameters, mHeaderParameters, mliListener);
        mTask.setmCacheManager(mCacheManager);
        mTask.execute();
        return this;
    }

    @Override
    boolean cancel() {
        if (mTask != null) {
            mTask.cancel(true);
            if (mTask.isCancelled()) {
                mliListener.onCancel();
                return true;
            }
        } else {
            return false;
        }
        return false;
    }


}
