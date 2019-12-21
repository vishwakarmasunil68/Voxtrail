package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.report.OverSpeedPOJO;
import com.voxtrail.voxtrail.pojo.report.UnderSpeedPOJO;
import com.voxtrail.voxtrail.util.UtilityFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class UnderSpeedAdapter extends RecyclerView.Adapter<UnderSpeedAdapter.ViewHolder> {
    private List<UnderSpeedPOJO> items;
    Activity activity;
    Fragment fragment;
    AdapterListener adapterListener;

    public void setAdapterItemAdapter(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public UnderSpeedAdapter(Activity activity, Fragment fragment, List<UnderSpeedPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_over_speed_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            holder.tv_duration.setText("Duration : "+String.valueOf(UtilityFunction.getDurationString(Math.abs(UtilityFunction.getDifferenceBtwTwoTimeInSec(items.get(position).getStart(),items.get(position).getEnd())))));
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.tv_start_time.setText("Start : "+items.get(position).getStart());
        holder.tv_end_time.setText("End : "+items.get(position).getEnd());
        holder.tv_top_speed.setText("Top Speed : "+items.get(position).getTopSpeed()+" km/h");
        holder.tv_avg_speed.setText("Avg Speed : "+items.get(position).getAvgSpeed()+" km/h");
        holder.tv_position.setText("Position : "+items.get(position).getUnderspeedPosition());

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_duration)
        TextView tv_duration;
        @BindView(R.id.tv_start_time)
        TextView tv_start_time;
        @BindView(R.id.tv_end_time)
        TextView tv_end_time;
        @BindView(R.id.tv_position)
        TextView tv_position;
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
