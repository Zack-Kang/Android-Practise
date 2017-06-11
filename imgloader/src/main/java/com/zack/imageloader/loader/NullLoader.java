package com.zack.imageloader.loader;

import android.graphics.Bitmap;

import com.zack.imageloader.request.BitmapRequest;

/**
 * Created by Zack on 2017/4/25.
 */

public class NullLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }
}
