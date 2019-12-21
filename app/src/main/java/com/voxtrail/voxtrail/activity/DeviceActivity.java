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
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.ViewPagerAdapter;
import com.voxtrail.voxtrail.fragment.DeviceDataFragment;
import com.voxtrail.voxtrail.fragment.DeviceDataFragment2;
import com.voxtrail.voxtrail.fragmentcontroller.ActivityManager;
import com.voxtrail.voxtrail.listener.DataParser;
import com.voxtrail.voxtrail.pojo.DeviceUpdatedPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DeviceDetailPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.GetWebServices;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceActivity extends ActivityManager implements OnMapReadyCallback {

    GoogleMap googleMap;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;
    @BindView(R.id.iv_map_type)
    ImageView iv_map_type;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_slider_1)
    ImageView iv_slider_1;
    @BindView(R.id.iv_slider_2)
    ImageView iv_slider_2;
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
    @BindView(R.id.tv_address)
    TextView tv_address;

    DevicePOJO devicePOJO;

    boolean is_satellite_view = false;
    boolean is_traffic_view = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);

        devicePOJO = (DevicePOJO) getIntent().getSerializableExtra("devicePOJO");

        previousLatLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));

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
                    animateToCurrentLocation(current_lat, current_lng);
//                    animateToCurrentLocation(28.5316888, 77.2126535);
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
                    double device_current_lat = Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat());
                    double device_current_lng = Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng());

                    LatLng phoneLatLng = new LatLng(current_lat, current_lng);
                    LatLng deviceLatLng = new LatLng(device_current_lat, device_current_lng);
                    String device_url = WebServicesUrls.getMapsApiDirectionsUrl(phoneLatLng, deviceLatLng);
                    Log.d(TagUtils.getTag(), "route_url:-" + device_url);
                    callRouteAPI(device_url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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
//            String result = intent.getStringExtra("data");
//                Log.d(TagUtils.getTag(), "chatresult:-" + result);
                if (imei.equalsIgnoreCase(devicePOJO.getImei())) {
//                    DeviceLocationPOJO deviceLocationPOJO = new Gson().fromJson(result, DeviceLocationPOJO.class);
                    drawTrackerPath(deviceUpdatedPOJO);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    boolean is_loaded = false;
    LatLng previousLatLng = null;
    Marker lastMarker = null;
    Polyline lastPolyline = null;

//    public void drawTrackerPath(DeviceLocationPOJO deviceLocationPOJO) {
//        if (previousLatLng != null && deviceLocationPOJO != null && googleMap != null) {
//            LatLng latLng = new LatLng(Double.parseDouble(deviceLocationPOJO.getLat()), Double.parseDouble(deviceLocationPOJO.getLng()));
//
//            try {
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View view = inflater.inflate(R.layout.device_window_info, null);
//
//                ImageView iv_truck = view.findViewById(R.id.iv_truck);
//                float bearing = 0;
//                if (previousLatLng != null) {
//                    bearing = UtilityFunction.getBearing(previousLatLng, latLng);
//                }
//
//
//                if (!Float.isNaN(bearing) && bearing != 0) {
//                    Log.d(TagUtils.getTag(), "bearing:-" + bearing);
//                    iv_truck.setRotation(bearing);
//                }
//
//                TextView tv_speed = view.findViewById(R.id.tv_speed);
//                TextView tv_time = view.findViewById(R.id.tv_time);
//                TextView tv_location = view.findViewById(R.id.tv_location);
//
//                try {
//                    tv_time.setText(String.valueOf(deviceLocationPOJO.getDtTracker()));
//                    tv_speed.setText(String.valueOf(deviceLocationPOJO.getSpeed()));
//                    tv_location.setText(String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                IconGenerator generator = new IconGenerator(this);
//                generator.setBackground(getResources().getDrawable(R.drawable.window_transparent));
//                generator.setContentView(view);
//                Bitmap icon = generator.makeIcon();
//
//                Polyline line = googleMap.addPolyline(new PolylineOptions()
//                        .add(previousLatLng, latLng)
//                        .width(10)
//                        .color(Color.BLUE));
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                if (lastMarker != null) {
//                    lastMarker.remove();
//                }
//                MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(icon)).anchor(0.5f, 0.5f);
//                lastMarker = googleMap.addMarker(markerOptions);
//
//                previousLatLng = latLng;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    public void drawTrackerPath(DeviceUpdatedPOJO deviceUpdatedPOJO) {
        if (previousLatLng != null && deviceUpdatedPOJO != null && googleMap != null) {
            LatLng latLng = new LatLng(Double.parseDouble(deviceUpdatedPOJO.getLat()), Double.parseDouble(deviceUpdatedPOJO.getLng()));

            try {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.device_window_info, null);

                ImageView iv_truck = view.findViewById(R.id.iv_truck);
                float bearing = 0;
                if (previousLatLng != null) {
                    bearing = UtilityFunction.getBearing(previousLatLng, latLng);
                }


                if (!Float.isNaN(bearing) && bearing != 0) {
                    Log.d(TagUtils.getTag(), "bearing:-" + bearing);
                    iv_truck.setRotation(bearing);
                }

                TextView tv_speed = view.findViewById(R.id.tv_speed);
                TextView tv_time = view.findViewById(R.id.tv_time);
                TextView tv_location = view.findViewById(R.id.tv_location);

                try {
                    tv_time.setText(String.valueOf(deviceUpdatedPOJO.getDtTracker()));
                    tv_speed.setText(String.valueOf(deviceUpdatedPOJO.getSpeed()));
                    tv_location.setText(String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));
                } catch (Exception e) {
                    e.printStackTrace();
                }

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

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DeviceDataFragment(devicePOJO), "Monitor");
        adapter.addFrag(new DeviceDataFragment2(devicePOJO), "Device");
//        adapter.addFrag(homeFragment4, "Me");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        iv_slider_1.setImageResource(R.drawable.ic_slider_filled);
                        iv_slider_2.setImageResource(R.drawable.ic_slider_unfilled);
                        break;
                    case 1:
                        iv_slider_1.setImageResource(R.drawable.ic_slider_unfilled);
                        iv_slider_2.setImageResource(R.drawable.ic_slider_filled);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        checkLocation();
        if (devicePOJO != null) {
            final LatLng latLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));
//            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_car_icon);
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).anchor(0.5f, 0.5f).title(devicePOJO.getDeviceDetailPOJO().getName());
            googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    slidingLogic();
                    return false;
                }
            });
            setupViewPager(viewPager);
            getCompleteAddress(devicePOJO.getDeviceDetailPOJO());
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

    public void setCurrentLocationMarker(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
//        LatLng latLng = new LatLng(28.5316888, 77.2126535);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Phone Location");
        googleMap.addMarker(markerOptions);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

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

    double current_lat = 0;
    double current_lng = 0;

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
                setCurrentLocationMarker(current_lat, current_lng);
            }
        });
    }

    public void callRouteAPI(String url) {
        showProgressBar();
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        dismissProgressBar();
//                        Log.d(TagUtils.getTag(), "google response:-" + response.toString());
//                        try {
//                            ParserTask parserTask = new ParserTask();
//                            // Invokes the thread for parsing the JSON data
//                            parserTask.execute(response.toString());
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        dismissProgressBar();
//                        Log.d(TagUtils.getTag(), "error:-" + error.toString());
//                        error.printStackTrace();
//                    }
//                }
//        );
//        queue.add(getRequest);

        new GetWebServices(getApplicationContext(), null, new WebServicesCallBack() {
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
                    googleMap.addPolyline(lineOptions);
                }
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

    public void getCompleteAddress(final DeviceDetailPOJO deviceDetailPOJO) {
        try {
//            showProgressBar();
            String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(deviceDetailPOJO.getLat()) + "," + String.valueOf(deviceDetailPOJO.getLng()) + "&sensor=true";
//
//            StringRequest req = new StringRequest(url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
////                            dismissProgressBar();
//                            try {
//                                Log.d(TagUtils.getTag(), "address response:-" + response);
//                                JSONObject jsonObject = new JSONObject(response);
//                                JSONArray jsonArray = jsonObject.optJSONArray("results");
//                                JSONObject jsonObject1 = jsonArray.optJSONObject(0);
//                                String formatted_address = jsonObject1.optString("formatted_address");
//                                tv_address.setText(formatted_address);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
////                    dismissProgressBar();
//                    Log.d(TagUtils.getTag(), "api error:-" + error.toString());
//                }
//            });
//            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//            queue.add(req);

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

}
