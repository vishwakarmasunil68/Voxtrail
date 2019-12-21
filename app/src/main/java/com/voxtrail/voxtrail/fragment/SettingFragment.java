package com.voxtrail.voxtrail.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.suke.widget.SwitchButton;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.DistanceMetricesFragment;
import com.voxtrail.voxtrail.fragment.driver.DriverListFragment;
import com.voxtrail.voxtrail.fragment.events.EventListFragment;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;

import butterknife.BindView;

public class SettingFragment extends FragmentController{

    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.switch_receive_notification)
    SwitchButton switch_receive_notification;
    @BindView(R.id.ll_time_zone)
    LinearLayout ll_time_zone;
    @BindView(R.id.ll_monitor)
    LinearLayout ll_monitor;
    @BindView(R.id.ll_tracking)
    LinearLayout ll_tracking;
    @BindView(R.id.ll_distance)
    LinearLayout ll_distance;
    @BindView(R.id.ll_pressure)
    LinearLayout ll_pressure;
    @BindView(R.id.ll_temperature)
    LinearLayout ll_temperature;
    @BindView(R.id.ll_volume)
    LinearLayout ll_volume;
    @BindView(R.id.ll_events)
    LinearLayout ll_events;
    @BindView(R.id.ll_driver)
    LinearLayout ll_driver;

//    String monitor_refresh_rate="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_setting,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        monitor_refresh_rate= Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.MONITOR_REFRESH_RATE,"");

        if(Pref.GetBooleanPref(getActivity().getApplicationContext(),StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION,false)){
            switch_receive_notification.setChecked(true);
        }else{
            switch_receive_notification.setChecked(false);
        }

        switch_receive_notification.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(),StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION,isChecked);
            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ll_time_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new TimeZoneFragment());
            }
        });

        ll_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new MonitorRefreshRateFragment());
            }
        });
        ll_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new DistanceMetricesFragment());
            }
        });
        ll_pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new PressureMetricesFragment());
            }
        });
        ll_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new TemperatureMetricesFragment());
            }
        });
        ll_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new VolumeMetricesFragment());
            }
        });
        ll_tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new TrackingRefreshRateFragment());
            }
        });

        ll_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityManager.startFragment(R.id.frame_home, new EventListFragment());
            }
        });
        ll_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityManager.startFragment(R.id.frame_home, new DriverListFragment());
            }
        });

    }
}
