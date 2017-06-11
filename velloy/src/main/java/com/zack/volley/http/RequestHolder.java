package com.zack.volley.http;

import com.zack.volley.http.interfaces.IHttpListener;
import com.zack.volley.http.interfaces.IHttpService;

/**
 * Created by Zack on 2017/6/11.
 */

public class RequestHolder<T> {
    private IHttpService httpService;
    private IHttpListener httpListener;
    private T requestInfo;
    private String url;

    public IHttpService getHttpService() {
        return httpService;
    }

    public void setHttpService(IHttpService httpService) {
        this.httpService = httpService;
    }

    public IHttpListener getHttpListener() {
        return httpListener;
    }

    public void setHttpListener(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    public T getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(T requestInfo) {
        this.requestInfo = requestInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
