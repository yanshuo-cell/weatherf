package com.taimin.weatherforecast.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.taimin.weatherforecast.bean.CityBean;
import com.taimin.weatherforecast.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;


public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;
    //创建数据库
    public SQLiteHelper(Context context){
        super(context, DBUtils.DATABASE_NAME, null, DBUtils.DATABASE_VERION);
        sqLiteDatabase = this.getWritableDatabase();
    }
    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DBUtils.DATABASE_TABLE+"("+DBUtils.CITY_ID+
                " integer primary key autoincrement,"+ DBUtils.CITY_NAME +
                " text)" );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    //添加数据
    public boolean insertData(String cityName){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBUtils.CITY_NAME,cityName);
        return
                sqLiteDatabase.insert(DBUtils.DATABASE_TABLE,null,contentValues)>0;
    }
    //删除数据
    public boolean deleteData(String id){
        String sql=DBUtils.CITY_ID+"=?";
        String[] contentValuesArray=new String[]{String.valueOf(id)};
        return
                sqLiteDatabase.delete(DBUtils.DATABASE_TABLE,sql,contentValuesArray)>0;
    }
    //修改数据
//    public boolean updateData(String id,String phoneName,String phoneNum){
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(DBUtils.PHONE_NAME,phoneName);
//        contentValues.put(DBUtils.PHONE_NUM,phoneNum);
//        String sql=DBUtils.PHONE_ID+"=?";
//        String[] strings=new String[]{id};
//        return
//                sqLiteDatabase.update(DBUtils.DATABASE_TABLE,contentValues,sql,strings)>0;
//    }
    //查询数据
    public List<CityBean> query(){
        List<CityBean> list=new ArrayList<CityBean>();
        Cursor cursor=sqLiteDatabase.query(DBUtils.DATABASE_TABLE,null,null,null,
                null,null,DBUtils.CITY_ID+" desc");
        if (cursor!=null){
            while (cursor.moveToNext()){
                CityBean city=new CityBean();
                String id = String.valueOf(cursor.getInt
                        (cursor.getColumnIndex(DBUtils.CITY_ID)));
                String cityName = cursor.getString(cursor.getColumnIndex
                        (DBUtils.CITY_NAME));
                city.setId(id);
                city.setCityName(cityName);
                list.add(city);
            }
            cursor.close();
        }
        return list;
    }
}