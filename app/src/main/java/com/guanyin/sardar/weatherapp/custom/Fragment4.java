package com.guanyin.sardar.weatherapp.custom;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanyin.sardar.weatherapp.R;
import com.guanyin.sardar.weatherapp.utils.MyApplication;

public class Fragment4 extends Fragment {

    private String TAG = "Fragment4";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment, container, false);
    }
}
