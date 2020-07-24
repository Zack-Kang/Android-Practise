package com.zack.imageloader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                SecondActivity.this.startActivity(intent);
                //textView.getBackground().setAlpha(50);
            }
        });

        findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,SecondActivity.class);
                SecondActivity.this.startActivity(intent);
            }
        });
        Log.i(this.getClass().getName(), this + " onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getName(), this + " onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getName(), this + " onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(this.getClass().getName(), this + " onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getName(), this + " onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getName(), this + " onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(this.getClass().getName(), this + " onRestart");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(this.getClass().getName(), this + " onNewIntent");
    }
}
