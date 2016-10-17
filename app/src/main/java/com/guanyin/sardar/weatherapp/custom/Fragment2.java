package com.guanyin.sardar.weatherapp.custom;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RadioGroup;

import com.guanyin.sardar.weatherapp.R;
import com.guanyin.sardar.weatherapp.utils.Const;
import com.guanyin.sardar.weatherapp.utils.MyApplication;

import java.util.ArrayList;

public class Fragment2 extends Fragment implements CompoundButton
        .OnCheckedChangeListener, View.OnClickListener {

//    private String TAG = "Fragment2";

    View view;
    CheckBox btn_photo_menu;
    RadioGroup news;

    // 动画效果的三个按钮
    Button btn_photo;
    Button btn_picture;
    Button btn_video;

    // 设置图片显示
    GridView galleryView;
    MyGalleryAdapter galleryAdapter;
    ArrayList<Bitmap> bitmaps = new ArrayList<>();
    ArrayList<String> locations = new ArrayList<>();

    MyApplication app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.live_fragment, container, false);
        initData();

        initView(view);
        return view;
    }


    private void initView(View view) {
        btn_photo_menu = (CheckBox) view.findViewById(R.id.btn_menu_photo);
        btn_photo_menu.setOnCheckedChangeListener(this);

        news = (RadioGroup) view.findViewById(R.id.news);
        news.check(R.id.real_time);

        btn_photo = (Button) view.findViewById(R.id.photo);
        btn_picture = (Button) view.findViewById(R.id.picture);
        btn_video = (Button) view.findViewById(R.id.video);

        btn_photo.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_video.setOnClickListener(this);

        galleryView = (GridView) view.findViewById(R.id.gallery);
        galleryView.setAdapter(galleryAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        if (app == null) {
            app = MyApplication.getInstance();
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nwu);

        bitmaps.clear();
        locations.clear();
        bitmaps.add(bitmap);
        locations.add("西北大学");
        galleryAdapter = new MyGalleryAdapter(getActivity(), bitmaps, locations);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // 动画集合
        // TODO 放弃位移动画
        AnimationSet animationSet = new AnimationSet(true);

        // 位移动画的基准点 以及 声明
//        float standardPointX = btn_photo_menu.getLeft();
//        float standardPointY = btn_photo_menu.getTop();
//
//        float photoX = btn_photo.getLeft();
//        float photoY = btn_photo.getTop();
//
//        float videoX = btn_video.getLeft();
//        float videoY = btn_video.getTop();
//
//        float pictureX = btn_picture.getLeft();
//        float pictureY = btn_picture.getTop();
//
//        TranslateAnimation translateAnimation;
        // 旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation
                .RELATIVE_TO_SELF, 0.5F, RotateAnimation.RELATIVE_TO_SELF, 0.5F);
        rotateAnimation.setDuration(500);
        animationSet.addAnimation(rotateAnimation);

        // 渐进动画
        AlphaAnimation alphaAnimation;
        // 缩放动画
        ScaleAnimation scaleAnimation;

        if (isChecked) {
            // 显示动画效果
            btn_photo.setVisibility(View.VISIBLE);
            btn_picture.setVisibility(View.VISIBLE);
            btn_video.setVisibility(View.VISIBLE);

            alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(500);

            animationSet.addAnimation(alphaAnimation);

            scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5F,
                    Animation.RELATIVE_TO_SELF, 0.5F);
            scaleAnimation.setDuration(500);
            animationSet.addAnimation(scaleAnimation);


            // 边旋转边位移
            btn_video
                    .startAnimation(animationSet);
            btn_photo
                    .startAnimation(animationSet);
            btn_picture
                    .startAnimation(animationSet);

        } else {
            // 收回动画效果

            alphaAnimation = new AlphaAnimation(1, 0);
            alphaAnimation.setDuration(500);

            animationSet.addAnimation(alphaAnimation);

            scaleAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5F,
                    Animation.RELATIVE_TO_SELF, 0.5F);
            scaleAnimation.setDuration(500);
            animationSet.addAnimation(scaleAnimation);
            btn_photo
                    .startAnimation(animationSet);
            btn_picture
                    .startAnimation(animationSet);
            btn_video
                    .startAnimation(animationSet);

            btn_photo.setVisibility(View.GONE);
            btn_picture.setVisibility(View.GONE);
            btn_video.setVisibility(View.GONE);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                btn_photo_menu.setChecked(false);
                Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (photoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(photoIntent, Const.REQUEST_PHOTO_CAPTURE);
                    break;
                }
            case R.id.video:
                btn_photo_menu.setChecked(false);
                break;
            case R.id.picture:
                btn_photo_menu.setChecked(false);
                Intent pictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                        .EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureIntent, Const.REQUEST_IMAGE_CAPTURE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_PHOTO_CAPTURE && resultCode == -1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmaps.add(imageBitmap);
            locations.add(app.address + extras.toString());
            galleryAdapter.notifyDataSetChanged();
        }
        if (requestCode == Const.REQUEST_IMAGE_CAPTURE && resultCode == -1 && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            String picturePath = "";
            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
            }

            bitmaps.add(BitmapFactory.decodeFile(picturePath));
            locations.add(app.address);
            galleryAdapter.notifyDataSetChanged();
        }
    }
}
