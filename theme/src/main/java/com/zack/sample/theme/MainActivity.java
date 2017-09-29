package com.zack.sample.theme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<String> list;
    private boolean isGrid = false;
    RecyclerView.ItemDecoration itemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        findViewById(R.id.btn_change).setOnClickListener(this);

        list = new ArrayList<>();
        for (int i=0;i<100;i++){
            list.add("item"+i);
        }

        adapter = new MyRecyclerViewAdapter(list);
        //布局摆放：线性
        //LinearLayoutManager:默认垂直
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //reverseLayout When set to true, layouts from end to start.
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        //recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //adapter = new MyStaggeredRecyclerViewAdapter(list);
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(10,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change:
                onChange();
        }
    }

    private void onChange() {
        if (itemDecoration!=null)
            recyclerView.removeItemDecoration(itemDecoration);
        if (isGrid){
            itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        }else{
            itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL);
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
           // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        }
        isGrid = !isGrid;
    }
}
