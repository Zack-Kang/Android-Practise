package com.zack.volley.http.download.interfaces;

import com.zack.volley.http.download.DownloadItem;

/**
 * Created by Zack on 2017/6/12.
 */

public interface IDownloadServiceCallable {

    void onDownloadStatusChanged(DownloadItem downloadItem);

    void onTotalLengthReceived(DownloadItem downloadItem);

    void onCurrentSizeChanged(DownloadItem downloadItem, double downLength);

    void onDownloadSuccess(DownloadItem downloadItem);

    void onDownloadPause(DownloadItem downloadItem);

    void onDownloadError(DownloadItem downloadItem, int var2, int var3);
}
