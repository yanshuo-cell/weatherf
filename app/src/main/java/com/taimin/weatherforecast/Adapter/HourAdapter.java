package com.taimin.weatherforecast.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taimin.weatherforecast.R;
import com.taimin.weatherforecast.bean.HourBean;
import com.taimin.weatherforecast.utils.SetForeCastImage;

import org.w3c.dom.Text;

import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.MyViewHolder>{

    Context context;
    List<HourBean> hours;

    public HourAdapter(Context context, List<HourBean> hours) {
        this.context = context;
        this.hours = hours;
    }

    @NonNull
    @Override
    public HourAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HourAdapter.MyViewHolder holder = new HourAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.hour_forecast_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HourAdapter.MyViewHolder holder, int position) {
        holder.hour.setText(hours.get(position).getDay().substring(hours.get(position).getDay().length()-3,hours.get(position).getDay().length()));
//        SetForeCastImage.setImage(context,holder.wea,hours.get(position).getWea_img());
        holder.wea.setText(hours.get(position).getWea());
        holder.tem.setText(hours.get(position).getTem());
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView hour;
        TextView wea;
        TextView tem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.hour);
            wea = itemView.findViewById(R.id.hour_wea);
            tem = itemView.findViewById(R.id.hour_tem);
        }
    }
}
