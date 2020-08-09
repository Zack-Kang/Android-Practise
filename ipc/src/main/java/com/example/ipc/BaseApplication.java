package com.example.ipc;

import android.app.Application;

import com.example.ipc.util.Logger;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(this);
    }
}
