package com.guanyin.sardar.weatherapp.custom;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanyin.sardar.weatherapp.R;
import com.guanyin.sardar.weatherapp.utils.Const;
import com.guanyin.sardar.weatherapp.utils.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Fragment2 extends Fragment {

    private String TAG = "Fragment2";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.live_fragment,container,false);
    }
}
