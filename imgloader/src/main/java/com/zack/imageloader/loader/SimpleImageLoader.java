package com.zack.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.zack.imageloader.config.DisplayConfig;
import com.zack.imageloader.config.ImageLoaderConfig;
import com.zack.imageloader.request.BitmapRequest;
import com.zack.imageloader.request.RequestQueue;

/**
 * Created by Zack on 2017/4/23.
 */

public class SimpleImageLoader {
    //配置文件
    private ImageLoaderConfig config;
    //请求队列
    private RequestQueue requestQueue;
    //单例对象
    private static volatile SimpleImageLoader instance;
    private SimpleImageLoader() {
    }

    private SimpleImageLoader(ImageLoaderConfig config) {
        this.config = config;
        requestQueue = new RequestQueue(config.getThreadCount());
        //开始请求队列
        requestQueue.start();
    }

    public static SimpleImageLoader getInstance(ImageLoaderConfig config){
        if (instance==null){
            synchronized (SimpleImageLoader.class){
                if (instance==null){
                    instance = new SimpleImageLoader(config);
                }
            }
        }
        return instance;
    }

    /**
     * 第二次开始获取单例
     * @return
     */
    public static SimpleImageLoader getInstance(){
        if (instance==null){
            throw new UnsupportedOperationException("SimpleImageLoader 没有初始化");
        }
        return instance;
    }

    /**
     *
     * @param imageView
     * @param uri  http/file开头
     */
    public void disPlayImage(ImageView imageView, String uri){
        disPlayImage(imageView,uri, null);
    }

    public void disPlayImage(ImageView imageView, String uri, DisplayConfig displayConfig){
        disPlayImage(imageView,uri,displayConfig,null);
    }

    public void disPlayImage(ImageView imageView, String uri, DisplayConfig displayConfig, ImageListener listener){
        //实例化一个请求
        BitmapRequest request = new BitmapRequest(imageView,displayConfig,uri,listener);
        //添加到队列
        requestQueue.addReuest(request);
    }

    public static interface ImageListener{
        /**
         *
         * @param imageView
         * @param bitmap
         */
        void onComplete(ImageView imageView, Bitmap bitmap);
    }

    public ImageLoaderConfig getConfig() {
        return config;
    }
}
