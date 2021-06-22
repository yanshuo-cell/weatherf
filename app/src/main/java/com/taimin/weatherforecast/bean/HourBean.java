package com.taimin.weatherforecast.bean;

import java.io.Serializable;

public class HourBean implements Serializable {
    private String hours;
    private String wea;
    private String wea_img;
    private String tem;
    private String win;
    private String win_speed;
    private String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public HourBean(String hours, String wea, String wea_img, String tem, String win, String win_speed, String day) {
        this.hours = hours;
        this.wea = wea;
        this.wea_img = wea_img;
        this.tem = tem;
        this.win = win;
        this.win_speed = win_speed;
        this.day = day;
    }

    public HourBean(String hours, String wea, String wea_img, String tem, String win, String win_speed) {
        this.hours = hours;
        this.wea = wea;
        this.wea_img = wea_img;
        this.tem = tem;
        this.win = win;
        this.win_speed = win_speed;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWea_img() {
        return wea_img;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }
}
