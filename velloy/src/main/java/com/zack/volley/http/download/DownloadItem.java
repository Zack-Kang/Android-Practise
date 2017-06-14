package com.zack.volley.http.download;

import com.zack.volley.http.HttpTask;

/**
 * Created by Zack on 2017/6/12.
 */

public class DownloadItem {
    private long currentLength;
    private long totalLength;
    private String url;
    private String filePath;
    private HttpTask httpTask;
    private DownloadStatus status;

    public DownloadStatus getStatus() {
        return status;
    }

    public long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public HttpTask getHttpTask() {
        return httpTask;
    }

    public void setHttpTask(HttpTask httpTask) {
        this.httpTask = httpTask;
    }
}
