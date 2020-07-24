package com.zack.imageloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                 MainActivity.this.startActivity(intent);
                 //main();

             }
         });
    }

    public  void main(){
        Gson gson = new Gson();
        User user1 = new User("name_1","pwd_1",101,false, "address_1");
        User user2 = new User("name_2","pwd_2",102,false, "address_2");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        User user = new User("name_","pwd_",100,false, "address_", users);
        Log.e("objson",gson.toJson(user1));
        //Log.e("objson",gson.toJson(users));

        ((TextView)findViewById(R.id.textView)).setText(gson.toJson(user));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(this.getClass().getName(), "onNewIntent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(this.getClass().getName(), "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getName(), "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getName(), "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getName(), "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getName(), "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(this.getClass().getName(), "onPause");
    }
}
