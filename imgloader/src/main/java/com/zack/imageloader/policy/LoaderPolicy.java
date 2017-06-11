package com.zack.imageloader.policy;

import com.zack.imageloader.request.BitmapRequest;

/**
 * 加载策略
 * Created by Zack on 2017/4/23.
 */

public interface LoaderPolicy {
    /**
     * 两个BitmapRequest优先级比较
     * @param request1
     * @param request2
     * @return
     */
    int compareto(BitmapRequest request1,BitmapRequest request2);
}
