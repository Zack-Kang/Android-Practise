package com.example.ipc.aidl;

import android.os.IBinder;
import android.os.RemoteException;

import com.example.ipc.util.Logger;

public class BinderPoolImpl extends IBinderPool.Stub{
    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        Logger.v("BinderPoolImpl", "queryBinder>>" + binderCode);
        switch (binderCode){
            case 1:
                return new BookManagerImpl();
            default:
        }
        return null;
    }
}