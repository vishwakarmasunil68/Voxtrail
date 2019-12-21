package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.report.DriveStopPOJO;
import com.voxtrail.voxtrail.pojo.report.DriveStopResultPOJO;
import com.voxtrail.voxtrail.pojo.report.ZoneInOutPOJO;
import com.voxtrail.voxtrail.util.UtilityFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class DriveStopAdapter extends RecyclerView.Adapter<DriveStopAdapter.ViewHolder> {
    private List<DriveStopResultPOJO> items;
    Activity activity;
    Fragment fragment;
    AdapterListener adapterListener;

    public void setAdapterItemAdapter(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public DriveStopAdapter(Activity activity, Fragment fragment, List<DriveStopResultPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_drive_stop_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_status.setText("Status : "+items.get(position).getStatus());
        try {
            holder.tv_duration.setText("Duration : "+String.valueOf(UtilityFunction.getDurationString(Math.abs(UtilityFunction.getDifferenceBtwTwoTimeInSec(items.get(position).getStartTime(),items.get(position).getEndTime())))));
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.tv_start_time.setText("Start : "+items.get(position).getStartTime());
        holder.tv_end_time.setText("End : "+items.get(position).getEndTime());
        holder.tv_fuel_consumption.setText("Fuel : "+items.get(position).getFuelConsumption()+" l");
        holder.tv_fuel_cost.setText("Fuel Cost : "+items.get(position).getFuelCost());

        holder.ll_moving_status.setVisibility(View.GONE);
        holder.ll_stop_status.setVisibility(View.GONE);

        if(items.get(position).getStatus().equalsIgnoreCase("stopped")){
            holder.ll_stop_status.setVisibility(View.VISIBLE);
            holder.tv_position.setText("Position : "+items.get(position).getStopPosition());
        }else{
            holder.ll_moving_status.setVisibility(View.VISIBLE);
            holder.tv_length.setText("Length : "+items.get(position).getLength()+" km");
            holder.tv_top_speed.setText("Top Speed : "+items.get(position).getTopSpeed()+" kph");
            holder.tv_avg_speed.setText("Avg Speed : "+items.get(position).getAvgSpeed()+" kph");
        }

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.tv_duration)
        TextView tv_duration;
        @BindView(R.id.tv_start_time)
        TextView tv_start_time;
        @BindView(R.id.tv_fuel_consumption)
        TextView tv_fuel_consumption;
        @BindView(R.id.tv_end_time)
        TextView tv_end_time;
        @BindView(R.id.tv_fuel_cost)
        TextView tv_fuel_cost;
        @BindView(R.id.tv_position)
        TextView tv_position;
        @BindView(R.id.ll_stop_status)
        LinearLayout ll_stop_status;
        @BindView(R.id.ll_moving_status)
        LinearLayout ll_moving_status;
        @BindView(R.id.tv_length)
        TextView tv_length;
        @BindView(R.id.tv_top_speed)
        TextView tv_top_speed;
        @BindView(R.id.tv_avg_speed)
        TextView tv_avg_speed;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
