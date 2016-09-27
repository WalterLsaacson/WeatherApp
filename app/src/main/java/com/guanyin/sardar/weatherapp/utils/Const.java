package com.guanyin.sardar.weatherapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.guanyin.sardar.weatherapp.custom.MyToast;

public class Const {

    // http://api.map.baidu.com/telematics/v3/weather？location=南昌&output=json&ak=你的API
    // Key&mcode=你的数字签名SHA1;com.example.administrator.jsontest（包名）


    // URL url = new URL("http://api.map.baidu
    // .com/telematics/v3/weather?location=南昌&output=json&ak=8ixCCFzlBB617YX7tONI2P5B&mcode=1C:6B
    // :42:33:E8:A6:DC:A2:11:6E:26:EC:84:BD:42:E3:8E:6B:57:9A;com.example.administrator.jsontest");
    public static final boolean debug = true;
    public static final String httpString = "http://api.map.baidu" +
            ".com/telematics/v3/weather？location=";
    // 使用时后边加城市名
    public static final String lastString =
            "&output=json&ak=LffHEyjROkWn0H6d6VsWILWAkjNPmCXz&mcode=27:D0:E5:BE:3E:D1:93:DA:80:FF" +
                    ":62:C9:DA:5A:FA:7F:E3:F6:44:A7;com.guanyin.sardar.weatherapp";

    public static final void showToast(Context context, String contents) {
        if (debug) {
            MyToast.makeshow(context, contents, Toast.LENGTH_SHORT);
        }

    }


    public static void log(String TAG, String message) {
        Log.e(TAG, message);
    }

    public static int decimalToBinary(String decimalString) {
        int decimal = Integer.parseInt(decimalString);
        int stack = 0;
        while (decimal > 0) {
            stack = stack * 10 + decimal % 2;
            decimal /= 2;
        }
        return stack;
    }


}
