package com.guanyin.sardar.weatherapp.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;


public class MyApplication extends Application {

    private static MyApplication instance;


    // 最近一次定位的经纬度
    public double latitude = 0;
    public double longitude = 0;
    public String city;
    public String address;

    public ArrayList<Bitmap> bitmaps = new ArrayList<>();

    public SharedPreferences sharedPreferences;

    // 定位
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    public void onCreate() {

        super.onCreate();

        instance = this;

        init();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private void init() {
        sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        long lasttime = sharedPreferences.getLong("lasttime", 0);
        if (lasttime == 0) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("lasttime", System.currentTimeMillis());
            editor.apply();
        }
        // 将需要缓存的数据存起来
        if (null == sharedPreferences.getString("defaultCity", " ")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("defaultCity", "北京");
            editor.putString("defaultAddress", "清华大学");
            editor.putString("defaultTemperature", "25°");
            editor.putString("defaultType", "阴");
            editor.apply();
        }
        // 开启百度地图
        mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener); // 注册监听函数

        initLocation();
        mLocationClient.start();

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//
        // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 300000;
        // requestLocation用于再次请求定位
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//
        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
            StringBuilder sb = new StringBuilder(256);

            sb.append("\ntime : ");
            sb.append(location.getTime());

            sb.append("\ncity : ");
            sb.append(location.getCity());
            city = location.getCity().substring(0, location.getCity().length() -
                    1);


            sb.append("\nlatitude : ");
            latitude = location.getLatitude();
            sb.append(location.getLatitude());

            sb.append("\nlongitude : ");
            sb.append(location.getLongitude());
            longitude = location.getLongitude();

            sb.append("\naddress : ");
            sb.append(location.getAddrStr());
            address = location.getAddrStr().replace("中国", "");

            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                // 运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            String TAG = "MyApplication";
            Log.e(TAG, sb.toString());

        }
    }

}
