package com.example.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ipc.aidl.Book;
import com.example.ipc.aidl.BookManagerService;
import com.example.ipc.aidl.IBookManager;
import com.example.ipc.aidl.IUpdateListener;

public class MainActivity extends AppCompatActivity {
    IBookManager mBookManager;
    BookServiceConnection mConnection;
    IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d("MainActivity", "binderDied >> " + Thread.currentThread().getName());
        }
    };

    IUpdateListener listener = new IUpdateListener.Stub(){

        @Override
        public void onUpdate(Book book) throws RemoteException {
            Log.d("MainActivity", "onUpdate >> " + Thread.currentThread().getName());
            ((TextView)findViewById(R.id.tv_book)).setText(book.name);
        }
    };
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnection = new BookServiceConnection();
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        findViewById(R.id.btn_add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBookManager !=null && mBookManager.asBinder().isBinderAlive()){
                    try {
                        mBookManager.addBook(new Book("book-->" + id));
                        id++;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBookManager!= null&&mBookManager.asBinder().isBinderAlive()){
            try {
                mBookManager.unregister(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
    }


    class BookServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MainActivity", "onServiceConnected >> " + Thread.currentThread().getName());
            mBookManager = IBookManager.Stub.asInterface(iBinder);
            try {
               // mBookManager.register(listener);
                iBinder.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBookManager = null;
            Log.d("MainActivity", "onServiceConnected >> " + Thread.currentThread().getName());
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.d("MainActivity", "onBindingDied >> " + Thread.currentThread().getName());
        }
    }
}
