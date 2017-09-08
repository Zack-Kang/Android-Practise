package com.zack.sample.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    IBaseDao<User> baseDao;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView)findViewById(R.id.tv_content);
        findViewById(R.id.btn_qury).setOnClickListener(this);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);

        baseDao = BaseDaoFactory.getInstance().getDatabaseHelper(UserDao.class,User.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_qury:
                query();
                break;
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_delete:
                delete();
        }
    }

    private void update() {

    }

    private void delete() {

    }

    private void insert() {

    }

    private void query() {

    }
}
