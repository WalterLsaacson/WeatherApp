package com.guanyin.sardar.weatherapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.guanyin.sardar.weatherapp.custom.MyToast;

public class Const {

    public static final boolean debug = true;

    public static final void showToast(Context context, String contents) {
        if (debug) {
            MyToast.makeshow(context, contents, Toast.LENGTH_SHORT);
        }

    }


    public static void log(String TAG, String message) {
        if (debug) {
            Log.e(TAG, message);
        }
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
