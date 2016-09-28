package com.guanyin.sardar.weatherapp.custom;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanyin.sardar.weatherapp.R;
import com.guanyin.sardar.weatherapp.utils.Const;
import com.guanyin.sardar.weatherapp.utils.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Fragment1 extends Fragment {

    private String TAG = "Fragment1";
    private MyApplication application;

    View view;
    ImageView imageView;
    TextView city_name;
    TextView address;
    TextView update_time;

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.weather_fragment, container, false);
        imageView = (ImageView) view
                .findViewById(R.id.add_city);
        imageView.setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // 设置顶部的地址信息
        city_name = (TextView) view.findViewById(R.id.city_name);
        city_name.setText(application.city);
        address = (TextView) view.findViewById(R.id.address);
        address.setText(application.address);
        update_time = (TextView) view.findViewById(R.id.update_time);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (application == null) {
            application = MyApplication.getInstance();
        }

        // 获取天气信息
        sendRequestWithHttpClient();

    }


    private void sendRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try {
//                    URL url = new URL(Const.httpString + application.city + Const.lastString);
                    URL url = new URL("http://wthrcdn.etouch" +
                            ".cn/weather_mini?city=" + URLEncoder.encode(application.city,
                            "UTF-8"));
                    Const.log(TAG, url.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    InputStream is;
                    is = connection.getInputStream();
                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferReader.readLine()) != null) {
                        response.append(line);
                    }
                    Const.log(TAG, response.toString());

                    // 在每次获取网络数据之后刷新时间间隔
                    freshenTime();


                    parseJSONObjectOrJSONArray(response.toString());

                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = response.toString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void freshenTime() {
        long currentTime = (System.currentTimeMillis() - application.updateTime) / 1000;
        if (currentTime / 3600 != 0) {
            update_time.setText((int) currentTime / 3600 + "小时前发布");
        } else {
            if (currentTime / 3600 != 0) {
                update_time.setText((int) currentTime / 60 + "小时前发布");
            } else {

            }
        }
        application.updateTime = System.currentTimeMillis();
    }

    private void parseJSONObjectOrJSONArray(String s) {
        System.out.println(s);
    }
}
