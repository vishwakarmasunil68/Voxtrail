package com.voxtrail.voxtrail.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.DeviceSelectActivity;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.gm.ObjectHistoryPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.pojo.pojo.VehicleStatusReportPOJO;
import com.voxtrail.voxtrail.pojo.travelsheet.TravelSheetDataPOJO;
import com.voxtrail.voxtrail.pojo.travelsheet.TravelSheetPOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.view.CustomScrollView;
import com.voxtrail.voxtrail.view.TouchableWrapper;
import com.voxtrail.voxtrail.view.WorkaroundMapFragment;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
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

public class VehicleStatusReportFragment extends FragmentController implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnMapReadyCallback, TouchableWrapper.UpdateMapAfterUserInterection {

    GoogleMap googleMap;

    @BindView(R.id.ic_back)
    ImageView ic_back;
    DevicePOJO devicePOJO;

    @BindView(R.id.card_vehicle)
    CardView card_vehicle;

    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_max_speed)
    TextView tv_max_speed;
    @BindView(R.id.tv_avg_speed)
    TextView tv_avg_speed;
    @BindView(R.id.tv_running_duration)
    TextView tv_running_duration;
    @BindView(R.id.tv_total_duration)
    TextView tv_total_duration;
    @BindView(R.id.tv_fuel_consumption)
    TextView tv_fuel_consumption;
    @BindView(R.id.tv_driven_km)
    TextView tv_driven_km;
    @BindView(R.id.tv_total_stop_time)
    TextView tv_total_stop_time;
    @BindView(R.id.tv_times_stopped)
    TextView tv_times_stopped;


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
    @BindView(R.id.scrollView)
    CustomScrollView scrollView;

    @BindView(R.id.tv_dt)
    TextView tv_dt;

    int total_vehicles = 0;
    int running_vehicles = 0;
    int idle_vehicles = 0;
    int offline_vehicles = 0;

    int max_speed = 0;
    int avg_speed = 0;
    int total_speed = 0;

    int[] colors = {Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW};

    public VehicleStatusReportFragment(DevicePOJO devicePOJO) {
        this.devicePOJO = devicePOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_vehicle_status_report, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WorkaroundMapFragment mapFragment = (WorkaroundMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapFragment.setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_search.setText(devicePOJO.getDeviceDetailPOJO().getName());

        card_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), DeviceSelectActivity.class), Constants.SELECT_DEVICE);
            }
        });

        setPlayBackLogic();

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

        iv_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingLogic();
            }
        });


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SELECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    VehicleStatusReportFragment.this.devicePOJO = devicePOJO;
                    tv_search.setText(devicePOJO.getDeviceDetailPOJO().getName());
                    getObjectHistory();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void getObjectHistory() {
        String from_dt = UtilityFunction.convertHistoryserverDateTime(startdatetime);
        String to_dt = UtilityFunction.convertHistoryserverDateTime(enddatetime);

        tv_running_duration.setText("-");
        tv_max_speed.setText("-");
        tv_avg_speed.setText("-");
        tv_total_duration.setText("-");
        tv_fuel_consumption.setText("-");
        tv_driven_km.setText("-");
        tv_total_stop_time.setText("-");

        if (googleMap != null) {
            googleMap.clear();
        }

        Log.d(TagUtils.getTag(),"start date time:-"+startdatetime);
        Log.d(TagUtils.getTag(),"end date time:-"+enddatetime);

        long hours = UtilityFunction.getDifferenceInHour(startdatetime, enddatetime);
        Log.d(TagUtils.getTag(), "hours :- " + hours);
        if (hours <= 48) {
            showProgressBar();
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("imei", devicePOJO.getImei()));
            nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
            nameValuePairs.add(new BasicNameValuePair("dtf", UtilityFunction.convertHistoryserverDateTime(startdatetime)));
            nameValuePairs.add(new BasicNameValuePair("dtt", UtilityFunction.convertHistoryserverDateTime(enddatetime)));
            nameValuePairs.add(new BasicNameValuePair("stop_duration", "5"));
            nameValuePairs.add(new BasicNameValuePair("show_coordinates", "true"));
            nameValuePairs.add(new BasicNameValuePair("show_addresses", "false"));
            nameValuePairs.add(new BasicNameValuePair("zones_addresses", "false"));
            String data_items = "[\"time_a\",\"position_a\",\"time_b\",\"position_b\",\"duration\",\"route_length\",\"fuel_consumption\",\"fuel_cost\",\"total\"]";
            nameValuePairs.add(new BasicNameValuePair("data_items", data_items));

            new WebServiceBaseResponse<VehicleStatusReportPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<VehicleStatusReportPOJO>() {
                @Override
                public void onGetMsg(ResponsePOJO<VehicleStatusReportPOJO> responsePOJO) {
                    dismissProgressBar();
                    try {
                        if (responsePOJO.isSuccess()) {
                            updateObjectCards(responsePOJO.getResult());
                            slidingLogic();
                        } else {
                            ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, VehicleStatusReportPOJO.class, "GET_DEVICE_HISTORY", false).execute(WebServicesUrls.TRAVEL_SHEET_REPORT_API);
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Time Period should not be greater than 2 day");
        }
    }

    private void updateObjectCards(VehicleStatusReportPOJO vehicleStatusReportPOJO) {

        try {
            TravelSheetPOJO travelSheetPOJO = vehicleStatusReportPOJO.getTravelSheetPOJO();
            tv_running_duration.setText(String.valueOf(UtilityFunction.getDurationString(Long.parseLong(travelSheetPOJO.getDrivesDurationTime()))));
            tv_max_speed.setText(String.valueOf(travelSheetPOJO.getTopSpeed()) + " Km/h");
            tv_avg_speed.setText(String.valueOf(String.format("%.2f", Double.parseDouble(travelSheetPOJO.getAvgSpeed()))) + " Km/h");
            tv_total_duration.setText(String.valueOf(UtilityFunction.getDurationString(Long.parseLong(travelSheetPOJO.getDrivesDurationTime()) + Long.parseLong(travelSheetPOJO.getStopsDurationTime()))));
            tv_fuel_consumption.setText(String.valueOf(String.format("%.2f", Math.abs(Double.parseDouble(travelSheetPOJO.getFuelConsumption()))) + " L"));
            tv_driven_km.setText(String.valueOf(String.format("%.2f", Math.abs(Double.parseDouble(travelSheetPOJO.getRouteLength())))) + " Km");
            tv_total_stop_time.setText(String.valueOf(UtilityFunction.getDurationString(Math.abs(Long.parseLong(travelSheetPOJO.getStopsDurationTime())))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<ObjectHistoryPOJO> deviceLocationPOJOS = vehicleStatusReportPOJO.getObjectHistoryPOJOS();

            List<List<ObjectHistoryPOJO>> objecLists = new ArrayList<>();

            for (int i = 0; i < vehicleStatusReportPOJO.getTravelSheetPOJO().getTravelSheetDataPOJOS().size(); i++) {
                TravelSheetDataPOJO travelSheetDataPOJO = vehicleStatusReportPOJO.getTravelSheetPOJO().getTravelSheetDataPOJOS().get(i);
                String startingPos = travelSheetDataPOJO.getPositionA();
                String endingPos = travelSheetDataPOJO.getPositionB();

                objecLists.add(getObjectHistoryList(startingPos, endingPos, deviceLocationPOJOS));

            }
//            Log.d(TagUtils.getTag(),"object lists:-"+objecLists.toString());
//            for (List<ObjectHistoryPOJO> objectHistoryPOJOS : objecLists) {
//                Log.d(TagUtils.getTag(), "starting position:-" + objectHistoryPOJOS.get(0).toString() + "   ending position:-" + objectHistoryPOJOS.get(objectHistoryPOJOS.size() - 1).toString());
//            }

            if (objecLists.size() > 0) {
                for (int i = 0; i < objecLists.size(); i++) {
                    if ((i + 1) == objecLists.size()) {
                        showHistoryTripMarkers(i, objecLists.get(i), true);
                    } else {
                        showHistoryTripMarkers(i, objecLists.get(i), false);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ObjectHistoryPOJO> getObjectHistoryList(String startingPos, String endingPos, List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        boolean is_started = false;
        List<ObjectHistoryPOJO> newObjectHistoryPOJOS = new ArrayList<>();
        for (ObjectHistoryPOJO objectHistoryPOJO : objectHistoryPOJOS) {
            if (!is_started) {
                if ((objectHistoryPOJO.getLat() + "," + objectHistoryPOJO.getLng()).equals(startingPos)) {
                    newObjectHistoryPOJOS.add(objectHistoryPOJO);
                    is_started = true;
                }
            } else {
                newObjectHistoryPOJOS.add(objectHistoryPOJO);
                if ((objectHistoryPOJO.getLat() + "," + objectHistoryPOJO.getLng()).equalsIgnoreCase(endingPos)) {
//                    newObjectHistoryPOJOS.add(objectHistoryPOJO);
                    return newObjectHistoryPOJOS;
                }
            }
        }

        return newObjectHistoryPOJOS;
    }

    public void showHistoryTripMarkers(int position, List<ObjectHistoryPOJO> objectHistoryPOJOS, boolean is_zoom) {
        List<LatLng> latLngs = new ArrayList<>();
        for (ObjectHistoryPOJO objectHistoryPOJO : objectHistoryPOJOS) {
            latLngs.add(new LatLng(Double.parseDouble(objectHistoryPOJO.getLat()), Double.parseDouble(objectHistoryPOJO.getLng())));
        }
        if (latLngs.size() > 0) {
            showRoute(position, latLngs, is_zoom);
        }
    }

    public void showTripMarkers(List<ObjectHistoryPOJO> objectHistoryPOJOS, List<String> runningStrings) {
        for (int j = 0; j < runningStrings.size(); j++) {
            String runningstring = runningStrings.get(j);
            int startIndex = Integer.parseInt(runningstring.split(",")[0]);
            int endIndex = Integer.parseInt(runningstring.split(",")[1]);
            List<LatLng> latLngs = new ArrayList<>();
            for (int i = startIndex; i < (endIndex + 1); i++) {
                latLngs.add(new LatLng(Double.parseDouble(objectHistoryPOJOS.get(i).getLat()), Double.parseDouble(objectHistoryPOJOS.get(i).getLng())));
            }
            boolean zoom = false;
            if (j == 0) {
                zoom = true;
            }
//            showRoute(latLngs, zoom);
        }
    }

    public void showRoute(int position, List<LatLng> latLngs, boolean zoom) {
        if (latLngs.size() > 0) {
            PolylineOptions lineOptions = new PolylineOptions();
            lineOptions.addAll(latLngs);
            lineOptions.width(10);
            lineOptions.color(colors[position % 7]);

            MarkerOptions startmarkerOptions = new MarkerOptions().position(latLngs.get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).anchor(0.5f, 0.5f);
            if (zoom) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 15));
            }
            googleMap.addMarker(startmarkerOptions);

            MarkerOptions endmarkerOptions = new MarkerOptions().position(latLngs.get(latLngs.size() - 1)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).anchor(0.5f, 0.5f);
            googleMap.addMarker(endmarkerOptions);
            googleMap.addPolyline(lineOptions);
        }

    }


    public void clearLists() {
        running_duration = 0;
        starting_index = -1;
        runningStrings.clear();
        stoppedStrings.clear();
    }

    int maxSpeed = 0;

    public int calculateMaxSpeed(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        maxSpeed = 0;
        for (ObjectHistoryPOJO objectHistoryPOJO : objectHistoryPOJOS) {
            if (!objectHistoryPOJO.getSpeed().equals("0")) {
                if (maxSpeed < (Integer.parseInt(objectHistoryPOJO.getSpeed()))) {
                    maxSpeed = Integer.parseInt(objectHistoryPOJO.getSpeed());
                }
            }
        }

        return maxSpeed;
    }

    long running_duration = 0;
    int kmInLitre = 20;

    public double calcAvgSpeed(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        int total_speed = 0;
        int total_interval = 0;
        running_duration = 0;
        for (String runningString : runningStrings) {
            running_duration += UtilityFunction.getDifferenceBtwTwoTimeInSec(objectHistoryPOJOS.get(Integer.parseInt(runningString.split(",")[1])).getDtTracker(), objectHistoryPOJOS.get(Integer.parseInt(runningString.split(",")[0])).getDtTracker());
            int startIndex = Integer.parseInt(runningString.split(",")[0]);
            int endIndex = Integer.parseInt(runningString.split(",")[1]);
            for (int i = startIndex; i < endIndex; i++) {
                total_speed = total_speed + Integer.parseInt(objectHistoryPOJOS.get(i).getSpeed());
                total_interval++;
            }
        }
        return ((double) total_speed / (double) total_interval);
    }

    public long getTotalDuration(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        if (objectHistoryPOJOS.size() > 0) {
            return (UtilityFunction.getDifferenceBtwTwoTimeInSec(objectHistoryPOJOS.get(objectHistoryPOJOS.size() - 1).getDtTracker(), objectHistoryPOJOS.get(0).getDtTracker()));
        }
        return 0;
    }

    public double getDriveDistance(long timeinSec, double avg_Speed) {
        return avg_Speed * (timeinSec / 3600);
    }

    public void removeDuplicacy(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {

            for (int j = i + 1; j < strings.size(); j++) {
                if (strings.get(i).equals(strings.get(j))) {
                    strings.remove(j);
                    j--;
                }
            }
        }
    }

    List<String> runningStrings = new ArrayList<>();
    List<String> stoppedStrings = new ArrayList<>();
    int starting_index = -1;

    public void checkForRunningStatus(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        for (int i = 0; i < objectHistoryPOJOS.size(); i++) {
            if (!objectHistoryPOJOS.get(i).getSpeed().equals("0")) {
                if (starting_index == -1) {
                    starting_index = i;
                }
                int next_stop_index = checkForZeroSpeedIndex(objectHistoryPOJOS, i);
                if (next_stop_index != -1) {
                    int next_speed_index = checkForNextSpeed(objectHistoryPOJOS, next_stop_index);
                    if (next_speed_index == -1) {
                        runningStrings.add(starting_index + "," + (next_stop_index - 1));
                        i = objectHistoryPOJOS.size();
                    } else {
                        long timeDiff = UtilityFunction.getDifferenceInMinInt(objectHistoryPOJOS, next_stop_index, next_speed_index);
                        if (timeDiff > 5) {
                            runningStrings.add(starting_index + "," + (next_stop_index - 1));
                            stoppedStrings.add(next_stop_index + "," + next_speed_index);
                            starting_index = -1;
                        } else {

                        }
                        i = next_speed_index;
                    }
                } else {
                    runningStrings.add(starting_index + "," + (objectHistoryPOJOS.size() - 1));
                }
            }
        }
    }

    public int checkForZeroSpeedIndex(List<ObjectHistoryPOJO> objectHistoryPOJOS, int starting_index) {
        if ((starting_index + 1) != objectHistoryPOJOS.size()) {
            for (int i = (starting_index + 1); i < objectHistoryPOJOS.size(); i++) {
                if (objectHistoryPOJOS.get(i).getSpeed().equals("0")) {
//                    int next_speed_index=checkForNextSpeed(objectHistoryPOJOS,i);
                    return i;
                }
            }
        }
        return -1;
    }

    public int checkForNextSpeed(List<ObjectHistoryPOJO> objectHistoryPOJOS, int starting_index) {
        if ((starting_index + 1) != objectHistoryPOJOS.size()) {
            for (int i = (starting_index + 1); i < objectHistoryPOJOS.size(); i++) {
                if (!objectHistoryPOJOS.get(i).getSpeed().equals("0")) {
                    return i;
                }
            }

        }
        return -1;
    }


    String startdatetime = "";
    String enddatetime = "";
    TextView tv_date_time;
    boolean is_custom_DT = false;

    boolean is_stating_dt = false;
    List<CheckBox> checkBoxes = new ArrayList<>();

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
                        VehicleStatusReportFragment.this,
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
                        VehicleStatusReportFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        check_today.setChecked(true);
        tv_start_datetime.setText(startdatetime);
        tv_end_datetime.setText(enddatetime);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getObjectHistory();
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


    String selected_date = "";

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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
                VehicleStatusReportFragment.this,
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
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (devicePOJO != null) {
            getObjectHistory();
        }
    }

    @Override
    public void onUpdateMapAfterUserInterection() {
        Log.d(TagUtils.getTag(), "map updated");
    }
}
