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
import com.voxtrail.voxtrail.pojo.event.LastEventPOJO;
import com.voxtrail.voxtrail.util.UtilityFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<LastEventPOJO> items;
    Activity activity;
    Fragment fragment;
    AdapterListener adapterListener;

    public void setAdapterItemAdapter(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public NotificationAdapter(Activity activity, Fragment fragment, List<LastEventPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_notification_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//        switch (position) {
//            case 0:
//                holder.tv_name.setText("Mercedes");
//                break;
//            case 1:
//                holder.tv_name.setText("Audi");
//                break;
//            case 2:
//                holder.tv_name.setText("BMW");
//                break;
//            case 3:
//                holder.tv_name.setText("Volvo");
//                break;
//            case 4:
//                holder.tv_name.setText("Ferrari");
//                break;
//            case 5:
//                holder.tv_name.setText("Honda");
//                break;
//            case 6:
//                holder.tv_name.setText("Hyundai");
//                break;
//            case 7:
//                holder.tv_name.setText("Fiat");
//                break;
//            case 8:
//                holder.tv_name.setText("Renault");
//                break;
//            case 9:
//                holder.tv_name.setText("Suzuki");
//                break;
//            case 10:
//                holder.tv_name.setText("Nissan");
//                break;
//        }
        holder.tv_name.setText(String.valueOf(items.get(position).getName()));
        holder.tv_date.setText(String.valueOf(UtilityFunction.parseUTCToIST(items.get(position).getDtTracker())));
        holder.tv_status.setText(String.valueOf(items.get(position).getEventDesc()));

        holder.ll_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterListener != null) {
                    adapterListener.onAdapterItemClickListener(position);
                }
//                Intent intent=new Intent(activity, VehicleStatusActivity.class) ;
//                intent.putExtra("notificationPOJO",items.get(position));
//                activity.startActivity(intent);
            }
        });
        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.ll_notification)
        LinearLayout ll_notification;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
