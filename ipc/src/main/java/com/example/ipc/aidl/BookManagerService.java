package com.example.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {
    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("BookManagerService", "onBind >> " + Thread.currentThread().getName());
        return new BookBinder();
    }

    static class BookBinder extends IBookManager.Stub{
        RemoteCallbackList<IUpdateListener> listeners;
        CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<>();
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d("BookManagerService", "getBookList >> " + Thread.currentThread().getName());
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d("BookManagerService", "addBook >> " + Thread.currentThread().getName());
            books.add(book);
            update(book);
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
            Log.d("BookManagerService", "register >> " + Thread.currentThread().getName());
            listeners.register(listener);

        }

        @Override
        public void unregister(IUpdateListener listener) throws RemoteException {
            Log.d("BookManagerService", "unregister >> " + Thread.currentThread().getName());
            listeners.unregister(listener);
        }
    }
}
