package com.guanyin.sardar.weatherapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.guanyin.sardar.weatherapp.custom.Fragment1;
import com.guanyin.sardar.weatherapp.custom.Fragment2;
import com.guanyin.sardar.weatherapp.custom.Fragment3;
import com.guanyin.sardar.weatherapp.custom.Fragment4;
import com.guanyin.sardar.weatherapp.utils.Const;
import com.guanyin.sardar.weatherapp.utils.MyApplication;

public class WeatherActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    // override key down event to implement double click to exit app
    private long exitTime = 0;
    private String TAG = "WeatherActivity";
    private Context context = this;

    // 底部菜单的空间
    RadioGroup menu;
    RadioButton weather;
    RadioButton live;
    RadioButton around;
    RadioButton me;
    Drawable drawable;

    // 内容碎片
    private Fragment1 weatherFragment;
    private Fragment2 liveFragment;
    private Fragment3 aroundFragment;
    private Fragment4 meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setContentFrame();
        setRadioGroup();
    }

    private void setContentFrame() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        weatherFragment = new Fragment1();
        transaction.replace(R.id.content_frame, weatherFragment);
        transaction.commit();
    }

    private void setRadioGroup() {

        menu = (RadioGroup) findViewById(R.id.bottom_menu);
        weather = (RadioButton) findViewById(R.id.weather);
        live = (RadioButton) findViewById(R.id.live);
        around = (RadioButton) findViewById(R.id.around);
        me = (RadioButton) findViewById(R.id.me);

        drawable = getResources().getDrawable(R.drawable.selector_weather);
        if (drawable != null) {
            drawable.setBounds(0, 3, 50, 50);
        }
        weather.setCompoundDrawables(null, drawable, null, null);

        drawable = getResources().getDrawable(R.drawable.selector_live);
        if (drawable != null) {
            drawable.setBounds(0, 3, 50, 50);
        }
        live.setCompoundDrawables(null, drawable, null, null);

        drawable = getResources().getDrawable(R.drawable.selector_around);
        if (drawable != null) {
            drawable.setBounds(0, 3, 50, 50);
        }
        around.setCompoundDrawables(null, drawable, null, null);

        drawable = getResources().getDrawable(R.drawable.selector_me);
        if (drawable != null) {
            drawable.setBounds(0, 3, 50, 50);
        }
        me.setCompoundDrawables(null, drawable, null, null);


        menu.check(R.id.weather);// 默认勾选首页，初始化时候让首页默认勾选

        // 设置切换fragment
        weather.setOnCheckedChangeListener(this);
        live.setOnCheckedChangeListener(this);
        around.setOnCheckedChangeListener(this);
        me.setOnCheckedChangeListener(this);
    }


    // 重写返回键按钮，实现在两秒内按两次返回键实现退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Const.showToast(WeatherActivity.this, "确定要退出最美天气吗，亲~~~");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.weather:
                    if (null == weatherFragment) {
                        weatherFragment = new Fragment1();
                        transaction.replace(R.id.content_frame, weatherFragment);
                    } else
                        transaction.replace(R.id.content_frame, weatherFragment);
                    break;
                case R.id.live:
                    if (null == liveFragment) {
                        liveFragment = new Fragment2();
                        transaction.replace(R.id.content_frame, liveFragment);
                    } else
                        transaction.replace(R.id.content_frame, liveFragment);
                    break;
                case R.id.around:
                    if (null == aroundFragment) {
                        aroundFragment = new Fragment3();
                        transaction.replace(R.id.content_frame, aroundFragment);
                    } else
                        transaction.replace(R.id.content_frame, aroundFragment);
                    break;
                case R.id.me:
                    if (null == meFragment) {
                        meFragment = new Fragment4();
                        transaction.replace(R.id.content_frame, meFragment);
                    } else
                        transaction.replace(R.id.content_frame, meFragment);
                    break;
            }
            transaction.commit();
        }
    }
}
