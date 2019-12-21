package com.voxtrail.voxtrail.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.PlayBackActivity;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.TagUtils;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class DeviceDataFragment2 extends FragmentController {

    @BindView(R.id.tv_immobilizer)
    TextView tv_immobilizer;
    @BindView(R.id.tv_odometer)
    TextView tv_odometer;
    @BindView(R.id.ll_playback)
    LinearLayout ll_playback;
    @BindView(R.id.ll_report)
    LinearLayout ll_report;
    @BindView(R.id.ll_alerts)
    LinearLayout ll_alerts;

    DevicePOJO devicePOJO;

    public DeviceDataFragment2(DevicePOJO devicePOJO){
        this.devicePOJO=devicePOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_device_data_2, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), PlayBackActivity.class);
                intent.putExtra("devicePOJO",devicePOJO);
                startActivity(intent);
            }
        });

        ll_alerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new DeviceAlertFragment(devicePOJO));
            }
        });

        Log.d(TagUtils.getTag(),"odometer value:-"+devicePOJO.getDeviceDetailPOJO().getOdometer());
        try {
            tv_odometer.setText((String.format("%.2f", Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getOdometer()))) + " Km");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
