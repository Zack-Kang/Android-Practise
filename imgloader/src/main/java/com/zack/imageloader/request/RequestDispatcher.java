package com.zack.imageloader.request;

import android.util.Log;

import com.zack.imageloader.loader.Loader;
import com.zack.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * 转发器
 * 请求转发线程，不断从请求队列获取获取请求
 * Created by Zack on 2017/4/23.
 */

public class RequestDispatcher extends Thread{
    private static String TAG = "";
    /**
     * 请求队列
     */
    private BlockingQueue<BitmapRequest> requestBlockingQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> requestBlockingQueue) {
        this.requestBlockingQueue = requestBlockingQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()){
            try {
                BitmapRequest request = requestBlockingQueue.take();
                /**
                 * 处理请求对象
                 */
                String schema= paresSchema(request.getImageUrl());
                //获取loader加载器
                Loader loader = LoaderManager.getInstance().getLoader(schema);
                loader.loadImage(request);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String paresSchema(String imageUrl) {
        if (imageUrl.contains("://")){
            return imageUrl.split("://")[0];
        }else{
            Log.i(TAG,"不支持此类型");
        }
        return null;
    }
}
