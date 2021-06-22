package com.taimin.weatherforecast.Adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.taimin.weatherforecast.fragment.wea_fragment;

import java.util.List;

public class WeaViewPagerAdapter extends FragmentPagerAdapter {

    private List<wea_fragment> mlist;


    public void setData(List<wea_fragment> list){
        this.mlist = list;
        Log.i("fragSetData","set");
        notifyDataSetChanged();
    }

    public WeaViewPagerAdapter(FragmentManager fm , List<wea_fragment> list) {
        super(fm);
        this.mlist = list;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("getItem", String.valueOf(mlist));
        return mlist.get(position);//显示第几个页面

    }

    @Override
    public int getCount() {
        return mlist.size();
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return WeaViewPagerAdapter.POSITION_NONE;
//    }
    @Override
    public long getItemId(int position) {
//        super.getItemId(position);
        //重写getItemId方法，把super方法去除换成新对象的hashcode
        return mlist.get(position).hashCode();
    }
}
