package com.zack.imageloader.cache;

import android.graphics.Bitmap;

import com.zack.imageloader.request.BitmapRequest;

/**
 * Created by Zack on 2017/4/23.
 */

public interface BitmapCache {
    /**
     * 缓存bitmap
     * @param request
     * @param bitmap
     */
    void put(BitmapRequest request,Bitmap bitmap);

    /**
     * 通过请求取
     * @param request
     * @return
     */
    Bitmap get(BitmapRequest request);

    /**
     * 移出
     * @param request
     */
    void remove(BitmapRequest request);
}
