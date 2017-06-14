package com.zack.volley.http.download;

import com.zack.volley.http.download.interfaces.IDownListener;
import com.zack.volley.http.download.interfaces.IDownloadServiceCallable;
import com.zack.volley.http.interfaces.IHttpService;

import org.apache.http.HttpEntity;

import java.io.File;
import java.util.Map;

/**
 * Created by Zack on 2017/6/12.
 */

public class DownloadListener implements IDownListener {
    private DownloadItem downloadItem;
    private File file;
    protected String url;
    private long breakPoint;
    private IDownloadServiceCallable downloadServiceCallable;
    private IHttpService httpService;

    public DownloadListener(DownloadItem downloadItem, IDownloadServiceCallable downloadServiceCallable, IHttpService httpService) {
        this.downloadItem = downloadItem;
        this.downloadServiceCallable = downloadServiceCallable;
        this.httpService = httpService;
        this.file = new File(downloadItem.getFilePath());
        /**
         * 得到已经下载的长度
         */
        this.breakPoint = file.length();
    }

    public void addHttpHeader(Map<String,String> headerMap){
        if ()
    }
    @Override
    public void onSuccess(HttpEntity httpEntity) {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void setHttpService(IHttpService httpService) {

    }

    @Override
    public void setCancelCalle() {

    }

    @Override
    public void setPuaseCallble() {

    }
}
