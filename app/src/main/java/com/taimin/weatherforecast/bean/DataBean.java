package com.taimin.weatherforecast.bean;

import java.io.Serializable;
import java.util.List;

public class DataBean implements Serializable {
    private String air; //空气质量
    private String air_level;  //空气等级
    private String air_tips; //小贴士
    private String day; //日期
    private String date;    //日期
    private String wea; //天气
    private String week; //星期
    private String wea_img; //图片资源名
    private String visbility; //能见度
    private String tem; //当前温度
    private String tem1; //最高温度
    private String tem2; //最低温度
    private List win; //风向
    private String win_speed; //风力等级
    private String win_meter; //风速
    private List<HourBean> hours;
    private List<IndexBean> index;


    public String getAir_level() {
        return air_level;
    }

    public void setAir_level(String air_level) {
        this.air_level = air_level;
    }

    public String getAir_tips() {
        return air_tips;
    }

    public void setAir_tips(String air_tips) {
        this.air_tips = air_tips;
    }

    public List<IndexBean> getIndex() {
        return index;
    }

    public void setIndex(List<IndexBean> indexs) {
        this.index = indexs;
    }

    public DataBean(String air, String air_level, String air_tips, String day, String date, String wea, String week, String wea_img, String visbility, String tem, String tem1, String tem2, List win, String win_speed, String win_meter, List<HourBean> hours, List<IndexBean> index) {
        this.air = air;
        this.air_level = air_level;
        this.air_tips = air_tips;
        this.day = day;
        this.date = date;
        this.wea = wea;
        this.week = week;
        this.wea_img = wea_img;
        this.visbility = visbility;
        this.tem = tem;
        this.tem1 = tem1;
        this.tem2 = tem2;
        this.win = win;
        this.win_speed = win_speed;
        this.win_meter = win_meter;
        this.hours = hours;
        this.index = index;
    }

    public DataBean() {
    }

    public DataBean(String air, String airLevel, String airTips, String day, String date, String wea, String week, String weaImg, String visbility, String tem, String tem1, String tem2, List win, String win_speed, String win_meter, List<HourBean> hours) {
        this.air = air;
        this.air_level = air_level;
        this.air_tips = air_tips;
        this.day = day;
        this.date = date;
        this.wea = wea;
        this.week = week;
        this.wea_img = wea_img;
        this.visbility = visbility;
        this.tem = tem;
        this.tem1 = tem1;
        this.tem2 = tem2;
        this.win = win;
        this.win_speed = win_speed;
        this.win_meter = win_meter;
        this.hours = hours;
    }

    public List<HourBean> getHours() {
        return hours;
    }

    public void setHours(List<HourBean> hours) {
        this.hours = hours;
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    public String getAirLevel() {
        return air_level;
    }

    public void setAirLevel(String airLevel) {
        this.air_level = airLevel;
    }

    public String getAirTips() {
        return air_tips;
    }

    public void setAirTips(String airTips) {
        this.air_tips = airTips;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWea_img() {
        return wea_img;
    }

    public void setWea_img(String weaImg) {
        this.wea_img = weaImg;
    }

    public String getVisbility() {
        return visbility;
    }

    public void setVisbility(String visbility) {
        this.visbility = visbility;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public List getWin() {
        return win;
    }

    public void setWin(List win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    public String getWin_meter() {
        return win_meter;
    }

    public void setWin_meter(String win_meter) {
        this.win_meter = win_meter;
    }
}
