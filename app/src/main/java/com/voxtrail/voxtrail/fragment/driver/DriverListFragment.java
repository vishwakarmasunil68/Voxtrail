package com.voxtrail.voxtrail.fragment.driver;

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
import com.voxtrail.voxtrail.adapter.DriverAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.device.DriverPOJO;
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

public class DriverListFragment extends FragmentController {

    @BindView(R.id.rv_drivers)
    RecyclerView rv_drivers;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_driver, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        callAPI();
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityManager.startFragmentForResult(R.id.frame_home,DriverListFragment.this,new DriverAddFragment(),101);
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI();
            }
        });
    }

    public void callAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        swipeRefreshLayout.setRefreshing(true);
        new WebServiceBaseResponseList<DriverPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<DriverPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<DriverPOJO> responseListPOJO) {
                swipeRefreshLayout.setRefreshing(false);
                try{
                    if(responseListPOJO.isSuccess()){
                        deviceStrings.clear();
                        deviceStrings.addAll(responseListPOJO.getResultList());
                        driverAdapter.notifyDataSetChanged();
                    }else{
                        ToastClass.showShortToast(getActivity().getApplicationContext(),responseListPOJO.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, DriverPOJO.class,"GET_DRIVERS",false).execute(WebServicesUrls.GET_DRIVERS);
    }


    DriverAdapter driverAdapter;
    List<DriverPOJO> deviceStrings = new ArrayList<>();

    public void attachAdapter() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_drivers.setHasFixedSize(true);
        rv_drivers.setLayoutManager(linearLayoutManager);
        driverAdapter = new DriverAdapter(getActivity(), this, deviceStrings);
        rv_drivers.setAdapter(driverAdapter);
        rv_drivers.setNestedScrollingEnabled(false);
        rv_drivers.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        callAPI();
    }
}
