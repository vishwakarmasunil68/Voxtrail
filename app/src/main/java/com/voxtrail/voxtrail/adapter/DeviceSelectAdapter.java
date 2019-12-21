package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.DeviceActivity;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class DeviceSelectAdapter extends RecyclerView.Adapter<DeviceSelectAdapter.ViewHolder> {
    private List<DevicePOJO> items;
    Activity activity;
    Fragment fragment;
    AdapterListener adapterListener;

    public void setAdapterItemAdapter(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public DeviceSelectAdapter(Activity activity, Fragment fragment, List<DevicePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_device_select, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.ll_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterListener != null) {
                    adapterListener.onAdapterItemClickListener(position);
                }
            }
        });

        Log.d(TagUtils.getTag(), "icon web url:-" + WebServicesUrls.IMAGE_BASE_URL + items.get(position).getDeviceDetailPOJO().getIcon());
//        Picasso.get()
//                .load(WebServicesUrls.IMAGE_BASE_URL + items.get(position).getDeviceDetailPOJO().getIcon())
//                .into(holder.iv_icon);

//        Glide.with(activity.getApplicationContext())
//                .load(WebServicesUrls.IMAGE_BASE_URL+items.get(position).getDeviceDetailPOJO().getIcon())
//                .into(holder.iv_icon);

        holder.tv_object_name.setText(items.get(position).getDeviceDetailPOJO().getName());
        holder.tv_imei.setText(items.get(position).getDeviceDetailPOJO().getImei());

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_object_name)
        TextView tv_object_name;
        @BindView(R.id.tv_imei)
        TextView tv_imei;
        @BindView(R.id.ll_device)
        LinearLayout ll_device;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
