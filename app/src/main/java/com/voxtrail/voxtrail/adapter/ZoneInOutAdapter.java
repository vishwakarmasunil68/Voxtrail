package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.report.ZoneInOutPOJO;
import com.voxtrail.voxtrail.util.UtilityFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class ZoneInOutAdapter extends RecyclerView.Adapter<ZoneInOutAdapter.ViewHolder> {
    private List<ZoneInOutPOJO> items;
    Activity activity;
    Fragment fragment;
    AdapterListener adapterListener;

    public void setAdapterItemAdapter(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public ZoneInOutAdapter(Activity activity, Fragment fragment, List<ZoneInOutPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_zone_in_out, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_zone_name.setText(items.get(position).getZoneName());
        try {
            holder.tv_duration.setText("Duration : " + UtilityFunction.getDurationString(UtilityFunction.getDifferenceBtwTwoTimeInSec(items.get(position).getZoneIn(), items.get(position).getZoneOut())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tv_zone_in.setText("Zone In : " + items.get(position).getZoneIn());
        holder.tv_zone_out.setText("Zone Out : " + items.get(position).getZoneOut());
        holder.tv_length.setText("Route Length : " + items.get(position).getRouteLength());
        holder.tv_zone_position.setText(items.get(position).getZonePosition());

        holder.tv_zone_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String[] str=items.get(position).getZonePosition().split(",");
                    double latitude=Double.parseDouble(str[0]);
                    double longitude=Double.parseDouble(str[1]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + latitude + ">,<" + longitude + ">?q=<" + latitude + ">,<" + longitude + ">(" + items.get(position).getZonePosition() + ")"));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_zone_name)
        TextView tv_zone_name;
        @BindView(R.id.tv_zone_position)
        TextView tv_zone_position;
        @BindView(R.id.tv_zone_in)
        TextView tv_zone_in;
        @BindView(R.id.tv_duration)
        TextView tv_duration;
        @BindView(R.id.tv_zone_out)
        TextView tv_zone_out;
        @BindView(R.id.tv_length)
        TextView tv_length;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
