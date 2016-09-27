package com.guanyin.sardar.weatherapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.guanyin.sardar.weatherapp.utils.MyApplication;

import java.util.Timer;
import java.util.TimerTask;

public class EntranceActivity extends AppCompatActivity {

    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  TODO: 2016/9/26  不能实现进入app时全屏  进入后透明状态栏
        // 去除title
        // 这一句失效，于是在application的style中去除了title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_entrance);


        if (null == application) {
            application = MyApplication.getInstance();
        }

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(EntranceActivity.this, WeatherActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, 2000);

    }
}
