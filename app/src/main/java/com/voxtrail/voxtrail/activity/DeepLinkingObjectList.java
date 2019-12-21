package com.voxtrail.voxtrail.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.DeviceListAdapter;
import com.voxtrail.voxtrail.pojo.DeviceUpdatedPOJO;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.pojo.sharing.ObjectSharingPOJO;
import com.voxtrail.voxtrail.pojo.user.UserDetail;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.ResponseListCallback;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponseList;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeepLinkingObjectList extends AppCompatActivity {

    String unique_key = "";
    @BindView(R.id.rv_objects)
    RecyclerView rv_objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_linking_object_list);
        ButterKnife.bind(this);
        attachAdapter();
        unique_key = getIntent().getStringExtra("unique_key");
        if (unique_key != null) {
            fetchObjectList();
        }
    }

    public boolean getTimeDifference(String time){
        try{

            Date d1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            Date d2=new Date();

            long diffMs = d1.getTime() - d2.getTime();
            long diffSec = diffMs / 1000;
            long min = diffSec / 60;
            long sec = diffSec % 60;
//            String str="The difference is "+min+" minutes and "+sec+" seconds.";
            Log.d(TagUtils.getTag(),"difference:-"+diffMs);
            if(diffMs<0||diffMs==0){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void showTokenExpiredPOPUP(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Token Expired").setMessage("The Token is Expired..!!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                dialog.dismiss();
            }
        }).show();
    }

    public void fetchObjectList() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("unique_reference", unique_key));
        new WebServiceBaseResponse<ObjectSharingPOJO>(nameValuePairs, getApplicationContext(), new ResponseCallBack<ObjectSharingPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<ObjectSharingPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {

                        if(getTimeDifference(responsePOJO.getResult().getSharingPOJO().getValidTo())){
                            Constants.devicePOJOS.clear();
                            Constants.devicePOJOS.addAll(responsePOJO.getResult().getDevicePOJOS());
                            Constants.userDetail=new UserDetail();
                            Constants.userDetail.setId(Constants.devicePOJOS.get(0).getUserId());
                            for (int i = 0; i < Constants.devicePOJOS.size(); i++) {
                                if (i == (Constants.devicePOJOS.size() - 1)) {
                                    device_imeis += "'" + Constants.devicePOJOS.get(i).getImei() + "'";
                                } else {
                                    device_imeis += "'" + Constants.devicePOJOS.get(i).getImei() + "',";
                                }
                            }
                            deviceListAdapter.notifyDataSetChanged();
                            getVehicleList();
                        }else{
                            showTokenExpiredPOPUP();
                        }
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), "No Object Found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ObjectSharingPOJO.class, "GET_DEVICES", false).execute(WebServicesUrls.GET_SHARED_OBJECTS);
    }


    public void getVehicleList() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("imeis", device_imeis));
        new WebServiceBaseResponseList<DeviceUpdatedPOJO>(nameValuePairs, this, new ResponseListCallback<DeviceUpdatedPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<DeviceUpdatedPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        updateDeviceLocation(responseListPOJO.getResultList());
                    } else {
//                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, DeviceUpdatedPOJO.class, "GET_DEVICE_LIST", false).execute(WebServicesUrls.OBJECT_UPDATE);
    }


    public void updateDeviceLocation(List<DeviceUpdatedPOJO> deviceUpdatedPOJOList) {
        try {
            if (Constants.devicePOJOS.size() > 0 && deviceUpdatedPOJOList.size() > 0) {
                for (DeviceUpdatedPOJO deviceUpdatedPOJO : deviceUpdatedPOJOList) {
                    for (int i = 0; i < Constants.devicePOJOS.size(); i++) {
                        DevicePOJO devicePOJO = Constants.devicePOJOS.get(i);
                        if (devicePOJO.getImei().equalsIgnoreCase(deviceUpdatedPOJO.getImei())) {
                            float bearing = -1;
                            try {
                                LatLng beginlaLatLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));
                                LatLng endLatLng = new LatLng(Double.parseDouble(deviceUpdatedPOJO.getLat()), Double.parseDouble(deviceUpdatedPOJO.getLng()));

//                                if (deviceUpdatedPOJO.getImei().equals("860016020904418")) {
//                                    Log.d(TagUtils.getTag(), "device updated info:- begin:-" + beginlaLatLng.latitude + "," + beginlaLatLng.longitude);
//                                    Log.d(TagUtils.getTag(), "device updated info:- end:-" + endLatLng.latitude + "," + endLatLng.longitude);
//                                }

                                if ((beginlaLatLng.latitude != endLatLng.latitude) && (beginlaLatLng.longitude != endLatLng.longitude)) {
                                    updateDeviceActivity(devicePOJO.getImei(), deviceUpdatedPOJO);
                                    try {
                                        if (devicePOJO.getDeviceDetailPOJO() != null) {


                                            devicePOJO.getDeviceDetailPOJO().setLat(deviceUpdatedPOJO.getLat());
                                            devicePOJO.getDeviceDetailPOJO().setLng(deviceUpdatedPOJO.getLng());
                                            devicePOJO.getDeviceDetailPOJO().setSpeed(deviceUpdatedPOJO.getSpeed());
                                            devicePOJO.getDeviceDetailPOJO().setAltitude(deviceUpdatedPOJO.getAltitude());
                                            devicePOJO.getDeviceDetailPOJO().setDtTracker(deviceUpdatedPOJO.getNormalDttracker());


                                            deviceListAdapter.notifyDataSetChanged();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                setTimer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateDeviceActivity(String imei, DeviceUpdatedPOJO deviceUpdatedPOJO) {
        Log.d(TagUtils.getTag(), "device updated info:-" + imei);
        Intent intent = new Intent(StringUtils.UPDATE_DEVICE_ACTIVITY);

        //put whatever data you want to send, if any
        intent.putExtra("imei", imei);
//        intent.putExtra("data", message);
        intent.putExtra("data", deviceUpdatedPOJO);

        //send broadcast
        sendBroadcast(intent);
    }

    public int time = -1;
    public boolean is_timer_running = false;

    public void setTimer() {

        switch (Pref.GetStringPref(getApplicationContext(), StringUtils.TRACKING_REFRESH_RATE, StringUtils.REF_10_SEC)) {
            case StringUtils.REF_DISABLE_AUTO_REFRESH:
                time = -1;
                break;
            case StringUtils.REF_05_SEC:
                time = 6000;
                break;
            case StringUtils.REF_10_SEC:
                time = 10000;
                break;
            case StringUtils.REF_30_SEC:
                time = 30000;
                break;
            case StringUtils.REF_1_MIN:
                time = 60000;
                break;
            case StringUtils.REF_3_MIN:
                time = 180000;
                break;
            case StringUtils.REF_5_MIN:
                time = 300000;
                break;
            default:
                is_timer_running = false;
        }

        if (time != -1) {
            is_timer_running = true;
        } else {
            is_timer_running = false;
        }

        if (time != -1) {
            int sec = (int) (time / 1000);

            new CountDownTimer(time, 1000) {

                @Override
                public void onTick(long l) {
//                        Log.d(TagUtils.getTag(),"timer:-"+l);
                    int sec = (int) (l / 1000);
                }

                @Override
                public void onFinish() {
                    getVehicleList();
                }
            }.start();
        }
    }



    public DeviceListAdapter deviceListAdapter;
    String device_imeis = "";

    public void attachAdapter() {
        Constants.devicePOJOS.clear();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_objects.setHasFixedSize(true);
        rv_objects.setLayoutManager(linearLayoutManager);
        deviceListAdapter = new DeviceListAdapter(this, null, Constants.devicePOJOS);
        rv_objects.setAdapter(deviceListAdapter);
        rv_objects.setNestedScrollingEnabled(false);
        rv_objects.setItemAnimator(new DefaultItemAnimator());
    }


}
