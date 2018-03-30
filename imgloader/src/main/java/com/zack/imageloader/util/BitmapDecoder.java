package com.zack.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Zack on 2017/4/26.
 */

public abstract class BitmapDecoder {
    public Bitmap decodeBitmap(int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只需要读取图片宽高信息，无需将整张图片加载到内存，inJustDecodeBounds设置true
        options.inJustDecodeBounds = true;
        decodeBitmapWidthOption(options);
        return null;
    }

    public abstract Bitmap decodeBitmapWidthOption(BitmapFactory.Options options);
}
