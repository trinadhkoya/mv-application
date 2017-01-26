package com.android.valley.type;

import com.android.valley.MindValleyHTTP;
import com.android.valley.listener.HttpListener;
import com.android.valley.model.HeaderParameter;
import com.android.valley.model.RequestParameter;
import com.android.valley.task.JSONObjectRequestTask;
import com.android.valley.utils.CacheManager;
import com.android.valley.utils.CacheManagerInterface;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by trinadhkoya on 26/01/17.
 */
public class JSONObjectType extends Type<JSONObject> {

    private String mUrl;
    private HttpListener<JSONObject> mliListener;
    private MindValleyHTTP.Method mMethod;
    private ArrayList<RequestParameter> mRequestParameters;
    private ArrayList<HeaderParameter> mHeaderParameters;
    private JSONObjectRequestTask mTask;
    private CacheManagerInterface<JSONObject> mCacheManager;

    /**
     *
     * @param method
     * @param url
     * @param requestParameters
     * @param headerParameters
     */
    public JSONObjectType(MindValleyHTTP.Method method, String url, ArrayList<RequestParameter> requestParameters, ArrayList<HeaderParameter> headerParameters) {
        this.mUrl = url;
        this.mMethod = method;
        this.mRequestParameters = requestParameters;
        this.mHeaderParameters = headerParameters;
    }


    /**
     *
     * @param cacheManager
     * @return JsonObject instance
     */
    @Override
    public JSONObjectType setCacheManager(CacheManager<JSONObject> cacheManager) {
        this.mCacheManager = cacheManager;
        return this;
    }


    /**
     *
     * @param listener
     * @return JsonObjectType instance
     */
    @Override
    public JSONObjectType setCallback(HttpListener<JSONObject> listener) {
        this.mliListener = listener;
        mliListener.onRequest();
        JSONObject data;
        if (mCacheManager != null) {
            data = mCacheManager.getDataFromCache(mUrl);
            if (data != null) {
                mliListener.onResponse(data);
                return this;
            }
        }
        mTask = new JSONObjectRequestTask(mMethod, mUrl, mRequestParameters, mHeaderParameters, mliListener);
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
