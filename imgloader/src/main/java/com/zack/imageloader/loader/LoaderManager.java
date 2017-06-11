package com.zack.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zack on 2017/4/25.
 */

public class LoaderManager {
    //缓存所有支持的Loader
    private Map<String,Loader> loaderMap = new HashMap<>();

    private static LoaderManager instance = new LoaderManager();
    private LoaderManager(){
        register("http",new UriLoader());
        register("https",new UriLoader());
        register("file",new LocalLoader());
    }

    public static LoaderManager getInstance(){
        return instance;
    }

    private void register(String schema, Loader loader) {
        loaderMap.put(schema,loader);
    }

    public Loader getLoader(String schema) {
        if (loaderMap.containsKey(schema)){
            return loaderMap.get(schema);
        }else{
            return new NullLoader();
        }
    }
}
