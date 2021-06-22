package com.taimin.weatherforecast.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.taimin.weatherforecast.CityList;
import com.taimin.weatherforecast.R;
import com.taimin.weatherforecast.bean.CityBean;
import com.taimin.weatherforecast.bean.LocateState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 城市列表适配器
 */
public class AllCityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private List<CityBean> mCities;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;
    private List<CityBean> mHotData = new ArrayList<>();

    int model = 0;

    public void setData(List<CityBean> mCities,int model) {
        this.model = model;
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null) {
            mCities = new ArrayList<>();
        }
        if(mCities!=null && mCities.size()!=0 && mCities.get(0).getId()!="-1"&&model==0){
            mCities.add(0, new CityBean("-1", "定位","#"));
            mCities.add(1, new CityBean("-1", "热门","#"));
        }

        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];

        for (int index = 0; index < size; index++) {
            //当前城市拼音首字母
            String currentLetter = mCities.get(index).getSortKey();
            Log.i("city",mCities.get(index).getCityName());
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? mCities.get(index - 1).getSortKey() : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
            //筛选出热门
            if (mCities.get(index).isHot())
                mHotData.add(mCities.get(index));
        }
        notifyDataSetChanged();
    }

    public AllCityListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 更新定位状态
     *
     * @param state
     */
    public void updateLocateState(int state, String city) {
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return model == 1 ? 2:position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public CityBean getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        Log.i("position", String.valueOf(viewType));
        switch (viewType) {
            case 0:     //定位
                view = inflater.inflate(R.layout.cp_view_locate_city, parent, false);
                ViewGroup container = (ViewGroup) view.findViewById(R.id.layout_locate);
                TextView state = (TextView) view.findViewById(R.id.tv_located_city);
                switch (locateState) {
                    case LocateState.LOCATING:
                        state.setText("正在定位…");
                        break;
                    case LocateState.FAILED:
                        state.setText("定位失败");
                        break;
                    case LocateState.SUCCESS:
                        state.setText(locatedCity);
                        break;
                    case LocateState.INIT:
                        state.setText("定位");
                        break;
                }
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (locateState == LocateState.FAILED) {
                            //重新定位
                            if (onCityClickListener != null) {
                                onCityClickListener.onLocateClick();
                            }
                        } else if (locateState == LocateState.SUCCESS) {
                            //返回定位城市
                            if (onCityClickListener != null) {
                                onCityClickListener.onCityClick(locatedCity);
                            }
                        }
                    }
                });
                break;
            case 1:     //热门
                view = inflater.inflate(R.layout.cp_view_hot_city, parent, false);
                GridView gridView = (GridView) view.findViewById(R.id.gridview_hot_city);
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext);
                gridView.setAdapter(hotCityGridAdapter);
                hotCityGridAdapter.setData(mHotData);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null) {
                            onCityClickListener.onCityClick(hotCityGridAdapter.getItem(position).getCityName());
                        }
                    }
                });
                break;
            case 2:     //所有
                if (view == null) {
                    view = inflater.inflate(R.layout.cp_item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                } else {
                    holder = (CityViewHolder) view.getTag();
                }

                    final String city = mCities.get(position).getCityName();
                    holder.name.setText(city);
                    Log.i("positioncity", city);
                    String currentLetter = mCities.get(position).getSortKey();
                    String previousLetter = position >= 1 ? mCities.get(position-1).getSortKey() : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)) {
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    } else {
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null) {
                                onCityClickListener.onCityClick(city);
                            }
                        }
                    });

                break;
        }
        return view;
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mCities.get(i).getSortKey();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if(firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    public static class CityViewHolder {
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener {
        void onCityClick(String name);

        void onLocateClick();
    }
}
