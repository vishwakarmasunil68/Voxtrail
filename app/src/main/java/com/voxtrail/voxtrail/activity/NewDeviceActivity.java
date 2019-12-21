package com.voxtrail.voxtrail.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragment.DeviceAlertFragment;
import com.voxtrail.voxtrail.fragment.NotificationFragment;
import com.voxtrail.voxtrail.fragment.ReportFragment;
import com.voxtrail.voxtrail.fragment.report.DriverScoreFragment;
import com.voxtrail.voxtrail.fragmentcontroller.ActivityManager;
import com.voxtrail.voxtrail.listener.DataParser;
import com.voxtrail.voxtrail.pojo.DeviceUpdatedPOJO;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.pojo.DeviceDetailPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.pojo.travelsheet.TravelSheetPOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.GetWebServices;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;
import com.xzl.marquee.library.MarqueeView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewDeviceActivity extends ActivityManager implements OnMapReadyCallback {

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;
    @BindView(R.id.tv_object_name)
    TextView tv_object_name;
    @BindView(R.id.tv_address)
    MarqueeView tv_address;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_speed)
    TextView tv_speed;
    @BindView(R.id.tv_km)
    TextView tv_km;
    @BindView(R.id.ll_alerts)
    LinearLayout ll_alerts;
    @BindView(R.id.ll_playback)
    LinearLayout ll_playback;
    @BindView(R.id.ll_report)
    LinearLayout ll_report;
    @BindView(R.id.iv_map_type)
    ImageView iv_map_type;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.iv_phone_location)
    ImageView iv_phone_location;
    @BindView(R.id.iv_equipment_location)
    ImageView iv_equipment_location;
    @BindView(R.id.iv_navigation)
    ImageView iv_navigation;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.iv_moving_status)
    ImageView iv_moving_status;
    @BindView(R.id.tv_odometer)
    TextView tv_odometer;
    @BindView(R.id.tv_last_trip)
    TextView tv_last_trip;
    @BindView(R.id.tv_driver_score)
    TextView tv_driver_score;
    @BindView(R.id.iv_battery)
    ImageView iv_battery;
    @BindView(R.id.iv_signal)
    ImageView iv_signal;
    @BindView(R.id.iv_ignition)
    ImageView iv_ignition;
    @BindView(R.id.ll_driver_score)
    LinearLayout ll_driver_score;

    GoogleMap googleMap;

    double current_lat;
    double current_lng;

    DevicePOJO devicePOJO;

    boolean is_loaded = false;
    LatLng previousLatLng = null;
    Marker lastMarker = null;
    Polyline lastPolyline = null;

    boolean is_satellite_view = false;
    boolean is_traffic_view = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_new_device);
        ButterKnife.bind(this);

        devicePOJO = (DevicePOJO) getIntent().getSerializableExtra("devicePOJO");
        if (devicePOJO != null) {
            Log.d(TagUtils.getTag(),"lat:-"+devicePOJO.getDeviceDetailPOJO().getLat());
            Log.d(TagUtils.getTag(),"lng:-"+devicePOJO.getDeviceDetailPOJO().getLng());
            previousLatLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));
        }
        updateDeviceData(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        iv_map_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMapView();
                if (is_satellite_view) {
                    iv_map_type.setImageResource(R.drawable.ic_satellite_view);
                } else {
                    iv_map_type.setImageResource(R.drawable.ic_normal_map);
                }
            }
        });

        tv_title.setText(devicePOJO.getDeviceDetailPOJO().getName());
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_phone_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_lat != 0 && current_lng != 0) {
//                    animateToCurrentLocation(current_lat, current_lng);
//                    animateToCurrentLocation(28.5316888, 77.2126535);
                    if (lastDeviceMarker != null) {
                        lastDeviceMarker.remove();
                        lastDeviceMarker = null;
                    } else {
                        setCurrentLocationMarker(current_lat, current_lng);
                    }
                }
            }
        });

        iv_equipment_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double current_lat = Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat());
                    double current_lng = Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng());

                    animateToCurrentLocation(current_lat, current_lng);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        iv_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (is_route_showing) {
                        if (navigation_polyline != null) {
                            navigation_polyline.remove();
                        }
                        is_route_showing = false;
                    } else {
                        setCurrentLocationMarker(current_lat, current_lng);
                        double device_current_lat = Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat());
                        double device_current_lng = Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng());

                        LatLng phoneLatLng = new LatLng(current_lat, current_lng);
                        LatLng deviceLatLng = new LatLng(device_current_lat, device_current_lng);
                        String device_url = WebServicesUrls.getMapsApiDirectionsUrl(phoneLatLng, deviceLatLng);
                        Log.d(TagUtils.getTag(), "route_url:-" + device_url);
                        callRouteAPI(device_url);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ll_alerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(R.id.frame_home, new DeviceAlertFragment(devicePOJO));
            }
        });
        ll_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewDeviceActivity.this, PlayBackActivity.class);
                intent.putExtra("devicePOJO", devicePOJO);
                startActivity(intent);
            }
        });
        ll_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(R.id.frame_home, new ReportFragment());
            }
        });
        tv_address.start();
        ll_driver_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(R.id.frame_home,new DriverScoreFragment(devicePOJO));
            }
        });
        ll_alerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(R.id.frame_home,new DeviceAlertFragment(devicePOJO));
            }
        });
        ll_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(R.id.frame_home,new ReportFragment());
            }
        });
        ll_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewDeviceActivity.this, PlayBackActivity.class);
                intent.putExtra("devicePOJO", devicePOJO);
                startActivity(intent);
            }
        });
    }

    public void updateSwipe(boolean show_parse_time) {

        if (show_parse_time) {
            if (!devicePOJO.getDeviceDetailPOJO().getDtTracker().equalsIgnoreCase("")) {
                try {
                    tv_date.setText(devicePOJO.getDeviceDetailPOJO().getDtTracker().split(" ")[0]);
                    tv_time.setText(devicePOJO.getDeviceDetailPOJO().getDtTracker().split(" ")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (!devicePOJO.getDeviceDetailPOJO().getNormalDtrackerTime().equalsIgnoreCase("")) {
                try {
                    tv_date.setText(devicePOJO.getDeviceDetailPOJO().getNormalDtrackerTime().split(" ")[0]);
                    tv_time.setText(devicePOJO.getDeviceDetailPOJO().getNormalDtrackerTime().split(" ")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


//        if (devicePOJO.getDeviceDetailPOJO().getSpeed().equalsIgnoreCase("0")) {
//            tv_status.setText("Stopped");
//            iv_moving_status.setImageResource(R.drawable.vehicle_stopped_status);
//            tv_speed.setText("0 km/h");
//        } else {
//            if (UtilityFunction.getDifferenceBtwTwoTimeInSec(devicePOJO.getDeviceDetailPOJO().getDtTracker(), UtilityFunction.getServerTimeCurrentTime()) > 300) {
//                tv_status.setText("Stopped");
//                iv_moving_status.setImageResource(R.drawable.vehicle_stopped_status);
//                tv_speed.setText("0 km/h");
//            } else {
//                tv_status.setText("Moving");
//                iv_moving_status.setImageResource(R.drawable.vehicle_moving_status);
//                tv_speed.setText(devicePOJO.getDeviceDetailPOJO().getSpeed() + " km/h");
//            }
//        }

        Log.d(TagUtils.getTag(), "device image url:-" + WebServicesUrls.IMAGE_BASE_URL + devicePOJO.getDeviceDetailPOJO().getIcon());
        Picasso.get()
                .load(WebServicesUrls.IMAGE_BASE_URL + devicePOJO.getDeviceDetailPOJO().getIcon())
                .placeholder(R.drawable.ic_car)
                .error(R.drawable.ic_car)
                .into(iv_icon);

        try {
            tv_odometer.setText((String.format("%.2f", Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getOdometer()))) + " km");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void updateDeviceData(boolean show_parse_time) {
        if (devicePOJO != null) {
            tv_object_name.setText(devicePOJO.getDeviceDetailPOJO().getName());
//            tv_object_name.setText("Honda City");
//            tv_object_name.setText("Mercedes");
            updateSwipe(show_parse_time);

            if (devicePOJO.getDeviceDetailPOJO().getDevice_address() == null || devicePOJO.getDeviceDetailPOJO().getDevice_address().equalsIgnoreCase("")) {
                tv_address.setText("Show Address");
            } else {
                tv_address.setText(devicePOJO.getDeviceDetailPOJO().getDevice_address());
            }
            showDeviceAddress(false);
            tv_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeviceAddress(true);
                }
            });
            getObjectHistory();
        }
    }

    public void showDeviceAddress(final boolean is_loaded) {
        if (is_loaded) {
            showProgressBar();
        }
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(devicePOJO.getDeviceDetailPOJO().getLat()) + "," + String.valueOf(devicePOJO.getDeviceDetailPOJO().getLng()) + "&sensor=true&key=AIzaSyC-L6KkHvJLkqTASANbRZC3gvAQExPFx24";
        Log.d(TagUtils.getTag(), "url:-" + url);
//        StringRequest req = new StringRequest(url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (is_loaded) {
//                            dismissProgressBar();
//                        }
//                        try {
//                            Log.d(TagUtils.getTag(), "address response:-" + response);
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray jsonArray = jsonObject.optJSONArray("results");
//                            JSONObject jsonObject1 = jsonArray.optJSONObject(0);
//                            String formatted_address = jsonObject1.optString("formatted_address");
//                            tv_address.setText(formatted_address);
//                            devicePOJO.getDeviceDetailPOJO().setDevice_address(formatted_address);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (is_loaded) {
//                    dismissProgressBar();
//                }
//                Log.d(TagUtils.getTag(), "api error:-" + error.toString());
//            }
//        });
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(req);


        new GetWebServices(NewDeviceActivity.this, null, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                if (is_loaded) {
                    dismissProgressBar();
                }
                try {
                    Log.d(TagUtils.getTag(), "address response:-" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("results");
                    JSONObject jsonObject1 = jsonArray.optJSONObject(0);
                    String formatted_address = jsonObject1.optString("formatted_address");
                    tv_address.setText(formatted_address);
                    devicePOJO.getDeviceDetailPOJO().setDevice_address(formatted_address);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_ADDRESS", false).execute(url);


    }

    @Override
    public void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.UPDATE_DEVICE_ACTIVITY));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String imei = intent.getStringExtra("imei");
                DeviceUpdatedPOJO deviceUpdatedPOJO = (DeviceUpdatedPOJO) intent.getSerializableExtra("data");
//                Log.d(TagUtils.getTag(), "device updated info:-" + deviceUpdatedPOJO.toString());
                //            String result = intent.getStringExtra("data");
//                Log.d(TagUtils.getTag(), "chatresult:-" + result);
                if (imei.equalsIgnoreCase(devicePOJO.getImei())) {
//                    DeviceLocationPOJO deviceLocationPOJO = new Gson().fromJson(result, DeviceLocationPOJO.class);
                    drawTrackerPath(deviceUpdatedPOJO);

                    devicePOJO.getDeviceDetailPOJO().setDtTracker(deviceUpdatedPOJO.getDtTracker());
                    devicePOJO.getDeviceDetailPOJO().setAltitude(deviceUpdatedPOJO.getAltitude());
                    devicePOJO.getDeviceDetailPOJO().setLat(deviceUpdatedPOJO.getLat());
                    devicePOJO.getDeviceDetailPOJO().setLng(deviceUpdatedPOJO.getLng());
                    devicePOJO.getDeviceDetailPOJO().setSpeed(deviceUpdatedPOJO.getSpeed());
                    devicePOJO.getDeviceDetailPOJO().setAngle(deviceUpdatedPOJO.getAngle());
                    devicePOJO.getDeviceDetailPOJO().setCn(deviceUpdatedPOJO.getCn());

                    updateSwipe(false);


                    if (devicePOJO.getDeviceDetailPOJO().getCn() != null) {
                        switch (devicePOJO.getDeviceDetailPOJO().getCn()) {
                            case 0:
                                iv_moving_status.setImageResource(R.drawable.vehicle_cngn_status);
                                iv_battery.setColorFilter(Color.parseColor("#757575"));
                                iv_signal.setColorFilter(Color.parseColor("#757575"));
                                tv_status.setText("OFFLINE");
                                iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                break;
                            case 1:
                                iv_moving_status.setImageResource(R.drawable.vehicle_cygn_status);
                                iv_battery.setColorFilter(Color.parseColor("#9ce751"));
                                iv_signal.setColorFilter(Color.parseColor("#757575"));
                                tv_status.setText("OFFLINE");
                                if(deviceUpdatedPOJO.getDeviceParamPOJO()!=null){
                                    devicePOJO.getDeviceDetailPOJO().setDeviceParamPOJO(deviceUpdatedPOJO.getDeviceParamPOJO());

                                    if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO() != null) {
                                        if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAcc() != null) {
                                            switch (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAcc()) {
                                                case "1":
                                                    iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                                    break;
                                                default:
                                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                                    break;
                                            }
                                        } else if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv() != null) {
                                            try {
                                                int value=Integer.parseInt(devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv());
                                                if(value>0){
                                                    iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                                }else{
                                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                            }
                                        }else{
                                            iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                        }
                                    } else {
                                        iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                    }

                                }else{
                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                }
                                break;
                            case 2:
                                iv_moving_status.setImageResource(R.drawable.vehicle_cygy_status);
                                iv_battery.setColorFilter(Color.parseColor("#9ce751"));
                                iv_signal.setColorFilter(Color.parseColor("#9ce751"));
                                if (devicePOJO.getDeviceDetailPOJO().getSpeed().equals("0")) {
                                    tv_status.setText("STOPPED");
                                } else {
                                    tv_status.setText("MOVING");
                                }
                                if(deviceUpdatedPOJO.getDeviceParamPOJO()!=null){
                                    devicePOJO.getDeviceDetailPOJO().setDeviceParamPOJO(deviceUpdatedPOJO.getDeviceParamPOJO());

                                    if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO() != null) {
                                        if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAcc() != null) {
                                            switch (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAcc()) {
                                                case "1":
                                                    iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                                    break;
                                                default:
                                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                                    break;
                                            }
                                        } else if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv() != null) {
                                            try {
                                                int value=Integer.parseInt(devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv());
                                                if(value>0){
                                                    iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                                }else{
                                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                            }
                                        }else{
                                            iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                        }
                                    } else {
                                        iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                    }

                                }else{
                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                }
                                break;
                            default:
                                iv_moving_status.setImageResource(R.drawable.vehicle_cngn_status);
                                iv_battery.setColorFilter(Color.parseColor("#757575"));
                                iv_signal.setColorFilter(Color.parseColor("#757575"));
                                tv_status.setText("OFFLINE");
                                iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                break;
                        }
                    } else {
                        iv_moving_status.setImageResource(R.drawable.vehicle_cngn_status);
                        iv_battery.setColorFilter(Color.parseColor("#757575"));
                        iv_signal.setColorFilter(Color.parseColor("#757575"));
                        tv_status.setText("OFFLINE");
                        iv_ignition.setColorFilter(Color.parseColor("#757575"));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void drawTrackerPath(DeviceUpdatedPOJO deviceUpdatedPOJO) {
        if (previousLatLng != null && deviceUpdatedPOJO != null && googleMap != null) {
            LatLng latLng = new LatLng(Double.parseDouble(deviceUpdatedPOJO.getLat()), Double.parseDouble(deviceUpdatedPOJO.getLng()));

            try {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.device_window_info, null);

                ImageView iv_truck = view.findViewById(R.id.iv_truck);
//                float bearing = 0;
//                if (previousLatLng != null) {
//                    bearing = UtilityFunction.getBearing(previousLatLng, latLng);
//                }

//                if (!Float.isNaN(bearing) && bearing != 0) {
//                    Log.d(TagUtils.getTag(), "bearing:-" + bearing);
//                    iv_truck.setRotation(bearing);
//                }
                iv_truck.setRotation(Float.parseFloat(deviceUpdatedPOJO.getAngle()));

                TextView tv_speed = view.findViewById(R.id.tv_speed);
                TextView tv_time = view.findViewById(R.id.tv_time);
                TextView tv_location = view.findViewById(R.id.tv_location);

                try{
                    int speed=Integer.parseInt(deviceUpdatedPOJO.getSpeed());
                    if(speed==0){
                        iv_truck.setImageResource(R.drawable.ic_car_red);
                    }else{
                        iv_truck.setImageResource(R.drawable.ic_car_green);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    tv_time.setText(String.valueOf(deviceUpdatedPOJO.getDtTracker()));
                    tv_speed.setText(String.valueOf(deviceUpdatedPOJO.getSpeed()) + " kph");
                    tv_location.setText(String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                NewDeviceActivity.this.tv_speed.setText(String.valueOf(deviceUpdatedPOJO.getSpeed()) + " kph");
//                NewDeviceActivity.this.tv_date.setText();

                IconGenerator generator = new IconGenerator(this);
                generator.setBackground(getResources().getDrawable(R.drawable.window_transparent));
                generator.setContentView(view);
                Bitmap icon = generator.makeIcon();
                if (lastPolyline != null) {
                    lastPolyline.remove();
                }
                lastPolyline = googleMap.addPolyline(new PolylineOptions()
                        .add(previousLatLng, latLng)
                        .width(10)
                        .color(Color.BLUE));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                if (lastMarker != null) {
                    lastMarker.remove();
                }
                if (deviceInitialMarker != null) {
                    deviceInitialMarker.remove();
                }

                MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(icon)).anchor(0.5f, 0.5f);
                lastMarker = googleMap.addMarker(markerOptions);

                previousLatLng = latLng;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setMapView() {
        if (googleMap != null) {
            if (is_satellite_view) {
                is_satellite_view = false;
                googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
            } else {
                is_satellite_view = true;
                googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
            }
        }
    }

    Marker deviceInitialMarker;

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        checkLocation();
        if (devicePOJO != null) {
            final LatLng latLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_car_red);

            tv_speed.setText(devicePOJO.getDeviceDetailPOJO().getSpeed() + " kph");
            try{
                int speed=Integer.parseInt(devicePOJO.getDeviceDetailPOJO().getSpeed());
                if(speed==0){
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_car_red);
                }else{
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_car_green);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(image)).anchor(0.5f, 0.5f).title(devicePOJO.getDeviceDetailPOJO().getName());
            deviceInitialMarker = googleMap.addMarker(markerOptions);
            deviceInitialMarker.setRotation(Float.parseFloat(devicePOJO.getDeviceDetailPOJO().getAngle()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    slidingLogic();
                    return false;
                }
            });

            slidingLogic();

//            getCompleteAddress(devicePOJO.getDeviceDetailPOJO());
            tv_address.setText(UtilityFunction.getGeoAddress(this, Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng())));

            tv_speed.setText(devicePOJO.getDeviceDetailPOJO().getSpeed() + " kph");
            if (devicePOJO.getDeviceDetailPOJO().getCn() != null) {
                switch (devicePOJO.getDeviceDetailPOJO().getCn()) {
                    case 0:
                        iv_moving_status.setImageResource(R.drawable.vehicle_cngn_status);
                        iv_battery.setColorFilter(Color.parseColor("#757575"));
                        iv_signal.setColorFilter(Color.parseColor("#757575"));
                        tv_status.setText("OFFLINE");
                        iv_ignition.setColorFilter(Color.parseColor("#757575"));
                        break;
                    case 1:
                        iv_moving_status.setImageResource(R.drawable.vehicle_cygn_status);
                        iv_battery.setColorFilter(Color.parseColor("#9ce751"));
                        iv_signal.setColorFilter(Color.parseColor("#757575"));
                        tv_status.setText("OFFLINE");

                        if(devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO()!=null){
                            if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO() != null) {
                                if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAcc() != null) {
                                    switch (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAcc()) {
                                        case "1":
                                            iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                            break;
                                        default:
                                            iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                            break;
                                    }
                                } else if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv() != null) {
                                    try {
                                        int value=Integer.parseInt(devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv());
                                        if(value>0){
                                            iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                        }else{
                                            iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                    }
                                }else{
                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                }
                            } else {
                                iv_ignition.setColorFilter(Color.parseColor("#757575"));
                            }

                        }else{
                            iv_ignition.setColorFilter(Color.parseColor("#757575"));
                        }
                        break;
                    case 2:
                        iv_moving_status.setImageResource(R.drawable.vehicle_cygy_status);
                        iv_battery.setColorFilter(Color.parseColor("#9ce751"));
                        iv_signal.setColorFilter(Color.parseColor("#9ce751"));
                        if (devicePOJO.getDeviceDetailPOJO().getSpeed().equals("0")) {
                            tv_status.setText("STOPPED");
                        } else {
                            tv_status.setText("MOVING");
                        }


                        if(devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO()!=null){
                            if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO() != null) {
                                if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAcc() != null) {
                                    switch (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAcc()) {
                                        case "1":
                                            iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                            break;
                                        default:
                                            iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                            break;
                                    }
                                } else if (devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv() != null) {
                                    try {
                                        int value=Integer.parseInt(devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv());
                                        if(value>0){
                                            iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                        }else{
                                            iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                    }
                                }else{
                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                }
                            } else {
                                iv_ignition.setColorFilter(Color.parseColor("#757575"));
                            }

                        }else{
                            iv_ignition.setColorFilter(Color.parseColor("#757575"));
                        }


                        break;
                    default:
                        iv_moving_status.setImageResource(R.drawable.vehicle_cngn_status);
                        iv_battery.setColorFilter(Color.parseColor("#757575"));
                        iv_signal.setColorFilter(Color.parseColor("#757575"));
                        tv_status.setText("OFFLINE");
                        iv_ignition.setColorFilter(Color.parseColor("#757575"));
                        break;
                }
            } else {
                iv_moving_status.setImageResource(R.drawable.vehicle_cngn_status);
                iv_battery.setColorFilter(Color.parseColor("#757575"));
                iv_signal.setColorFilter(Color.parseColor("#757575"));
                tv_status.setText("OFFLINE");
                iv_ignition.setColorFilter(Color.parseColor("#757575"));
            }
        }
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

    Marker lastDeviceMarker = null;

    public void setCurrentLocationMarker(double lat, double lng) {

        if (lastDeviceMarker != null) {
            lastDeviceMarker.remove();
        }
        LatLng latLng = new LatLng(lat, lng);
//        LatLng latLng = new LatLng(28.5316888, 77.2126535);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Phone Location");
        lastDeviceMarker = googleMap.addMarker(markerOptions);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (lastDeviceMarker != null) {
                    lastDeviceMarker.remove();
                }
            }
        }, 15000);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                slidingLogic();
                return false;
            }
        });
    }

    public void animateToCurrentLocation(double lat, double lng) {
        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 17));
        }
    }

    public void checkLocation() {

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                double[] location = UtilityFunction.getLocation(getApplicationContext());
                current_lat = location[0];
                current_lng = location[1];
//                current_lat = 28.5316888;
//                current_lng = 77.2126535;
//                setCurrentLocationMarker(current_lat, current_lng);
            }
        });
    }

    boolean is_route_showing = false;

    public void callRouteAPI(String url) {
        showProgressBar();

        new GetWebServices(NewDeviceActivity.this, null, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                dismissProgressBar();
                Log.d(TagUtils.getTag(), "google response:-" + response.toString());
                try {
                    ParserTask parserTask = new ParserTask();
                    // Invokes the thread for parsing the JSON data
                    parserTask.execute(response.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_ADDRESS", false).execute(url);


    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                if (googleMap != null) {
                    if (navigation_polyline != null) {
                        navigation_polyline.remove();
                    }
                    is_route_showing = true;
                    navigation_polyline = googleMap.addPolyline(lineOptions);
                }
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

    Polyline navigation_polyline;

    public void getCompleteAddress(final DeviceDetailPOJO deviceDetailPOJO) {
        try {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(deviceDetailPOJO.getLat()) + "," + String.valueOf(deviceDetailPOJO.getLng()) + "&sensor=true&key=" + getResources().getString(R.string.google_map_api_key);

            new GetWebServices(getApplicationContext(), null, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try {
                        Log.d(TagUtils.getTag(), "address response:-" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.optJSONArray("results");
                        JSONObject jsonObject1 = jsonArray.optJSONObject(0);
                        String formatted_address = jsonObject1.optString("formatted_address");
                        tv_address.setText(formatted_address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "GET_ADDRESS", false).execute(url);

        } catch (Exception e) {
//            dismissProgressBar();
            e.printStackTrace();
        }
    }

    public void getObjectHistory() {

        if (devicePOJO != null) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("imei", devicePOJO.getImei()));
            nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
            nameValuePairs.add(new BasicNameValuePair("dtf", UtilityFunction.convertHistoryserverDateTime(UtilityFunction.getCurrentDate() + " 00:00:00")));
            nameValuePairs.add(new BasicNameValuePair("dtt", UtilityFunction.convertHistoryserverDateTime(UtilityFunction.getCurrentDate() + " 23:59:00")));
            nameValuePairs.add(new BasicNameValuePair("stop_duration", "30"));
            nameValuePairs.add(new BasicNameValuePair("show_coordinates", "true"));
            nameValuePairs.add(new BasicNameValuePair("show_addresses", "false"));
            nameValuePairs.add(new BasicNameValuePair("zones_addresses", "false"));
            String data_items = "[\"time_a\",\"position_a\",\"time_b\",\"position_b\",\"duration\",\"route_length\",\"fuel_consumption\",\"fuel_cost\",\"total\"]";
            nameValuePairs.add(new BasicNameValuePair("data_items", data_items));

            new WebServiceBaseResponse<TravelSheetPOJO>(nameValuePairs, this, new ResponseCallBack<TravelSheetPOJO>() {
                @Override
                public void onGetMsg(ResponsePOJO<TravelSheetPOJO> responsePOJO) {
                    try {
                        if (responsePOJO.isSuccess()) {
                            tv_km.setText(responsePOJO.getResult().getRouteLength() + " km");
                            if (responsePOJO.getResult().getTravelSheetDataPOJOS() != null && responsePOJO.getResult().getTravelSheetDataPOJOS().size() > 0) {
                                tv_last_trip.setText(responsePOJO.getResult().getTravelSheetDataPOJOS().get(responsePOJO.getResult().getTravelSheetDataPOJOS().size() - 1).getRouteLength() + " km");
                            }
                            setTimer();
                        } else {
                            tv_km.setText("0 km");
                            tv_last_trip.setText("0 km");

//                            ToastClass.showShortToast(getApplicationContext(), responsePOJO.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, TravelSheetPOJO.class, "GET_DEVICE_HISTORY", false).execute(WebServicesUrls.HOME_TRAVEL_SHEET_REPORT_API);
        } else {
            ToastClass.showShortToast(getApplicationContext(), "Please Select Device");
        }
    }

    public int time = -1;

    public void setTimer() {

        switch (Pref.GetStringPref(getApplicationContext(), StringUtils.MONITOR_REFRESH_RATE, StringUtils.REF_10_SEC)) {
            case StringUtils.REF_DISABLE_AUTO_REFRESH:
                time = -1;
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
                    getObjectHistory();
//                    getCompleteAddress(devicePOJO.getDeviceDetailPOJO());
                    tv_address.setText(UtilityFunction.getGeoAddress(NewDeviceActivity.this, Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng())));
                }
            }.start();
        }
    }


}
