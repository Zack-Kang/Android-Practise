package com.example.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ipc.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {
    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Logger.d("BookManagerService", " >> onBind");
        return new BookBinder();
    }



    static class BookBinder extends IBookManager.Stub{
        RemoteCallbackList<IUpdateListener> listeners = new RemoteCallbackList<>();
        CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<>();
        @Override
        public List<Book> getBookList() throws RemoteException {
            Logger.d("BookManagerService", " >> getBookList ");
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Logger.d("BookManagerService", " >> addBook");
            books.add(book);
            update(book);
            mH.sendEmptyMessageDelayed(1, 200);
        }

        void update(Book book) throws RemoteException {
            int n = listeners.beginBroadcast();
            for (int i=0;i<n;i++){
                IUpdateListener listener = listeners.getBroadcastItem(i);
                if (listener != null){
                    listener.onUpdate(book);
                }
            }
            listeners.finishBroadcast();
        }

        @Override
        public void register(IUpdateListener listener) throws RemoteException {
            Logger.d("BookManagerService", " >> register");
            listeners.register(listener);
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
            Logger.d("BookManagerService", " >> unregister");
            listeners.unregister(listener);
            int n = listeners.beginBroadcast();
            if (n==0 && t!=null){
                t.quit();
                t = null;
                mH = null;
            }
            listeners.finishBroadcast();

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
                        update(books.get(0));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    mH.sendEmptyMessageDelayed(1, 200);
                }
            }
        }

    }


}
