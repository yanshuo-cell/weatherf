package com.taimin.weatherforecast.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.taimin.weatherforecast.R;

public class Radarmap_fragment extends Fragment {
    View view;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.radarmap_fragment, container,false);
        webView = (WebView) view.findViewById(R.id.radar_webview);
        initWebview();
        return view;
    }

    private void initWebview() {
        webView.loadUrl("http://wap.weather.com.cn/radarMap/index.html");
        WebSettings webSettings = webView.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        webSettings.setTextZoom(90); // 通过百分比来设置文字的大小，默认值是100。
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
