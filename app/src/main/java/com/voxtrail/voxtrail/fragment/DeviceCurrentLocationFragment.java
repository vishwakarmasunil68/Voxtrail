package com.voxtrail.voxtrail.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;

import butterknife.BindView;

public class DeviceCurrentLocationFragment extends FragmentController implements OnMapReadyCallback {

    GoogleMap googleMap;

    DevicePOJO devicePOJO;

    @BindView(R.id.ic_back)
    ImageView ic_back;

    public DeviceCurrentLocationFragment(DevicePOJO devicePOJO) {
        this.devicePOJO = devicePOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_device_current_loc, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (devicePOJO != null) {
            showLocation(devicePOJO);
        }
    }

    public void showLocation(DevicePOJO devicePOJO) {
        try {
            if (devicePOJO.getDeviceDetailPOJO().getLat() != null && devicePOJO.getDeviceDetailPOJO().getLng() != null) {
                LatLng latLng = new LatLng(Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLat()), Double.parseDouble(devicePOJO.getDeviceDetailPOJO().getLng()));
//                BitmapDescriptor icon = getBitmapDescriptor(devicePOJO.getDeviceDetailPOJO().getDevice().getVehicleType().toLowerCase());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(devicePOJO.getDeviceDetailPOJO().getName());
                Marker marker = googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}