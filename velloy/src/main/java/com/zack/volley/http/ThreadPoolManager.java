package com.zack.volley.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by Zack on 2017/6/11.
 */

public class ThreadPoolManager {
    public static final String TAG = "ThreadPoolManager";
    private static ThreadPoolManager instance = new ThreadPoolManager();

    private LinkedBlockingQueue<Future<?>> taskQueue = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;

    public static ThreadPoolManager getInstance(){
        return instance;
    }

    private ThreadPoolManager(){
        threadPoolExecutor = new ThreadPoolExecutor(4,8,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4),handler);
        threadPoolExecutor.execute(runnable);
    }

    public <T> void excute(FutureTask<T> futureTask) throws InterruptedException {
        taskQueue.put(futureTask);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                FutureTask futureTask = null;
                try {
                    /**
                     * 柱塞式函数
                     */
                    Log.i(TAG,"等待队列："+taskQueue.size());
                    futureTask = (FutureTask) taskQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureTask!=null){
                    threadPoolExecutor.execute(futureTask);
                }
            }
        }
    }

    private RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                taskQueue.put(new FutureTask<Object>(r,null) {
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


}
