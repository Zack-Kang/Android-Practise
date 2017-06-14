package com.zack.volley.http.download.interfaces;

import com.zack.volley.http.interfaces.IHttpListener;
import com.zack.volley.http.interfaces.IHttpService;

/**
 * Created by Zack on 2017/6/12.
 */

public interface IDownListener extends IHttpListener {
    void setHttpService(IHttpService httpService);
    void setCancelCalle();
    void setPuaseCallble();
}
