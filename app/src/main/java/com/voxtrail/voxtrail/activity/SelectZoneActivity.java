package com.voxtrail.voxtrail.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.SelectZoneAdapter;
import com.voxtrail.voxtrail.pojo.geofence.GeoFenceCollectionPOJO;
import com.voxtrail.voxtrail.pojo.geofence.GeoFencePOJO;
import com.voxtrail.voxtrail.util.ToastClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectZoneActivity extends AppCompatActivity {

    @BindView(R.id.rv_zone_list)
    RecyclerView rv_zone_list;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.iv_select)
    ImageView iv_select;

    String zones="";
    GeoFenceCollectionPOJO geoFenceCollectionPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_zone);
        zones=getIntent().getStringExtra("zones");

        ButterKnife.bind(this);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        geoFenceCollectionPOJO= (GeoFenceCollectionPOJO) getIntent().getSerializableExtra("geoFenceCollectionPOJO");
        if(geoFenceCollectionPOJO!=null){
            strings.addAll(geoFenceCollectionPOJO.getGeoFencePOJOS());
        }

        attachAdapter();


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

