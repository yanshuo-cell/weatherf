package com.taimin.weatherforecast.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taimin.weatherforecast.bean.WeatherBean;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParserJson {
    public static WeatherBean parserJson(String result) {
        WeatherBean weatherBean;
        try {
//            JSONObject jsonObject = new JSONObject(result);
            Gson gson = new Gson();
            weatherBean = gson.fromJson(result,WeatherBean.class);
            Log.i("json",weatherBean.getCity());
            return weatherBean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
