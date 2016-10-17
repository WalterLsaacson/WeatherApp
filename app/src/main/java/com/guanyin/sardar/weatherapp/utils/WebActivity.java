package com.guanyin.sardar.weatherapp.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.guanyin.sardar.weatherapp.R;

public class WebActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initView();

        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");

        // uri为空就加载百度的首页
        if (TextUtils.isEmpty(uri))
            uri = "www.baidu.com";

        webView.loadUrl(uri);

    }

    private void initView() {
        webView = (WebView) findViewById(R.id.my_web);
    }
}
