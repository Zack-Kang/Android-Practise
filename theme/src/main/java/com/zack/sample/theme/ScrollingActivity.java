package com.zack.sample.theme;

import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {
    SwipeRefreshLayout refreshLayout;
    ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.btn_alterDialog).setOnClickListener(this);
        findViewById(R.id.btn_popuwindow).setOnClickListener(this);
        findViewById(R.id.btn_popuMenu).setOnClickListener(this);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setSize(SwipeRefreshLayout.LARGE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新加载数据

            }
        });

        refreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW);
        //设置进度条的背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.GRAY);
        //设置下拉多少距离开始刷新
        refreshLayout.setDistanceToTriggerSync(70);

        //popuWindow数据
        String[] items = {"条目1","条目2","条目3","条目4","条目5","条目6"};
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings1) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onShowAlterDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("这是title");
        builder.setMessage("这是message");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_alterDialog:
            onShowAlterDialog();
            break;
            case R.id.btn_popuwindow:
                onShowPopuWindow(view);
                break;
            case R.id.btn_popuMenu:
                onShowPopuMenu(view);
        }
    }

    private void onShowPopuMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_scrolling,popupMenu.getMenu());
        popupMenu.show();
    }

    private void onShowPopuWindow(View view) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(adapter);
        //设置锚点，弹出的位置是相对于V的位置
        listPopupWindow.setAnchorView(view);
        listPopupWindow.setWidth(800);
        listPopupWindow.setHeight(500);
        listPopupWindow.show();
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"点了第"+i+"条",Toast.LENGTH_SHORT).show();
                listPopupWindow.dismiss();
            }
        });

    }
}
