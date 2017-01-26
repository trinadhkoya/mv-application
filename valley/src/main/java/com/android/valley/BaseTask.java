package com.android.valley;

import android.net.Uri;
import android.os.AsyncTask;

import com.android.valley.model.HeaderParameter;
import com.android.valley.model.RequestParameter;
import com.android.valley.model.Response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.android.valley.Constants.CONN_READ_TIMEOUT;
import static com.android.valley.Constants.CONN_TIMEOUT;
import static com.android.valley.MindValleyHTTP.Method.DELETE;
import static com.android.valley.MindValleyHTTP.Method.GET;
import static com.android.valley.MindValleyHTTP.Method.POST;
import static com.android.valley.MindValleyHTTP.Method.PUT;

/**
 * Created by trinadhkoya on 26/01/17.
 */

public abstract class BaseTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {


    CacheManagerInterface<Result> mCacheManager;
    HttpURLConnection connection;


    public void setmCacheManager(CacheManagerInterface<Result> mCacheManager) {
        this.mCacheManager = mCacheManager;
    }

    protected Response executeRequest(String url, MindValleyHTTP.Method method, ArrayList<RequestParameter> requestParameters, ArrayList<HeaderParameter> headerParameters) throws IOException {
        InputStream inputStream = null;
        // Only display the first 500 characters of the retrieved
        // web page content.

        URL mUrl = new URL(url);
        connection = (HttpURLConnection) mUrl.openConnection();
        connection.setReadTimeout(CONN_READ_TIMEOUT /* milliseconds */);
        connection.setConnectTimeout(CONN_TIMEOUT /* milliseconds */);

        switch (method) {
            case GET:
                connection.setRequestMethod(Constants.GET_METHOD);
                break;

            case POST:
                connection.setRequestMethod(Constants.POST_METHOD);
                break;

            case PUT:
                connection.setRequestMethod(Constants.PUT_METHOD);
                break;

            case DELETE:
                connection.setRequestMethod(Constants.DELETE_METHOD);
                break;
        }
        connection.setDoInput(true);
        connection.setDoOutput(method != MindValleyHTTP.Method.GET);


        //write headers if any
        if (headerParameters.size() > 0) {
            for (HeaderParameter header : headerParameters) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
        }


        Uri.Builder builder = new Uri.Builder();

        //write request parameters
        if (requestParameters.size() > 0) {
            for (RequestParameter itm : requestParameters) {
                builder.appendQueryParameter(itm.getKey(), itm.getValue());
            }

            String query = builder.build().getEncodedQuery();

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
        }


        connection.connect();


        int response = connection.getResponseCode();
        inputStream = connection.getInputStream();

        Response resp = new Response();
        resp.setResponseCode(response);
        resp.setResponseData(inputStream);
        return resp;

    }
}
