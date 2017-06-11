package com.zack.volley.http.interfaces;

/**
 * Created by Zack on 2017/6/9.
 */

public interface IDataListener<M> {

    /**
     * 回调结果给调用层
     * @param m
     */
    void onSuccess(M m);

    void onFail();

}
