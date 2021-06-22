package com.taimin.weatherforecast.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taimin.weatherforecast.R;
import com.taimin.weatherforecast.bean.CityBean;

import java.util.List;


public class HotCityGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<CityBean> mCities;

    public HotCityGridAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<CityBean> data) {
        mCities = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public CityBean getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_hot_city_gridview, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        } else {
            holder = (HotCityViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position).getCityName());
        return view;
    }

    public static class HotCityViewHolder {
        TextView name;
    }
}
