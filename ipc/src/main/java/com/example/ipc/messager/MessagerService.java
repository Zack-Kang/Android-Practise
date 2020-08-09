package com.example.ipc.messager;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import com.example.ipc.util.Logger;

public class MessagerService extends Service {
    public MessagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new Messenger(new M()).getBinder();
    }
    
    class M extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    onReceive(msg);
                    break;
                default:
            }
        }
    }

    private void onReceive(Message msg) {
        Logger.d("MessagerService", "recive = " + msg.getData());
        Messenger client = msg.replyTo;
        Message message = Message.obtain();
        message.what = 1;
        Bundle bundle = new Bundle();
        bundle.putString("msg" , "hello client, i received your msg!");
        message.setData(bundle);
        try {
            client.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
