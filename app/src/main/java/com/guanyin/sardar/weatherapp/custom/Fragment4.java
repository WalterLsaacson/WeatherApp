package com.guanyin.sardar.weatherapp.custom;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.guanyin.sardar.weatherapp.R;

import java.util.ArrayList;

public class Fragment4 extends Fragment {

    View view;
    ArrayList<Bitmap> bitmaps;
    ArrayList<String> themes;
    MeListAdapter meListAdapter;
    ListView meList;

    private String TAG = "Fragment4";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        initData();
        view = inflater.inflate(R.layout.me_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        meList = (ListView) view.findViewById(R.id.me_content);
        meList.setAdapter(meListAdapter);
    }

    private void initData() {
        bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable
                .ic_me_login));
        bitmaps.add(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable
                .ic_me_theme));
        bitmaps.add(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable
                .ic_me_app));
        bitmaps.add(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable
                .ic_me_welfare));
        bitmaps.add(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable
                .ic_me_xiaobing));
        bitmaps.add(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable
                .ic_me_historyweather));
        bitmaps.add(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable
                .ic_me_news));
        bitmaps.add(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable
                .ic_me_setting));
        bitmaps.add(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable
                .ic_me_web));

        themes = new ArrayList<>();
        themes.add("登录");
        themes.add("天气主题");
        themes.add("最美应用");
        themes.add("红包福利");
        themes.add("微软小冰");
        themes.add("历史天气");
        themes.add("大事件");
        themes.add("设置");
        themes.add("官网");

        meListAdapter = new MeListAdapter(getActivity(), bitmaps, themes);

    }
}
