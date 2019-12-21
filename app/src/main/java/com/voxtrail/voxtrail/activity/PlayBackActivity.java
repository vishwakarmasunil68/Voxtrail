package com.voxtrail.voxtrail.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.gson.Gson;
import com.google.maps.android.ui.IconGenerator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.ActivityManager;
import com.voxtrail.voxtrail.pojo.gm.ObjectHistoryPOJO;
import com.voxtrail.voxtrail.pojo.gm.SnappedPointsList;
import com.voxtrail.voxtrail.pojo.gm.SnappedPointsPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class PlayBackActivity extends ActivityManager implements OnMapReadyCallback, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.iv_drop)
    ImageView iv_drop;
    @BindView(R.id.tv_dt)
    TextView tv_dt;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;

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
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.ic_back)
    ImageView iv_back;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.spinner_speed)
    Spinner spinner_speed;
    @BindView(R.id.tv_speed)
    TextView tv_speed;
    @BindView(R.id.tv_time)
    TextView tv_time;

    GoogleMap googleMap;

    List<CheckBox> checkBoxes = new ArrayList<>();

    DevicePOJO devicePOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_play_back);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        devicePOJO = (DevicePOJO) getIntent().getSerializableExtra("devicePOJO");

        iv_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingLogic();
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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlayLogic();
            }
        });
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
                        PlayBackActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        ll_end_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_date_time = tv_end_datetime;
                is_stating_dt = false;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        PlayBackActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
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
                callAPI(startdatetime, enddatetime);
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
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
                PlayBackActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getFragmentManager(), "Select Time");
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

    PlayBackPOJO playBackPOJO;
    Marker initialMarker = null;
    int countPosition = 1;
    boolean is_running = false;

    public void callAPI(String startdatetime, String enddatetime) {
        if (devicePOJO != null) {
            googleMap.clear();
            is_running = false;
            this.playBackPOJO = null;
            this.initialMarker = null;
            routeStopPOJOS.clear();

            tv_speed.setText("");
            tv_time.setText("");

            iv_play.setImageResource(R.drawable.ic_play);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("cmd", "load_route_data"));
            nameValuePairs.add(new BasicNameValuePair("imei", devicePOJO.getImei()));
            nameValuePairs.add(new BasicNameValuePair("dtf", startdatetime));
            nameValuePairs.add(new BasicNameValuePair("dtt", enddatetime));
            nameValuePairs.add(new BasicNameValuePair("min_stop_duration", "30"));
            nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
            new WebServiceBase(nameValuePairs, null, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    parseResponse(response);
                }
            }, "GET_PLAY_BACK", true).execute("http://platform.voxtrail.com/func/func_history_new.php");
        }
    }

    List<RouteStopPOJO> routeStopPOJOS = new ArrayList<>();
    Marker stopMarkers[];
    Marker endMarker;
    Marker startMarker;

    LatLngBounds.Builder builder;

    public void parseResponse(String response) {
        try {
            Log.d(TagUtils.getTag(), "parsing started");
            JSONObject jsonObject = new JSONObject(response);
            JSONArray routeJsonArray = jsonObject.optJSONObject("result").optJSONArray("route");

            List<LatLng> latLngslist = new ArrayList<>();
            List<String> timigs = new ArrayList<>();
            List<String> speed = new ArrayList<>();

            builder = new LatLngBounds.Builder();


            for (int i = 0; i < routeJsonArray.length(); i++) {
                JSONArray routeArray = (JSONArray) routeJsonArray.get(i);
                LatLng latLng = new LatLng(Double.parseDouble((String) routeArray.get(1)), Double.parseDouble((String) routeArray.get(2)));
                timigs.add((String) routeArray.get(0));
                speed.add(String.valueOf((int) routeArray.get(5)));
                latLngslist.add(latLng);
                builder.include(latLng);
            }

            Log.d(TagUtils.getTag(), "parsing done");
            LatLng[] latLngs = new LatLng[latLngslist.size()];
            for (int i = 0; i < latLngslist.size(); i++) {
                latLngs[i] = latLngslist.get(i);
            }

            JSONArray stopsJsonArray = jsonObject.optJSONObject("result").optJSONArray("stops");
            for (int i = 0; i < stopsJsonArray.length(); i++) {
                JSONArray jsonArray = stopsJsonArray.getJSONArray(i);
                RouteStopPOJO routeStopPOJO = new RouteStopPOJO();
                LatLng latLng = new LatLng(Double.parseDouble(jsonArray.optString(2)), Double.parseDouble(jsonArray.optString(3)));
                routeStopPOJO.setLatLng(latLng);
                routeStopPOJO.setFromDate(jsonArray.optString(6));
                routeStopPOJO.setEndDate(jsonArray.optString(7));
                routeStopPOJO.setStopDuration(jsonArray.optString(8));
                routeStopPOJO.setEngineHour(jsonArray.optString(11));

                routeStopPOJOS.add(routeStopPOJO);
            }

            googleMap.addPolyline(new PolylineOptions()
                    .add(latLngs)
                    .width(10)
                    .color(Color.DKGRAY));

            stopMarkers = new Marker[routeStopPOJOS.size()];

            MarkerOptions endMarkerOption = new MarkerOptions().position(latLngslist.get(0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_eout_start_flag));
            endMarker = googleMap.addMarker(endMarkerOption);
            endMarker.setTag("end");

            MarkerOptions startMarkerOption = new MarkerOptions().position(latLngslist.get(latLngslist.size() - 1)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_route_end_flag));
            startMarker = googleMap.addMarker(startMarkerOption);
            startMarker.setTag("start");


            if (routeStopPOJOS.size() > 0) {
                int stopCount = 1;
                for (int i = 0; i < routeStopPOJOS.size(); i++) {

                    if (routeStopPOJOS.get(i).getLatLng().latitude == latLngslist.get(0).latitude
                            && routeStopPOJOS.get(i).getLatLng().longitude == latLngslist.get(0).longitude) {


                    } else if (routeStopPOJOS.get(i).getLatLng().latitude == latLngslist.get(latLngslist.size() - 1).latitude
                            && routeStopPOJOS.get(i).getLatLng().longitude == latLngslist.get(latLngslist.size() - 1).longitude) {


                    } else {

                        IconGenerator iconGenerator = new IconGenerator(this);
                        iconGenerator.setStyle(IconGenerator.STYLE_RED);

                        MarkerOptions markerOptions2 = new MarkerOptions()
                                .position(routeStopPOJOS.get(i).getLatLng())
                                .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("" + String.valueOf(stopCount))));
                        stopMarkers[i] = googleMap.addMarker(markerOptions2);
                        stopMarkers[i].setTag("stop:" + i + ",position:" + stopCount);

                        stopCount++;
                    }
                }

                stopMarkers = new Marker[routeStopPOJOS.size()];

            }

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs[0], 15));

            PlayBackPOJO playBackPOJO = new PlayBackPOJO();
            playBackPOJO.setLatLngs(latLngs);
            playBackPOJO.setSpeed(speed);
            playBackPOJO.setTime(timigs);

            try {
                LatLngBounds bounds = builder.build();
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.20); // offset from edges of the map 10% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                googleMap.animateCamera(cu);
            } catch (Exception e) {
                e.printStackTrace();
            }

            startPlay(playBackPOJO);

        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Nothing found");
        }
    }

    public void startPlay(PlayBackPOJO playBackPOJO) {
        this.playBackPOJO = playBackPOJO;
        countPosition = 1;
        is_running = false;
        initialMarker = null;
    }

    public void setPlayLogic() {
        if (playBackPOJO != null) {
            LatLng[] latLngs = playBackPOJO.getLatLngs();
            if (latLngs != null) {
                if (is_running) {
                    is_running = false;
                    iv_play.setImageResource(R.drawable.ic_play);
                } else {
                    if (latLngs.length > 1) {
                        if (initialMarker == null) {
                            countPosition = 1;
//                            if (devicePOJO.getDeviceDetailPOJO().getObject_type().equals("1")) {
//                                MarkerOptions markerOptions = new MarkerOptions().position(latLngs[0]).title("Device").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bike_on_map));
//                                initialMarker = googleMap.addMarker(markerOptions);
//                            } else {
                            MarkerOptions markerOptions = new MarkerOptions().position(latLngs[0]).title(devicePOJO.getDeviceDetailPOJO().getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_icon));
                            initialMarker = googleMap.addMarker(markerOptions);
//                            }

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs[0], 15));
                        }
                        iv_play.setImageResource(R.drawable.ic_pause);
                        if (initialMarker != null) {
                            is_running = true;
                            if ((countPosition + 1) == latLngs.length) {
                                countPosition = 1;
                                initialMarker.remove();
//                                if (devicePOJO.getDeviceDetailPOJO().getObject_type().equals("1")) {
//                                    MarkerOptions markerOptions = new MarkerOptions().position(latLngs[0]).title("Device").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bike_on_map));
//                                    Marker marker = googleMap.addMarker(markerOptions);
//                                    initialMarker = marker;
//                                } else {
                                MarkerOptions markerOptions = new MarkerOptions().position(latLngs[0]).title("Device").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_icon));
                                Marker marker = googleMap.addMarker(markerOptions);
                                initialMarker = marker;
//                                }

                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs[0], 15));
                                animateMarkerNew(latLngs);
                            } else {
                                animateMarkerNew(latLngs);
                            }

                        }
                    }
                }
            }
        }
    }

    private void animateMarkerNew(final LatLng[] latLngs) {

        if (initialMarker != null) {

            tv_speed.setText(playBackPOJO.getSpeed().get(countPosition) + " kph");
//            tv_time.setText(UtilityFunction.parseUTCToIST(playBackPOJO.getTime().get(countPosition)));
            tv_time.setText(playBackPOJO.getTime().get(countPosition));

            final LatLng startPosition = initialMarker.getPosition();
            final LatLng endPosition = new LatLng(latLngs[countPosition].latitude, latLngs[countPosition].longitude);

            final float startRotation = initialMarker.getRotation();
            final LatLngInterpolatorNew latLngInterpolator = new LatLngInterpolatorNew.LinearFixed();

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);

            switch (spinner_speed.getSelectedItemPosition()) {
                case 0:
                    valueAnimator.setDuration(1500);
                    break;
                case 1:
                    valueAnimator.setDuration(1200);
                    break;
                case 2:
                    valueAnimator.setDuration(900);
                    break;
                case 3:
                    valueAnimator.setDuration(600);
                    break;
                case 4:
                    valueAnimator.setDuration(300);
                    break;
            }
            final LatLng[] latLng = new LatLng[1];

            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                        initialMarker.setPosition(newPosition);
                        latLng[0] = newPosition;
                        if (countPosition == 1) {
                            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                    .target(newPosition)
                                    .zoom(16.5f)
                                    .build()));
                        } else {
//                            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
//                                    .target(newPosition)
////                                    .zoom(15.5f)
//                                    .build()));
//                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(newPosition));
                        }

                        float bearing = getBearing(startPosition, new LatLng(latLngs[countPosition].latitude, latLngs[countPosition].longitude));


                        if (!Float.isNaN(bearing)) {
                            initialMarker.setRotation(bearing);

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (latLng[0] != null) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng[0]));
                    }
                    if (is_running) {
                        if (latLngs.length != (countPosition + 1)) {
//                            countPosition = countPosition + (spinner_speed.getSelectedItemPosition() + 1);
                            countPosition = countPosition + 1;
                            animateMarkerNew(latLngs);
                        } else {
                            is_running = false;
                            initialMarker = null;
                            iv_play.setImageResource(R.drawable.ic_play);
                        }
                    }
                }
            });
            valueAnimator.start();
        }
    }

    private interface LatLngInterpolatorNew {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolatorNew {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                // Take the shortest path across the 180th meridian.
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }

    //Method for finding bearing between two points
    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

    class PlayBackPOJO {
        List<String> speed;
        List<String> time;
        LatLng[] latLngs;

        public List<String> getSpeed() {
            return speed;
        }

        public void setSpeed(List<String> speed) {
            this.speed = speed;
        }

        public List<String> getTime() {
            return time;
        }

        public void setTime(List<String> time) {
            this.time = time;
        }

        public LatLng[] getLatLngs() {
            return latLngs;
        }

        public void setLatLngs(LatLng[] latLngs) {
            this.latLngs = latLngs;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        is_running = false;
    }

    class RouteStopPOJO {
        LatLng latLng;
        String fromDate;
        String endDate;
        String stopDuration;
        String engineHour;

        public LatLng getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getStopDuration() {
            return stopDuration;
        }

        public void setStopDuration(String stopDuration) {
            this.stopDuration = stopDuration;
        }

        public String getEngineHour() {
            return engineHour;
        }

        public void setEngineHour(String engineHour) {
            this.engineHour = engineHour;
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
