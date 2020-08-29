package com.zack.uicomponent;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.zack.uicomponent.widget.StickView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   /*     final StickView stickView = findViewById(R.id.stack_view);
        stickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this,"StickView", Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.tv_move).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stickView.smoothScrollTo(500,500);
            }
        });*/
        findViewById(R.id.stack_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.ipc.aidl.RemoteService_remote");
                sendBroadcast(intent,"com.example.ipc.aidl.RemoteReceiver");
            }
        });
        listView = findViewById(R.id.lsitview);
        listView.setAdapter(createAdapter() );
        Log.e(this.getClass().getName(),"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(this.getClass().getName(),"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(this.getClass().getName(),"onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(this.getClass().getName(),"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(this.getClass().getName(),"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(this.getClass().getName(),"onStop");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(this.getClass().getName(),"onConfigurationChanged");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(this.getClass().getName(),"onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(this.getClass().getName(),"onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(this.getClass().getName(),"onRestoreInstanceState");
    }

    private android.widget.ListAdapter createAdapter() {
        List<String> datas = new ArrayList<>();
        for (int i=0;i<50;i++){
            datas.add("name : " + i);
        }
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, datas );

    }


}
