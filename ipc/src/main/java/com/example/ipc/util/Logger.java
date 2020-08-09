package com.example.ipc.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.util.Log;

public class Logger {
    private static Context mContext;
    public static void init(Context context){
        mContext = context;
    }
    public static void d(String tag, String msg){
        Log.d(tag, "Pname = " + getProcessName()+ ", Pid = " + Process.myPid() + ", Tid = " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + ", msg = " + msg);
    }
    public static void v(String tag, String msg){
        Log.v(tag, "Pname = " + getProcessName()+ ", Pid = " + Process.myPid() + ", Tid = " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + ", msg = " + msg);
    }

    public static void e(String tag, String msg){
        Log.e(tag, "Pname = " + getProcessName()+ ", Pid = " + Process.myPid() + ", Tid = " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + ", msg = " + msg);
    }
    public static void i(String tag, String msg){
        Log.i(tag, "Pname = " + getProcessName()+ ", Pid = " + Process.myPid() + ", Tid = " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + ", msg = " + msg);
    }

    private static String getProcessName(){
        int pid = android.os.Process.myPid();
        @SuppressLint("WrongConstant")
        ActivityManager manager =
                (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        try {
            for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                if (process.pid == pid) {
                    return process.processName;
                }
            }
        } catch (Exception e) {
            // ActivityManager.getRunningAppProcesses() may throw NPE in some custom-made devices (oem BIRD)
        }
        return "";
    }
}
