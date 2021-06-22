package com.taimin.weatherforecast.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.taimin.weatherforecast.Adapter.ForecastAdapter;
import com.taimin.weatherforecast.MainActivity;
import com.taimin.weatherforecast.MyApplication;
import com.taimin.weatherforecast.R;
import com.taimin.weatherforecast.bean.IndexBean;
import com.taimin.weatherforecast.bean.WeatherBean;
import com.taimin.weatherforecast.utils.NetUtils;
import com.taimin.weatherforecast.utils.ParserJson;
import com.taimin.weatherforecast.utils.WeatherShare;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class wea_fragment extends Fragment {
    public WeatherBean weatherBean;

    TextView city;
    TextView curWeather;
    TextView curTemper;
    TextView tips;
    TextView wind;
    RecyclerView forecast;
    ForecastAdapter forecastAdapter;
    SwipeRefreshLayout mSwipe;
    int swipeflag = 0;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;
    public AMapLocationClientOption mLocationOption = null;

    String map_district; //地区信息
    Integer map_district_id;

    Context context;
    View mview;
    List<TextView> indexs;
    RefreshWeather refreshWeather = new RefreshWeather();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mview=inflater.inflate(R.layout.wea_fragment, container,false);
        this.map_district = getArguments().getString("map_district");
        Log.i("getArguments",map_district);
        this.map_district_id = getArguments().getInt("id");
        initView();
        getWeather();
        MyApplication myApplication = (MyApplication)getActivity().getApplication();
        if (myApplication.isRefrash() == true){
            refreshWeather.handler.postDelayed(refreshWeather.refrashRunnable,60*60*1000);
        }

        return mview;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        initMap();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("frag","onCreate");
        this.context = getContext();

        Log.i("district", map_district);

//        getWeather();
    }


    private void initView() {
//        city = mview.findViewById(R.id.city);
        mSwipe =  mview.findViewById(R.id.mswipeRefreshLayout);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeather();
                swipeflag = 1;
            }
        });
        curWeather = mview.findViewById(R.id.CurWeather);
        curTemper = mview.findViewById(R.id.CurTemper);
        forecast = mview.findViewById(R.id.forecast);
        tips = mview.findViewById(R.id.tips);
        wind = mview.findViewById(R.id.wind);
        indexs = new ArrayList<>();
        indexs.add(mview.findViewById(R.id.zwx));
        indexs.add(mview.findViewById(R.id.yd));
        indexs.add(mview.findViewById(R.id.xt));
        indexs.add(mview.findViewById(R.id.cy));
        indexs.add(mview.findViewById(R.id.xc));
        indexs.add(mview.findViewById(R.id.kq));

    }

    public void getWeather(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uri = "https://www.tianqiapi.com/api/?appid=72525837&appsecret=DqFMVYZ4&cityid=0&city="+map_district+"&ip=0&callback=0";
                String res = NetUtils.Get(uri);
                weatherBean = ParserJson.parserJson(res);
                Log.i("weather",weatherBean.getCity());
                mHandler.sendMessage(mHandler.obtainMessage(Integer.parseInt("1")));
            }
        }).start();
    }

    public void shareWeather(){
        new WeatherShare(context).share(map_district+"今日的天气为"+weatherBean.getData().get(0).getWea()+","+weatherBean.getData().get(0).getTem()+","
                +weatherBean.getData().get(0).getAirTips());
    }



    public void setData(){
//        city.setText(weatherBean.getCity());
        Log.i("setData",weatherBean.getCity());
        Main_fragement fragment = (Main_fragement) this.getParentFragment();
        try{
            Log.i("setData", String.valueOf(fragment.CityList));
            fragment.setWeatherData(map_district_id,weatherBean.getData().get(0));//更换背景图
        }catch (Exception e){
            e.printStackTrace();
        }
        curWeather.setText(weatherBean.getData().get(0).getWea());
        curTemper.setText(weatherBean.getData().get(0).getTem());
        tips.setText("Tips:"+weatherBean.getData().get(0).getAirTips());
        wind.setText(weatherBean.getData().get(0).getWin().get(0)+" "+weatherBean.getData().get(0).getWin_speed());
        forecast.setLayoutManager(new LinearLayoutManager(context));
        forecastAdapter = new ForecastAdapter(context, weatherBean.getData());
        forecast.setAdapter(forecastAdapter);
        Log.i("data",weatherBean.getCity());
        List<IndexBean> indexList = weatherBean.getData().get(0).getIndex();
        for(int i = 0;i < indexList.size(); i++){
            indexs.get(i).setText(indexList.get(i).getLevel());
        }
    }


    Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setData();
                    if(swipeflag == 1){
                        Toast toast= Toast.makeText(context, "天气更新成功，更新时间:"+weatherBean.getUpdate_time(), Toast.LENGTH_LONG);
                        toast.show();
                        mSwipe.setRefreshing(false);
                        swipeflag = 0;
                    }
                    break;
                default:
                    break;
            }
        }
    };
    class RefreshWeather{
        private Handler handler = new Handler();
        private Runnable refrashRunnable = new Runnable() {
            public void run () {
                getWeather();//获取新数据
                handler.postDelayed(this,60*60*1000); //一小时刷新一次
            }
        };
    }


}
