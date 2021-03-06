package com.zack.volley.http;

import com.zack.volley.http.interfaces.IHttpListener;
import com.zack.volley.http.interfaces.IHttpService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Zack on 2017/6/10.
 */

public class JsonHttpService implements IHttpService {
    private IHttpListener httpListener;
    private HttpClient httpClient = new DefaultHttpClient();
    private HttpPost httpPost;
    private String url;
    private byte[] requestData;
    /**
     * HttpClient获取网络的回调
     */
    private HttpResponeseHandler httpResponeseHandler = new HttpResponeseHandler();
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void excute() {
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

    @Override
    public void setHttpListener(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
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

    @Override
    public void pause() {

    }

    @Override
    public void isPause() {

    }

    private class HttpResponeseHandler extends BasicResponseHandler{
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
