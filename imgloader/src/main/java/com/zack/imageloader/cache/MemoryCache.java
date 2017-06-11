package com.zack.imageloader.cache;

import android.graphics.Bitmap;

import com.zack.imageloader.request.BitmapRequest;

/**
 * Created by Zack on 2017/4/23.
 */

public class MemoryCache implements BitmapCache {
    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {

    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return null;
    }

    @Override
    public void remove(BitmapRequest request) {

    }
}
