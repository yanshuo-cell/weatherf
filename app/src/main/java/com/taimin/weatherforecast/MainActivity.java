package com.taimin.weatherforecast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.taimin.weatherforecast.Adapter.ForecastAdapter;
import com.taimin.weatherforecast.Adapter.WeaViewPagerAdapter;
import com.taimin.weatherforecast.bean.CityBean;
import com.taimin.weatherforecast.bean.DataBean;
import com.taimin.weatherforecast.bean.WeatherBean;
import com.taimin.weatherforecast.db.SQLiteHelper;
import com.taimin.weatherforecast.fragment.FragmentFactory;
import com.taimin.weatherforecast.fragment.FragmentID;
import com.taimin.weatherforecast.fragment.wea_fragment;
import com.taimin.weatherforecast.utils.NetUtils;
import com.taimin.weatherforecast.utils.ParserJson;
import com.taimin.weatherforecast.utils.SetForeCastBg;
import com.taimin.weatherforecast.utils.WeatherShare;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FragmentManager fm;
    FragmentTransaction ft;
    LinearLayout bar1;
    LinearLayout bar3;
    LinearLayout bar2;
    ImageView bar1Img;
    ImageView bar2Img;
    ImageView bar3Img;
    TextView bar1Text;
    TextView bar2Text;
    TextView bar3Text;
    Fragment mainfrag;
    Fragment mefrag;
    Fragment curfrag;
    Fragment radarfrag;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setFrag();

    }

    private void initView() {
        bar1 = findViewById(R.id.bar_1);
        bar2 = findViewById(R.id.bar_2);
        bar3 = findViewById(R.id.bar_3);
        bar1.setOnClickListener(this);
        bar2.setOnClickListener(this);
        bar3.setOnClickListener(this);
        bar1Img = findViewById(R.id.bar_1_img);
        bar2Img = findViewById(R.id.bar_2_img);
        bar3Img = findViewById(R.id.bar_3_img);
        bar1Text = findViewById(R.id.bar_1_text);
        bar2Text = findViewById(R.id.bar_2_text);
        bar3Text = findViewById(R.id.bar_3_text);
        changeStyle(R.id.bar_1);
    }

    private void changeStyle(int id){
        bar1Img.setImageResource(R.drawable.icon_bar_1);
        bar1Text.setTextColor(getResources().getColor(R.color.colorUnSelect));
        bar2Img.setImageResource(R.drawable.icon_bar_2);
        bar2Text.setTextColor(getResources().getColor(R.color.colorUnSelect));
        bar3Img.setImageResource(R.drawable.icon_bar_3);
        bar3Text.setTextColor(getResources().getColor(R.color.colorUnSelect));
        switch (id){
            case R.id.bar_1:

                bar1Img.setImageResource(R.drawable.icon_bar_1_select);
                bar1Text.setTextColor(getResources().getColor(R.color.colorSelect));
                break;
            case R.id.bar_2:

                bar2Img.setImageResource(R.drawable.icon_bar_2_select);
                bar2Text.setTextColor(getResources().getColor(R.color.colorSelect));
                break;
            case R.id.bar_3:
                bar3Img.setImageResource(R.drawable.icon_bar_3_select);
                bar3Text.setTextColor(getResources().getColor(R.color.colorSelect));
        }
    }

    private void setFrag() {
        mainfrag = FragmentFactory.createById(FragmentID.MAIN_FRAGEMENT);
        mefrag = FragmentFactory.createById(FragmentID.ME_FRAGMENT);
        radarfrag = FragmentFactory.createById(FragmentID.RADAR_FRAGMENT);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.cl_content, mainfrag);
        ft.add(R.id.cl_content, mefrag);
        ft.add(R.id.cl_content,radarfrag);
        ft.hide(mefrag);
        ft.hide(radarfrag);
        curfrag = mainfrag;
        ft.commit();
    }


    @Override
    public void onClick(View v) {
        ft = fm.beginTransaction();
        switch (v.getId()){
            case R.id.bar_1:
                ft.hide(curfrag).show(mainfrag);
                curfrag = mainfrag;
                Log.i("bar1","bar1");
                changeStyle(v.getId());
                break;
            case R.id.bar_2:
                ft.hide(curfrag).show(radarfrag);
                curfrag = radarfrag;
                Log.i("bar2","bar2");
                changeStyle(v.getId());
                break;
            case R.id.bar_3:
                ft.hide(curfrag).show(mefrag);
                curfrag = mefrag;
                changeStyle(v.getId());
                break;
        }
        ft.commit();
    }
}
