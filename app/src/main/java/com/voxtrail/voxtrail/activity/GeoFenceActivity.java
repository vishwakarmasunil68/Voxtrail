package com.voxtrail.voxtrail.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.DeviceListAdapter;
import com.voxtrail.voxtrail.adapter.GeoFenceAdapter;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.event.LastEventPOJO;
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
import butterknife.ButterKnife;

public class GeoFenceActivity extends AppCompatActivity {

    @BindView(R.id.rv_geofence)
    RecyclerView rv_geofence;
    @BindView(R.id.iv_plus)
    ImageView iv_plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_geo_fence);
        ButterKnife.bind(this);

        attachAdapter();
        iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeoFenceActivity.this,AddGeoFenceActivity.class));
            }
        });
    }

//
//    public void callNotificationAPI() {
//        swipeRefreshLayout.setRefreshing(true);
//        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
//        new WebServiceBaseResponseList<LastEventPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<LastEventPOJO>() {
//            @Override
//            public void onGetMsg(ResponseListPOJO<LastEventPOJO> responseListPOJO) {
//                swipeRefreshLayout.setRefreshing(false);
//                try {
//                    if (responseListPOJO.isSuccess()) {
//                        deviceStrings.clear();
//                        deviceStrings.addAll(responseListPOJO.getResultList());
//                        notificationAdapter.notifyDataSetChanged();
//                    } else {
//                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, LastEventPOJO.class, "GET_ALERTS", true).execute(WebServicesUrls.GET_ALERTS);
//    }

    List<String> strings=new ArrayList<>();
    GeoFenceAdapter geoFenceAdapter;

    public void attachAdapter() {

        for(int i=0;i<10;i++){
            strings.add("");
        }

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        rv_geofence.setHasFixedSize(true);
//        rv_geofence.setLayoutManager(linearLayoutManager);
//        geoFenceAdapter = new GeoFenceAdapter(this, null, strings);
//        rv_geofence.setAdapter(geoFenceAdapter);
//        rv_geofence.setNestedScrollingEnabled(false);
//        rv_geofence.setItemAnimator(new DefaultItemAnimator());
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
