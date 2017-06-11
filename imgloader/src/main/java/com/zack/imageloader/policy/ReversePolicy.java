package com.zack.imageloader.policy;

import com.zack.imageloader.request.BitmapRequest;

/**
 * Created by Zack on 2017/4/23.
 */

public class ReversePolicy implements LoaderPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNo() - request1.getSerialNo();
    }
}
