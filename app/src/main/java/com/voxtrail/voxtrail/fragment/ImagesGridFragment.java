package com.voxtrail.voxtrail.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.DeviceSelectActivity;
import com.voxtrail.voxtrail.adapter.ImagesAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.ImagesPOJO;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.ResponseListCallback;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponseList;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImagesGridFragment extends FragmentController {

    @BindView(R.id.rv_images)
    RecyclerView rv_images;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.frame_device)
    FrameLayout frame_device;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_device_name)
    TextView tv_device_name;

    DevicePOJO devicePOJO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_images, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Constants.devicePOJOS != null && Constants.devicePOJOS.size() > 0) {
            tv_device_name.setText(Constants.devicePOJOS.get(0).getDeviceDetailPOJO().getName());
        }

        frame_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DeviceSelectActivity.class);
                startActivityForResult(i, Constants.SELECT_DEVICE);
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Constants.devicePOJOS != null && Constants.devicePOJOS.size() > 0) {
            devicePOJO = Constants.devicePOJOS.get(0);
        }

        callImagesAPI();

    }

    @Override
    public void onResume() {
        super.onResume();
        callImagesAPI();
    }

    public void callImagesAPI() {
        if (devicePOJO != null) {
            swipeRefreshLayout.setRefreshing(true);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
            nameValuePairs.add(new BasicNameValuePair("imei", devicePOJO.getImei()));
            new WebServiceBaseResponseList<ImagesPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<ImagesPOJO>() {
                @Override
                public void onGetMsg(ResponseListPOJO<ImagesPOJO> responseListPOJO) {
                    swipeRefreshLayout.setRefreshing(false);
                    strings.clear();
                    try {
                        if (responseListPOJO.isSuccess()) {
                            strings.addAll(responseListPOJO.getResultList());
                        } else {
                            ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    geoFenceAdapter.notifyDataSetChanged();
                }
            }, ImagesPOJO.class, "GET_IMAGES", true).execute(WebServicesUrls.IMAGES_API);
        }
    }


    List<ImagesPOJO> strings = new ArrayList<>();
    ImagesAdapter geoFenceAdapter;

    public void attachAdapter() {

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3);
        rv_images.setHasFixedSize(true);
        rv_images.setLayoutManager(linearLayoutManager);
        geoFenceAdapter = new ImagesAdapter(getActivity(), this, strings);
        rv_images.setAdapter(geoFenceAdapter);
        rv_images.setNestedScrollingEnabled(false);
        rv_images.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SELECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    tv_device_name.setText(devicePOJO.getDeviceDetailPOJO().getName());
                    this.devicePOJO = devicePOJO;
                    callImagesAPI();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
