package com.zack.volley.http.download;

import android.util.Log;

import com.zack.volley.http.JsonHttpService;
import com.zack.volley.http.download.interfaces.IDownListener;
import com.zack.volley.http.interfaces.IHttpListener;
import com.zack.volley.http.interfaces.IHttpService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Zack on 2017/6/12.
 */

public class FileDownHttpService implements IHttpService {
    public static String TAG = "FileDownHttpService";
    private HttpClient httpClient = new DefaultHttpClient();
    private HttpPost httpPost;
    private String url;
    /**
     * 即将添加到请求头的信息
     */
    private Map<String,String> headerMap = Collections.synchronizedMap(new HashMap<String,String>());
    /**
     * 含有请求处理的接口
     */
    private IHttpListener httpListener;
    private byte[] requestData;

    private HttpResponeseHandler httpResponeseHandler = new HttpResponeseHandler();

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void excute() {
        constrcutHeader();
        httpPost = new HttpPost(url);
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestData);
        httpPost.setEntity(byteArrayEntity);
        try {
            httpClient.execute(httpPost,httpResponeseHandler);
        } catch (IOException e) {
            httpListener.onFail();
            e.printStackTrace();
        }
    }

    private void constrcutHeader() {
        Iterator<String> iterator = headerMap.keySet().iterator();
        while (iterator.hasNext()){
            String key  = iterator.next();
            String value = headerMap.get(key);
            Log.i(TAG,"请求头信息："+key+" value:"+value);
            httpPost.addHeader(key,value);
        }
    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {

    }

    @Override
    public void setRequestData(byte[] requestData) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void isPause() {

    }

    @Override
    public Map<String, String> getHttpHeadMap() {
        return null;
    }

    @Override
    public boolean cancel() {
        return false;
    }

    @Override
    public boolean isCancel() {
        return false;
    }

    private class HttpResponeseHandler extends BasicResponseHandler {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException {
            int code = response.getStatusLine().getStatusCode();
            if (code==200){
                httpListener.onSuccess(response.getEntity());
            }else{
                httpListener.onFail();
            }
            return null;
        }
    }
}
