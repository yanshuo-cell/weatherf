package com.taimin.weatherforecast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.taimin.weatherforecast.Adapter.CitylistAdapter;
import com.taimin.weatherforecast.bean.CityBean;
import com.taimin.weatherforecast.db.SQLiteHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityList extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    LinearLayout new_city;
    RecyclerView city_list;
    List<CityBean> CityList;
    SQLiteHelper mSQLiteHelper;
    CitylistAdapter citylistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        mSQLiteHelper = new SQLiteHelper(CityList.this);
        initData();
        initView();
    }

    private void initData() {
        CityList=(List<CityBean>) getIntent().getSerializableExtra("CityList");

//        mSQLiteHelper.insertData("阳谷");
//        mSQLiteHelper.insertData("聊城");
//        mSQLiteHelper.insertData("淄博");
//        mSQLiteHelper.insertData("佛山");
//        CityList = mSQLiteHelper.query();
//        citylistAdapter.setData(CityList);
    }

    private void changeData(){
        CityList = CityList.subList(0,1);
        CityList.addAll(mSQLiteHelper.query());
        citylistAdapter.setData(CityList);
    }

    private void initView() {
        back = findViewById(R.id.back);
        new_city = findViewById(R.id.new_city_btn);
        new_city.setOnClickListener(this);
        back.setOnClickListener(this);
        city_list = findViewById(R.id.city_list);
        citylistAdapter = new CitylistAdapter(CityList.this,CityList );
        citylistAdapter.setOnItemClickListener(new CitylistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent = new Intent();
                intent.putExtra("idx",Integer.valueOf(data));
                setResult(2,intent);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CityList.this)
                        .setMessage("是否删除该城市？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CityBean cityBean = CityList.get(position);
                                mSQLiteHelper.deleteData(cityBean.getId());
                                if(mSQLiteHelper.deleteData(cityBean.getId()));{
                                    changeData();
                                    setResult(1);
                                    Toast.makeText(CityList.this, "删除成功", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        city_list.setLayoutManager(new LinearLayoutManager(this));
        city_list.setAdapter(citylistAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.new_city_btn:
                Intent intent = new Intent();
                intent.setClass(CityList.this, CityPicker.class);
                startActivityForResult(intent, 1);
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1 && requestCode == 1){
            mSQLiteHelper.insertData(data.getStringExtra("city"));
            changeData();
            setResult(1);
        }
    }
}
