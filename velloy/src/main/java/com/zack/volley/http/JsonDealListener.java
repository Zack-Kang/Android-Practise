package com.zack.volley.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.zack.volley.http.interfaces.IDataListener;
import com.zack.volley.http.interfaces.IHttpListener;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Zack on 2017/6/9.
 * M 对应响应类
 */

public class JsonDealListener<M> implements IHttpListener {
    private Class<M> responese;
    /**
     * 回调应用层的接口
     */
    private IDataListener<M> dataListener;

    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonDealListener(Class<M> responese, IDataListener<M> dataListener) {
        this.responese = responese;
        this.dataListener = dataListener;
    }

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        InputStream inputStream = null;
        try{
            inputStream = httpEntity.getContent();
            /**
             * 得到网络返回的数据
             */
            String content = getContent(inputStream);
            final M m = JSON.parseObject(content,responese);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onSuccess(m);
                }
            });
        }catch (IOException e){
            dataListener.onFail();
        }
    }

    private String getContent(InputStream inputStream) {
        StringBuffer sb = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            sb = new StringBuffer();
            String line = null;
            while ((line=reader.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            dataListener.onFail();
        } finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    @Override
    public void onFail() {
        dataListener.onFail();
    }
}
