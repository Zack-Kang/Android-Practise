package com.zack.volley.http;

import com.zack.volley.http.interfaces.IDataListener;
import com.zack.volley.http.interfaces.IHttpListener;
import com.zack.volley.http.interfaces.IHttpService;

import java.util.concurrent.FutureTask;

/**
 * Created by Zack on 2017/6/8.
 */

public class Volley {
    /**
     *
     * @param <T>  请求参数类型
     * @param <M>  响应参数类型
     */
    public static <T,M> void sendRequest(T requestInfo, String url, Class<M> responese, IDataListener<M> dataListener){
        RequestHolder<T> requestHolder = new RequestHolder<>();
        requestHolder.setUrl(url);
        IHttpService httpService = new JsonHttpService();
        IHttpListener httpListener = new JsonDealListener<>(responese,dataListener);
        requestHolder.setHttpService(httpService);
        requestHolder.setHttpListener(httpListener);
        HttpTask<T> httpTask = new HttpTask<>(requestHolder);
        try {
            ThreadPoolManager.getInstance().excute(new FutureTask<Object>(httpTask,null));
        } catch (InterruptedException e) {
            e.printStackTrace();
            dataListener.onFail();
        }
    }
}
