package com.taimin.weatherforecast.utils;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.taimin.weatherforecast.R;

public class SetForeCastBg {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setImage(Context context, ViewGroup wea, String weather){
        switch (weather){
            case "xue":
                wea.setBackground(context.getResources().getDrawable(R.drawable.xue_bg));
                break;
            case "lei":
                wea.setBackground(context.getResources().getDrawable(R.drawable.lei_bg));
                break;
            case "shachen":
                wea.setBackground(context.getResources().getDrawable(R.drawable.shachen_bg));
                break;
            case "wu":
                wea.setBackground(context.getResources().getDrawable(R.drawable.wu_bg));
                break;
            case "bingbao":
                wea.setBackground(context.getResources().getDrawable(R.drawable.xue_bg));
                break;
            case "yun":
                wea.setBackground(context.getResources().getDrawable(R.drawable.yun_bg));
                break;
            case "yu":
                wea.setBackground(context.getResources().getDrawable(R.drawable.yu_bg));
                break;
            case "qing":
                wea.setBackground(context.getResources().getDrawable(R.drawable.qing_bg));
                break;
            case "yin":
                wea.setBackground(context.getResources().getDrawable(R.drawable.yin_bg));
                break;
            default:
                wea.setBackground(context.getResources().getDrawable(R.drawable.qing_bg));
                break;
        }
    }
}
