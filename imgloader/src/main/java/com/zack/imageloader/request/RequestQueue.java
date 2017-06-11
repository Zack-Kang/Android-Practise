package com.zack.imageloader.request;

import android.util.Log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Zack on 2017/4/23.
 */

public class RequestQueue {
    public static  final String TAG = "";
    /**
     * 阻塞式队列
     * 多线程共享
     * 生产效率和消费效率相差太远了
     * disPlayImage()
     * 使用优先级队列
     * 优先级高的队列先被消费
     * 每一个产品都有编号
     */
    private BlockingQueue<BitmapRequest> requestBlockingDeque = new PriorityBlockingQueue<>();

    /**
     * 转发器数量
     */
    private int threadCount;

    private AtomicInteger i = new AtomicInteger(0);

    /**
     * 转发器
     */
    private RequestDispatcher[] dispatchers;

    public RequestQueue(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * 添加请求对象
     * @param request
     */
    public void addReuest(BitmapRequest request){
        if (!requestBlockingDeque.contains(request)){
            /**
             * 给请求进行编号
             */
            request.setSerialNo(i.incrementAndGet());
            requestBlockingDeque.add(request);
        }else{
            Log.i(TAG,"请求对象已经存在 编号："+request.getSerialNo());
        }
    }

    /**
     * 开启请求
     */
    public void start(){
        stop();
        startDispatchers();

    }

    private void startDispatchers() {
        dispatchers = new RequestDispatcher[threadCount];
        for (int i=0;i<threadCount;i++){
            RequestDispatcher dispatcher = new RequestDispatcher(requestBlockingDeque);
            dispatchers[i] = dispatcher;
            dispatchers[i].start();

        }
    }

    /**
     * 停止
     */
    public void stop(){

    }
}
