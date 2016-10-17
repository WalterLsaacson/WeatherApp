package com.guanyin.sardar.weatherapp.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanyin.sardar.weatherapp.R;

import java.util.ArrayList;

public class MyGalleryAdapter extends BaseAdapter {

    Context context;
    ArrayList<Bitmap> bitmaps;
    ArrayList<String> locations;

    public MyGalleryAdapter(Context context, ArrayList<Bitmap> bitmaps, ArrayList<String>
            locations) {
        this.context = context;
        this.bitmaps = bitmaps;
        this.locations = locations;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.gallery_item, null);
            viewHolder = new ViewHolder();
            viewHolder.picture = (ImageView) convertView.findViewById(R.id.item_picture);
            viewHolder.location = (TextView) convertView.findViewById(R.id.item_location);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        ImageView picture = viewHolder.picture;
        picture.setImageBitmap(bitmaps.get(position));
        TextView location = viewHolder.location;
        location.setText(locations.get(position));
        return convertView;
    }

    class ViewHolder {
        ImageView picture;
        TextView location;
    }
}
