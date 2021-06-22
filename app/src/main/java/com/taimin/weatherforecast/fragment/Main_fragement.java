package com.taimin.weatherforecast.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.taimin.weatherforecast.Adapter.WeaViewPagerAdapter;
import com.taimin.weatherforecast.CityList;
import com.taimin.weatherforecast.MainActivity;
import com.taimin.weatherforecast.R;
import com.taimin.weatherforecast.bean.CityBean;
import com.taimin.weatherforecast.bean.DataBean;
import com.taimin.weatherforecast.db.SQLiteHelper;
import com.taimin.weatherforecast.utils.SetForeCastBg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;

public class Main_fragement extends Fragment implements View.OnClickListener {

    View mview;


    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;
    public AMapLocationClientOption mLocationOption = null;
    //
    String map_district; //地区信息
    TextView curDistrict;
    ViewPager wea_viewpager;
    List<wea_fragment> frgList;

    ArrayList<View> dots;
    ImageView menu_btn;
    ImageView share;
    private static final int MENU_RESULT = 1;
    private static final int SELECT_RESULT = 2;
    private static final int MENU_REQUEST = 1;
    private static final int SELECT_REQUEST = 2;
    List<CityBean> CityList;
    SQLiteHelper mSQLiteHelper;
    LinearLayout main_bg;
    Integer CurItem = 0;
    Integer curPosition = CurItem;
    CityBean mapCity;
    WeaViewPagerAdapter weaViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mview=inflater.inflate(R.layout.main_fragment, container,false);
        return mview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMap();
    }

    private void initData() {
        CityList = new ArrayList<>();
        Log.i("log_initData","mapCity");
        CityList.add(mapCity);
        Log.i("log_initData","mSQLiteHelper");
        mSQLiteHelper = new SQLiteHelper(getContext());
        Log.i("log_initData","CityList");
        CityList.addAll(mSQLiteHelper.query());
        Log.i("log_initData","initData");
    }

    private void changeData(){
        Log.i("ChangeData","change");
        CityList = new ArrayList<CityBean>();
        CityList.add(mapCity);
        mSQLiteHelper = new SQLiteHelper(getContext());
        CityList.addAll(mSQLiteHelper.query());
        frgList = new ArrayList<wea_fragment>();
        for(int i = 0; i < CityList.size(); i++){
            Bundle bundle = new Bundle();
            bundle.putString("map_district", CityList.get(i).getCityName());
            bundle.putInt("id",i);
            wea_fragment frag = (wea_fragment) FragmentFactory.createById(FragmentID.WEA_FRAGEMENT,bundle);
            frgList.add(frag);
        }
        weaViewPagerAdapter.setData(frgList);

        curPosition = curPosition>=CityList.size()?CityList.size()-1:curPosition;
        getDotList();
        wea_viewpager.setCurrentItem(curPosition);
        curDistrict.setText(CityList.get(curPosition).getCityName());
    }

    private void initViewPager() {
        wea_viewpager = (ViewPager)mview.findViewById(R.id.wea_viewpager);
        wea_viewpager.addOnPageChangeListener(new MyPagerChangeListener());
        frgList = new ArrayList<wea_fragment>();
        for(int i = 0; i < CityList.size(); i++){
            Bundle bundle = new Bundle();
            bundle.putString("map_district", CityList.get(i).getCityName());
            bundle.putInt("id",i);
            wea_fragment frag = (wea_fragment) FragmentFactory.createById(FragmentID.WEA_FRAGEMENT,bundle);
            frgList.add(frag);
        }
        getDotList();
        weaViewPagerAdapter = new WeaViewPagerAdapter(getChildFragmentManager(), frgList);
        wea_viewpager.setAdapter(weaViewPagerAdapter);
        wea_viewpager.setCurrentItem(CurItem);
    }

    private void initView() {
        menu_btn =mview.findViewById(R.id.menu_btn);
        curDistrict =mview.findViewById(R.id.district);
        menu_btn.setOnClickListener(this);
        curDistrict.setText(CityList.get(CurItem).getCityName());
        main_bg =mview.findViewById(R.id.main_bg);
        share =mview.findViewById(R.id.share);
        share.setOnClickListener(this);
    }



    private void initMap() {
        mLocationListener = aMapLocation -> {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    map_district = aMapLocation.getDistrict();
                    map_district = map_district.substring(0,map_district.length()-1);
                    Log.i("log_map",map_district);

                    mapCity = new CityBean("-1",map_district);
                    Log.i("log_initData","initData");
                    initData();
                    Log.i("log_initData","initData");
                    initView();
                    initViewPager();
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                    mapCity = new CityBean("-1","北京");
                    initData();
                    initView();
                    initViewPager();
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);


        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest(true);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        mLocationOption.setOnceLocation(true);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void getDotList() {
        dots = new ArrayList<View>();
        LinearLayout dotcontaint = (LinearLayout)mview.findViewById(R.id.dotcontaint);
        dotcontaint.removeAllViews();
        //循环图片数组，创建对应数量的dot
        for(int i=0;i<CityList.size();i++){
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dot_layout, null);//加载布局
            View dot = view.findViewById(R.id.dotView);//得到布局中的dot点组件
            //收集dot
            dots.add(dot);
            //把布局添加到线性布局
            dotcontaint.addView(view);
        }
        dots.get(curPosition).setBackgroundResource(R.drawable.dot_focus);
    }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_btn:
                Intent intent = new Intent();
                intent.setClass(getContext(), com.taimin.weatherforecast.CityList.class);
                intent.putExtra("CityList",(Serializable)CityList);
                startActivityForResult(intent, MENU_REQUEST);
                break;
            case R.id.share:
                frgList.get(curPosition).shareWeather();
            default:
                break;
        }
    }

    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onPageSelected(int position) {
            curDistrict.setText(CityList.get(position).getCityName());
            dots.get(curPosition).setBackgroundResource(R.drawable.dot_nomal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focus);
            try{
                SetForeCastBg.setImage(getContext(),main_bg,CityList.get(position).getWeatherData().getWea_img());
            } catch (Exception e){
                //
            }
            curPosition = position;
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == MENU_RESULT && requestCode == MENU_REQUEST){
            //TODO
            Log.i("back","back!");
            changeData();
        }else if(resultCode == SELECT_RESULT && requestCode == MENU_REQUEST){
            Log.i("select","select!");
            changeData();
            wea_viewpager.setCurrentItem(data.getIntExtra("idx",0),false);
        }
    }

    public void setWeatherData(int id, DataBean data){
        CityList.get(id).setWeatherData(data);
        Log.i("setdata_id", String.valueOf(id));
        Log.i("setdata", String.valueOf(curPosition));
        Log.i("setdata_img", String.valueOf(CityList.get(id).getWeatherData().getWea_img()));
        if(id == curPosition){
            SetForeCastBg.setImage(getContext(),main_bg,CityList.get(id).getWeatherData().getWea_img());
        }

    }
}
