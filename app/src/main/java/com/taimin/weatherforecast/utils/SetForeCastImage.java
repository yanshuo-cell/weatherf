package com.taimin.weatherforecast.utils;

import android.content.Context;
import android.widget.ImageView;

import com.taimin.weatherforecast.R;

public class SetForeCastImage {
    public static void setImage(Context context,ImageView wea, String weather){
        switch (weather){
            case "xue":
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.xue));
                break;
            case "lei":
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.lei));
                break;
            case "shachen":
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.shachen));
                break;
            case "wu":
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.wu));
                break;
            case "bingbao":
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.xue));
                break;
            case "yun":
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.yun));
                break;
            case "yu":
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.yu));
                break;
            case "qing":
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.qing));
                break;
            case "yin":
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.yin));
                break;
            default:
                wea.setImageDrawable(context.getResources().getDrawable(R.drawable.qing));
                break;
        }
    }
}
