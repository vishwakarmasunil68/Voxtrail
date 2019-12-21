package com.voxtrail.voxtrail.fragment.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.SelectZoneActivity;
import com.voxtrail.voxtrail.adapter.NotificationAdapter;
import com.voxtrail.voxtrail.adapter.ZoneInOutAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.event.LastEventPOJO;
import com.voxtrail.voxtrail.pojo.geofence.GeoFenceCollectionPOJO;
import com.voxtrail.voxtrail.pojo.geofence.GeoFencePOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.pojo.report.GeneralInformationPOJO;
import com.voxtrail.voxtrail.pojo.report.ZoneInOutPOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.ResponseListCallback;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponseList;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class ZoneInOutReportFragment extends FragmentController implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ic_back)
    ImageView iv_back;
    DevicePOJO devicePOJO;

    @BindView(R.id.rv_zone_report)
    RecyclerView rv_zone_report;

    @BindView(R.id.tv_dt)
    TextView tv_dt;
    @BindView(R.id.check_today)
    CheckBox check_today;
    @BindView(R.id.check_yesterday)
    CheckBox check_yesterday;
    @BindView(R.id.check_hour)
    CheckBox check_hour;
    @BindView(R.id.check_user_defined)
    CheckBox check_user_defined;
    @BindView(R.id.ll_custom)
    LinearLayout ll_custom;
    @BindView(R.id.tv_start_datetime)
    TextView tv_start_datetime;
    @BindView(R.id.ll_start_datetime)
    LinearLayout ll_start_datetime;
    @BindView(R.id.ll_end_datetime)
    LinearLayout ll_end_datetime;
    @BindView(R.id.tv_end_datetime)
    TextView tv_end_datetime;
    @BindView(R.id.btn_ok)
    Button btn_ok;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;
    @BindView(R.id.iv_drop)
    ImageView iv_drop;
    @BindView(R.id.ll_zones)
    LinearLayout ll_zones;
    @BindView(R.id.tv_zones)
    TextView tv_zones;
    @BindView(R.id.tv_object_name)
    TextView tv_object_name;

    List<CheckBox> checkBoxes = new ArrayList<>();
    List<GeoFencePOJO> geoFencePOJOS = new ArrayList<>();

    public ZoneInOutReportFragment(DevicePOJO devicePOJO) {
        this.devicePOJO = devicePOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_zone_in_out, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        tv_object_name.setText(devicePOJO.getDeviceDetailPOJO().getName());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI();
            }
        });
        setPlayBackLogic();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sliding_layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState.name().equals("EXPANDED")) {
                    iv_drop.setImageResource(R.drawable.ic_down);
                } else {
                    iv_drop.setImageResource(R.drawable.ic_up);
                }
            }
        });

        callZoneAPI();
    }

    public void callZoneAPI() {
        activityManager.showProgressBar();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        new WebServiceBaseResponseList<GeoFencePOJO>(nameValuePairs, getActivity(), new ResponseListCallback<GeoFencePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<GeoFencePOJO> responseListPOJO) {
                activityManager.dismissProgressBar();
                try {
                    if (responseListPOJO.isSuccess()) {
                        geoFencePOJOS.clear();
                        for(GeoFencePOJO geoFencePOJO:responseListPOJO.getResultList()){
                            geoFencePOJO.setChecked(true);
                            geoFencePOJOS.add(geoFencePOJO);
                        }
                        tv_zones.setText((geoFencePOJOS.size())+" zones selected");
                        callAPI();
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, GeoFencePOJO.class, "GET_USER_ZONES", true).execute(WebServicesUrls.GET_USER_ZONES);
    }

    public void callAPI() {
        if(geoFencePOJOS.size()>0){
            List<GeoFencePOJO> selectedZones=new ArrayList<>();
            for(GeoFencePOJO geoFencePOJO:geoFencePOJOS){
                if(geoFencePOJO.isChecked()){
                    selectedZones.add(geoFencePOJO);
                }
            }

            if (selectedZones.size() > 0) {
                swipeRefreshLayout.setRefreshing(true);

                String zone_ids="";

                for(int i=0;i<selectedZones.size();i++){
                    if((i+1)==selectedZones.size()){
                        zone_ids+=selectedZones.get(i).getZoneId();
                    }else{
                        zone_ids+=selectedZones.get(i).getZoneId()+",";
                    }
                }

                Log.d(TagUtils.getTag(), "start DT:-" + startdatetime);
                Log.d(TagUtils.getTag(), "end DT:-" + enddatetime);

                tv_zones.setText((selectedZones.size())+" zones selected");

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("imei", devicePOJO.getImei()));
                nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
                nameValuePairs.add(new BasicNameValuePair("dtf", UtilityFunction.convertHistoryserverDateTime(startdatetime)));
                nameValuePairs.add(new BasicNameValuePair("dtt", UtilityFunction.convertHistoryserverDateTime(enddatetime)));
                nameValuePairs.add(new BasicNameValuePair("stop_duration", "5"));
                nameValuePairs.add(new BasicNameValuePair("show_coordinates", "true"));
                nameValuePairs.add(new BasicNameValuePair("show_addresses", "false"));
                nameValuePairs.add(new BasicNameValuePair("zones_addresses", "false"));
                nameValuePairs.add(new BasicNameValuePair("zone_ids", zone_ids));

                activityManager.showProgressBar();

                new WebServiceBaseResponseList<ZoneInOutPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<ZoneInOutPOJO>() {
                    @Override
                    public void onGetMsg(ResponseListPOJO<ZoneInOutPOJO> responseListPOJO) {
                        activityManager.dismissProgressBar();
                        swipeRefreshLayout.setRefreshing(false);
                        zoneInOutPOJOS.clear();
                        try{
                            if(responseListPOJO.isSuccess()){
                                zoneInOutPOJOS.addAll(responseListPOJO.getResultList());
                            }else{
                                ToastClass.showShortToast(getActivity().getApplicationContext(),"No Data Found");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        zoneInOutAdapter.notifyDataSetChanged();
                    }
                }, ZoneInOutPOJO.class, "ZONE_IN_OUT_REPORT", false).execute(WebServicesUrls.ZONE_IN_OUT_REPORT);
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select Zone");
                swipeRefreshLayout.setRefreshing(false);
            }
        }else{
            ToastClass.showShortToast(getActivity().getApplicationContext(), "No Zone Found");
        }

    }

    ZoneInOutAdapter zoneInOutAdapter;
    List<ZoneInOutPOJO> zoneInOutPOJOS = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_zone_report.setHasFixedSize(true);
        rv_zone_report.setLayoutManager(linearLayoutManager);
        zoneInOutAdapter = new ZoneInOutAdapter(getActivity(), null, zoneInOutPOJOS);
        rv_zone_report.setAdapter(zoneInOutAdapter);
        rv_zone_report.setNestedScrollingEnabled(false);
        rv_zone_report.setItemAnimator(new DefaultItemAnimator());

    }


    String startdatetime = "";
    String enddatetime = "";
    TextView tv_date_time;
    boolean is_custom_DT = false;

    boolean is_stating_dt = false;

    public void setPlayBackLogic() {
        checkBoxes.add(check_today);
        checkBoxes.add(check_yesterday);
        checkBoxes.add(check_hour);
        checkBoxes.add(check_user_defined);

        check_user_defined.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    is_custom_DT = true;
                    ll_custom.setVisibility(View.VISIBLE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_user_defined)) {
                            checkBox.setChecked(false);
                        }
                    }
                } else {
                    ll_custom.setVisibility(View.GONE);
                }
            }
        });

        check_hour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.GONE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_hour)) {
                            checkBox.setChecked(false);
                        }
                        getHourAgo();
                    }
                }
            }
        });

        check_today.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.GONE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_today)) {
                            checkBox.setChecked(false);
                        }
                    }
                    getTodayRange();
                }
            }
        });

        check_yesterday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.GONE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_yesterday)) {
                            checkBox.setChecked(false);
                        }
                    }
                    getYesterdayRange();
                }
            }
        });

        ll_start_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_date_time = tv_start_datetime;
                is_stating_dt = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ZoneInOutReportFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        ll_end_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_date_time = tv_end_datetime;
                is_stating_dt = false;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ZoneInOutReportFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        ll_zones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoFenceCollectionPOJO geoFenceCollectionPOJO=new GeoFenceCollectionPOJO();
                geoFenceCollectionPOJO.setGeoFencePOJOS(geoFencePOJOS);
                Intent i = new Intent(getActivity(), SelectZoneActivity.class);
                i.putExtra("geoFenceCollectionPOJO",geoFenceCollectionPOJO);
                startActivityForResult(i, 1);
            }
        });
        check_today.setChecked(true);
        tv_start_datetime.setText(startdatetime);
        tv_end_datetime.setText(enddatetime);
        slidingLogic();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingLogic();
                callAPI();
            }
        });

    }

    public void getTodayRange() {
        is_custom_DT = false;
        startdatetime = UtilityFunction.getCurrentDate() + " 00:00:00";
        enddatetime = UtilityFunction.getCurrentDate() + " 23:59:00";
        setDateTime();
    }

    public void getYesterdayRange() {
        is_custom_DT = false;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        startdatetime = dateFormat.format(cal.getTime()) + " 00:00:00";
        enddatetime = dateFormat.format(cal.getTime()) + " 23:59:00";
        setDateTime();
    }

    public void getHourAgo() {
        is_custom_DT = false;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        startdatetime = dateFormat.format(cal.getTime());
        enddatetime = UtilityFunction.getCurrentDateTime();
        setDateTime();
    }


    public void slidingLogic() {
        Log.d(TagUtils.getTag(), "sliding logic");
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    String selected_date = "";

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

        String date = day + "-" + month + "-" + year;
        selected_date = date;
        openTimePicker();
    }

    public void openTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                ZoneInOutReportFragment.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getActivity().getFragmentManager(), "Select Time");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        is_custom_DT = true;
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = hourString + ":" + minuteString + ":" + secondString;
        tv_date_time.setText(selected_date + " " + time);
        if (is_stating_dt) {
            startdatetime = selected_date + " " + time;
        } else {
            enddatetime = selected_date + " " + time;
        }
        setDateTime();
    }

    public void setDateTime() {
        tv_dt.setText(startdatetime + "  -  " + enddatetime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                GeoFenceCollectionPOJO geoFenceCollectionPOJO= (GeoFenceCollectionPOJO) data.getSerializableExtra("geoFenceCollectionPOJO");

                Log.d(TagUtils.getTag(),"passed geofence:-"+geoFenceCollectionPOJO.getGeoFencePOJOS().toString());

                for(GeoFencePOJO geoFencePOJO:geoFencePOJOS){
                    geoFencePOJO.setChecked(false);
                }

                for(GeoFencePOJO geoFencePOJO:geoFenceCollectionPOJO.getGeoFencePOJOS()){
                    for(GeoFencePOJO prevGeoFencePOJO:geoFencePOJOS){
                        if(geoFencePOJO.getZoneId().equalsIgnoreCase(prevGeoFencePOJO.getZoneId())){
                            prevGeoFencePOJO.setChecked(geoFencePOJO.isChecked());
                        }
                    }
                }
                Log.d(TagUtils.getTag(),"geofencepojo:-"+geoFencePOJOS.toString());

                int count=0;
                for(GeoFencePOJO geoFencePOJO:geoFencePOJOS){
                    if(geoFencePOJO.isChecked()){
                        count++;
                    }
                }

                tv_zones.setText(count+" Zones Selected");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
