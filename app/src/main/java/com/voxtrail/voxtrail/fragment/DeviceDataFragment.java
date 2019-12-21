package com.voxtrail.voxtrail.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.TagUtils;

import org.json.JSONObject;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class DeviceDataFragment extends FragmentController{

    @BindView(R.id.tv_ignition)
    TextView tv_ignition;
    @BindView(R.id.tv_speed)
    TextView tv_speed;
    @BindView(R.id.tv_ac)
    TextView tv_ac;
    @BindView(R.id.tv_distance)
    TextView tv_distance;

    DevicePOJO devicePOJO;

    public DeviceDataFragment(DevicePOJO devicePOJO){
        this.devicePOJO=devicePOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_device_data,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            JSONObject jsonObject=new JSONObject(devicePOJO.getDeviceDetailPOJO().getParams());
            if (jsonObject.optString("acc").equals("0")) {
                tv_ignition.setText("OFF");
            }else{
                tv_ignition.setText("ON");
            }
        }catch (Exception e){
            e.printStackTrace();
            tv_ignition.setText("OFF");
        }
//        tv_ignition.setText();
        tv_speed.setText(devicePOJO.getDeviceDetailPOJO().getSpeed()+" km/h");
        Log.d(TagUtils.getTag(),"odometer value:-"+devicePOJO.getDeviceDetailPOJO().getOdometer());
        try {
            tv_distance.setText((String.format("%.2f", Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getOdometer()))) + " Km");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
