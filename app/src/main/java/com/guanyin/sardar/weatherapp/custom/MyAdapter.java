package com.guanyin.sardar.weatherapp.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanyin.sardar.weatherapp.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<DayInfo> dayInfo;

    public MyAdapter(Context context, ArrayList dayInfo) {
        this.context = context;
        this.dayInfo = dayInfo;
    }

    @Override
    public int getCount() {
        return dayInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return dayInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.day_info, null);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView type = (TextView) convertView.findViewById(R.id.type);
            TextView temperature = (TextView) convertView.findViewById(R.id.temperature);
            ImageView iv_type = (ImageView) convertView.findViewById(R.id.iv_type);
            viewHolder = new ViewHolder();
            viewHolder.date = date;
            viewHolder.iv_type = iv_type;
            viewHolder.temperature = temperature;
            viewHolder.type = type;
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        TextView date = viewHolder.date;
        date.setText(dayInfo.get(position).date);
        TextView type = viewHolder.type;
        type.setText(dayInfo.get(position).type);
        TextView temperature = viewHolder.temperature;
        temperature.setText(dayInfo.get(position).low);
        ImageView iv_type = viewHolder.iv_type;
        switch (dayInfo.get(position).type) {
            case "阴":
                break;
        }
        return convertView;
    }

    class ViewHolder {
        TextView date;
        TextView type;
        TextView temperature;
        ImageView iv_type;
    }
}
