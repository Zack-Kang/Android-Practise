package com.zack.volley.http.download;

import com.zack.volley.http.interfaces.IHttpService;

import java.util.Map;

/**
 * Created by Zack on 2017/6/12.
 */

public class DownloadFileManager {

    /**
     * 下载
     * @param url
     */
    public void down(String url){
        IHttpService httpService = new FileDownHttpService();
        Map<String,String> map = httpService.getHttpHeadMap();
    }
}
