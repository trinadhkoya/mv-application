package com.android.valley.type;

import com.android.valley.MindValleyHTTP;
import com.android.valley.listener.HttpListener;
import com.android.valley.model.HeaderParameter;
import com.android.valley.model.RequestParameter;
import com.android.valley.task.JSONArrayRequestTask;
import com.android.valley.utils.CacheManager;
import com.android.valley.utils.CacheManagerInterface;

import org.json.JSONArray;

import java.util.ArrayList;


/**
 * Created by trinadhkoya on 26/01/17.
 */
public class JSONArrayType extends BaseType<JSONArray> {

    private String mUrl;
    private HttpListener<JSONArray> mliListener;
    private MindValleyHTTP.Method mMethod;
    private ArrayList<RequestParameter> mRequestParameters;
    private ArrayList<HeaderParameter> mHeaderParameters;
    private JSONArrayRequestTask mTask;
    private CacheManagerInterface<JSONArray> mCacheManager;


    public JSONArrayType(MindValleyHTTP.Method method, String url, ArrayList<RequestParameter> requestParameters, ArrayList<HeaderParameter> headerParameters) {
        this.mUrl = url;
        this.mMethod = method;
        this.mRequestParameters = requestParameters;
        this.mHeaderParameters = headerParameters;
    }

    @Override
    public JSONArrayType setCacheManager(CacheManager<JSONArray> cacheManager) {
        this.mCacheManager = cacheManager;
        return this;
    }

    @Override
    public JSONArrayType setCallback(HttpListener<JSONArray> listener) {
        this.mliListener = listener;
        mliListener.onRequest();
        JSONArray data;
        if (mCacheManager != null) {
            data = mCacheManager.getDataFromCache(mUrl);
            if (data != null) {
                mliListener.onResponse(data);
                return this;
            }
        }
        mTask = new JSONArrayRequestTask(mMethod, mUrl, mRequestParameters, mHeaderParameters, mliListener);
        mTask.setmCacheManager(mCacheManager);
        mTask.execute();
        return this;
    }

    @Override
    public boolean cancel() {
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
