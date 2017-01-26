package com.android.valley.listener;


/**
 * Created by trinadhkoya on 26/01/17.
 */

public interface HttpListener<T> {

    public void onRequest();

    public void onResponse(T data);

    public void onError();

    public void onCancel();

}
