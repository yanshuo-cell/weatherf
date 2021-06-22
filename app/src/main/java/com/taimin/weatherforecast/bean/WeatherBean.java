package com.taimin.weatherforecast.bean;

import java.util.List;

public class WeatherBean {
    private AqiBean aqi;
    private String city;
    private String update_time;

    public String getUpdate_time() {
        return update_time;
    }

    public WeatherBean(AqiBean aqi, String city, String update_time, List<DataBean> data) {
        this.aqi = aqi;
        this.city = city;
        this.update_time = update_time;
        this.data = data;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    private List<DataBean> data;

    public AqiBean getAqi() {
        return aqi;
    }

    public void setAqi(AqiBean aqi) {
        this.aqi = aqi;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
}
