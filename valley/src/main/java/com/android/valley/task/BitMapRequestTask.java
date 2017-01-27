package com.android.valley.task;

import android.graphics.Bitmap;

import com.android.valley.MindValleyHTTP;
import com.android.valley.listener.HttpListener;
import com.android.valley.model.HeaderParameter;
import com.android.valley.model.RequestParameter;
import com.android.valley.model.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by trinadhkoya on 26/01/17.
 */
public class BitMapRequestTask extends BaseTask<String, Void, Bitmap> {


    private MindValleyHTTP.Method mMethod;
    private String mUrl;
    private ArrayList<RequestParameter> mRequestParameters;
    private ArrayList<HeaderParameter> mHeaderParameters;
    private HttpListener<Bitmap> mliListener;

    private boolean error = false;

    public BitMapRequestTask(MindValleyHTTP.Method method, String url, ArrayList<RequestParameter> requestParameters, ArrayList<HeaderParameter> headerParameters, HttpListener<Bitmap> listener) {
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
    protected Bitmap doInBackground(String... params) {

        Response response = null;
        try {
            response = executeRequest(mUrl, mMethod, mRequestParameters, mHeaderParameters);
            Bitmap bitmap = response.getBitMapFormat();
            if (this.mCacheManager != null) {
                if (this.mCacheManager.getDataFromCache(mUrl) == null) {
                    this.mCacheManager.addDataToCache(mUrl, bitmap);
                }
            }
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
            error = true;
        }


        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (!error) {
            this.mliListener.onResponse(bitmap);
        } else {
            this.mliListener.onError();
        }
    }


    /**
     * if there is any cahe,remove it.
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (this.mCacheManager != null) {
            this.mCacheManager.clearDataFromCache(mUrl);
        }
    }
}
