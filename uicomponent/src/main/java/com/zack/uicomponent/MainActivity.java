package com.zack.uicomponent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        listView = findViewById(R.id.lsitview);
        listView.setAdapter(createAdapter() );
    }

    private android.widget.ListAdapter createAdapter() {
        List<String> datas = new ArrayList<>();
        for (int i=0;i<50;i++){
            datas.add("name : " + i);
        }
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, datas );

    }


}
