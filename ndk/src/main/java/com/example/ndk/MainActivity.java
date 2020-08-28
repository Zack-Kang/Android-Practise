package com.example.ndk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    static {
        System.loadLibrary("native-lib");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_call_jni).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)findViewById(R.id.tv_message)).setText(SayHello());
            }
        });

        findViewById(R.id.tv_call_dynamic_jni).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)findViewById(R.id.tv_message)).setText(dynamicMedth("动态注册"));
            }
        });

        findViewById(R.id.tv_create_obj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persion[] persions = createJavaObjects();
                StringBuilder stringBuilder = new StringBuilder();
                for (Persion persion : persions){
                    stringBuilder.append(persion);
                }
                ((TextView)findViewById(R.id.tv_message)).setText(stringBuilder.toString());
            }
        });
    }

    private native static Persion[] createJavaObjects();

    public native String staticMethod();

    public native String dynamicMedth(String string);
}
