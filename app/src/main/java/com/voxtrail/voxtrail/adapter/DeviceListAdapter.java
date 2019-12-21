package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.HomeActivity;
import com.voxtrail.voxtrail.activity.NewDeviceActivity;
import com.voxtrail.voxtrail.activity.PlayBackActivity;
import com.voxtrail.voxtrail.fragment.DeviceCurrentLocationFragment;
import com.voxtrail.voxtrail.fragment.device.DeviceEditFragment;
import com.voxtrail.voxtrail.fragment.sharing.SelectObjectForSharingFragment;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.GetWebServices;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {
    private List<DevicePOJO> items;
    Activity activity;
    Fragment fragment;
    AdapterListener adapterListener;

    public void setAdapterItemAdapter(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public DeviceListAdapter(Activity activity, Fragment fragment, List<DevicePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_device_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.frame_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterListener != null) {
                    adapterListener.onAdapterItemClickListener(position);
                } else {
                    Intent intent = new Intent(activity, NewDeviceActivity.class);
                    intent.putExtra("devicePOJO", items.get(position));
                    activity.startActivity(intent);
                }
            }
        });
//        Log.d(TagUtils.getTag(), "icon web url:-" + WebServicesUrls.IMAGE_BASE_URL + items.get(position).getDeviceDetailPOJO().getIcon());

        if ((WebServicesUrls.IMAGE_BASE_URL + items.get(position).getDeviceDetailPOJO().getIcon()).contains(".svg")) {
            String url = (WebServicesUrls.IMAGE_BASE_URL + items.get(position).getDeviceDetailPOJO().getIcon()).replace(".svg", ".png");
//            Log.d(TagUtils.getTag(), "icon web url:-" + url);
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_car)
                    .error(R.drawable.ic_car)
                    .into(holder.iv_icon);

//        Glide.with(activity.getApplicationContext())
//                .load(WebServicesUrls.IMAGE_BASE_URL+items.get(position).getDeviceDetailPOJO().getIcon())
//                .into(holder.iv_icon);
        }

        holder.tv_object_name.setText(items.get(position).getDeviceDetailPOJO().getName());


//        switch (position) {
//            case 0:
//                holder.tv_object_name.setText("Mercedes");
//                break;
//            case 1:
//                holder.tv_object_name.setText("Audi");
//                break;
//            case 2:
//                holder.tv_object_name.setText("BMW");
//                break;
//            case 3:
//                holder.tv_object_name.setText("Volvo");
//                break;
//            case 4:
//                holder.tv_object_name.setText("Ferrari");
//                break;
//            case 5:
//                holder.tv_object_name.setText("Honda");
//                break;
//            case 6:
//                holder.tv_object_name.setText("Hyundai");
//                break;
//            case 7:
//                holder.tv_object_name.setText("Fiat");
//                break;
//            case 8:
//                holder.tv_object_name.setText("Renault");
//                break;
//            case 9:
//                holder.tv_object_name.setText("Suzuki");
//                break;
//            case 10:
//                holder.tv_object_name.setText("Nissan");
//                break;
//        }
//        holder.tv_address.setText(items.get(position).getDeviceDetailPOJO().getName());

//        if (items.get(position).getDeviceDetailPOJO().getImei().equals("860016021969444")) {
//            Log.d(TagUtils.getTag(), "860016021969444 device speed:-" + items.get(position).getDeviceDetailPOJO().getSpeed());
//        }

//        if (items.get(position).getDeviceDetailPOJO().getImei().equals("863586038790782")) {
//            Log.d(TagUtils.getTag(), "863586038790782 cn:-" + items.get(position).getDeviceDetailPOJO().getCn());
//            Log.d(TagUtils.getTag(), "863586038790782 cn:-" + items.get(position).getDeviceDetailPOJO().getSpeed());
//        }

        holder.tv_speed.setText(items.get(position).getDeviceDetailPOJO().getSpeed() + " km/h");

        if (items.get(position).getDeviceDetailPOJO().getCn() != null) {
            switch (items.get(position).getDeviceDetailPOJO().getCn()) {
                case 0:
                    holder.iv_moving_status.setImageResource(R.drawable.vehicle_cngn_status);
                    holder.iv_power.setColorFilter(Color.parseColor("#757575"));
                    holder.iv_wifi.setColorFilter(Color.parseColor("#757575"));
                    holder.tv_status.setText("OFFLINE");
                    holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                    break;
                case 1:
                    holder.iv_moving_status.setImageResource(R.drawable.vehicle_cygn_status);
                    holder.iv_power.setColorFilter(Color.parseColor("#9ce751"));
                    holder.iv_wifi.setColorFilter(Color.parseColor("#757575"));
                    holder.tv_status.setText("OFFLINE");


                    if (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO() != null) {
                        if (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAcc() != null) {
                            switch (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAcc()) {
                                case "1":
                                    holder.iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                    break;
                                default:
                                    holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                    break;
                            }
                        } else if (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAccv() != null) {
                            try {
                                int value=Integer.parseInt(items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAccv());
                                if(value>0){
                                    holder.iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                }else{
                                    holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                            }
                        }else{
                            holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                        }
                    } else {
                        holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                    }


                    break;
                case 2:
                    holder.iv_moving_status.setImageResource(R.drawable.vehicle_cygy_status);
                    holder.iv_power.setColorFilter(Color.parseColor("#9ce751"));
                    holder.iv_wifi.setColorFilter(Color.parseColor("#9ce751"));
                    if (items.get(position).getDeviceDetailPOJO().getSpeed().equals("0")) {
                        holder.tv_status.setText("STOPPED");
                    } else {
                        holder.tv_status.setText("MOVING");
                    }


                    if (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO() != null) {
                        if (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAcc() != null) {
                            switch (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAcc()) {
                                case "1":
                                    holder.iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                    break;
                                default:
                                    holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                    break;
                            }
                        } else if (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAccv() != null) {
                            try {
                                int value=Integer.parseInt(items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAccv());
                                if(value>0){
                                    holder.iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
                                }else{
                                    holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                            }
                        }else{
                            holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                        }
                    } else {
                        holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                    }


                    break;
                default:
                    holder.iv_moving_status.setImageResource(R.drawable.vehicle_cngn_status);
                    holder.iv_power.setColorFilter(Color.parseColor("#757575"));
                    holder.iv_wifi.setColorFilter(Color.parseColor("#757575"));
                    holder.tv_status.setText("OFFLINE");
                    holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
                    break;
            }
        } else {
            holder.iv_moving_status.setImageResource(R.drawable.vehicle_cngn_status);
            holder.iv_power.setColorFilter(Color.parseColor("#757575"));
            holder.iv_wifi.setColorFilter(Color.parseColor("#757575"));
            holder.tv_status.setText("OFFLINE");
            holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
        }

//        try {
//            int speed = Integer.parseInt(items.get(position).getDeviceDetailPOJO().getSpeed());
////            if (items.get(position).getImei().equals("860016020904418")) {
////                Log.d(TagUtils.getTag(), "speed:-" + speed);
////                Log.d(TagUtils.getTag(), "initial time:-" + items.get(position).getDeviceDetailPOJO().getDtTracker());
////                Log.d(TagUtils.getTag(), "final time:-" + UtilityFunction.getServerTimeCurrentTime());
////            }
//            if (speed > 0) {
//                if (Math.abs(UtilityFunction.getDifferenceBtwTwoTimeInSec(UtilityFunction.parseUTCToIST(items.get(position).getDeviceDetailPOJO().getNormalDtrackerTime()), UtilityFunction.getServerTimeCurrentTime())) > 400) {
//                    Log.d(TagUtils.getTag(), "time difference:-" + Math.abs(UtilityFunction.getDifferenceBtwTwoTimeInSec(UtilityFunction.parseUTCToIST(items.get(position).getDeviceDetailPOJO().getNormalDtrackerTime()), UtilityFunction.getServerTimeCurrentTime())));
//                    holder.tv_status.setText("Stopped");
//                    holder.tv_speed.setText("0 km/h");
//                    holder.iv_moving_status.setImageResource(R.drawable.vehicle_stopped_status);
//                } else {
//                    Log.d(TagUtils.getTag(), "time difference");
//                    holder.tv_status.setText("Moving");
//                    holder.tv_speed.setText(items.get(position).getDeviceDetailPOJO().getSpeed() + " km/h");
//                    holder.iv_moving_status.setImageResource(R.drawable.vehicle_moving_status);
//                }
//
//            } else {
//                holder.tv_status.setText("Stopped");
//                holder.tv_speed.setText("0 km/h");
//                holder.iv_moving_status.setImageResource(R.drawable.vehicle_stopped_status);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            holder.tv_status.setText("Stopped");
//            holder.tv_speed.setText("0 km/h");
//            holder.iv_moving_status.setImageResource(R.drawable.vehicle_stopped_status);
//        }
//        if(items.get(position).getDeviceDetailPOJO().getImei().equals("352887078398314")) {
//            Log.d(TagUtils.getTag(), "adapter device speed:-" + items.get(position).getDeviceDetailPOJO().getSpeed());
//        }
//        holder.tv_speed.setText(items.get(position).getDeviceDetailPOJO().getSpeed()+" kph");

//        try {
////            Log.d(TagUtils.getTag(), "params:-" + items.get(position).getDeviceDetailPOJO().getParams());
//            JSONObject jsonObject = new JSONObject(items.get(position).getDeviceDetailPOJO().getParams());
//            if (jsonObject.has("acc")) {
//                if (jsonObject.optString("acc").equals("0")) {
//                    holder.tv_ignition.setText("OFF");
//                    holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
//                } else {
//                    holder.tv_ignition.setText("ON");
//                    holder.iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
//                }
//            } else {
//                holder.tv_ignition.setText("OFF");
//                holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
//            }
//        } catch (Exception e) {
////            e.printStackTrace();
//            holder.tv_ignition.setText("OFF");
//            holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
//        }

//        if (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO() != null) {
//            if (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAcc() != null) {
//                switch (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAcc()) {
//                    case "1":
//                        holder.iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
//                        break;
//                    default:
//                        holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
//                        break;
//                }
//            } else if (items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAccv() != null) {
//                try {
//                    int value=Integer.parseInt(items.get(position).getDeviceDetailPOJO().getDeviceParamPOJO().getAccv());
//                    if(value>0){
//                        holder.iv_ignition.setColorFilter(Color.parseColor("#9ce751"));
//                    }else{
//                        holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
//                }
//            }else{
//                holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
//            }
//        } else {
//            holder.iv_ignition.setColorFilter(Color.parseColor("#757575"));
//        }

//        holder.tv_ignition.setText(items.get(position).getDeviceDetailPOJO().getName());
//        holder.tv_ac_status.setText(items.get(position).getDeviceDetailPOJO().getName());
        holder.tv_date.setText(UtilityFunction.convertToMMMDate(items.get(position).getDeviceDetailPOJO().getDtTracker()));
        if (!UtilityFunction.convertTrackerDTtoTime(items.get(position).getDeviceDetailPOJO().getDtTracker()).equals("")) {
            holder.tv_time.setText(UtilityFunction.convertTrackerDTtoTime(items.get(position).getDeviceDetailPOJO().getDtTracker()));
            holder.ll_time.setVisibility(View.VISIBLE);
        } else {
            holder.ll_time.setVisibility(View.GONE);
        }

        holder.ll_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PlayBackActivity.class);
                intent.putExtra("devicePOJO", items.get(position));
                activity.startActivity(intent);
            }
        });

        holder.ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (activity instanceof HomeActivity) {
//                    HomeActivity homeActivity = (HomeActivity) activity;
//                    homeActivity.startFragment(R.id.frame_home, new DeviceCurrentLocationFragment(items.get(position)));
//                }
                Intent intent = new Intent(activity, NewDeviceActivity.class);
                intent.putExtra("devicePOJO", items.get(position));
                activity.startActivity(intent);
            }
        });

        holder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TagUtils.getTag(), "share data clicked");
                if (activity instanceof HomeActivity) {
                    HomeActivity newHomeActivity = (HomeActivity) activity;
                    newHomeActivity.startFragment(R.id.frame_home, new SelectObjectForSharingFragment(items.get(position)));
                }
            }
        });

        if (items.get(position).getDeviceDetailPOJO().getDevice_address() != null
                && !items.get(position).getDeviceDetailPOJO().getDevice_address().equals("")) {
            holder.tv_address.setText(items.get(position).getDeviceDetailPOJO().getDevice_address());
        } else {
            holder.tv_address.setText("Show Address");
        }

        holder.ll_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;
                    homeActivity.startFragment(R.id.frame_home,new DeviceEditFragment(items.get(position)));
                }
            }
        });

        holder.tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof HomeActivity) {
                    final HomeActivity homeActivity = (HomeActivity) activity;
                    homeActivity.showProgressBar();
                    String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(items.get(position).getDeviceDetailPOJO().getLat()) + "," + String.valueOf(items.get(position).getDeviceDetailPOJO().getLng()) + "&sensor=true&key=AIzaSyC-L6KkHvJLkqTASANbRZC3gvAQExPFx24";
                    Log.d(TagUtils.getTag(), "url:-" + url);
//                    StringRequest req = new StringRequest(url,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            homeActivity.dismissProgressBar();
//                            Log.d(TagUtils.getTag(), "api error:-" + error.toString());
//                        }
//                    });
//                    RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
//                    queue.add(req);


                    new GetWebServices(activity.getApplicationContext(), null, new WebServicesCallBack() {
                        @Override
                        public void onGetMsg(String apicall, String response) {
                            homeActivity.dismissProgressBar();
                            try {
                                Log.d(TagUtils.getTag(), "address response:-" + response);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.optJSONArray("results");
                                JSONObject jsonObject1 = jsonArray.optJSONObject(0);
                                String formatted_address = jsonObject1.optString("formatted_address");
                                holder.tv_address.setText(formatted_address);
                                items.get(position).getDeviceDetailPOJO().setDevice_address(formatted_address);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, "GET_ADDRESS", false).execute(url);


                }
            }
        });
        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.frame_device)
        FrameLayout frame_device;
        @BindView(R.id.tv_object_name)
        TextView tv_object_name;
        @BindView(R.id.tv_address)
        TextView tv_address;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.tv_speed)
        TextView tv_speed;
        @BindView(R.id.tv_ignition)
        TextView tv_ignition;
        @BindView(R.id.tv_ac_status)
        TextView tv_ac_status;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.iv_moving_status)
        ImageView iv_moving_status;
        @BindView(R.id.ll_time)
        LinearLayout ll_time;
        @BindView(R.id.ll_location)
        LinearLayout ll_location;
        @BindView(R.id.ll_play)
        LinearLayout ll_play;
        @BindView(R.id.ll_share)
        LinearLayout ll_share;
        @BindView(R.id.iv_power)
        ImageView iv_power;
        @BindView(R.id.iv_wifi)
        ImageView iv_wifi;
        @BindView(R.id.iv_ignition)
        ImageView iv_ignition;
        @BindView(R.id.ll_edit)
        LinearLayout ll_edit;
//        @BindView(R.id.swipeRevealLayout)
//        SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
