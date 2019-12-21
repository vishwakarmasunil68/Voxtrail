package com.voxtrail.voxtrail.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.DeviceSelectAdapter;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceSelectActivity extends AppCompatActivity {

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rv_device_list)
    RecyclerView rv_device_list;
    @BindView(R.id.ic_back)
    ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_device_select);
        ButterKnife.bind(this);
        attachAdapter();
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_search.getText().toString().length()>0){
                    filterDevice();
                }else{
                    if(Constants.devicePOJOS!=null&&Constants.devicePOJOS.size()>0) {
                        deviceStrings.addAll(Constants.devicePOJOS);
                        deviceListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void filterDevice(){
        if(Constants.devicePOJOS!=null&&Constants.devicePOJOS.size()>0){
            List<DevicePOJO> devicePOJOS=new ArrayList<>();
            for(DevicePOJO devicePOJO:Constants.devicePOJOS){
                if(devicePOJO.getImei().toLowerCase().contains(et_search.getText().toString().toLowerCase())
                        ||devicePOJO.getDeviceDetailPOJO().getName().toLowerCase().contains(et_search.getText().toString().toLowerCase())
                        ||devicePOJO.getDeviceDetailPOJO().getPlateNumber().toLowerCase().contains(et_search.getText().toString().toLowerCase())){
                    devicePOJOS.add(devicePOJO);
                }
            }
            if(devicePOJOS.size()>0){
                deviceStrings.clear();
                deviceStrings.addAll(devicePOJOS);
                deviceListAdapter.notifyDataSetChanged();;
            }
        }
    }

    DeviceSelectAdapter deviceListAdapter;
    List<DevicePOJO> deviceStrings = new ArrayList<>();

    public void attachAdapter() {

        if (Constants.devicePOJOS != null && Constants.devicePOJOS.size() > 0) {
            deviceStrings.addAll(Constants.devicePOJOS);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rv_device_list.setHasFixedSize(true);
            rv_device_list.setLayoutManager(linearLayoutManager);
            deviceListAdapter = new DeviceSelectAdapter(this, null, deviceStrings);
            rv_device_list.setAdapter(deviceListAdapter);
            rv_device_list.setNestedScrollingEnabled(false);
            rv_device_list.setItemAnimator(new DefaultItemAnimator());

            deviceListAdapter.setAdapterItemAdapter(new AdapterListener() {
                @Override
                public void onAdapterItemClickListener(int position) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("devicePOJO", deviceStrings.get(position));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.ic_header);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

}
