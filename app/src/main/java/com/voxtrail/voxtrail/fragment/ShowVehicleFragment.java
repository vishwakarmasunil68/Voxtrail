package com.voxtrail.voxtrail.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;

import java.util.List;

import butterknife.BindView;

public class ShowVehicleFragment extends FragmentController implements OnMapReadyCallback {

    @BindView(R.id.ic_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    List<DevicePOJO> devicePOJOS;
    String title = "";
    GoogleMap googleMap;

    public ShowVehicleFragment(String title, List<DevicePOJO> devicePOJOS) {
        this.devicePOJOS = devicePOJOS;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_show_devices, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_title.setText(title);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (devicePOJOS != null && devicePOJOS.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int i = 0; i < devicePOJOS.size(); i++) {
                DevicePOJO devicePOJO = devicePOJOS.get(i);
                try {
                    if (devicePOJO.getDeviceDetailPOJO().getLat() != null && devicePOJO.getDeviceDetailPOJO().getLng() != null) {
                        LatLng latLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));
//                BitmapDescriptor icon = getBitmapDescriptor(devicePOJO.getDeviceDetailPOJO().getDevice().getVehicleType().toLowerCase());
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(devicePOJO.getDeviceDetailPOJO().getName());
                        Marker marker = googleMap.addMarker(markerOptions);
                        builder.include(marker.getPosition());
//                        if (i == (devicePOJOS.size() - 1)) {
//                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
//                        }
                    }

                    LatLngBounds bounds = builder.build();

                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                    googleMap.animateCamera(cu);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
