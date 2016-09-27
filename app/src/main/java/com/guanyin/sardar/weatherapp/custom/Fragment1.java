package com.guanyin.sardar.weatherapp.custom;

import android.app.Fragment;
import android.os.Message;
import android.util.Log;

import com.guanyin.sardar.weatherapp.utils.Const;
import com.guanyin.sardar.weatherapp.utils.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Fragment1 extends Fragment {

    private String TAG = "Fragment1";
    private MyApplication application = (MyApplication) getActivity().getApplication();

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    String result = (String) msg.obj;
//                    tv.setText(result);
//                    break;
//            }
//        }
//    };


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weather);
//
//
//        if (application == null) {
//            application = MyApplication.getInstance();
//        }
//
//        tv = (TextView) findViewById(R.id.dataDisplay);
//
//        findViewById(R.id.catchWeatherInfo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.catchWeatherInfo:
//                        Const.log(TAG, "获取网络数据");
//                        Const.showToast(context, "获取网络数据");
//                        sendRequestWithHttpClient();
//                        break;
//                }
//            }
//        });
//    }


    private void sendRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection connection;
                try {
                    URL url = new URL(Const.httpString + application.city + Const.lastString);
                    Const.log(TAG, url.toString());
                    connection = (HttpsURLConnection) url.openConnection();
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
                    Log.i("TAG", response.toString());
                    Const.log(TAG, response.toString());
//                    parseJSONObjectOrJSONArray(response.toString());

                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = response.toString();
//                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
