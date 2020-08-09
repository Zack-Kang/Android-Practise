package com.example.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ipc.aidl.BinderPool;
import com.example.ipc.aidl.Book;
import com.example.ipc.aidl.BookManagerService;
import com.example.ipc.aidl.IBinderPool;
import com.example.ipc.aidl.IBookManager;
import com.example.ipc.aidl.IUpdateListener;
import com.example.ipc.util.Logger;

import java.util.List;

public class BinderPoolActivity extends AppCompatActivity {

    int mBookCount = 0;
    TextView mTvResult;
    IBookManager mBookManager;
    IUpdateListener mUpdateListener = new IUpdateListener.Stub() {

        @Override
        public void onUpdate(final Book book) throws RemoteException {
            Thread t = Thread.currentThread();
            Logger.i(BinderPoolActivity.this.getClass().getName()," >> onUpdate");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvResult.setText(book.name);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        BinderPool.getInstance().init(this);
        initView();
        bindService();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mBookManager = IBookManager.Stub.asInterface(BinderPool.getInstance().queryBinder(1));
                    mBookManager.register(mUpdateListener);
                } catch (RemoteException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        mTvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBookManager!=null && mBookManager.asBinder().isBinderAlive()){
                    try {
                        mBookManager.addBook(new Book(" >> book = " + mBookCount));mBookCount++;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.btn_del_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBookManager!=null && mBookManager.asBinder().isBinderAlive()){
                    try {
                        List<Book> books = mBookManager.getBookList();
                        mTvResult.setText(books.toString());
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
        if (mBookManager!=null){
            try {
                mBookManager.unregister(mUpdateListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService();
    }



    private void bindService() {
        Logger.i(this.getClass().getName()," >> bindService");
        BinderPool.getInstance().bindService();
    }
    private void unbindService() {
        Logger.i(this.getClass().getName()," >> unbindService");
        BinderPool.getInstance().unbindService();
    }
}