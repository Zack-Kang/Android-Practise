package com.zack.imageloader.request;

import android.widget.ImageView;

import com.zack.imageloader.config.DisplayConfig;
import com.zack.imageloader.loader.SimpleImageLoader;
import com.zack.imageloader.policy.LoaderPolicy;

import java.lang.ref.SoftReference;
import java.util.Comparator;

/**
 * Created by Zack on 2017/4/23.
 */

public class BitmapRequest implements Comparator<BitmapRequest>{
    //持有imageview的软引用
    private SoftReference<ImageView> imageViewSoftRef;
    //图片路径
    private String imageUrl;
    //MD5的图片路径
    private String imageUriMD5;
    //下载完成后监听
    public SimpleImageLoader.ImageListener imageListener;
    private DisplayConfig displayConfig;
    //加载策略
    private LoaderPolicy loaderPolicy = SimpleImageLoader.getInstance().getConfig().getLoaderPolicy();
    /**
     * 编号
     */
    private int serialNo;

    public BitmapRequest(ImageView imageView, DisplayConfig displayConfig,  String imageUrl, SimpleImageLoader.ImageListener imageListener) {
        this.imageViewSoftRef = new SoftReference<ImageView>(imageView);
        //设置可见的imageView的tag,要下载的图片路径
        imageView.setTag(imageUrl);
        this.imageUrl = imageUrl;
        this.imageListener = imageListener;
        if (displayConfig!=null){
            this.displayConfig = displayConfig;
        }
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * 优先级的确定
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(BitmapRequest o1, BitmapRequest o2) {
        return loaderPolicy.compareto(o1,o2);
    }

    @Override
    public int hashCode() {
        int result = loaderPolicy!=null?loaderPolicy.hashCode():0;
        result = 31*result + serialNo;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj==null||getClass()!=obj.getClass()) return false;
        BitmapRequest that = (BitmapRequest) obj;
        if (this.serialNo!=that.serialNo) return false;
        return loaderPolicy!=null?loaderPolicy.equals(this.loaderPolicy):this.loaderPolicy==null;
    }

    public ImageView getImageView() {
        return imageViewSoftRef.get();
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
