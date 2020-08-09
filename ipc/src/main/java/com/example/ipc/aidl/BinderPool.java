package com.example.ipc.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.ipc.util.Logger;

public class BinderPool {
    Context mContext;
    IBinderPool mBinderPool;
    static BinderPool  sInstance;
    public BinderPool() {

    }
    public void init(Context context){
        this.mContext = context;
    }
    public static BinderPool getInstance(){
        if (sInstance == null){
            synchronized (BinderPool.class){
                if (sInstance==null){
                    sInstance = new BinderPool();
                }
            }
        }
        return sInstance;
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.i(BinderPool.this.getClass().getName()," >> onServiceConnected");
            mBinderPool = IBinderPool.Stub.asInterface(iBinder);
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.i(BinderPool.this.getClass().getName()," >> onServiceDisconnected");
            mBinderPool = null;
        }
    };

    IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Logger.i(BinderPool.this.getClass().getName()," >> binderDied");
            if (mBinderPool == null){
                return;
            }
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient,0);
            mBinderPool = null;
            bindService();
        }
    };

    public void bindService() {
        Logger.i(this.getClass().getName()," >> bindService");
        Intent intent = new Intent(mContext, RemoteService.class);
        mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }
    public void unbindService() {
        Logger.i(this.getClass().getName()," >> unbindService");
        if (mServiceConnection !=null && mBinderPool!=null){
            mContext.unbindService(mServiceConnection);
        }
    }
    public IBinder queryBinder(int binderCode) throws RemoteException {
        Logger.v(this.getClass().getName(), "queryBinder>>" + binderCode);
        return mBinderPool.queryBinder(binderCode);
    }
}
