package com.voxtrail.voxtrail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.AddGeoFenceActivity;
import com.voxtrail.voxtrail.activity.GeoFenceActivity;
import com.voxtrail.voxtrail.adapter.GeoFenceAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.geofence.GeoFencePOJO;
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

public class GeoFenceFragment extends FragmentController {

    @BindView(R.id.rv_geofence)
    RecyclerView rv_geofence;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.iv_plus)
    ImageView iv_plus;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_geo_fence, container, false);
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

        iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddGeoFenceActivity.class).putExtra("is_update",false));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callGeoFenceAPI();
    }

    public void callGeoFenceAPI() {
        swipeRefreshLayout.setRefreshing(true);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        new WebServiceBaseResponseList<GeoFencePOJO>(nameValuePairs, getActivity(), new ResponseListCallback<GeoFencePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<GeoFencePOJO> responseListPOJO) {
                swipeRefreshLayout.setRefreshing(false);
                try {
                    if (responseListPOJO.isSuccess()) {
                        strings.clear();
                        strings.addAll(responseListPOJO.getResultList());
                        geoFenceAdapter.notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, GeoFencePOJO.class, "GET_USER_ZONES", true).execute(WebServicesUrls.GET_USER_ZONES);
    }


    List<GeoFencePOJO> strings = new ArrayList<>();
    GeoFenceAdapter geoFenceAdapter;

    public void attachAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_geofence.setHasFixedSize(true);
        rv_geofence.setLayoutManager(linearLayoutManager);
        geoFenceAdapter = new GeoFenceAdapter(getActivity(), this, strings);
        rv_geofence.setAdapter(geoFenceAdapter);
        rv_geofence.setNestedScrollingEnabled(false);
        rv_geofence.setItemAnimator(new DefaultItemAnimator());
    }

}
