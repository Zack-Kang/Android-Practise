package com.example.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ipc.messager.MessagerService;
import com.example.ipc.util.Logger;

public class MessengerActivity extends AppCompatActivity {
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    class C extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Logger.d("MessengerActivity","revice service msg, " + msg.getData());
            }
        }
    }

    Messenger mClient = new Messenger(new C());

    Messenger mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        initView();
        bindService();
    }

    private void initView() {
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg  = Message.obtain();
                msg.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("msg", "hello service, I'm client!");
                msg.setData(bundle);
                msg.replyTo = mClient;
                try {
                    mService.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }

    private void unbindService() {
        unbindService(mConnection);
    }

    private void bindService() {
        Intent intent = new Intent(this, MessagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }
}