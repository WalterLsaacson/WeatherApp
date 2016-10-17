package com.guanyin.sardar.weatherapp.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanyin.sardar.weatherapp.R;
import com.guanyin.sardar.weatherapp.utils.WebActivity;

import java.util.ArrayList;

public class MeListAdapter extends BaseAdapter {

    ArrayList<Bitmap> bitmaps;
    ArrayList<String> themes;
    Context context;

    public MeListAdapter(Context context, ArrayList<Bitmap> bitmaps,
                         ArrayList<String> themes) {
        this.bitmaps = bitmaps;
        this.themes = themes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.me_item, null);
            TextView theme = (TextView) convertView.findViewById(R.id.me_item_text);
            ImageView picture = (ImageView) convertView.findViewById(R.id.me_item_picture);
            viewHolder = new ViewHolder();
            viewHolder.picture = picture;
            viewHolder.theme = theme;
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        TextView theme = viewHolder.theme;
        theme.setText(themes.get(position));
        ImageView picture = viewHolder.picture;
        picture.setImageBitmap(bitmaps.get(position));
        switch (position) {
            case 8:
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("uri", "www.sougou.com");
                        context.startActivity(intent);
                    }
                });

                break;
        }
        return convertView;
    }

    class ViewHolder {
        TextView theme;
        ImageView picture;
    }
}
