package com.guanyin.sardar.weatherapp.custom;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.guanyin.sardar.weatherapp.R;
import com.guanyin.sardar.weatherapp.utils.Const;
import com.guanyin.sardar.weatherapp.utils.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Fragment1 extends Fragment implements View.OnClickListener {

    private String TAG = "Fragment1";
    private MyApplication application;

    Context context = this.getActivity();

    View view;
    ImageView addCity_iv;
    ImageView share_iv;
    TextView city_name;
    TextView address;
    TextView update_time;
    TextView defaultTemp;
    TextView defaultType;
    ListView multiForecast;
    MyAdapter myAdapter;

    String currentTemperature;
    String locationCity;
    String hint;
    String currentType;

    ArrayList<DayInfo> dayInfo;

    String[] info;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    info = (String[]) msg.obj;
                    city_name.setText(info[0]);
                    address.setText(info[1]);
                    update_time.setText(info[2]);
                    defaultTemp.setText(info[3]);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.weather_fragment, container, false);
        initData();

        initView(view);
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

    private void initView(View view) {
        addCity_iv = (ImageView) view
                .findViewById(R.id.add_city);
        addCity_iv.setOnClickListener(this);

        share_iv = (ImageView) view.findViewById(R.id.share_iv);
        share_iv.setOnClickListener(this);

        // 设置顶部的地址信息
        city_name = (TextView) view.findViewById(R.id.city_name);
        city_name.setText(application.sharedPreferences.getString("defaultCity", ""));

        address = (TextView) view.findViewById(R.id.address);
        address.setText(application.sharedPreferences.getString("defaultAddress", ""));

        update_time = (TextView) view.findViewById(R.id.update_time);

        defaultTemp = (TextView) view.findViewById(R.id.defaultTemp);
        defaultTemp.setText(application.sharedPreferences.getString("defaultTemperature", ""));

        defaultType = (TextView) view.findViewById(R.id.defaultType);
        defaultType.setText(application.sharedPreferences.getString("defaultType", ""));

        multiForecast = (ListView) view.findViewById(R.id.multiForecast);
        multiForecast.setAdapter(myAdapter);


        long lastTime = application.sharedPreferences.getLong("lasttime", 0);
        long currentTime = System.currentTimeMillis();
        // 得到时间间隔的秒数
        long timeSpan = (currentTime - lastTime) / 1000;

        String tempUpdateTime;
        if (timeSpan / (3600 * 24 * 30) != 0) {
            tempUpdateTime = timeSpan / (3600 * 24 * 30) + "月前刷新";
        } else {
            if (timeSpan / (3600 * 24) != 0) {
                tempUpdateTime = timeSpan / (3600 * 24) + "天前刷新";
            } else {
                if (timeSpan / 3600 != 0) {
                    tempUpdateTime = timeSpan / 3600 + "小时前刷新";
                } else {
                    if (timeSpan / 60 != 0) {
                        tempUpdateTime = timeSpan / 60 + "分钟前刷新";
                    } else {
                        tempUpdateTime = "刚刚刷新";
                    }
                }
            }
        }
        update_time.setText(tempUpdateTime);
    }

    private void initData() {
        // TODO: 2016/10/4  需要让整个activity进行滑动，设置listview的=监听事件滑动到底就让radiogroup
        // 消失，滑动就刷新，设置两个listview，进行趋势和天气的设置，分型的自定义view，弄一个自定义浏览器
        dayInfo = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {

            DayInfo tempDay = new DayInfo();
            tempDay.date = "星期一";
            tempDay.low = "15°";
            tempDay.high = "25°";
            tempDay.fengli = "大风";
            tempDay.fengxiang = "西南风";
            tempDay.type = "阴";

            dayInfo.add(tempDay);
        }
        myAdapter = new MyAdapter(getActivity(), dayInfo);
    }


    private void sendRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try {
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

                    parseJSONObjectOrJSONArray(response.toString());

                    // 在每次获取网络数据之后刷新时间间隔
                    freshenTime();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private void freshenTime() {

        Message msg = new Message();
        msg.what = 0;
        info = new String[4];
        info[0] = application.city;
        info[1] = application.address;
        info[2] = "刚刚刷新";
        info[3] = currentTemperature + "°";
        msg.obj = info;
        handler.sendMessage(msg);
        long currentTime = System.currentTimeMillis();
        SharedPreferences.Editor editor = application.sharedPreferences.edit();
        editor.putLong("lasttime", currentTime);
        editor.putString("defaultCity", application.city);
        editor.putString("defaultAddress", application.address);
        editor.putString("defaultTemperature", currentTemperature + "°");
        editor.putString("defaultType", currentType);
        editor.apply();
    }

    private void parseJSONObjectOrJSONArray(String s) {

        try {
            JSONObject originJson = new JSONObject(s);
            String desc = originJson.getString("desc");
            if (desc.equals("OK")) {
                Const.log(TAG, "获取成功");

                JSONObject dataJson = originJson.getJSONObject("data");
                Const.log("datajson", dataJson.toString());

                currentTemperature = dataJson.getString("wendu");
                hint = dataJson.getString("ganmao");
                locationCity = dataJson.getString("city");

                JSONArray forecastArray = dataJson.getJSONArray("forecast");
                Const.log("预报数组", forecastArray.toString());

                JSONObject tempJson;
                dayInfo.clear();
                for (int i = 0; i < forecastArray.length(); i++) {

                    DayInfo tempDay = new DayInfo();
                    tempJson = forecastArray.getJSONObject(i);
                    Const.log("tempJson", tempJson.toString());
                    tempDay.date = tempJson.getString("date");
                    tempDay.low = tempJson.getString("low");
                    tempDay.high = tempJson.getString("high");
                    tempDay.fengli = tempJson.getString("fengli");
                    tempDay.fengxiang = tempJson.getString("fengxiang");
                    tempDay.type = tempJson.getString("type");

                    dayInfo.add(tempDay);
                }

            } else {
                Const.showToast(context, "获取天气信息失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Const.log(TAG, "解析完成");
        Const.log(TAG, dayInfo.size() + "");
        for (DayInfo day : dayInfo
                ) {
            Const.log("array", "date" + day.date + " low" + day.low + " high" +
                    day.high + " fengli" + day.fengli + " fengxiang" +
                    day.fengxiang);
        }
        currentType = dayInfo.get(0).type;
        multiForecast.deferNotifyDataSetChanged();
    }

    //分享文字
    private void shareWeatherInfo() {
        // 弹出pop window
        final PopupWindow shareWindow;
        final View popWindow_view = getActivity().getLayoutInflater().inflate(R.layout
                .popwindows, null);
        shareWindow = new PopupWindow(popWindow_view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        shareWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);

        // 设置pop window各个空间的监听事件
        popWindow_view.findViewById(R.id.empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWindow.dismiss();
            }
        });
        popWindow_view.findViewById(R.id.share_qq).setOnClickListener(this);
        popWindow_view.findViewById(R.id.share_wechat).setOnClickListener(this);
        popWindow_view.findViewById(R.id.share_pyq).setOnClickListener(this);
        popWindow_view.findViewById(R.id.share_weibo).setOnClickListener(this);
        popWindow_view.findViewById(R.id.share_sms).setOnClickListener(this);
        popWindow_view.findViewById(R.id.share_more).setOnClickListener(this);

        shareWindow.dismiss();

    }

    @Override
    public void onClick(View v) {
        String shareString = "";
        switch (v.getId()) {
            case R.id.add_city:
                break;
            case R.id.share_iv:
                // TODO: 2016/9/28 这里接入各种sdk实现分享的功能
                if (null == dayInfo.get(0)) {
                    Const.showToast(context, "请先刷新天气状况哦~~~");
                } else {
                    shareString = "今天" + dayInfo.get(0).date + "," + locationCity + dayInfo
                            .get(0).type + ",最"
                            + dayInfo.get(0).low + ",最" + dayInfo.get(0).high + ",当前温度：" +
                            currentTemperature + "℃";
                    shareWeatherInfo();
                }
                break;
            case R.id.share_more:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
                shareIntent.setType("text/plain");

                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
        }
    }
}
