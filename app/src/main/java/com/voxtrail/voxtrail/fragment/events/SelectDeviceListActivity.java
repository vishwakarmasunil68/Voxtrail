package com.voxtrail.voxtrail.fragment.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.ShareDeviceSelectAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.ActivityManager;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.device.DeviceCollectionPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectDeviceListActivity extends ActivityManager {

    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rv_device_list)
    RecyclerView rv_device_list;
    @BindView(R.id.iv_select)
    ImageView iv_select;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device);
        ButterKnife.bind(this);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        attachAdapter();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_search.getText().toString().length() > 0) {
                    filterDevice();
                } else {
                    if (Constants.devicePOJOS != null && Constants.devicePOJOS.size() > 0) {
                        deviceStrings.addAll(Constants.devicePOJOS);
                        deviceListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSelectedDevice();
            }
        });

    }

    public void checkSelectedDevice(){

        List<DevicePOJO> selecteDevices=new ArrayList<>();

        for(DevicePOJO devicePOJO:Constants.devicePOJOS){
            if(devicePOJO.isIs_selected()){
                Log.d(TagUtils.getTag(),"selected device:-"+devicePOJO.getDeviceDetailPOJO().getName());
                selecteDevices.add(devicePOJO);
            }
        }

        if(selecteDevices.size()>0){
            DeviceCollectionPOJO deviceCollectionPOJO=new DeviceCollectionPOJO();
            deviceCollectionPOJO.setDevicePOJOS(selecteDevices);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("deviceCollectionPOJO",deviceCollectionPOJO);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }else{
            ToastClass.showShortToast(getApplicationContext(),"Please Select Device");
        }

        Log.d(TagUtils.getTag(),"selected device size:- "+selecteDevices.size());

    }


    public void filterDevice() {
        if (Constants.devicePOJOS != null && Constants.devicePOJOS.size() > 0) {
            List<DevicePOJO> devicePOJOS = new ArrayList<>();
            for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
                if (devicePOJO.getImei().toLowerCase().contains(et_search.getText().toString().toLowerCase())
                        || devicePOJO.getDeviceDetailPOJO().getName().toLowerCase().contains(et_search.getText().toString().toLowerCase())
                        || devicePOJO.getDeviceDetailPOJO().getPlateNumber().toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                    devicePOJOS.add(devicePOJO);
                }
            }
            if (devicePOJOS.size() > 0) {
                deviceStrings.clear();
                deviceStrings.addAll(devicePOJOS);
                deviceListAdapter.notifyDataSetChanged();
            }
        }
    }

    ShareDeviceSelectAdapter deviceListAdapter;
    List<DevicePOJO> deviceStrings = new ArrayList<>();

    public void attachAdapter() {
        if (Constants.devicePOJOS != null && Constants.devicePOJOS.size() > 0) {
//            for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
//                devicePOJO.setIs_selected(false);
//                deviceStrings.add(devicePOJO);
//                if(this.devicePOJO!=null&&this.devicePOJO.getImei().equals(devicePOJO.getImei())){
//                    devicePOJO.setIs_selected(true);
//                }
//            }

            deviceStrings.addAll(Constants.devicePOJOS);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rv_device_list.setHasFixedSize(true);
            rv_device_list.setLayoutManager(linearLayoutManager);
            deviceListAdapter = new ShareDeviceSelectAdapter(this, null, deviceStrings);
            rv_device_list.setAdapter(deviceListAdapter);
            rv_device_list.setNestedScrollingEnabled(false);
            rv_device_list.setItemAnimator(new DefaultItemAnimator());

            deviceListAdapter.setAdapterItemAdapter(new AdapterListener() {
                @Override
                public void onAdapterItemClickListener(int position) {

                }
            });


        }
    }
}
