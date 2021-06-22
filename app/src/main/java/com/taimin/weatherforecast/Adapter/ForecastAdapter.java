package com.taimin.weatherforecast.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taimin.weatherforecast.MainActivity;
import com.taimin.weatherforecast.R;
import com.taimin.weatherforecast.bean.DataBean;
import com.taimin.weatherforecast.utils.SetForeCastImage;

import org.w3c.dom.Text;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {

    Context context;
    List<DataBean> forcast;
    HourAdapter hourAdapter;

    public ForecastAdapter(Context context, List<DataBean> forcast) {
        this.context = context;
        this.forcast = forcast;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.forecast_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(position == 0){
            holder.hours.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            hourAdapter = new HourAdapter(context, forcast.get(position).getHours());
            holder.hours.setAdapter(hourAdapter);
            holder.hours_wrap.setVisibility(View.VISIBLE);
        }
        holder.week.setText(position == 0?forcast.get(position).getWeek()+" 今天":forcast.get(position).getWeek());
        SetForeCastImage.setImage(context,holder.wea,forcast.get(position).getWea_img());
        holder.tem1.setText(forcast.get(position).getTem1());
        holder.tem2.setText(forcast.get(position).getTem2());
    }

    @Override
    public int getItemCount() {
        return forcast.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView week;
        ImageView wea;
        TextView tem1;
        TextView tem2;
        RecyclerView hours;
        LinearLayout hours_wrap;
        public MyViewHolder(View view) {
            super(view);
            week = (TextView) view.findViewById(R.id.week);
            wea = (ImageView) view.findViewById(R.id.wea);
            tem1 = (TextView) view.findViewById(R.id.tem1);
            tem2 = (TextView) view.findViewById(R.id.tem2);
            hours = (RecyclerView) view.findViewById(R.id.hours);
            hours_wrap = view.findViewById(R.id.hours_wrap);
        }
    }
}
