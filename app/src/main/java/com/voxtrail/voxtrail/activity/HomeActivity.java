package com.voxtrail.voxtrail.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.CustomAutoCompleteAdapter;
import com.voxtrail.voxtrail.adapter.DeviceListAdapter;
import com.voxtrail.voxtrail.fragment.DeviceCurrentLocationFragment;
import com.voxtrail.voxtrail.fragment.GeoFenceFragment;
import com.voxtrail.voxtrail.fragment.ImagesGridFragment;
import com.voxtrail.voxtrail.fragment.NotificationFragment;
import com.voxtrail.voxtrail.fragment.ProfileFragment;
import com.voxtrail.voxtrail.fragment.PushStartFragment;
import com.voxtrail.voxtrail.fragment.ReportFragment;
import com.voxtrail.voxtrail.fragment.SettingFragment;
import com.voxtrail.voxtrail.fragment.ShowVehicleFragment;
import com.voxtrail.voxtrail.fragmentcontroller.ActivityManager;
import com.voxtrail.voxtrail.pojo.DeviceUpdatedPOJO;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.dashboard.DashBoardPOJO;
import com.voxtrail.voxtrail.pojo.device.DeviceParamPOJO;
import com.voxtrail.voxtrail.pojo.device.DeviceUpdateListPOJO;
import com.voxtrail.voxtrail.pojo.gm.ObjectHistoryPOJO;
import com.voxtrail.voxtrail.pojo.object.ObjectDataPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DeviceDetailPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.pojo.report.MileageReportPOJO;
import com.voxtrail.voxtrail.pojo.report.MileageReportResPOJO;
import com.voxtrail.voxtrail.pojo.travelsheet.TravelSheetPOJO;
import com.voxtrail.voxtrail.testing.ColoredLabelXAxisRenderer;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.GetWebServices;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.ResponseListCallback;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponseList;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.xzl.marquee.library.MarqueeView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends ActivityManager implements OnMapReadyCallback, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private Toolbar toolbar;
    @BindView(R.id.ic_ham)
    ImageView ic_ham;
    @BindView(R.id.iv_menu)
    ImageView iv_menu;
    @BindView(R.id.ll_graph_type)
    LinearLayout ll_graph_type;
    @BindView(R.id.ll_map_type)
    LinearLayout ll_map_type;
    @BindView(R.id.ll_device_type)
    LinearLayout ll_device_type;
    @BindView(R.id.rv_device_list)
    RecyclerView rv_device_list;
    @BindView(R.id.iv_notification)
    ImageView iv_notification;
    @BindView(R.id.tv_refresh_rate)
    TextView tv_refresh_rate;
    @BindView(R.id.tv_notification_num)
    TextView tv_notification_num;
    @BindView(R.id.iv_refresh)
    ImageView iv_refresh;
    GoogleMap googleMap;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;

    @BindView(R.id.card_vehicle)
    CardView card_vehicle;

    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_running)
    TextView tv_running;
    @BindView(R.id.tv_parked)
    TextView tv_parked;
    @BindView(R.id.tv_offline)
    TextView tv_offline;
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
    @BindView(R.id.ll_total)
    LinearLayout ll_total;
    @BindView(R.id.ll_running)
    LinearLayout ll_running;
    @BindView(R.id.ll_parked)
    LinearLayout ll_parked;
    @BindView(R.id.ll_offline)
    LinearLayout ll_offline;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_start_datetime)
    TextView tv_start_datetime;
    @BindView(R.id.tv_end_datetime)
    TextView tv_end_datetime;
    @BindView(R.id.iv_start_datetime)
    ImageView iv_start_datetime;
    @BindView(R.id.frame_start_date)
    FrameLayout frame_start_date;
    @BindView(R.id.frame_end_date)
    FrameLayout frame_end_date;
    @BindView(R.id.iv_end_datetime)
    ImageView iv_end_datetime;
    @BindView(R.id.iv_done)
    ImageView iv_done;
    //    @BindView(R.id.pie_vehicle_data)
//    PieChart pie_vehicle_data;
    @BindView(R.id.iv_dashboard_view)
    ImageView iv_dashboard_view;
    @BindView(R.id.ll_box)
    LinearLayout ll_box;
    @BindView(R.id.ll_chart)
    LinearLayout ll_chart;
    @BindView(R.id.ll_anim)
    LinearLayout ll_anim;
    @BindView(R.id.horizontal_bar_chart)
    HorizontalBarChart horizontal_bar_chart;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.iv_moving_status)
    ImageView iv_moving_status;
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
    @BindView(R.id.tv_odometer)
    TextView tv_odometer;
    @BindView(R.id.tv_last_trip)
    TextView tv_last_trip;
    @BindView(R.id.ll_alerts)
    LinearLayout ll_alerts;
    @BindView(R.id.ll_playback)
    LinearLayout ll_playback;
    @BindView(R.id.ll_report)
    LinearLayout ll_report;
    @BindView(R.id.spinner_vehicle_type)
    Spinner spinner_vehicle_type;
    @BindView(R.id.et_search_device)
    AutoCompleteTextView et_search_device;
    @BindView(R.id.iv_battery)
    ImageView iv_battery;
    @BindView(R.id.iv_signal)
    ImageView iv_signal;
    @BindView(R.id.iv_ignition)
    ImageView iv_ignition;

    CustomAutoCompleteAdapter adapter = null;
//    String device_imeis = "";

    String startdatetime = "";
    String enddatetime = "";

    public void getTodayRange() {
        startdatetime = UtilityFunction.getCurrentDate() + " 00:00:00";
        enddatetime = UtilityFunction.getCurrentDate() + " 23:59:00";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        settingNavDrawer();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getTodayRange();

        tv_start_datetime.setText(startdatetime);
        tv_end_datetime.setText(enddatetime);

        switch (Pref.GetStringPref(getApplicationContext(), StringUtils.HOME_SCREEN_PREF, StringUtils.HOME_DASHBOARD)) {
            case StringUtils.HOME_DASHBOARD:
                ll_graph_type.setVisibility(View.VISIBLE);
                break;
            case StringUtils.HOME_MAP:
                ll_map_type.setVisibility(View.VISIBLE);
                break;
            case StringUtils.HOME_DEVICES:
                ll_device_type.setVisibility(View.VISIBLE);
                break;
        }

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(HomeActivity.this, view);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_graph_type:
                                ll_graph_type.setVisibility(View.VISIBLE);
                                ll_map_type.setVisibility(View.GONE);
                                ll_device_type.setVisibility(View.GONE);
                                Pref.SetStringPref(getApplicationContext(), StringUtils.HOME_SCREEN_PREF, StringUtils.HOME_DASHBOARD);
                                break;
                            case R.id.pop_map_type:
                                ll_graph_type.setVisibility(View.GONE);
                                ll_map_type.setVisibility(View.VISIBLE);
                                ll_device_type.setVisibility(View.GONE);
                                Pref.SetStringPref(getApplicationContext(), StringUtils.HOME_SCREEN_PREF, StringUtils.HOME_MAP);
                                break;
                            case R.id.popup_setting:
                                startFragment(R.id.frame_home, new SettingFragment());
                                break;
                            case R.id.popup_device_type:
                                ll_graph_type.setVisibility(View.GONE);
                                ll_map_type.setVisibility(View.GONE);
                                ll_device_type.setVisibility(View.VISIBLE);
                                Pref.SetStringPref(getApplicationContext(), StringUtils.HOME_SCREEN_PREF, StringUtils.HOME_DEVICES);
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_home_type);
                menu.show();
            }
        });

        setupDashboardView();

        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(R.id.frame_home, new NotificationFragment());
            }
        });

        card_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, DeviceSelectActivity.class);
                startActivityForResult(i, Constants.SELECT_DEVICE);
            }
        });
        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (googleMap != null) {
                    getDashBoardData();
                }
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
                        rvdevicesPojos.addAll(Constants.devicePOJOS);
                        deviceListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        checkAppVersionCode();

        iv_start_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_date_time = tv_start_datetime;
                is_stating_dt = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        HomeActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        iv_end_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_date_time = tv_end_datetime;
                is_stating_dt = false;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        HomeActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        frame_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_start_datetime.callOnClick();
            }
        });
        frame_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_end_datetime.callOnClick();
            }
        });

        iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getObjectHistory(true);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDashBoardData();
            }
        });

        ll_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.devicePOJOS.size() > 0) {
//                    startFragment(R.id.frame_home, new ShowVehicleFragment("All Vehicles", Constants.devicePOJOS));
                    showMap();
//                    showAllDevices(Constants.devicePOJOS);
                    spinner_vehicle_type.setSelection(0);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "No vehicles found");
                }
            }
        });
        ll_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offlineDevicePOJOS.size() > 0) {
//                    startFragment(R.id.frame_home, new ShowVehicleFragment("Offline Vehicles", offlineDevicePOJOS));
                    showMap();
//                    showAllDevices(offlineDevicePOJOS);
                    spinner_vehicle_type.setSelection(3);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "No vehicles are offline");
                }
            }
        });
        ll_parked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idleDevicePOJOS.size() > 0) {
//                    startFragment(R.id.frame_home, new ShowVehicleFragment("Parked Vehicles", idleDevicePOJOS));
                    showMap();
//                    showAllDevices(idleDevicePOJOS);
                    spinner_vehicle_type.setSelection(2);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "No vehicles are parked");
                }
            }
        });
        ll_running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (runningDevicePOJOS.size() > 0) {
//                    startFragment(R.id.frame_home, new ShowVehicleFragment("Running Vehicles", runningDevicePOJOS));
                    showMap();
//                    showAllDevices(runningDevicePOJOS);
                    spinner_vehicle_type.setSelection(1);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "No vehicles are running");
                }
            }
        });
        spinner_vehicle_type.setSelection(0, false);
        spinner_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                makeSlidingDown();
                switch (position) {
                    case 0:
                        showAllDevices(Constants.devicePOJOS);
                        break;
                    case 1:
                        showAllDevices(runningDevicePOJOS);
                        break;
                    case 2:
                        showAllDevices(idleDevicePOJOS);
                        break;
                    case 3:
                        showAllDevices(offlineDevicePOJOS);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        showNotificationCount();
    }

    public void setupDashboardView() {
        switch (Pref.GetStringPref(getApplicationContext(), StringUtils.HOME_DASHBOARD_VIEW, StringUtils.HOME_ANIM)) {
            case StringUtils.HOME_INFO:
                ll_box.setVisibility(View.VISIBLE);
                break;
            case StringUtils.HOME_CHART:
                ll_chart.setVisibility(View.VISIBLE);
                break;
            case StringUtils.HOME_ANIM:
                ll_anim.setVisibility(View.VISIBLE);
                break;
            default:
                ll_box.setVisibility(View.VISIBLE);
                break;
        }

        iv_dashboard_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(HomeActivity.this, view);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_dashboard:
                                ll_box.setVisibility(View.VISIBLE);
                                ll_chart.setVisibility(View.GONE);
                                ll_anim.setVisibility(View.GONE);

                                Pref.SetStringPref(getApplicationContext(), StringUtils.HOME_DASHBOARD_VIEW, StringUtils.HOME_INFO);
                                break;
                            case R.id.popup_chart:
                                ll_box.setVisibility(View.GONE);
                                ll_chart.setVisibility(View.VISIBLE);
                                ll_anim.setVisibility(View.GONE);

                                Pref.SetStringPref(getApplicationContext(), StringUtils.HOME_DASHBOARD_VIEW, StringUtils.HOME_CHART);
                                break;
                            case R.id.popup_anim:
                                ll_box.setVisibility(View.GONE);
                                ll_chart.setVisibility(View.GONE);
                                ll_anim.setVisibility(View.VISIBLE);

                                Pref.SetStringPref(getApplicationContext(), StringUtils.HOME_DASHBOARD_VIEW, StringUtils.HOME_ANIM);
                                break;

                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_home_dashboard);
                menu.show();
            }
        });
        et_search_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
//                    List<DevicePOJO> devicePOJOS = null;
//                    switch (spinner_vehicle_type.getSelectedItemPosition()) {
//                        case 0:
//                            devicePOJOS = Constants.devicePOJOS;
//                            break;
//                        case 1:
//                            devicePOJOS = runningDevicePOJOS;
//                            break;
//                        case 2:
//                            devicePOJOS = idleDevicePOJOS;
//                            break;
//                        case 3:
//                            devicePOJOS = offlineDevicePOJOS;
//                            break;
//                    }


//                    if (devicePOJOS != null && devicePOJOS.size() > 0) {

                    Log.d(TagUtils.getTag(), "selected index:-" + i);
                    Log.d(TagUtils.getTag(), "selected index:-" + l);

                    DevicePOJO devicePOJO = null;
                    switch (spinner_vehicle_type.getSelectedItemPosition()) {
                        case 0:
                            devicePOJO = Constants.devicePOJOS.get(i);
                            break;
                        case 1:
                            devicePOJO = runningDevicePOJOS.get(i);
                            break;
                        case 2:
                            devicePOJO = idleDevicePOJOS.get(i);
                            break;
                        case 3:
                            devicePOJO = offlineDevicePOJOS.get(i);
                            break;
                    }
                    if (devicePOJO != null) {
                        LatLng latLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));
                        Log.d(TagUtils.getTag(), "device selected imei:-" + devicePOJO.getDeviceDetailPOJO().getName());
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                        updateDeviceData(devicePOJO, true);
                    }
//                    }
                    et_search_device.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    TextView tv_date_time;
    boolean is_stating_dt = false;

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
                rvdevicesPojos.clear();
                rvdevicesPojos.addAll(devicePOJOS);
                deviceListAdapter.notifyDataSetChanged();
            }
        }
    }

    public void getVehicleList() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("cmd", "load_object_data"));
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        new WebServiceBaseResponseList<ObjectDataPOJO>(nameValuePairs, this, new ResponseListCallback<ObjectDataPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<ObjectDataPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        List<DeviceUpdatedPOJO> deviceUpdatedPOJOS = new ArrayList<>();
                        for (ObjectDataPOJO objectDataPOJO : responseListPOJO.getResultList()) {
                            DeviceUpdatedPOJO deviceUpdatedPOJO = new DeviceUpdatedPOJO();
                            if (objectDataPOJO != null && objectDataPOJO.getObjectDetailPOJO() != null) {
                                deviceUpdatedPOJO.setAltitude(objectDataPOJO.getObjectDetailPOJO().getAltitude());
                                deviceUpdatedPOJO.setAngle(objectDataPOJO.getObjectDetailPOJO().getAngle());
                                deviceUpdatedPOJO.setDtTracker(objectDataPOJO.getObjectDetailPOJO().getDtTracker());
                                deviceUpdatedPOJO.setImei(objectDataPOJO.getImei());
                                deviceUpdatedPOJO.setLat(objectDataPOJO.getObjectDetailPOJO().getLat());
                                deviceUpdatedPOJO.setLng(objectDataPOJO.getObjectDetailPOJO().getLng());
                                deviceUpdatedPOJO.setSpeed(String.valueOf(objectDataPOJO.getObjectDetailPOJO().getSpeed()));
                                deviceUpdatedPOJO.setCn(objectDataPOJO.getCn());
                                deviceUpdatedPOJO.setSt(objectDataPOJO.getSt());

                                if (objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO() != null) {
                                    DeviceParamPOJO deviceParamPOJO = new DeviceParamPOJO();
                                    if (objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO().getAcc() != null) {
                                        deviceParamPOJO.setAcc(objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO().getAcc());
                                    }
                                    if (objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO().getAccv() != null) {
                                        deviceParamPOJO.setAccv(objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO().getAccv());
                                    }
                                    deviceUpdatedPOJO.setDeviceParamPOJO(deviceParamPOJO);
                                }

                                deviceUpdatedPOJOS.add(deviceUpdatedPOJO);
                            }
                        }
                        updatelocations(deviceUpdatedPOJOS);
//                        Log.d(TagUtils.getTag(), "parsing done");
//                        vehiclestatusCount();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ObjectDataPOJO.class, "GET_DEVICE_LIST", false).execute(WebServicesUrls.PLATFORM_DEVICE_LIST_API);
    }

    public void updatelocations(List<DeviceUpdatedPOJO> deviceUpdatedPOJOS) {

        for (DeviceUpdatedPOJO deviceUpdatedPOJO : deviceUpdatedPOJOS) {
            int main_position = UtilityFunction.getIndexFromList(Constants.devicePOJOS, deviceUpdatedPOJO.getImei());
            if (main_position != -1) {
                if ((Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().getLat().equals(deviceUpdatedPOJO.getLat()))
                        || (Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().getLng().equals(deviceUpdatedPOJO.getLng()))) {

                } else {
                    LatLng previouslatLng = new LatLng(Double.parseDouble(Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().getLat()),
                            Double.parseDouble(Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().getLng()));

                    Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().setLat(deviceUpdatedPOJO.getLat());
                    Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().setLng(deviceUpdatedPOJO.getLng());
                    Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().setSpeed(deviceUpdatedPOJO.getSpeed());
                    Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().setAltitude(deviceUpdatedPOJO.getAltitude());
                    Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().setAngle(deviceUpdatedPOJO.getAngle());
                    Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().setDtTracker(deviceUpdatedPOJO.getNormalDttracker());
                    Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().setCn(deviceUpdatedPOJO.getCn());
                    Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().setSt(deviceUpdatedPOJO.getSt());
                    if (deviceUpdatedPOJO.getDeviceParamPOJO() != null) {
                        Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().setDeviceParamPOJO(deviceUpdatedPOJO.getDeviceParamPOJO());
                    }

                    updateDeviceActivity(deviceUpdatedPOJO.getImei(), deviceUpdatedPOJO);

                    if (initialMarker != null)
                        for (Marker marker : initialMarker) {
                            if (marker != null && marker.getTag().toString().equals(Constants.devicePOJOS.get(main_position).getImei())) {
                                LatLng newLatLng = new LatLng(Double.parseDouble(Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().getLat()),
                                        Double.parseDouble(Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().getLng()));
                                marker.setPosition(newLatLng);
                                marker.setRotation(Float.parseFloat(Constants.devicePOJOS.get(main_position).getDeviceDetailPOJO().getAngle()));

                                int removeIndex = -1;
                                for (int i = 0; i < polylines.size(); i++) {
                                    Polyline polyline = polylines.get(i);
                                    if (polyline != null && polyline.getTag() != null && polyline.getTag().toString().equals(Constants.devicePOJOS.get(main_position).getImei())) {
                                        polyline.remove();
                                    }
                                }

                                if (removeIndex != -1) {
                                    polylines.remove(removeIndex);
                                }

                                Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                                        .add(previouslatLng, newLatLng)
                                        .width(10)
                                        .color(Color.BLUE));
                                polyline.setTag(Constants.devicePOJOS.get(main_position).getImei());
                                polylines.add(polyline);

                            }
                        }

                    if (visibleDevicePOJO != null && visibleDevicePOJO.getImei().equals(Constants.devicePOJOS.get(main_position).getImei())) {
                        updateDeviceData(Constants.devicePOJOS.get(main_position), false);
                    }

                }

//                if (spinner_vehicle_type.getSelectedItemPosition() == 0) {
//                    updateMarkerLocation(main_position, Constants.devicePOJOS.get(main_position));
//                } else if (spinner_vehicle_type.getSelectedItemPosition() == 1) {
//                    int running_index = UtilityFunction.getIndexFromList(runningDevicePOJOS, deviceUpdatedPOJO.getImei());
//                    if (running_index != -1) {
//                        updateMarkerLocation(running_index, runningDevicePOJOS.get(running_index));
//                    }
//                }
            }
        }

        deviceListAdapter.notifyDataSetChanged();
        setTimer();
    }

    public void updateDeviceActivity(String imei, DeviceUpdatedPOJO deviceUpdatedPOJO) {
//        Log.d(TagUtils.getTag(), "device updated info:-" + imei);
        Intent intent = new Intent(StringUtils.UPDATE_DEVICE_ACTIVITY);
        intent.putExtra("imei", imei);
        intent.putExtra("data", deviceUpdatedPOJO);
        sendBroadcast(intent);
    }

    List<Polyline> polylines = new ArrayList<>();


    public void updateDeviceData(DevicePOJO devicePOJO, boolean slideUp) {
        if (devicePOJO != null) {
            visibleDevicePOJO = devicePOJO;
            tv_object_name.setText(devicePOJO.getDeviceDetailPOJO().getName());

            if (!devicePOJO.getDeviceDetailPOJO().getDtTracker().equalsIgnoreCase("")) {
                tv_date.setText(devicePOJO.getDeviceDetailPOJO().getDtTracker().split(" ")[0]);
                tv_time.setText(devicePOJO.getDeviceDetailPOJO().getDtTracker().split(" ")[1]);
            }
            tv_speed.setText(devicePOJO.getDeviceDetailPOJO().getSpeed() + " kph");
            tv_odometer.setText((String.format("%.2f", Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getOdometer()))) + " km");
//            getCompleteAddress(devicePOJO.getDeviceDetailPOJO(), tv_address);

            tv_address.setText(UtilityFunction.getGeoAddress(this, Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng())));
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
                                    int value = Integer.parseInt(devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv());
                                    if (value > 0) {
                                        iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                    } else {
                                        iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                }
                            } else {
                                iv_ignition.setColorFilter(Color.parseColor("#757575"));
                            }
                        } else {
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
                                    int value = Integer.parseInt(devicePOJO.getDeviceDetailPOJO().getDeviceParamPOJO().getAccv());
                                    if (value > 0) {
                                        iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                    } else {
                                        iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                }
                            } else {
                                iv_ignition.setColorFilter(Color.parseColor("#757575"));
                            }
                        } else {
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
            if (slideUp) {
                makeSlidingUP();
            }
        }
    }

    public void makeSlidingUP() {
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
//            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    public void makeSlidingDown() {
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    public void updateMarkerLocation(int position, DevicePOJO devicePOJO) {
        try {
            LatLng previousLatLng = initialMarker[position].getPosition();
            LatLng newlaLatLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));

            if (previousLatLng.latitude == newlaLatLng.longitude && previousLatLng.longitude == newlaLatLng.longitude) {

            } else {
                initialMarker[position].setPosition(newlaLatLng);
                initialMarker[position].setRotation(Float.parseFloat(devicePOJO.getDeviceDetailPOJO().getAngle()));

//                if (polylines[position] != null) {
//                    polylines[position].remove();
//                }
//
//                polylines[position] = googleMap.addPolyline(new PolylineOptions()
//                        .add(previousLatLng, newlaLatLng)
//                        .width(10)
//                        .color(Color.BLUE));

                if (visibleDevicePOJO != null && devicePOJO.getImei().equals(visibleDevicePOJO.getImei())) {
//                moveCamera(visibleDevicePOJO);
//                    updateDeviceData(devicePOJO);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void vehiclestatusCount() {

        offlineDevicePOJOS.clear();
        runningDevicePOJOS.clear();
        idleDevicePOJOS.clear();

        if (Constants.devicePOJOS.size() > 0) {
            for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
                if (devicePOJO.getDeviceDetailPOJO().getSt() != null) {
                    switch (devicePOJO.getDeviceDetailPOJO().getSt().toLowerCase()) {
                        case "off":
                            offlineDevicePOJOS.add(devicePOJO);
                            break;
                        case "m":
                            runningDevicePOJOS.add(devicePOJO);
                            break;
                        case "s":
                            idleDevicePOJOS.add(devicePOJO);
                            break;
                        case "i":
                            idleDevicePOJOS.add(devicePOJO);
                            break;
                        default:
                            offlineDevicePOJOS.add(devicePOJO);
                            break;
                    }
                } else {
                    offlineDevicePOJOS.add(devicePOJO);
                }
            }
            updateDeviceStatus();
        }
    }

    public void updateDeviceStatus() {
//        Log.d(TagUtils.getTag(), "total vehicles:-" + Constants.devicePOJOS.size());
//        Log.d(TagUtils.getTag(), "offline_vehicles:-" + offlineDevicePOJOS.size());
//        Log.d(TagUtils.getTag(), "idle_vehicles:-" + idleDevicePOJOS.size());
//        Log.d(TagUtils.getTag(), "running_vehicles:-" + runningDevicePOJOS.size());

        tv_total.setText(String.valueOf("Total : " + Constants.devicePOJOS.size()));
        tv_running.setText(String.valueOf("Running : " + runningDevicePOJOS.size()));
        tv_parked.setText(String.valueOf("Parked : " + idleDevicePOJOS.size()));
        tv_offline.setText(String.valueOf("Offline : " + offlineDevicePOJOS.size()));
        setPolarChartData(Constants.devicePOJOS.size(), runningDevicePOJOS.size(), offlineDevicePOJOS.size(), idleDevicePOJOS.size());
        setHorizontal_bar_chart(Constants.devicePOJOS.size(), runningDevicePOJOS.size(), offlineDevicePOJOS.size(), idleDevicePOJOS.size());
    }

    public void getDashBoardData() {

        showProgressBar();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("cmd", "load_object_data"));

        new WebServiceBaseResponseList<ObjectDataPOJO>(nameValuePairs, this, new ResponseListCallback<ObjectDataPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<ObjectDataPOJO> responseListPOJO) {
                dismissProgressBar();
                swipeRefreshLayout.setRefreshing(false);
                try {
                    if (responseListPOJO.isSuccess()) {
                        if (googleMap != null) {
                            googleMap.clear();
                        }
                        Constants.devicePOJOS.clear();
                        offlineDevicePOJOS.clear();
                        runningDevicePOJOS.clear();
                        idleDevicePOJOS.clear();

                        for (int i = 0; i < responseListPOJO.getResultList().size(); i++) {

                            try{
                                ObjectDataPOJO objectDataPOJO = responseListPOJO.getResultList().get(i);
                                if (objectDataPOJO != null && objectDataPOJO.getObjectDetailPOJO() != null) {
                                    Log.d(TagUtils.getTag(), "not null at i:-" + i);
                                } else {
                                    Log.d(TagUtils.getTag(), "null at i:-" + i);
                                }
                                if (objectDataPOJO != null && objectDataPOJO.getObjectDetailPOJO() != null) {
                                    DevicePOJO devicePOJO = new DevicePOJO();
                                    devicePOJO.setImei(objectDataPOJO.getImei());
                                    devicePOJO.setUserId(Constants.userDetail.getId());

                                    DeviceDetailPOJO deviceDetailPOJO = new DeviceDetailPOJO();

                                    deviceDetailPOJO.setDtTracker(objectDataPOJO.getObjectDetailPOJO().getDtTracker());
                                    deviceDetailPOJO.setLat(objectDataPOJO.getObjectDetailPOJO().getLat());
                                    deviceDetailPOJO.setLng(objectDataPOJO.getObjectDetailPOJO().getLng());
                                    deviceDetailPOJO.setAngle(objectDataPOJO.getObjectDetailPOJO().getAngle());
                                    deviceDetailPOJO.setSpeed(String.valueOf(objectDataPOJO.getObjectDetailPOJO().getSpeed()));
                                    try{
                                        if(objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO()!=null) {
                                            deviceDetailPOJO.setParams(objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO().toString());
                                        }else{
                                            deviceDetailPOJO.setParams("");
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        deviceDetailPOJO.setParams("");
                                    }

                                    deviceDetailPOJO.setName(objectDataPOJO.getName());
                                    deviceDetailPOJO.setIcon(objectDataPOJO.getIcon());
                                    deviceDetailPOJO.setCn(objectDataPOJO.getCn());
                                    deviceDetailPOJO.setDevice(objectDataPOJO.getDevice());
                                    deviceDetailPOJO.setModel(objectDataPOJO.getModel());
                                    deviceDetailPOJO.setPlateNumber(objectDataPOJO.getPlate_number());
                                    deviceDetailPOJO.setOdometer(objectDataPOJO.getOdometer());
                                    deviceDetailPOJO.setSt(objectDataPOJO.getSt());

                                    if (objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO() != null) {
                                        DeviceParamPOJO deviceParamPOJO = new DeviceParamPOJO();
                                        if (objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO().getAcc() != null) {
                                            deviceParamPOJO.setAcc(objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO().getAcc());
                                        }
                                        if (objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO().getAccv() != null) {
                                            deviceParamPOJO.setAccv(objectDataPOJO.getObjectDetailPOJO().getObjectParamPOJO().getAccv());
                                        }
                                        deviceDetailPOJO.setDeviceParamPOJO(deviceParamPOJO);
                                    }

                                    devicePOJO.setDeviceDetailPOJO(deviceDetailPOJO);

                                    Constants.devicePOJOS.add(devicePOJO);

                                    if (objectDataPOJO.getSt() != null) {
                                        switch (objectDataPOJO.getSt().toLowerCase()) {
                                            case "off":
                                                offlineDevicePOJOS.add(devicePOJO);
                                                break;
                                            case "m":
                                                runningDevicePOJOS.add(devicePOJO);
                                                break;
                                            case "s":
                                                idleDevicePOJOS.add(devicePOJO);
                                                break;
                                            case "i":
                                                idleDevicePOJOS.add(devicePOJO);
                                                break;
                                            default:
                                                offlineDevicePOJOS.add(devicePOJO);
                                                break;
                                        }
                                    } else {
                                        offlineDevicePOJOS.add(devicePOJO);
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        updateDeviceStatus();

                        rvdevicesPojos.clear();
                        rvdevicesPojos.addAll(Constants.devicePOJOS);
                        deviceListAdapter.notifyDataSetChanged();

                        showAllDevices(Constants.devicePOJOS);
                        String selected_imei = Pref.GetStringPref(getApplicationContext(), StringUtils.HOME_LAST_SELECTED_DEVICE, "");
                        if (selected_imei.equals("")) {
                            selectedDevicePOJO = Constants.devicePOJOS.get(0);
                        } else {
                            selectedDevicePOJO = UtilityFunction.getDeviceFromList(Constants.devicePOJOS, selected_imei);
                        }
                        getObjectHistory(false);

                        setTimer();

                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ObjectDataPOJO.class, "GET_DEVICE_LIST", false).execute(WebServicesUrls.PLATFORM_DEVICE_LIST_API);
    }

    Marker[] initialMarker;
    //    Polyline[] polylines;
    DevicePOJO visibleDevicePOJO;

    public void showAllDevices(final List<DevicePOJO> devicePOJOS) {
        if (googleMap != null) {

            Log.d(TagUtils.getTag(), "listing devicepojos:-" + devicePOJOS.toString());

            googleMap.clear();

            if (initialMarker != null) {
                for (Marker marker : initialMarker) {
                    marker = null;
                }
            }

            polylines.clear();
//            if (polylines != null) {
//                for (Polyline polyline : polylines) {
//                    polyline = null;
//                }
//            }

            initialMarker = null;
//            polylines = null;
            if (devicePOJOS.size() > 0) {
                initialMarker = new Marker[devicePOJOS.size()];
//                polylines = new Polyline[devicePOJOS.size()];
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (int i = 0; i < devicePOJOS.size(); i++) {
                    DevicePOJO devicePOJO = devicePOJOS.get(i);
                    if (devicePOJO.getDeviceDetailPOJO().getLat() != null && devicePOJO.getDeviceDetailPOJO().getLng() != null) {
                        try {
                            LatLng latLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(devicePOJO.getDeviceDetailPOJO().getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_icon));
                            Marker marker = googleMap.addMarker(markerOptions);
                            marker.setRotation(Float.parseFloat(devicePOJO.getDeviceDetailPOJO().getAngle()));
                            initialMarker[i] = marker;
//                            initialMarker[i].setTag(String.valueOf(i));
                            initialMarker[i].setTag(devicePOJO.getImei());
                            builder.include(marker.getPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

//                visibleDevicePOJO = devicePOJOS.get(devicePOJOS.size() - 1);

                LatLngBounds bounds = builder.build();

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.20); // offset from edges of the map 10% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                googleMap.animateCamera(cu);

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        try {
                            Log.d(TagUtils.getTag(), "marker tag:-" + marker.getTag().toString());
//                            int position = Integer.parseInt(marker.getTag().toString());
//                            visibleDevicePOJO = devicePOJOS.get(position);
                            Log.d(TagUtils.getTag(), "devices:-" + devicePOJOS.toString());
                            DevicePOJO devicePOJO = UtilityFunction.getDeviceFromList(devicePOJOS, marker.getTag().toString());
                            if (devicePOJO != null) {
                                Log.d(TagUtils.getTag(), "clicked devicepojo:-" + devicePOJO.getDeviceDetailPOJO().getName());
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng())), 16));
                                updateDeviceData(devicePOJO, true);
                            } else {
                                Log.d(TagUtils.getTag(), "null found");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                });

                adapter = new CustomAutoCompleteAdapter(this, new ArrayList<DevicePOJO>(devicePOJOS));
                et_search_device.setAdapter(adapter);

            }
        } else {
            Log.d(TagUtils.getTag(), "google map is null");
        }
    }

    public void getSelectAutoCompleteDevice(DevicePOJO devicePOJO) {
        if (devicePOJO != null) {
            Log.d(TagUtils.getTag(), "devicepojo:-" + devicePOJO.getDeviceDetailPOJO().getName());
            et_search_device.dismissDropDown();
            LatLng latLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));
            Log.d(TagUtils.getTag(), "device selected imei:-" + devicePOJO.getDeviceDetailPOJO().getName());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            et_search_device.setText("");
            updateDeviceData(devicePOJO, true);
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


    List<DevicePOJO> runningDevicePOJOS = new ArrayList<>();
    List<DevicePOJO> idleDevicePOJOS = new ArrayList<>();
    List<DevicePOJO> offlineDevicePOJOS = new ArrayList<>();

    public DeviceListAdapter deviceListAdapter;
    public List<DevicePOJO> rvdevicesPojos = new ArrayList<>();

    public void attachAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_device_list.setHasFixedSize(true);
        rv_device_list.setLayoutManager(linearLayoutManager);
        deviceListAdapter = new DeviceListAdapter(this, null, rvdevicesPojos);
        rv_device_list.setAdapter(deviceListAdapter);
        rv_device_list.setNestedScrollingEnabled(false);
        rv_device_list.setItemAnimator(new DefaultItemAnimator());
    }


    private void settingNavDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        View view = nvDrawer.inflateHeaderView(R.layout.home_header);

        LinearLayout ll_header = view.findViewById(R.id.ll_header);
        TextView tv_profile_name = view.findViewById(R.id.tv_profile_name);
        tv_profile_name.setText(Constants.userDetail.getUsername());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupDrawerContent(nvDrawer);

        setupDrawerToggle();
//        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(false);

        nvDrawer.setItemIconTintList(null);
        ic_ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });

        ll_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(R.id.frame_home, new ProfileFragment());
            }
        });

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here

            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawer.addDrawerListener(drawerToggle);
    }

    public void showHome() {
        ll_device_type.setVisibility(View.GONE);
        ll_graph_type.setVisibility(View.VISIBLE);
        ll_map_type.setVisibility(View.GONE);
    }

    public void showObjects() {
        ll_device_type.setVisibility(View.VISIBLE);
        ll_graph_type.setVisibility(View.GONE);
        ll_map_type.setVisibility(View.GONE);
    }

    public void showMap() {
        ll_device_type.setVisibility(View.GONE);
        ll_graph_type.setVisibility(View.GONE);
        ll_map_type.setVisibility(View.VISIBLE);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.popup_home:
                ll_device_type.setVisibility(View.GONE);
                ll_graph_type.setVisibility(View.VISIBLE);
                ll_map_type.setVisibility(View.GONE);
                break;
            case R.id.popup_objects:
                ll_device_type.setVisibility(View.VISIBLE);
                ll_graph_type.setVisibility(View.GONE);
                ll_map_type.setVisibility(View.GONE);
                break;
            case R.id.popup_map:
                ll_device_type.setVisibility(View.GONE);
                ll_graph_type.setVisibility(View.GONE);
                ll_map_type.setVisibility(View.VISIBLE);
                break;
            case R.id.popup_events:
                startFragment(R.id.frame_home, new NotificationFragment());
                break;
            case R.id.popup_setting:
                startFragment(R.id.frame_home, new SettingFragment());
                break;
            case R.id.popup_history:
                startActivityForResult(new Intent(this, DeviceSelectActivity.class), Constants.SELECT_DEVICE_FOR_HISTORY);
                break;
            case R.id.popup_report:
                startFragment(R.id.frame_home, new ReportFragment());
                break;
            case R.id.popup_geo:
//                startActivity(new Intent(HomeActivity.this, GeoFenceActivity.class));
                startFragment(R.id.frame_home, new GeoFenceFragment());
                break;
            case R.id.popup_push_start:
                startFragment(R.id.frame_home, new PushStartFragment());
                break;
            case R.id.popup_image:
//                startActivity(new Intent(HomeActivity.this, GeoFenceActivity.class));
                startFragment(R.id.frame_home, new ImagesGridFragment());
                break;
            case R.id.popup_logout:
                callLogoutAPI();
                break;
        }
        mDrawer.closeDrawers();
    }

    public void callLogoutAPI() {
        showProgressBar();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("token", Pref.GetDeviceToken(getApplicationContext(), "")));
        new WebServiceBase(nameValuePairs, null, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                dismissProgressBar();
                try {
                    Pref.SetIntPref(getApplicationContext(), StringUtils.NOTIFICATION_COUNT, 0);
                    Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false);
                    Pref.SetStringPref(getApplicationContext(), StringUtils.HOME_LAST_SELECTED_DEVICE, "");
                    Constants.devicePOJOS.clear();
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finishAffinity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "LOGOUT_API", false).execute(WebServicesUrls.LOGOUT);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
//        getVehicleList();
        getDashBoardData();
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        setcurrentlocationcontrol();
    }

    public void getObjectHistory(final boolean show_progress) {
        tv_running_duration.setText("-");
        tv_max_speed.setText("-");
        tv_avg_speed.setText("-");
        tv_total_duration.setText("-");
        tv_fuel_consumption.setText("-");
        tv_driven_km.setText("-");
        tv_total_stop_time.setText("-");
        if (selectedDevicePOJO != null) {
            if (show_progress) {
                showProgressBar();
            }
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("imei", selectedDevicePOJO.getImei()));
            nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
            nameValuePairs.add(new BasicNameValuePair("dtf", UtilityFunction.convertHistoryserverDateTime(startdatetime)));
            nameValuePairs.add(new BasicNameValuePair("dtt", UtilityFunction.convertHistoryserverDateTime(enddatetime)));
            nameValuePairs.add(new BasicNameValuePair("stop_duration", "5"));
            nameValuePairs.add(new BasicNameValuePair("show_coordinates", "true"));
            nameValuePairs.add(new BasicNameValuePair("show_addresses", "false"));
            nameValuePairs.add(new BasicNameValuePair("zones_addresses", "false"));
            String data_items = "[\"time_a\",\"position_a\",\"time_b\",\"position_b\",\"duration\",\"route_length\",\"fuel_consumption\",\"fuel_cost\",\"total\"]";
            nameValuePairs.add(new BasicNameValuePair("data_items", data_items));

            new WebServiceBaseResponse<TravelSheetPOJO>(nameValuePairs, this, new ResponseCallBack<TravelSheetPOJO>() {
                @Override
                public void onGetMsg(ResponsePOJO<TravelSheetPOJO> responsePOJO) {
                    if (show_progress) {
                        dismissProgressBar();
                    }
                    try {
                        if (responsePOJO.isSuccess()) {
                            updateObjectCards(selectedDevicePOJO, responsePOJO.getResult());
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), responsePOJO.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, TravelSheetPOJO.class, "GET_DEVICE_HISTORY", false).execute(WebServicesUrls.HOME_TRAVEL_SHEET_REPORT_API);
        } else {
            ToastClass.showShortToast(getApplicationContext(), "Please Select Vehicle");
        }
    }

    private void updateObjectCards(DevicePOJO devicePOJO, TravelSheetPOJO travelSheetPOJO) {
        try {
            tv_search.setText(devicePOJO.getDeviceDetailPOJO().getName());
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
    }

    public void setcurrentlocationcontrol() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
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

    DevicePOJO selectedDevicePOJO;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SELECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                selectedDevicePOJO = devicePOJO;
                if (devicePOJO != null) {
                    tv_search.setText(devicePOJO.getDeviceDetailPOJO().getName());
                    Pref.SetStringPref(getApplicationContext(), StringUtils.HOME_LAST_SELECTED_DEVICE, devicePOJO.getImei());
                    getObjectHistory(true);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == Constants.SELECT_DEVICE_FOR_HISTORY) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    Intent intent = new Intent(this, PlayBackActivity.class);
                    intent.putExtra("devicePOJO", devicePOJO);
                    startActivity(intent);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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

            new CountDownTimer(time, 1000) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    getVehicleList();
                }
            }.start();
        }
    }


    public void checkAppVersionCode() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        new WebServiceBase(nameValuePairs, null, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("1")) {
                        String version_code = jsonObject.optJSONObject("result").optString("version_code");
                        if (!version_code.equalsIgnoreCase(StringUtils.VERSION_CODE)) {
                            showVersionUpgradePOPUP();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_VERSION_CODE", false).execute(WebServicesUrls.GET_APP_INFO);
    }

    public void showVersionUpgradePOPUP() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("New Version Released").setMessage("A new version of the app is released please update to the new version").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    public void getCompleteAddress(final DeviceDetailPOJO deviceDetailPOJO, final MarqueeView textView) {
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
                        textView.setText(formatted_address);
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

    public void setPolarChartData(int total_vehicles, int running_vehicles, int offline_vehicles, int parked_vehicles) {
        webview.getSettings().setJavaScriptEnabled(true); // enable javascript


        webview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
                Log.d(TagUtils.getTag(), "error description:-" + description);
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
        String url_called = "http://platform.voxtrail.com/report/polarchart.php?total_vehicles=" + total_vehicles + "&parked_vehicles=" + parked_vehicles + "&offline_vehicles=" + offline_vehicles + "&running_vehicles=" + running_vehicles;
        Log.d(TagUtils.getTag(), "url_called:-" + url_called);
        webview.loadUrl(url_called);

    }

    public void setHorizontal_bar_chart(int total_vehicles, int running_vehicles, int offline_vehicles, int parked_vehicles) {

        horizontal_bar_chart.setDrawBarShadow(false);

        horizontal_bar_chart.setDrawValueAboveBar(true);

        horizontal_bar_chart.getDescription().setEnabled(false);

        horizontal_bar_chart.setMaxVisibleValueCount(60);

        horizontal_bar_chart.setPinchZoom(false);

        horizontal_bar_chart.setDrawGridBackground(false);

        XAxis xl = horizontal_bar_chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = horizontal_bar_chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = horizontal_bar_chart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

//        setData(12, 50);

        float barWidth = 9f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        String[] labels = new String[40];
        labels[10] = "Running";
        labels[20] = "Parked";
        labels[30] = "Offline";
        yVals1.add(new BarEntry(10, running_vehicles));
        yVals1.add(new BarEntry(20, parked_vehicles));
        yVals1.add(new BarEntry(30, offline_vehicles));
        BarDataSet set1 = new BarDataSet(yVals1, "Vehicles");
        set1.setDrawIcons(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(barWidth);
        horizontal_bar_chart.setData(data);
        horizontal_bar_chart.getXAxis().setValueFormatter(new LabelFormatter(labels));

        horizontal_bar_chart.setFitBars(true);
        horizontal_bar_chart.animateY(2500);

        horizontal_bar_chart.setDragEnabled(true); // on by default
//        horizontal_bar_chart.setVisibleXRange(0,3); // sets the viewport to show 3 bars

        Legend l = horizontal_bar_chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);


    }

    public void showNotificationCount() {
        tv_notification_num.setText(String.valueOf(Pref.GetIntPref(getApplicationContext(), StringUtils.NOTIFICATION_COUNT, 0)));
    }

    public class LabelFormatter implements IAxisValueFormatter {
        private final String[] mLabels;

        public LabelFormatter(String[] labels) {
            mLabels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mLabels[(int) value];
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
                HomeActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getFragmentManager(), "Select Time");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
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
    }

    @Override
    public void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.UPDATE_ALERT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showNotificationCount();
        }
    };

}
