package com.guanyin.sardar.weatherapp.custom;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.guanyin.sardar.weatherapp.R;

public class Fragment2 extends Fragment implements CompoundButton
        .OnCheckedChangeListener {

//    private String TAG = "Fragment2";

    View view;
    CheckBox btn_photo_menu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.live_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_photo_menu = (CheckBox) view.findViewById(R.id.btn_menu_photo);
        btn_photo_menu.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // 显示动画效果

        } else {
            // 收回动画效果

        }
    }

}
