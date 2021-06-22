package com.taimin.weatherforecast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.taimin.weatherforecast.Adapter.AllCityListAdapter;
import com.taimin.weatherforecast.bean.AreasBean;
import com.taimin.weatherforecast.bean.City;
import com.taimin.weatherforecast.bean.CityBean;
import com.taimin.weatherforecast.bean.CityPickerBean;
import com.taimin.weatherforecast.bean.LocateState;
import com.taimin.weatherforecast.utils.BKeyComparator;
import com.taimin.weatherforecast.utils.CharacterParser;
import com.taimin.weatherforecast.utils.ReadAssetsFileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CityPicker extends AppCompatActivity {

    ListView city_list;
    TextView dialog;
    Set<String> bKey;
    AllCityListAdapter mCityAdapter;
    TextView sort_key;
    SideBar sideBar;
    EditText searchView;
    ArrayList<CityBean> cities;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);
        initView();
        initData();
        getLocation();
    }

    private void initData() {
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        bKey = new HashSet<>();
        String json = ReadAssetsFileUtil.getJson(this, "city.json");
        Gson gson = new Gson();
        CityPickerBean bean = gson.fromJson(json, CityPickerBean.class);
        HashSet<CityBean> citys = new HashSet<>();
        setEachArea(bean.data.areas, citys);
        //set转换list
        cities = new ArrayList<CityBean>(citys);
        //按照字母排序
        Collections.sort(cities, new Comparator<CityBean>() {
            @Override
            public int compare(CityBean city, CityBean t1) {
                return city.getPinyin().compareTo(t1.getPinyin());
            }
        });
        String[] b= bKey.toArray(new String[bKey.size()]);
        Arrays.sort(b,new BKeyComparator());

        mCityAdapter.setData(cities, 0);
        mCityAdapter.setOnCityClickListener(new AllCityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                Toast.makeText(CityPicker.this, name, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("city", name);
                setResult(1, intent);
                finish();
            }

            @Override
            public void onLocateClick() {

            }
        });
        city_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView,  int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("fitem", String.valueOf(firstVisibleItem));
                if(firstVisibleItem>=2){
                    sort_key.setVisibility(View.VISIBLE);
                    sort_key.setText(cities.get(firstVisibleItem).getSortKey());
                }else{
                    sort_key.setVisibility(View.GONE);
                }

            }
        });
        sideBar.setB(b);
        sideBar.setTextView(dialog);
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                Log.v("this",this.toString());
                int position = mCityAdapter.getPositionForSection(s.charAt(0));
                if(position != -1) {
                    city_list.setSelection(position);
                }

            }
        });
    }

    private void setEachArea(List<AreasBean> areas, HashSet<CityBean> citys){
        for (AreasBean areasBean : areas) {
            String name = areasBean.name.replace("　", "");
            String pinyin = CharacterParser.getInstance().getSelling(areasBean.name);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")) {
                bKey.add(sortString.toUpperCase());
                sortString = sortString.toUpperCase();
            } else {
                bKey.add("#");
                sortString = "#";
            };
            citys.add(new CityBean(String.valueOf(areasBean.id), name, null, areasBean.is_hot == 1, sortString, pinyin));
            if(areasBean.children != null){
                setEachArea(areasBean.children, citys);
            }
        }
    }

    private void initView() {
        city_list = findViewById(R.id.city_list);
        dialog = findViewById(R.id.dialog);
        mCityAdapter = new AllCityListAdapter(this);
        city_list.setAdapter(mCityAdapter);
        sort_key = findViewById(R.id.sort_key);
        searchView = (EditText) findViewById(R.id.search_text);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchContent = searchView.getText().toString().trim();
                if(searchContent.length()!=0){
                    searchData(searchContent);
                    sort_key.setVisibility(View.GONE);
                    sideBar.setVisibility(View.GONE);
//                    city_list.setSelection(2);
//                    search_out.setVisibility(View.VISIBLE);
                }else{
//                    searchList.setVisibility(View.GONE);
//                    search_out.setVisibility(View.GONE);
                    mCityAdapter.setData(cities,0);
                    sort_key.setVisibility(View.VISIBLE);
                    sideBar.setVisibility(View.VISIBLE);
//                    city_list.setSelection(0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchData(String searchContent) {
        ArrayList<CityBean> searchList = new ArrayList<CityBean>();
        for(int i = 0; i < cities.size(); i++){
            CityBean cityBean = cities.get(i);
            if (cityBean.getCityName().contains(searchContent)){
                searchList.add(cityBean);
            }
        }
        mCityAdapter.setData(searchList,1);
    }

    private void getLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果：
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    if (amapLocation.getCity() != null && !amapLocation.getCity().equals("")) {
                        mCityAdapter.updateLocateState(LocateState.SUCCESS, amapLocation.getCity().replace("市", ""));
                    } else {
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    }
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                } else {
                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("高德地图", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
}
