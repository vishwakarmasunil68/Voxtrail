package com.voxtrail.voxtrail.fragment;

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
import com.voxtrail.voxtrail.activity.HomeActivity;
import com.voxtrail.voxtrail.adapter.NotificationAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.event.LastEventPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.ResponseListCallback;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponseList;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class NotificationFragment extends FragmentController {

    @BindView(R.id.rv_notification)
    RecyclerView rv_notification;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ic_back)
    ImageView iv_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notification, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callNotificationAPI();
            }
        });
//        getTodayRange();
        getWeekRange();
        callNotificationAPI();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    String startdatetime="";
    String enddatetime="";

    public void getTodayRange() {
        startdatetime = UtilityFunction.getCurrentDate() + " 00:00:00";
        enddatetime = UtilityFunction.getCurrentDate() + " 23:59:00";
    }

    public void getWeekRange(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        startdatetime = dateFormat.format(cal.getTime()) + " 00:00:00";
        enddatetime = UtilityFunction.getCurrentDate() + " 23:59:00";
//        enddatetime = dateFormat.format(cal.getTime()) + " 23:59:00";
//        setDateTime();
    }

    public void callNotificationAPI() {
        swipeRefreshLayout.setRefreshing(true);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("dtf", UtilityFunction.convertHistoryserverDateTime(startdatetime)));
        nameValuePairs.add(new BasicNameValuePair("dtt", UtilityFunction.convertHistoryserverDateTime(enddatetime)));
        new WebServiceBaseResponseList<LastEventPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<LastEventPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<LastEventPOJO> responseListPOJO) {
                swipeRefreshLayout.setRefreshing(false);
                try {
                    if (responseListPOJO.isSuccess()) {
                        deviceStrings.clear();
                        deviceStrings.addAll(responseListPOJO.getResultList());
                        notificationAdapter.notifyDataSetChanged();
                        Pref.SetIntPref(getActivity().getApplicationContext(), StringUtils.NOTIFICATION_COUNT, 0);
                        if(getActivity() instanceof HomeActivity){
                            HomeActivity homeActivity= (HomeActivity) getActivity();
                            homeActivity.showNotificationCount();
                        }
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, LastEventPOJO.class, "GET_ALERTS", true).execute(WebServicesUrls.GET_ALERTS_IN_RANGE);
    }

    NotificationAdapter notificationAdapter;
    List<LastEventPOJO> deviceStrings = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_notification.setHasFixedSize(true);
        rv_notification.setLayoutManager(linearLayoutManager);
        notificationAdapter = new NotificationAdapter(getActivity(), null, deviceStrings);
        rv_notification.setAdapter(notificationAdapter);
        rv_notification.setNestedScrollingEnabled(false);
        rv_notification.setItemAnimator(new DefaultItemAnimator());
        notificationAdapter.setAdapterItemAdapter(new AdapterListener() {
            @Override
            public void onAdapterItemClickListener(int position) {
//                getObject(deviceStrings.get(position).getUserId(), deviceStrings.get(position).getImei());
                activityManager.startFragment(R.id.frame_home, new NotificationEventFragment(deviceStrings.get(position)));
            }
        });
    }

    public void getObject(String user_id, String imei) {
        activityManager.showProgressBar();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        new WebServiceBaseResponse<DevicePOJO>(nameValuePairs, getActivity(), new ResponseCallBack<DevicePOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<DevicePOJO> responsePOJO) {
                activityManager.dismissProgressBar();
                try {
                    if (responsePOJO.isSuccess()) {
//                        Intent intent = new Intent(getActivity(), DeviceActivity.class);
//                        responsePOJO.getResult().getDeviceDetailPOJO().setLat(lat);
//                        responsePOJO.getResult().getDeviceDetailPOJO().setLng(lng);
//                        intent.putExtra("devicePOJO", responsePOJO.getResult());
//                        startActivity(intent);
//                        activityManager.startFragment(R.id.frame_home, new NotificationEventFragment(responsePOJO.getResult()));
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, DevicePOJO.class, "GET_OBJECT", true).execute(WebServicesUrls.GET_OBJECT);
    }

}
