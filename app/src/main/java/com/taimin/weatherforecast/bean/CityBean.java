package com.taimin.weatherforecast.bean;

import java.io.Serializable;

public class CityBean implements Serializable {
    String id;
    String cityName;
    DataBean weatherData;
    private boolean isHot;
    private String sortKey;
    private String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }



    public CityBean(String id, String cityName, DataBean weatherData, boolean isHot, String sortKey, String pinyin) {
        this.id = id;
        this.cityName = cityName;
        this.weatherData = weatherData;
        this.isHot = isHot;
        this.sortKey = sortKey;
        this.pinyin = pinyin;
    }

    public DataBean getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(DataBean weatherData) {
        this.weatherData = weatherData;
    }

    public CityBean() {
    }

    public CityBean(String id, String cityName) {
        this.id = id;
        this.cityName = cityName;

    }
    public CityBean(String id, String cityName, String sortKey) {
        this.id = id;
        this.cityName = cityName;
        this.sortKey = sortKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
