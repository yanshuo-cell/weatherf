package com.taimin.weatherforecast;


import android.app.Application;

public class MyApplication extends Application {

    private boolean isRefrash = true;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    public void setRefrash(boolean refrash) {
        isRefrash = refrash;
    }

    public boolean isRefrash() {
        return isRefrash;
    }
}
