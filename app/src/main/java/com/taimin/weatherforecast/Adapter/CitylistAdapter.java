package com.taimin.weatherforecast.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taimin.weatherforecast.R;
import com.taimin.weatherforecast.bean.CityBean;

import java.util.List;

public class CitylistAdapter extends RecyclerView.Adapter<CitylistAdapter.MyViewHolder> {

    Context context;
    List<CityBean> CityList;

    public void setData(List cityList){
        this.CityList = cityList;
        notifyDataSetChanged();
    }

    public CitylistAdapter(Context context, List cityList) {
        this.context = context;
        CityList = cityList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.city_list_item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cityText.setText(CityList.get(position).getCityName());
        holder.cityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, String.valueOf(position));
            }
        });

        holder.cityItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("long","longclick");
                onItemClickListener.onLongClick(v,position);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return CityList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cityText;
        LinearLayout cityItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cityText = itemView.findViewById(R.id.city_text);
            cityItem = itemView.findViewById(R.id.city_item);
        }
    }

    public interface OnItemClickListener{

        void onItemClick(View view, String data);

        void onLongClick(View view, int data);
    }

    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
