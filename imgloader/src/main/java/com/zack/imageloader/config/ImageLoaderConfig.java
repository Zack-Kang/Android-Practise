package com.zack.imageloader.config;

import com.zack.imageloader.cache.BitmapCache;
import com.zack.imageloader.policy.LoaderPolicy;

/**
 * Created by Zack on 2017/4/23.
 */

public class ImageLoaderConfig {
    //缓存策略
    private BitmapCache bitmapCache;
    //加载策略
    private LoaderPolicy loaderPolicy;
    //默认线程数
    private int threadCount= Runtime.getRuntime().availableProcessors();
    //
    private DisplayConfig displayConfig = new DisplayConfig();

    private ImageLoaderConfig(){

    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public LoaderPolicy getLoaderPolicy() {
        return loaderPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    /**
     * 建造者模式
     */
    public static class Builder{
        private ImageLoaderConfig imageLoaderConfig;
        public Builder(){
            imageLoaderConfig = new ImageLoaderConfig();
        }

        /**
         * 设置缓存策略
         * @param bitmapCache
         * @return
         */
        public Builder setCachePolicy(BitmapCache bitmapCache){
            imageLoaderConfig.bitmapCache = bitmapCache;
            return this;
        }

        /**
         * 设置加载策略
         * @param loaclPolicy
         * @return
         */
        public Builder setLoaclPolicy(LoaderPolicy loaclPolicy){
            imageLoaderConfig.loaderPolicy = loaclPolicy;
            return this;
        }

        /**
         * 设置线程数
         * @return
         */
        public Builder setThreadCount(int count){
            imageLoaderConfig.threadCount = count;
            return this;
        }

        /**
         * 设置加载过程的图片
         * @param resId
         * @return
         */
        public Builder setLoadingImage(int resId){
            imageLoaderConfig.displayConfig.loadingImag = resId;
            return this;
        }

        /**
         * 设置加载失败的图片
         * @param resId
         * @return
         */
        public Builder setFaildImage(int resId){
            imageLoaderConfig.displayConfig.failedImage = resId;
            return this;
        }




        public ImageLoaderConfig buile(){
            return imageLoaderConfig;
        }
    }
}
