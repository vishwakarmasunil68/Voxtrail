package com.voxtrail.voxtrail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.HomeActivity;
import com.voxtrail.voxtrail.activity.PlayBackActivity;
import com.voxtrail.voxtrail.adapter.DeviceListAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DeviceListFragment extends FragmentController {

//    @BindView(R.id.rv_devices)
//    RecyclerView rv_devices;
//    @BindView(R.id.ic_back)
//    ImageView ic_back;
//    @BindView(R.id.iv_close)
//    ImageView iv_close;
//    @BindView(R.id.et_search)
//    EditText et_search;
//    @BindView(R.id.iv_search)
//    ImageView iv_search;
//    @BindView(R.id.frame_search)
//    FrameLayout frame_search;
//    @BindView(R.id.tv_title)
//    TextView tv_title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_device_list, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        attachAdapter();
//        ic_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//        iv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                frame_search.setVisibility(View.VISIBLE);
//                iv_search.setVisibility(View.GONE);
//                tv_title.setVisibility(View.GONE);
//            }
//        });
//
//        iv_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (et_search.getText().toString().length() > 0) {
//                    et_search.setText("");
//                } else {
//                    frame_search.setVisibility(View.GONE);
//                    iv_search.setVisibility(View.VISIBLE);
//                    tv_title.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        ic_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

    }

//
//    DeviceListAdapter deviceListAdapter;
//    List<DevicePOJO> deviceStrings = new ArrayList<>();
//
//    public void attachAdapter() {
//
//        if (getActivity() instanceof HomeActivity) {
//            HomeActivity homeActivity = (HomeActivity) getActivity();
//            deviceStrings.addAll(homeActivity.devicePOJOS);
//
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//            rv_devices.setHasFixedSize(true);
//            rv_devices.setLayoutManager(linearLayoutManager);
//            deviceListAdapter = new DeviceListAdapter(getActivity(), null, deviceStrings);
//            rv_devices.setAdapter(deviceListAdapter);
//            rv_devices.setNestedScrollingEnabled(false);
//            rv_devices.setItemAnimator(new DefaultItemAnimator());
//
//            deviceListAdapter.setAdapterItemAdapter(new AdapterListener() {
//                @Override
//                public void onAdapterItemClickListener(int position) {
//                    Intent intent = new Intent(getActivity(), PlayBackActivity.class);
//                    intent.putExtra("devicePOJO", deviceStrings.get(position));
//                    startActivity(intent);
//                }
//            });
//        }
//    }

}
