package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.HomeActivity;
import com.voxtrail.voxtrail.fragment.driver.DriverEditFragment;
import com.voxtrail.voxtrail.pojo.device.DriverPOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    private List<DriverPOJO> items;
    Activity activity;
    Fragment fragment;

    public DriverAdapter(Activity activity, Fragment fragment, List<DriverPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_driver_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_driver_name.setText(items.get(position).getDriverName());
        holder.tv_phone.setText(items.get(position).getDriverPhone());

        holder.frame_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TagUtils.getTag(),"frame driver clicked");
                if(activity instanceof HomeActivity){
                    HomeActivity newHomeActivity= (HomeActivity) activity;
                    newHomeActivity.startFragment(R.id.frame_home,new DriverEditFragment(items.get(position)));
                }
            }
        });

        holder.ll_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof HomeActivity){
                    HomeActivity newHomeActivity= (HomeActivity) activity;
                    newHomeActivity.startFragment(R.id.frame_home,new DriverEditFragment(items.get(position)));
                }
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_driver_name)
        TextView tv_driver_name;
        @BindView(R.id.tv_phone)
        TextView tv_phone;
        @BindView(R.id.frame_driver)
        FrameLayout frame_driver;
        @BindView(R.id.cv_profile_pic)
        CircleImageView cv_profile_pic;
        @BindView(R.id.ll_edit)
        LinearLayout ll_edit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
