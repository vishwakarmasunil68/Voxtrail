package com.voxtrail.voxtrail.fragment.events;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.EventListAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.device.DeviceCollectionPOJO;
import com.voxtrail.voxtrail.pojo.event.UserEventPOJO;
import com.voxtrail.voxtrail.pojo.geofence.GeoFenceCollectionPOJO;
import com.voxtrail.voxtrail.pojo.geofence.GeoFencePOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.ResponseListCallback;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponseList;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EventListFragment extends FragmentController {

    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.rv_events)
    RecyclerView rv_events;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_event_list, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDialog(null);
            }
        });
        listAllUserEvents();

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        callGeoFenceAPI();
    }

    public void callGeoFenceAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        new WebServiceBaseResponseList<GeoFencePOJO>(nameValuePairs, getActivity(), new ResponseListCallback<GeoFencePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<GeoFencePOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        allgeofences.clear();
                        allgeofences.addAll(responseListPOJO.getResultList());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, GeoFencePOJO.class, "GET_USER_ZONES", false).execute(WebServicesUrls.GET_USER_ZONES);
    }

    List<GeoFencePOJO> allgeofences = new ArrayList<>();

    public void listAllUserEvents() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));

        new WebServiceBaseResponseList<UserEventPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<UserEventPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<UserEventPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        userEventPOJOS.clear();
                        userEventPOJOS.addAll(responseListPOJO.getResultList());
                        eventListAdapter.notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, UserEventPOJO.class, "GET_ALL_EVENTS", true).execute(WebServicesUrls.GET_ALL_EVENTS);
    }

    EventListAdapter eventListAdapter;
    List<UserEventPOJO> userEventPOJOS = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_events.setHasFixedSize(true);
        rv_events.setLayoutManager(linearLayoutManager);
        eventListAdapter = new EventListAdapter(getActivity(), this, userEventPOJOS);
        rv_events.setAdapter(eventListAdapter);
        rv_events.setNestedScrollingEnabled(false);
        rv_events.setItemAnimator(new DefaultItemAnimator());
        eventListAdapter.setAdapterItemAdapter(new AdapterListener() {
            @Override
            public void onAdapterItemClickListener(int position) {
//                getObject(deviceStrings.get(position).getUserId(), deviceStrings.get(position).getImei());
                eventDialog(userEventPOJOS.get(position));
            }
        });
    }

    public void eventDialog(final UserEventPOJO userEventPOJO) {

        selectedDevicePOJOS.clear();

        for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
            devicePOJO.setIs_selected(false);
        }

        geoFencePOJOS.clear();

        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog1.setContentView(R.layout.dialog_event_setting);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        final EditText et_event_name = dialog1.findViewById(R.id.et_event_name);
        final Spinner spinner_type = dialog1.findViewById(R.id.spinner_type);
        final TextView tv_selected_devices = dialog1.findViewById(R.id.tv_selected_devices);
        final TextView tv_selected_zones = dialog1.findViewById(R.id.tv_selected_zones);
        final EditText et_speed_limit = dialog1.findViewById(R.id.et_speed_limit);
        final LinearLayout ll_speed_limit = dialog1.findViewById(R.id.ll_speed_limit);
        Button btn_submit = dialog1.findViewById(R.id.btn_submit);
        final LinearLayout ll_zones = dialog1.findViewById(R.id.ll_zones);

        selectDeviceeditText = tv_selected_devices;

        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ll_speed_limit.setVisibility(View.VISIBLE);
                } else {
                    ll_speed_limit.setVisibility(View.GONE);
                }
                if (position == 1 || position == 2) {
                    ll_zones.setVisibility(View.VISIBLE);
                } else {
                    ll_zones.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (userEventPOJO != null) {

            btn_submit.setText("Update");
            Log.d(TagUtils.getTag(),"type:-"+userEventPOJO.getType());
            switch (userEventPOJO.getType()) {
                case "overspeed":
                    spinner_type.setSelection(0);
                    et_speed_limit.setText(userEventPOJO.getCheckedValue());
                    break;
                case "zone_in":
                    spinner_type.setSelection(1);
                    et_speed_limit.setText("");
                    break;
                case "zone_out":
                    spinner_type.setSelection(2);
                    et_speed_limit.setText("");
                    break;
                case "gpsyes":
                    spinner_type.setSelection(6);
                    et_speed_limit.setText("");
                    break;
                case "gpsno":
                    spinner_type.setSelection(7);
                    et_speed_limit.setText("");
                    break;
                case "haccel":
                    spinner_type.setSelection(3);
                    et_speed_limit.setText("");
                    break;
                case "hbrake":
                    spinner_type.setSelection(4);
                    et_speed_limit.setText("");
                    break;
                case "hcorn":
                    spinner_type.setSelection(5);
                    et_speed_limit.setText("");
                    break;
            }

            String selected_imeis = userEventPOJO.getImei();
            String[] imeis = selected_imeis.split(",");
            List<DevicePOJO> devicePOJOS = new ArrayList<>();
            for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
                for (String imei : imeis) {
                    if (devicePOJO.getImei().equals(imei)) {
                        devicePOJOS.add(devicePOJO);
                        devicePOJO.setIs_selected(true);
                    }
                }
            }

//            String deviceString = "";
//            for (int i = 0; i < devicePOJOS.size(); i++) {
//                if ((i + 1) == devicePOJOS.size()) {
//                    deviceString += devicePOJOS.get(i).getDeviceDetailPOJO().getName();
//                } else {
//                    deviceString += devicePOJOS.get(i).getDeviceDetailPOJO().getName() + ",";
//                }
//            }

            tv_selected_devices.setText(devicePOJOS.size()+" Selected");
            selectedDevicePOJOS.clear();
            selectedDevicePOJOS.addAll(devicePOJOS);

            et_event_name.setText(userEventPOJO.getName());

            if (userEventPOJO.getType().equals("zone_in") || userEventPOJO.getType().equals("zone_out")) {
                ll_zones.setVisibility(View.VISIBLE);
                String selectedZones = userEventPOJO.getZones();
                String[] zones = selectedZones.split(",");

//                List<GeoFencePOJO> geoFencePOJOS = new ArrayList<>();
                for (int i = 0; i < allgeofences.size(); i++) {
                    for (String zone : zones) {
                        if (zone.equals(allgeofences.get(i).getZoneId())) {
                            geoFencePOJOS.add(allgeofences.get(i));
                        }
                    }
                }

//                String selectedZonenamString = "";
//                for (int i = 0; i < geoFencePOJOS.size(); i++) {
//                    if ((i + 1) == geoFencePOJOS.size()) {
//                        selectedZonenamString += geoFencePOJOS.get(i).getZoneName();
//                    } else {
//                        selectedZonenamString += geoFencePOJOS.get(i).getZoneName() + ",";
//                    }
//                }

                tv_selected_zones.setText(geoFencePOJOS.size()+" selected");
            } else {
                ll_zones.setVisibility(View.GONE);
            }
        } else {
            btn_submit.setText("SUBMIT");

            ll_zones.setVisibility(View.GONE);
        }

        tv_selected_devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDeviceeditText = tv_selected_devices;
                Log.d(TagUtils.getTag(), "selected devices clicked");
                startActivityForResult(new Intent(getActivity(), SelectDeviceListActivity.class), 101);
            }
        });

        ll_zones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedZoneTextView = tv_selected_zones;
                Intent intent = new Intent(getActivity(), ZoneSelectActivity.class);
                String selectedZonenamString = "";
                for (int i = 0; i < geoFencePOJOS.size(); i++) {
                    if ((i + 1) == geoFencePOJOS.size()) {
                        selectedZonenamString += geoFencePOJOS.get(i).getZoneId();
                    } else {
                        selectedZonenamString += geoFencePOJOS.get(i).getZoneId() + ",";
                    }
                }
                intent.putExtra("zones", selectedZonenamString);
                startActivityForResult(intent, 102);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectedDevicePOJOS.size() > 0) {

                    String imeis = "";
                    for (int i = 0; i < selectedDevicePOJOS.size(); i++) {
                        if ((i + 1) == selectedDevicePOJOS.size()) {
                            imeis += selectedDevicePOJOS.get(i).getImei();
                        } else {
                            imeis += selectedDevicePOJOS.get(i).getImei() + ",";
                        }
                    }

                    EventInsertPOJO eventInsertPOJO = null;

                    if (et_event_name.getText().toString().length() > 0) {

                        if (selectedDevicePOJOS.size() > 0) {

                            String selectedZonenamString = "";
                            if (spinner_type.getSelectedItemPosition() == 1 || spinner_type.getSelectedItemPosition() == 2) {
                                for (int i = 0; i < geoFencePOJOS.size(); i++) {
                                    if ((i + 1) == geoFencePOJOS.size()) {
                                        selectedZonenamString += geoFencePOJOS.get(i).getZoneId();
                                    } else {
                                        selectedZonenamString += geoFencePOJOS.get(i).getZoneId() + ",";
                                    }
                                }
                            }

                            switch (spinner_type.getSelectedItemPosition()) {
                                case 0:
                                    eventInsertPOJO = getOverspeedPOJO(et_event_name.getText().toString(), imeis);
                                    break;
                                case 1:
                                    eventInsertPOJO = getZoneINPOJO(et_event_name.getText().toString(), imeis, selectedZonenamString);
                                    break;
                                case 2:
                                    eventInsertPOJO = getZoneOutPOJO(et_event_name.getText().toString(), imeis, selectedZonenamString);
                                    break;
                                case 3:
                                    eventInsertPOJO = getHarshAcclerationPOJO(et_event_name.getText().toString(), imeis);
                                    break;
                                case 4:
                                    eventInsertPOJO = getHarshBrakingPOJO(et_event_name.getText().toString(), imeis);
                                    break;
                                case 5:
                                    eventInsertPOJO = getHarshCorn(et_event_name.getText().toString(), imeis);
                                    break;
                                case 6:
                                    eventInsertPOJO = getGpsYesPOJO(et_event_name.getText().toString(), imeis);
                                    break;
                                case 7:
                                    eventInsertPOJO = getGpsNoPOJO(et_event_name.getText().toString(), imeis);
                                    break;
                                case 8:
                                    eventInsertPOJO = getAccOn(et_event_name.getText().toString(), imeis);
                                    break;
                                case 9:
                                    eventInsertPOJO = getAccOFF(et_event_name.getText().toString(), imeis);
                                    break;
                            }
                        }

                        if (eventInsertPOJO != null) {
                            if (userEventPOJO != null) {
                                //update
                                updateEvent(eventInsertPOJO, dialog1, userEventPOJO.getEventId());
                            } else {
                                insertEvent(eventInsertPOJO, dialog1);
                            }

                        } else {
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong! please try again later.");
                        }
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Please enter event name");
                    }
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select Devices");
                }
            }

        });
    }

    public EventInsertPOJO getOverspeedPOJO(String event_name, String imeis) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "overspeed",
                event_name,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                "60",
                "off",
                "off",
                "",
                "",
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    public EventInsertPOJO getZoneINPOJO(String eventName, String imeis, String zones) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "zone_in",
                eventName,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                "",
                "off",
                "off",
                "",
                zones,
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    public EventInsertPOJO getZoneOutPOJO(String eventName, String imeis, String zones) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "zone_out",
                eventName,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                "",
                "off",
                "off",
                "",
                zones,
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    public EventInsertPOJO getGpsYesPOJO(String eventName, String imeis) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "gpsyes",
                eventName,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                "",
                "off",
                "off",
                "",
                "",
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    public EventInsertPOJO getGpsNoPOJO(String eventName, String imeis) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "gpsno",
                eventName,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                "",
                "off",
                "off",
                "",
                "",
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    public EventInsertPOJO getHarshAcclerationPOJO(String eventName, String imeis) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "haccel",
                eventName,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                "",
                "off",
                "off",
                "",
                "",
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    public EventInsertPOJO getHarshBrakingPOJO(String eventName, String imeis) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "hbrake",
                eventName,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                "",
                "off",
                "off",
                "",
                "",
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    public EventInsertPOJO getHarshCorn(String eventName, String imeis) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "hcorn",
                eventName,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                "",
                "off",
                "off",
                "",
                "",
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    public EventInsertPOJO getAccOn(String eventName, String imeis) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "param",
                eventName,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                getAccOnJsonValue(),
                "off",
                "off",
                "",
                "",
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    public String getAccOnJsonValue(){
        try{
            JSONArray jsonArray=new JSONArray();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("src","acc");
            jsonObject.put("cn","eq");
            jsonObject.put("val","1");
            jsonArray.put(jsonObject);
            return jsonArray.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String getAccOFFJsonValue(){
        try{
            JSONArray jsonArray=new JSONArray();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("src","acc");
            jsonObject.put("cn","eq");
            jsonObject.put("val","0");
            jsonArray.put(jsonObject);
            return jsonArray.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public EventInsertPOJO getAccOFF(String eventName, String imeis) {
        EventInsertPOJO eventInsertPOJO = getEventInsertPOJO(Constants.userDetail.getId(),
                "param",
                eventName,
                "true",
                "false",
                "0",
                "true,true,true,true,true,true,true",
                getdayTime(),
                imeis,
                getAccOFFJsonValue(),
                "off",
                "off",
                "",
                "",
                "true,true,true,alarm1.mp3",
                "false",
                "",
                "false",
                "",
                "0",
                "0",
                "false",
                "arrow_yellow",
                "false",
                "#FFFF00",
                "false",
                "gprs",
                "ascii",
                "");

        return eventInsertPOJO;
    }

    List<DevicePOJO> selectedDevicePOJOS = new ArrayList<>();
    List<GeoFencePOJO> geoFencePOJOS = new ArrayList<>();

    public String getdayTime() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dt", false);
            jsonObject.put("mon", false);
            jsonObject.put("mon_from", "00:00");
            jsonObject.put("mon_to", "24:00");
            jsonObject.put("tue", false);
            jsonObject.put("tue_from", "00:00");
            jsonObject.put("tue_to", "24:00");
            jsonObject.put("wed", false);
            jsonObject.put("wed_from", "00:00");
            jsonObject.put("wed_to", "24:00");
            jsonObject.put("thu", false);
            jsonObject.put("thu_from", "00:00");
            jsonObject.put("thu_to", "24:00");
            jsonObject.put("fri", false);
            jsonObject.put("fri_from", "00:00");
            jsonObject.put("fri_to", "24:00");
            jsonObject.put("sat", false);
            jsonObject.put("sat_from", "00:00");
            jsonObject.put("sat_to", "24:00");
            jsonObject.put("sun", false);
            jsonObject.put("sun_from", "00:00");
            jsonObject.put("sun_to", "24:00");

            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public void insertEvent(EventInsertPOJO eventInsertPOJO, final Dialog dialog) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", eventInsertPOJO.getUser_id()));
        nameValuePairs.add(new BasicNameValuePair("type", eventInsertPOJO.getType()));
        nameValuePairs.add(new BasicNameValuePair("name", eventInsertPOJO.getName()));
        nameValuePairs.add(new BasicNameValuePair("active", eventInsertPOJO.getActive()));
        nameValuePairs.add(new BasicNameValuePair("duration_from_last_event", eventInsertPOJO.getDuration_from_last_event()));
        nameValuePairs.add(new BasicNameValuePair("duration_from_last_event_minutes", eventInsertPOJO.getDuration_from_last_event_minutes()));
        nameValuePairs.add(new BasicNameValuePair("week_days", eventInsertPOJO.getWeek_days()));
        nameValuePairs.add(new BasicNameValuePair("day_time", eventInsertPOJO.getDay_time()));
        nameValuePairs.add(new BasicNameValuePair("imei", eventInsertPOJO.getImei()));
        nameValuePairs.add(new BasicNameValuePair("checked_value", eventInsertPOJO.getChecked_value()));
        nameValuePairs.add(new BasicNameValuePair("route_trigger", eventInsertPOJO.getRoute_trigger()));
        nameValuePairs.add(new BasicNameValuePair("zone_trigger", eventInsertPOJO.getZone_trigger()));
        nameValuePairs.add(new BasicNameValuePair("routes", eventInsertPOJO.getRoutes()));
        nameValuePairs.add(new BasicNameValuePair("zones", eventInsertPOJO.getZones()));
        nameValuePairs.add(new BasicNameValuePair("notify_system", eventInsertPOJO.getNotify_system()));
        nameValuePairs.add(new BasicNameValuePair("notify_email", eventInsertPOJO.getNotify_email()));
        nameValuePairs.add(new BasicNameValuePair("notify_email_address", eventInsertPOJO.getNotify_email_address()));
        nameValuePairs.add(new BasicNameValuePair("notify_sms", eventInsertPOJO.getNotify_sms()));
        nameValuePairs.add(new BasicNameValuePair("notify_sms_number", eventInsertPOJO.getNotify_sms_number()));
        nameValuePairs.add(new BasicNameValuePair("email_template_id", eventInsertPOJO.getEmail_template_id()));
        nameValuePairs.add(new BasicNameValuePair("sms_template_id", eventInsertPOJO.getSms_template_id()));
        nameValuePairs.add(new BasicNameValuePair("notify_arrow", eventInsertPOJO.getNotify_arrow()));
        nameValuePairs.add(new BasicNameValuePair("notify_arrow_color", eventInsertPOJO.getNotify_arrow_color()));
        nameValuePairs.add(new BasicNameValuePair("notify_ohc", eventInsertPOJO.getNotify_ohc()));
        nameValuePairs.add(new BasicNameValuePair("notify_ohc_color", eventInsertPOJO.getNotify_ohc_color()));
        nameValuePairs.add(new BasicNameValuePair("cmd_send", eventInsertPOJO.getCmd_send()));
        nameValuePairs.add(new BasicNameValuePair("cmd_gateway", eventInsertPOJO.getCmd_gateway()));
        nameValuePairs.add(new BasicNameValuePair("cmd_type", eventInsertPOJO.getCmd_type()));
        nameValuePairs.add(new BasicNameValuePair("cmd_string", eventInsertPOJO.getCmd_string()));

        new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("1")) {
                        listAllUserEvents();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "INSERT_EVENT_LIST", true).execute(WebServicesUrls.INSERT_EVENT);

    }

    public void updateEvent(EventInsertPOJO eventInsertPOJO, final Dialog dialog, String event_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", eventInsertPOJO.getUser_id()));
        nameValuePairs.add(new BasicNameValuePair("type", eventInsertPOJO.getType()));
        nameValuePairs.add(new BasicNameValuePair("name", eventInsertPOJO.getName()));
        nameValuePairs.add(new BasicNameValuePair("active", eventInsertPOJO.getActive()));
        nameValuePairs.add(new BasicNameValuePair("duration_from_last_event", eventInsertPOJO.getDuration_from_last_event()));
        nameValuePairs.add(new BasicNameValuePair("duration_from_last_event_minutes", eventInsertPOJO.getDuration_from_last_event_minutes()));
        nameValuePairs.add(new BasicNameValuePair("week_days", eventInsertPOJO.getWeek_days()));
        nameValuePairs.add(new BasicNameValuePair("day_time", eventInsertPOJO.getDay_time()));
        nameValuePairs.add(new BasicNameValuePair("imei", eventInsertPOJO.getImei()));
        nameValuePairs.add(new BasicNameValuePair("checked_value", eventInsertPOJO.getChecked_value()));
        nameValuePairs.add(new BasicNameValuePair("route_trigger", eventInsertPOJO.getRoute_trigger()));
        nameValuePairs.add(new BasicNameValuePair("zone_trigger", eventInsertPOJO.getZone_trigger()));
        nameValuePairs.add(new BasicNameValuePair("routes", eventInsertPOJO.getRoutes()));
        nameValuePairs.add(new BasicNameValuePair("zones", eventInsertPOJO.getZones()));
        nameValuePairs.add(new BasicNameValuePair("notify_system", eventInsertPOJO.getNotify_system()));
        nameValuePairs.add(new BasicNameValuePair("notify_email", eventInsertPOJO.getNotify_email()));
        nameValuePairs.add(new BasicNameValuePair("notify_email_address", eventInsertPOJO.getNotify_email_address()));
        nameValuePairs.add(new BasicNameValuePair("notify_sms", eventInsertPOJO.getNotify_sms()));
        nameValuePairs.add(new BasicNameValuePair("notify_sms_number", eventInsertPOJO.getNotify_sms_number()));
        nameValuePairs.add(new BasicNameValuePair("email_template_id", eventInsertPOJO.getEmail_template_id()));
        nameValuePairs.add(new BasicNameValuePair("sms_template_id", eventInsertPOJO.getSms_template_id()));
        nameValuePairs.add(new BasicNameValuePair("notify_arrow", eventInsertPOJO.getNotify_arrow()));
        nameValuePairs.add(new BasicNameValuePair("notify_arrow_color", eventInsertPOJO.getNotify_arrow_color()));
        nameValuePairs.add(new BasicNameValuePair("notify_ohc", eventInsertPOJO.getNotify_ohc()));
        nameValuePairs.add(new BasicNameValuePair("notify_ohc_color", eventInsertPOJO.getNotify_ohc_color()));
        nameValuePairs.add(new BasicNameValuePair("cmd_send", eventInsertPOJO.getCmd_send()));
        nameValuePairs.add(new BasicNameValuePair("cmd_gateway", eventInsertPOJO.getCmd_gateway()));
        nameValuePairs.add(new BasicNameValuePair("cmd_type", eventInsertPOJO.getCmd_type()));
        nameValuePairs.add(new BasicNameValuePair("cmd_string", eventInsertPOJO.getCmd_string()));
        nameValuePairs.add(new BasicNameValuePair("event_id", event_id));

        new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("1")) {
                        listAllUserEvents();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "INSERT_EVENT_LIST", true).execute(WebServicesUrls.UPDATE_EVENT);

    }

    TextView selectDeviceeditText, selectedZoneTextView;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                DeviceCollectionPOJO deviceCollectionPOJO = (DeviceCollectionPOJO) data.getSerializableExtra("deviceCollectionPOJO");
                if (selectDeviceeditText != null) {
                    String deviceLists = "";
//                    for (int i = 0; i < deviceCollectionPOJO.getDevicePOJOS().size(); i++) {
//                        DevicePOJO devicePOJO = deviceCollectionPOJO.getDevicePOJOS().get(i);
//                        if ((i + 1) == deviceCollectionPOJO.getDevicePOJOS().size()) {
//                            deviceLists += devicePOJO.getDeviceDetailPOJO().getName();
//                        } else {
//                            deviceLists += devicePOJO.getDeviceDetailPOJO().getName() + ",";
//                        }
//                    }
                    selectDeviceeditText.setText(deviceCollectionPOJO.getDevicePOJOS().size()+" Selected");
                    selectedDevicePOJOS.clear();
                    selectedDevicePOJOS.addAll(deviceCollectionPOJO.getDevicePOJOS());
                } else {
                    selectedDevicePOJOS.clear();
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                GeoFenceCollectionPOJO geoFenceCollectionPOJO = (GeoFenceCollectionPOJO) data.getSerializableExtra("geoFenceCollectionPOJO");
                if (selectedZoneTextView != null) {
                    String zoneLists = "";
//                    for (int i = 0; i < geoFenceCollectionPOJO.getGeoFencePOJOS().size(); i++) {
//                        GeoFencePOJO geoFencePOJO = geoFenceCollectionPOJO.getGeoFencePOJOS().get(i);
//                        if ((i + 1) == geoFenceCollectionPOJO.getGeoFencePOJOS().size()) {
//                            zoneLists += geoFencePOJO.getZoneName();
//                        } else {
//                            zoneLists += geoFencePOJO.getZoneName() + ",";
//                        }
//                    }
                    selectedZoneTextView.setText(geoFenceCollectionPOJO.getGeoFencePOJOS().size()+" selected");
                    geoFencePOJOS.clear();
                    geoFencePOJOS.addAll(geoFenceCollectionPOJO.getGeoFencePOJOS());
                } else {
                    geoFencePOJOS.clear();
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public EventInsertPOJO getEventInsertPOJO(String user_id, String type, String name, String active, String duration_from_last_event, String duration_from_last_event_minutes, String week_days, String day_time, String imei, String checked_value, String route_trigger, String zone_trigger, String routes, String zones, String notify_system, String notify_email, String notify_email_address, String notify_sms, String notify_sms_number, String email_template_id, String sms_template_id, String notify_arrow, String notify_arrow_color, String notify_ohc, String notify_ohc_color, String cmd_send, String cmd_gateway, String cmd_type, String cmd_string) {
        EventInsertPOJO eventInsertPOJO = new EventInsertPOJO();

        eventInsertPOJO.setUser_id(user_id);
        eventInsertPOJO.setType(type);
        eventInsertPOJO.setName(name);
        eventInsertPOJO.setActive(active);
        eventInsertPOJO.setDuration_from_last_event(duration_from_last_event);
        eventInsertPOJO.setDuration_from_last_event_minutes(duration_from_last_event_minutes);
        eventInsertPOJO.setWeek_days(week_days);
        eventInsertPOJO.setDay_time(day_time);
        eventInsertPOJO.setImei(imei);
        eventInsertPOJO.setChecked_value(checked_value);
        eventInsertPOJO.setRoute_trigger(route_trigger);
        eventInsertPOJO.setZone_trigger(zone_trigger);
        eventInsertPOJO.setZones(zones);
        eventInsertPOJO.setNotify_system(notify_system);
        eventInsertPOJO.setNotify_email(notify_email);
        eventInsertPOJO.setNotify_email_address(notify_email_address);
        eventInsertPOJO.setNotify_sms(notify_sms);
        eventInsertPOJO.setNotify_sms_number(notify_sms_number);
        eventInsertPOJO.setEmail_template_id(email_template_id);
        eventInsertPOJO.setSms_template_id(sms_template_id);
        eventInsertPOJO.setNotify_arrow(notify_arrow);
        eventInsertPOJO.setNotify_arrow_color(notify_arrow_color);
        eventInsertPOJO.setNotify_ohc(notify_ohc);
        eventInsertPOJO.setNotify_ohc_color(notify_ohc_color);
        eventInsertPOJO.setCmd_send(cmd_send);
        eventInsertPOJO.setCmd_gateway(cmd_gateway);
        eventInsertPOJO.setCmd_type(cmd_type);
        eventInsertPOJO.setCmd_string(cmd_string);

        return eventInsertPOJO;
    }


    class EventInsertPOJO {
        String user_id;
        String type;
        String name;
        String active;
        String duration_from_last_event;
        String duration_from_last_event_minutes;
        String week_days;
        String day_time;
        String imei;
        String checked_value;
        String route_trigger;
        String zone_trigger;
        String routes;
        String zones;
        String notify_system;
        String notify_email;
        String notify_email_address;
        String notify_sms;
        String notify_sms_number;
        String email_template_id;
        String sms_template_id;
        String notify_arrow;
        String notify_arrow_color;
        String notify_ohc;
        String notify_ohc_color;
        String cmd_send;
        String cmd_gateway;
        String cmd_type;
        String cmd_string;


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getDuration_from_last_event() {
            return duration_from_last_event;
        }

        public void setDuration_from_last_event(String duration_from_last_event) {
            this.duration_from_last_event = duration_from_last_event;
        }

        public String getDuration_from_last_event_minutes() {
            return duration_from_last_event_minutes;
        }

        public void setDuration_from_last_event_minutes(String duration_from_last_event_minutes) {
            this.duration_from_last_event_minutes = duration_from_last_event_minutes;
        }

        public String getWeek_days() {
            return week_days;
        }

        public void setWeek_days(String week_days) {
            this.week_days = week_days;
        }

        public String getDay_time() {
            return day_time;
        }

        public void setDay_time(String day_time) {
            this.day_time = day_time;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getChecked_value() {
            return checked_value;
        }

        public void setChecked_value(String checked_value) {
            this.checked_value = checked_value;
        }

        public String getRoute_trigger() {
            return route_trigger;
        }

        public void setRoute_trigger(String route_trigger) {
            this.route_trigger = route_trigger;
        }

        public String getZone_trigger() {
            return zone_trigger;
        }

        public void setZone_trigger(String zone_trigger) {
            this.zone_trigger = zone_trigger;
        }

        public String getRoutes() {
            return routes;
        }

        public void setRoutes(String routes) {
            this.routes = routes;
        }

        public String getZones() {
            return zones;
        }

        public void setZones(String zones) {
            this.zones = zones;
        }

        public String getNotify_system() {
            return notify_system;
        }

        public void setNotify_system(String notify_system) {
            this.notify_system = notify_system;
        }

        public String getNotify_email() {
            return notify_email;
        }

        public void setNotify_email(String notify_email) {
            this.notify_email = notify_email;
        }

        public String getNotify_email_address() {
            return notify_email_address;
        }

        public void setNotify_email_address(String notify_email_address) {
            this.notify_email_address = notify_email_address;
        }

        public String getNotify_sms() {
            return notify_sms;
        }

        public void setNotify_sms(String notify_sms) {
            this.notify_sms = notify_sms;
        }

        public String getNotify_sms_number() {
            return notify_sms_number;
        }

        public void setNotify_sms_number(String notify_sms_number) {
            this.notify_sms_number = notify_sms_number;
        }

        public String getEmail_template_id() {
            return email_template_id;
        }

        public void setEmail_template_id(String email_template_id) {
            this.email_template_id = email_template_id;
        }

        public String getSms_template_id() {
            return sms_template_id;
        }

        public void setSms_template_id(String sms_template_id) {
            this.sms_template_id = sms_template_id;
        }

        public String getNotify_arrow() {
            return notify_arrow;
        }

        public void setNotify_arrow(String notify_arrow) {
            this.notify_arrow = notify_arrow;
        }

        public String getNotify_arrow_color() {
            return notify_arrow_color;
        }

        public void setNotify_arrow_color(String notify_arrow_color) {
            this.notify_arrow_color = notify_arrow_color;
        }

        public String getNotify_ohc() {
            return notify_ohc;
        }

        public void setNotify_ohc(String notify_ohc) {
            this.notify_ohc = notify_ohc;
        }

        public String getNotify_ohc_color() {
            return notify_ohc_color;
        }

        public void setNotify_ohc_color(String notify_ohc_color) {
            this.notify_ohc_color = notify_ohc_color;
        }

        public String getCmd_send() {
            return cmd_send;
        }

        public void setCmd_send(String cmd_send) {
            this.cmd_send = cmd_send;
        }

        public String getCmd_gateway() {
            return cmd_gateway;
        }

        public void setCmd_gateway(String cmd_gateway) {
            this.cmd_gateway = cmd_gateway;
        }

        public String getCmd_type() {
            return cmd_type;
        }

        public void setCmd_type(String cmd_type) {
            this.cmd_type = cmd_type;
        }

        public String getCmd_string() {
            return cmd_string;
        }

        public void setCmd_string(String cmd_string) {
            this.cmd_string = cmd_string;
        }
    }
}
