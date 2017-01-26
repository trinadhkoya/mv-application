package com.android.valley.task;

import com.android.valley.MindValleyHTTP;
import com.android.valley.listener.HttpListener;
import com.android.valley.model.HeaderParameter;
import com.android.valley.model.RequestParameter;
import com.android.valley.model.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by trinadhkoya on 26/01/17.
 */
public class JSONObjectRequestTask extends BaseTask<String, Void, JSONObject> {


    private MindValleyHTTP.Method mMethod;
    private String mUrl;
    private ArrayList<RequestParameter> mRequestParameters;
    private ArrayList<HeaderParameter> mHeaderParameters;
    private HttpListener<JSONObject> mliListener;

    private boolean error = false;

    public JSONObjectRequestTask(MindValleyHTTP.Method method, String url, ArrayList<RequestParameter> requestParameters, ArrayList<HeaderParameter> headerParameters, HttpListener<JSONObject> listener) {
        this.mMethod = method;
        this.mUrl = url;
        this.mRequestParameters = requestParameters;
        this.mHeaderParameters = headerParameters;
        this.mliListener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        Response response = null;
        try {
            response = executeRequest(mUrl, mMethod, mRequestParameters, mHeaderParameters);
            JSONObject data = response.getStringFormat();
            if (this.mCacheManager != null) {
                if (this.mCacheManager.getDataFromCache(mUrl) == null) {
                    this.mCacheManager.addDataToCache(mUrl, data);
                }
            }
            return data;

        } catch (IOException e) {
            e.printStackTrace();
            error = true;
        }


        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if (!error) {
            this.mliListener.onResponse(jsonObject);
        } else {
            this.mliListener.onError();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (this.mCacheManager != null) {
            this.mCacheManager.clearDataFromCache(mUrl);
        }
    }
}
