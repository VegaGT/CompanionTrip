package com.example.journey.util;

/**
 * Created by Li on 2016/8/17.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
