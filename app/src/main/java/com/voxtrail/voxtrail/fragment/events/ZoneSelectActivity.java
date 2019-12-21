package com.voxtrail.voxtrail.fragment.events;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.SelectZoneAdapter;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.geofence.GeoFenceCollectionPOJO;
import com.voxtrail.voxtrail.pojo.geofence.GeoFencePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
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

public class ZoneSelectActivity extends AppCompatActivity {

    @BindView(R.id.rv_zone_list)
    RecyclerView rv_zone_list;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.iv_select)
    ImageView iv_select;

    String zones="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_select);

        zones=getIntent().getStringExtra("zones");

        ButterKnife.bind(this);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        attachAdapter();
        callGeoFenceAPI();

        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GeoFencePOJO> geoFencePOJOS=new ArrayList<>();
                for(GeoFencePOJO geoFencePOJO:strings){
                    if(geoFencePOJO.isChecked()){
                        geoFencePOJOS.add(geoFencePOJO);
                    }
                }

                if(geoFencePOJOS.size()>0){
                    GeoFenceCollectionPOJO geoFenceCollectionPOJO=new GeoFenceCollectionPOJO();
                    geoFenceCollectionPOJO.setGeoFencePOJOS(geoFencePOJOS);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("geoFenceCollectionPOJO",geoFenceCollectionPOJO);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please Select Zones");
                }
            }
        });
    }

    public void callGeoFenceAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        new WebServiceBaseResponseList<GeoFencePOJO>(nameValuePairs, this, new ResponseListCallback<GeoFencePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<GeoFencePOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        strings.clear();
                        strings.addAll(responseListPOJO.getResultList());
                        Log.d(TagUtils.getTag(),"zones:-"+zones);
                        if(!zones.equals("")){
                            String splitZones[]=zones.split(",");
                            for(String zone:splitZones){
                                for(GeoFencePOJO geoFencePOJO:strings){
                                    if(geoFencePOJO.getZoneId().equals(zone)){
                                        geoFencePOJO.setChecked(true);
                                    }
                                }
                            }
                        }

                        geoFenceAdapter.notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, GeoFencePOJO.class, "GET_USER_ZONES", true).execute(WebServicesUrls.GET_USER_ZONES);
    }


    List<GeoFencePOJO> strings = new ArrayList<>();
    SelectZoneAdapter geoFenceAdapter;

    public void attachAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_zone_list.setHasFixedSize(true);
        rv_zone_list.setLayoutManager(linearLayoutManager);
        geoFenceAdapter = new SelectZoneAdapter(this, null, strings);
        rv_zone_list.setAdapter(geoFenceAdapter);
        rv_zone_list.setNestedScrollingEnabled(false);
        rv_zone_list.setItemAnimator(new DefaultItemAnimator());
    }
}
