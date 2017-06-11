package com.zack.volley.http.interfaces;

import org.apache.http.HttpEntity;

/**
 * Created by Zack on 2017/6/8.
 * 处理结果
 */

public interface IHttpListener {
    /**
     * 网络访问框架调用
     * @param httpEntity
     */
    void onSuccess(HttpEntity httpEntity);
    void onFail();
}
