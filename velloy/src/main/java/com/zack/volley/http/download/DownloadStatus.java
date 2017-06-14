package com.zack.volley.http.download;

/**
 * Created by Zack on 2017/6/14.
 */

public enum DownloadStatus {
    waitting(0),
    starting(1),
    downloading(2),
    pause(3),
    finish(4),
    failed(5);
    private int value;
    private DownloadStatus(int value){
        this.value = value;
    }
}
