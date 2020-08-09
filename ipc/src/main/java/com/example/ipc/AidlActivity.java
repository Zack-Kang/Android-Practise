package com.example.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ipc.aidl.Book;
import com.example.ipc.aidl.BookManagerService;
import com.example.ipc.aidl.IBookManager;
import com.example.ipc.aidl.IUpdateListener;
import com.example.ipc.util.Logger;

import java.util.List;

public class AidlActivity extends AppCompatActivity {
    int mBookCount = 0;
    TextView mTvResult;
    IBookManager mBookManager;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.i(AidlActivity.this.getClass().getName()," >> onServiceConnected");
            mBookManager = IBookManager.Stub.asInterface(iBinder);

            try {
                mBookManager.register(mUpdateListener);
                mBookManager.asBinder().linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.i(AidlActivity.this.getClass().getName()," >> onServiceDisconnected");
            mBookManager = null;
        }
    };

    IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Logger.i(AidlActivity.this.getClass().getName()," >> binderDied");
            if (mBookManager == null){
                return;
            }
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient,0);
            mBookManager = null;
            bindService();
        }
    };

    IUpdateListener mUpdateListener = new IUpdateListener.Stub() {

        @Override
        public void onUpdate(final Book book) throws RemoteException {
            Thread t = Thread.currentThread();
            Logger.i(AidlActivity.this.getClass().getName()," >> onUpdate");
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
        setContentView(R.layout.activity_aidl);
        initView();
        bindService();
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
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }
    private void unbindService() {
        Logger.i(this.getClass().getName()," >> unbindService");
        if (mServiceConnection !=null && mBookManager!=null){
            unbindService(mServiceConnection);
        }
    }
}