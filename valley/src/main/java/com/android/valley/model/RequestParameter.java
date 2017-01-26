package com.android.valley.model;

/**
 * Created by trinadhkoya on 26/01/17.
 */

public class RequestParameter {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public RequestParameter setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public RequestParameter setValue(String value) {
        this.value = value;
        return this;
    }
}
