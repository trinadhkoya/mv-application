package com.android.valley.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by trinadhkoya on 26/01/17.
 */

public class Response {
    private int responseCode;
    private InputStream responseData;

    public int getResponseCode() {
        return responseCode;
    }

    public Response setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public InputStream getResponseData() {
        return responseData;
    }

    public Response setResponseData(InputStream responseData) {
        this.responseData = responseData;
        return this;
    }

    /**
     * from inputstream
     * @return string format data
     * @throws IOException
     */
    public String getStringFormat() throws IOException {


        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder stringBuilder = new StringBuilder();
        Reader in = new InputStreamReader(responseData, "UTF-8");
        for (; ; ) {
            int response = in.read(buffer, 0, buffer.length);
            if (response < 0) break;
            stringBuilder.append(buffer, 0, response);
        }
        if (responseData != null) {
            responseData.close();
        }
        return stringBuilder.toString();
    }


    /**
     *from inputstream
     * @return bitmap
     */
    public Bitmap getBitMapFormat() {
        Bitmap bitmap = BitmapFactory.decodeStream(this.responseData);
        if (responseData != null) {
            try {
                responseData.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;

    }
}
