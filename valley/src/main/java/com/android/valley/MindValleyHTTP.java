package com.android.valley;

import android.content.Context;

import com.android.valley.model.HeaderParameter;
import com.android.valley.model.RequestParameter;
import com.android.valley.type.BitMapType;
import com.android.valley.type.JSONArrayType;
import com.android.valley.type.JSONObjectType;

import java.util.ArrayList;

/**
 * Created by trinadhkoya on 26/01/17.
 */


/**
 *
 */
public class MindValleyHTTP {


    public static enum Method {
        GET, POST, PUT, DELETE
    }

    private Context mContext;
    private String mUrl;
    private Method mMethod;

    private static MindValleyHTTP instance = null;
    private ArrayList<RequestParameter> requestParameters = new ArrayList<>();
    private ArrayList<HeaderParameter> headerParameters = new ArrayList<>();

    /**
     * @param context
     */
    public MindValleyHTTP(Context context) {
        this.mContext = context;
    }


    public static MindValleyHTTP from(Context context) {
        return getInstance(context);
    }


    public static MindValleyHTTP getInstance(Context context) {
        synchronized (MindValleyHTTP.class) {
            if (instance == null) {
                instance = new MindValleyHTTP(context);
            }
        }
        if (context == null) throw new NullPointerException("Something Went Wrong");

        return instance;
    }

    /**
     * @param method
     * @param url    to serve
     * @return MindvalleyInstance
     */
    public MindValleyHTTP serve(Method method, String url) {
        this.mMethod = method;
        this.mUrl = url;
        return this;
    }

    /**
     * @return jsonarray typed data
     */
    public JSONArrayType asJSONArray() {
        return new JSONArrayType(mMethod, mUrl, requestParameters, headerParameters);
    }

    /**
     * @return jsonobject data type
     */
    public JSONObjectType asJSONObject() {
        return new JSONObjectType(mMethod, mUrl, requestParameters, headerParameters);
    }

    /**
     * @return bitmap data type
     */
    public BitMapType asBitMap() {
        return new BitMapType(mMethod, mUrl, requestParameters, headerParameters);
    }

    /**
     * @param key
     * @param value
     * @return instance of MindvalleyHttp
     */
    public MindValleyHTTP setRequestParameters(String key, String value) {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setKey(key);
        requestParameter.setValue(value);
        this.requestParameters.add(requestParameter);
        return this;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public MindValleyHTTP setHeaderParameters(String key, String value) {
        HeaderParameter headerParameter = new HeaderParameter();
        headerParameter.setKey(key);
        headerParameter.setValue(value);
        this.headerParameters.add(headerParameter);
        return this;
    }
}
