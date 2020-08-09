package com.example.ipc.aidl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import com.example.ipc.util.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerImpl extends IBookManager.Stub {
    RemoteCallbackList<IUpdateListener> mCallbackList = new RemoteCallbackList<>();
    CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    @Override
    public List<Book> getBookList() throws RemoteException {
        return mBookList;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        Logger.d("BookManagerImpl", " >> addBook");
        mBookList.add(book);
        update(book);
        mH.removeMessages(1);
        mH.sendEmptyMessageDelayed(1, 200);
    }

    @Override
    public void register(IUpdateListener listener) throws RemoteException {
        Logger.d("BookManagerImpl", " >> register");
        mCallbackList.register(listener);
        synchronized (this){
            if (t==null||!t.isAlive()){
                t = new HandlerThread("");
                t.start();
                mH = new H(t.getLooper());
            }
        }
    }

    @Override
    public void unregister(IUpdateListener listener) throws RemoteException {
        Logger.d("BookManagerImpl", " >> unregister");
        mCallbackList.unregister(listener);
        int n = mCallbackList.beginBroadcast();
        if (n==0 && t!=null){
            t.quit();
            t = null;
            mH = null;
        }
        mCallbackList.finishBroadcast();
    }

    void update(Book book) throws RemoteException {
        int n = mCallbackList.beginBroadcast();
        for (int i=0;i<n;i++){
            IUpdateListener listener = mCallbackList.getBroadcastItem(i);
            if (listener != null){
                listener.onUpdate(book);
            }
        }
        mCallbackList.finishBroadcast();
    }


    HandlerThread t = null;
    Handler mH = null;

    class H extends Handler{
        public H(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                try {
                    update(mBookList.get(0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mH.sendEmptyMessageDelayed(1, 200);
            }
        }
    }
}
