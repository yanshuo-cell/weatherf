package com.taimin.weatherforecast.utils;

import android.content.Context;
import android.content.Intent;

import static androidx.core.content.ContextCompat.startActivity;

public class WeatherShare {
    Context context;
    public WeatherShare(Context context) {
        this.context = context;
    }

    public void share(String text){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        Intent chooserIntent = Intent.createChooser(sendIntent, "分享到：");
        context.startActivity(chooserIntent);
    }
}
