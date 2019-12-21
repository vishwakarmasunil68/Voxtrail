package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.AddGeoFenceActivity;
import com.voxtrail.voxtrail.pojo.geofence.GeoFencePOJO;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class GeoFenceAdapter extends RecyclerView.Adapter<GeoFenceAdapter.ViewHolder> {
    private List<GeoFencePOJO> items;
    Activity activity;
    Fragment fragment;

    public GeoFenceAdapter(Activity activity, Fragment fragment, List<GeoFencePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_geo_fence, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.ll_geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, AddGeoFenceActivity.class);
                intent.putExtra("geoFencePOJO",items.get(position));
                intent.putExtra("is_update",true);
                activity.startActivity(intent);
            }
        });

        holder.ll_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, AddGeoFenceActivity.class);
                intent.putExtra("geoFencePOJO",items.get(position));
                intent.putExtra("is_update",true);
                activity.startActivity(intent);
            }
        });

        holder.frame_geo_fence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, AddGeoFenceActivity.class);
                intent.putExtra("geoFencePOJO",items.get(position));
                intent.putExtra("is_update",true);
                activity.startActivity(intent);
            }
        });

        holder.ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("zone_id",items.get(position).getZoneId()));
                new WebServiceBase(nameValuePairs, null, activity, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.optString("status").equalsIgnoreCase("1")){
                                items.remove(position);
                                notifyDataSetChanged();
                            }else{
                                ToastClass.showShortToast(activity.getApplicationContext(),"Zone not found");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },"DELETE ZONE",true).execute(WebServicesUrls.deleteZone);
            }
        });

        try {
            holder.view_color.setBackgroundColor(Color.parseColor(items.get(position).getZoneColor()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tv_area.setText("Area : " + items.get(position).getZoneArea());
        holder.tv_geo_name.setText(items.get(position).getZoneName());

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_color)
        View view_color;
        @BindView(R.id.tv_area)
        TextView tv_area;
        @BindView(R.id.tv_geo_name)
        TextView tv_geo_name;
        @BindView(R.id.ll_geo)
        LinearLayout ll_geo;
        @BindView(R.id.ll_edit)
        LinearLayout ll_edit;
        @BindView(R.id.ll_delete)
        LinearLayout ll_delete;
        @BindView(R.id.frame_geo_fence)
        FrameLayout frame_geo_fence;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
