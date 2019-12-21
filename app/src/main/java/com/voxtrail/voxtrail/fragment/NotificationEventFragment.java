package com.voxtrail.voxtrail.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.event.LastEventPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DeviceDetailPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.webservice.GetWebServices;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.xzl.marquee.library.MarqueeView;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;

public class NotificationEventFragment extends FragmentController implements OnMapReadyCallback {

    GoogleMap googleMap;

//    DevicePOJO devicePOJO;

    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.tv_object_name)
    TextView tv_object_name;
    @BindView(R.id.tv_event_name)
    TextView tv_event_name;
    @BindView(R.id.tv_dt)
    TextView tv_dt;
    @BindView(R.id.tv_speed)
    TextView tv_speed;
    @BindView(R.id.tv_address)
    TextView tv_address;


//    public NotificationEventFragment(DevicePOJO devicePOJO) {
//        this.devicePOJO = devicePOJO;
//    }

    LastEventPOJO lastEventPOJO;
    public NotificationEventFragment(LastEventPOJO lastEventPOJO){
        this.lastEventPOJO=lastEventPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notification_event, container, false);
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

        if (lastEventPOJO != null) {
            showLocation(lastEventPOJO);
        }
    }

    public void showLocation(LastEventPOJO lastEventPOJO) {
        try {
            LatLng latLng=new LatLng(Double.parseDouble(lastEventPOJO.getLat()),Double.parseDouble(lastEventPOJO.getLng()));
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(lastEventPOJO.getName());
            Marker marker = googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));


            tv_object_name.setText(lastEventPOJO.getName());
            tv_event_name.setText(lastEventPOJO.getEventDesc());
            tv_dt.setText(lastEventPOJO.getDtTracker());
            tv_speed.setText(lastEventPOJO.getSpeed()+" kph");
            getCompleteAddress();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCompleteAddress() {
        try {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(lastEventPOJO.getLat()) + "," + String.valueOf(lastEventPOJO.getLng()) + "&sensor=true&key=" + getResources().getString(R.string.google_map_api_key);

            new GetWebServices(getActivity(), null, new WebServicesCallBack() {
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