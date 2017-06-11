package com.zack.imageloader.policy;

import com.zack.imageloader.request.BitmapRequest;

/**
 * Created by Zack on 2017/4/23.
 */

public class SerialPolicy implements LoaderPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request1.getSerialNo() - request2.getSerialNo();
    }
}
