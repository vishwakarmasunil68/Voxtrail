package com.voxtrail.voxtrail.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.pojo.geofence.GeoFencePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import net.igenius.customcheckbox.CustomCheckBox;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.defaults.colorpicker.ColorPickerPopup;

public class AddGeoFenceActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    List<LatLng> latLngList = new ArrayList<>();

    @BindView(R.id.btn_reset)
    Button btn_reset;
    @BindView(R.id.btn_set)
    Button btn_set;

    boolean is_update = false;
    GeoFencePOJO geoFencePOJO;
    String zone_color="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_add_geo_fence);
        ButterKnife.bind(this);

        is_update = getIntent().getBooleanExtra("is_update", false);
        geoFencePOJO = (GeoFencePOJO) getIntent().getSerializableExtra("geoFencePOJO");
        if(geoFencePOJO!=null){
            zone_color=geoFencePOJO.getZoneColor();
        }else{
            zone_color="#0000FF";
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (latLngList.size() >= 3) {
                    if (btn_set.getText().toString().equalsIgnoreCase("update")) {
                        updateZone();
                    } else {
                        addAddressDialog();
                    }
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please Select atleast 3 latlngs");
                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                latLngList.clear();
                googleMap.clear();
            }
        });

    }

    private void updateZone() {
        if (geoFencePOJO != null) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("zone_id", geoFencePOJO.getZoneId()));
            String latlngs = "";
            for (int i = 0; i < latLngList.size(); i++) {
                if ((i + 1) == latLngList.size()) {
                    latlngs += latLngList.get(i).latitude + "," + latLngList.get(i).longitude;
                } else {
                    latlngs += latLngList.get(i).latitude + "," + latLngList.get(i).longitude + ",";
                }
            }

            Log.d(TagUtils.getTag(), "lat lngs size:-" + latLngList.size());
            Log.d(TagUtils.getTag(), "lat lngs:-" + latLngList.toString());
            nameValuePairs.add(new BasicNameValuePair("zone_vertices", latlngs));

            new WebServiceBase(nameValuePairs, null, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optString("status").equalsIgnoreCase("1")) {
                            finish();
                        }
                        ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "UPDATE ZONE", false).execute(WebServicesUrls.updateZone);
        }
    }

    public List<LatLng> splitVertices(String zone_vertices) {
        String vertices[] = zone_vertices.split(",");
        List<LatLng> latLngs = new ArrayList<>();
//        Log.d(TagUtils.getTag(), "zone vertices:-" + zone_vertices);
//        for (int i = 0; i < (vertices.length) / 2; i++) {
//            LatLng latLng = new LatLng(Double.parseDouble(vertices[i]), Double.parseDouble(vertices[i + 1]));
//            latLngs.add(latLng);
//        }

        for (int i = 0; i < vertices.length; i++) {
            LatLng latLng = new LatLng(Double.parseDouble(vertices[i]), Double.parseDouble(vertices[i + 1]));
            latLngs.add(latLng);
            i++;
        }


        Log.d(TagUtils.getTag(), "lat lngs:-" + latLngs.toString());

        return latLngs;
    }

    String hex = "FF0000";

    public void addAddressDialog() {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_add_address);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final EditText et_name = dialog1.findViewById(R.id.et_name);
        final CustomCheckBox check_visible = dialog1.findViewById(R.id.check_visible);
        final CustomCheckBox check_name_visible = dialog1.findViewById(R.id.check_name_visible);
        final View view_color = dialog1.findViewById(R.id.view_color);
        Button btn_add = dialog1.findViewById(R.id.btn_add);

        view_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new ColorPickerPopup.Builder(AddGeoFenceActivity.this)
                        .initialColor(Color.RED) // Set initial color
                        .enableAlpha(true) // Enable alpha slider or not
                        .okTitle("Choose")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(view, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {
                                Log.d(TagUtils.getTag(), "color:-" + color);
                                hex = String.valueOf(Integer.toHexString(color));
                                view.setBackgroundColor(color);
                            }

                            @Override
                            public void onColor(int color, boolean fromUser) {
                                Log.d(TagUtils.getTag(), "color new:-" + color);
                            }
                        });
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_name.getText().toString().length() > 0) {
                    createFence(et_name.getText().toString(), check_visible.isChecked(), check_name_visible.isChecked());
                    dialog1.dismiss();
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Address");
                }
            }
        });

    }

    public void createFence(String name, boolean zone_visible, boolean name_visible) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("group_id", "0"));
        nameValuePairs.add(new BasicNameValuePair("zone_name", name));
        nameValuePairs.add(new BasicNameValuePair("zone_color", "#" + hex));
        nameValuePairs.add(new BasicNameValuePair("zone_visible", String.valueOf(zone_visible)));
        nameValuePairs.add(new BasicNameValuePair("zone_name_visible", String.valueOf(name_visible)));
        nameValuePairs.add(new BasicNameValuePair("zone_area", "0"));
        String latlngs = "";
        for (int i = 0; i < latLngList.size(); i++) {
            if ((i + 1) == latLngList.size()) {
                latlngs += latLngList.get(i).latitude + "," + latLngList.get(i).longitude;
            } else {
                latlngs += latLngList.get(i).latitude + "," + latLngList.get(i).longitude + ",";
            }
        }

        Log.d(TagUtils.getTag(), "lat lngs size:-" + latLngList.size());
        Log.d(TagUtils.getTag(), "lat lngs:-" + latLngList.toString());
        nameValuePairs.add(new BasicNameValuePair("zone_vertices", latlngs));

        new WebServiceBase(nameValuePairs, null, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("1")) {
                        finish();
                    }
                    ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CREATE_POLYFENCE", false).execute(WebServicesUrls.SAVE_ZONE);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TagUtils.getTag(), "latitude:-" + latLng.latitude + " ,long:-" + latLng.longitude);
                latLngList.add(latLng);
                setPolyMarker();
            }
        });
        if (is_update) {
            btn_set.setText("UPDATE");
            if (geoFencePOJO != null) {

                Log.d(TagUtils.getTag(), "geofence data:-" + geoFencePOJO.toString());
//                latLngList.add(latLng);
//                setPolyMarker();
                String zone_vertices = geoFencePOJO.getZoneVertices();
                for (LatLng latLng : splitVertices(zone_vertices)) {
                    latLngList.add(latLng);
                }
                setPolyMarker();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngList.get(0), 17));
            }
        } else {
            btn_set.setText("Set");
            Permissions.check(this/*context*/, Manifest.permission.ACCESS_FINE_LOCATION, null, new PermissionHandler() {
                @Override
                public void onGranted() {
                    // do your task.
                    double[] latlng = UtilityFunction.getLocation(getApplicationContext());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latlng[0], latlng[1]), 17));
                }
            });
        }
    }

    public void setPolyMarker() {
        googleMap.clear();
        if (latLngList.size() > 0) {
            PolygonOptions rectOptions = new PolygonOptions();
            for (LatLng latLng : latLngList) {
                showPolyLocation(latLng.latitude, latLng.longitude);
                rectOptions.add(latLng);
            }
            rectOptions.strokeColor(Color.RED);
            rectOptions.fillColor(Color.parseColor(zone_color));
            googleMap.addPolygon(rectOptions);
        }
    }

    public void checkLocation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
            double[] latlong = UtilityFunction.getLocation(getApplicationContext());
            showLocation(latlong[0], latlong[1]);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.ACCESS_LOCATION);
            return;
        } else {
            double[] latlong = UtilityFunction.getLocation(getApplicationContext());
            showLocation(latlong[0], latlong[1]);
        }
    }

    public void showLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("current loc");
        Marker marker = googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }


    public void showPolyLocation(double lat, double lng) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_polygon_marker);

        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("").icon(icon);
        Marker marker = googleMap.addMarker(markerOptions);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

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