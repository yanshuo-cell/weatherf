package com.taimin.weatherforecast.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class FragmentFactory {
    public static Fragment createById(int id, Bundle bundle){
        Fragment fragment = null;
        switch (id){
            case FragmentID.WEA_FRAGEMENT:
                fragment = new wea_fragment();
                Log.i("arg", (String) bundle.get("map_district"));
                fragment.setArguments(bundle);
                break;
        }
        return fragment;
    }

    public static Fragment createById(int id){
        Fragment fragment = null;
        switch (id){
            case FragmentID.MAIN_FRAGEMENT:
                fragment = new Main_fragement();
                break;
            case FragmentID.ME_FRAGMENT:
                fragment = new Me_fragment();
                break;
            case FragmentID.RADAR_FRAGMENT:
                fragment = new Radarmap_fragment();
                break;
        }
        return fragment;
    }
}
