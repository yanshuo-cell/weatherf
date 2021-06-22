package com.taimin.weatherforecast.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.taimin.weatherforecast.MyApplication;
import com.taimin.weatherforecast.R;

public class Me_fragment extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.me_fragment, container,false);
        TextView about = view.findViewById(R.id.btn_about);
        Switch aSwitch = view.findViewById(R.id.open);
        MyApplication myApplication = (MyApplication)getActivity().getApplication();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myApplication.setRefrash(false);
                } else {
                    myApplication.setRefrash(true);
                }
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast= Toast.makeText(getActivity(),"安卓大作业\n作者：xxx",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
