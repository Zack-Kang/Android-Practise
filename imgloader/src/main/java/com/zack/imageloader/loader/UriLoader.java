package com.zack.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zack.imageloader.request.BitmapRequest;
import com.zack.imageloader.util.BitmapDecoder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Zack on 2017/4/23.
 */

public class UriLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        URLConnection connection = null;
        InputStream is = null;
        BitmapDecoder bitmapDecoder = null;
        try {
            connection = (new URL(request.getImageUrl())).openConnection();
            is = connection.getInputStream();
            final BufferedInputStream bis = new BufferedInputStream(is);
            //解码图片
            bitmapDecoder = new BitmapDecoder() {
                @Override
                public Bitmap decodeBitmapWidthOption(BitmapFactory.Options options) {
                    Bitmap bitmap = BitmapFactory.decodeStream(bis,null,options);
                    if (options.inJustDecodeBounds){
                        try {
                            //重置
                            //则输入流总会在调用Mark之后记住所有读取的字节
                            bis.reset();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return bitmap;
                }

            };


            return null;//bitmapDecoder.decodeBitmap();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
