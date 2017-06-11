package com.zack.volley.http;

import com.alibaba.fastjson.JSON;
import com.zack.volley.http.interfaces.IHttpService;

import java.io.UnsupportedEncodingException;

/**
 * Created by Zack on 2017/6/11.
 */

public class HttpTask<T> implements Runnable {
    private IHttpService httpService;

    public HttpTask(RequestHolder<T> requestHolder){
        httpService = requestHolder.getHttpService();
        httpService.setHttpListener(requestHolder.getHttpListener());
        httpService.setUrl(requestHolder.getUrl());
        T request = requestHolder.getRequestInfo();
        String requestInfo = JSON.toJSONString(request);
        try {
            httpService.setRequestData(requestInfo.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        httpService.excute();
    }
}
