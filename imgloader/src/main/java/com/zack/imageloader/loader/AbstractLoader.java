package com.zack.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.zack.imageloader.cache.BitmapCache;
import com.zack.imageloader.config.DisplayConfig;
import com.zack.imageloader.request.BitmapRequest;

/**
 * Created by Zack on 2017/4/23.
 */

public abstract class AbstractLoader implements Loader {
    /**
     * 拿到用户自定义配置的缓存策略
     */
    private BitmapCache bitmapCache = SimpleImageLoader.getInstance().getConfig().getBitmapCache();

    /**
     * 拿到显示配置
     * @param request
     */
    private DisplayConfig displayConfig = SimpleImageLoader.getInstance().getConfig().getDisplayConfig();

    @Override
    public void loadImage(BitmapRequest request) {
        /**
         * 从缓存中取到bitmap
         */
        Bitmap bitmap = bitmapCache.get(request);
        if (bitmap==null){
            //显示默认加载图片
            showLoadingImage(request);
            //开始真正加载图片
            bitmap = onLoad(request);
            //缓存图片
            cacheBitmap(request,bitmap);
        }
    }

    /**
     * 缓存图片
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if(request!=null&&bitmap!=null){
            synchronized (AbstractLoader.class){
                bitmapCache.put(request,bitmap);
            }
        }
    }

    //抽象加载网络图片和本地图片
    protected abstract Bitmap onLoad(BitmapRequest request) ;

    private void showLoadingImage(BitmapRequest request) {
        if (hasLoadingPlaceHolder()){
            final ImageView imageView = request.getImageView();
            if (imageView!=null){
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.loadingImag);
                    }
                });
            }
        }
    }

    private boolean hasLoadingPlaceHolder() {
        return (displayConfig!=null&&displayConfig.loadingImag>0);
    }
    private boolean hasFailedPlaceHolder() {
        return (displayConfig!=null&&displayConfig.failedImage >0);
    }

}
