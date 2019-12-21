package com.voxtrail.voxtrail.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.view.anim.MyBounceInterpolator;
import com.voxtrail.voxtrail.webservice.GetWebServices;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class PushStartFragment extends FragmentController {

    @BindView(R.id.iv_car_toggle)
    ImageView iv_car_toggle;
    @BindView(R.id.spinner_devices)
    Spinner spinner_devices;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.iv_car_off)
    ImageView iv_car_off;
    @BindView(R.id.iv_car_on)
    ImageView iv_car_on;
    @BindView(R.id.tv_speed)
    TextView tv_speed;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_vehicle_name)
    TextView tv_vehicle_name;
    @BindView(R.id.tv_cut_off_time)
    TextView tv_cut_off_time;

    boolean iginition = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_push_start, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_car_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iginition) {
                    iv_car_toggle.setImageResource(R.drawable.ic_car_off);
                    iginition = false;
                } else {
                    iginition = true;
                    iv_car_toggle.setImageResource(R.drawable.ic_car_on);
                }
            }
        });

        if (Constants.devicePOJOS.size() > 0) {
            List<String> deStrings = new ArrayList<>();
            for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
                deStrings.add(devicePOJO.getDeviceDetailPOJO().getName());
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, deStrings); //selected item will look like a spinner set from XML
//            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spinner_devices.setAdapter(spinnerArrayAdapter);

            Log.d(TagUtils.getTag(), "device list:-" + deStrings.toString());

            getCompleteAddress(Constants.devicePOJOS.get(0).getDeviceDetailPOJO().getLat(),
                    Constants.devicePOJOS.get(0).getDeviceDetailPOJO().getLng(), tv_address);

            tv_speed.setText(Constants.devicePOJOS.get(0).getDeviceDetailPOJO().getSpeed() + " kph");
            tv_date.setText(Constants.devicePOJOS.get(0).getDeviceDetailPOJO().getDtTracker());
            tv_vehicle_name.setText(Constants.devicePOJOS.get(0).getDeviceDetailPOJO().getName());
        }

        if (Constants.devicePOJOS.size() > 1) {
            spinner_devices.setVisibility(View.VISIBLE);
        } else {
            spinner_devices.setVisibility(View.GONE);
        }

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner_devices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position != -1) {

                        getCompleteAddress(Constants.devicePOJOS.get(position).getDeviceDetailPOJO().getLat(),
                                Constants.devicePOJOS.get(position).getDeviceDetailPOJO().getLng(), tv_address);
                        tv_speed.setText(Constants.devicePOJOS.get(position).getDeviceDetailPOJO().getSpeed() + " kph");
                        tv_date.setText(Constants.devicePOJOS.get(position).getDeviceDetailPOJO().getDtTracker());
                        tv_vehicle_name.setText(Constants.devicePOJOS.get(position).getDeviceDetailPOJO().getName());
                        checkVehicleLastTime(Constants.devicePOJOS.get(position).getImei());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        iv_car_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offDisclaimer(true);
            }
        });
        iv_car_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offDisclaimer(false);
            }
        });
    }

    public void checkVehicleLastTime(String device_imei){
        iv_car_on.setEnabled(false);
        iv_car_off.setEnabled(false);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("imei", device_imei));

        new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.optString("result").equalsIgnoreCase("0")||
                            jsonObject.optString("result").equalsIgnoreCase("true")){
                        iv_car_off.setEnabled(true);
                        iv_car_on.setEnabled(true);
                        tv_cut_off_time.setVisibility(View.GONE);
                    }else{
                        try{
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date serverDate=sdf.parse(jsonObject.optString("result"));
                            Date currentDate=new Date();

                            long diff = currentDate.getTime() - serverDate.getTime();

                            long diffSeconds = diff / 1000 % 60;
                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000) % 24;
                            long diffDays = diff / (24 * 60 * 60 * 1000);

                            Log.d(TagUtils.getTag(),"difference sec:-"+diffSeconds);

                            new CountDownTimer(((185-diffSeconds)*1000),1000){

                                @Override
                                public void onTick(long l) {
                                    Log.d(TagUtils.getTag(),"difference sec l:-"+l);
                                    tv_cut_off_time.setVisibility(View.VISIBLE);
                                    tv_cut_off_time.setText("Please wait for "+(l/1000)+" seconds.");
                                }

                                @Override
                                public void onFinish() {
                                    tv_cut_off_time.setVisibility(View.GONE);
                                    iv_car_on.setEnabled(true);
                                    iv_car_off.setEnabled(true);
                                }
                            }.start();

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Exception in response");
                }
            }
        }, "TURN OFF", true).execute(WebServicesUrls.GET_LAST_DT_DEVICE);

    }

    public void engineOFF() {
        if (spinner_devices.getSelectedItemPosition() != -1) {
            DevicePOJO devicePOJO = Constants.devicePOJOS.get(spinner_devices.getSelectedItemPosition());
            switch (devicePOJO.getDeviceDetailPOJO().getDevice().toLowerCase()) {
                case "vmt":
                    setEngineoff(devicePOJO.getImei(), "1");
                    break;
                case "vbt+":
                    setEngineoff(devicePOJO.getImei(), "2");
                    break;
                case "vbtplus":
                    setEngineoff(devicePOJO.getImei(), "2");
                    break;
                case "vbt +":
                    setEngineoff(devicePOJO.getImei(), "2");
                    break;
                case "vss03":
                    setEngineoff(devicePOJO.getImei(), "3");
                    break;
                case "vss06":
                    setEngineoff(devicePOJO.getImei(), "4");
                    break;
                case "ev02":
                    setEngineoff(devicePOJO.getImei(), "5");
                    break;
                default:
//                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Specify gps type");
                    setEngineoff(devicePOJO.getImei(), "2");
                    break;
            }
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select Vehicle");
        }
    }

    public void engineON() {
        if (spinner_devices.getSelectedItemPosition() != -1) {
            DevicePOJO devicePOJO = Constants.devicePOJOS.get(spinner_devices.getSelectedItemPosition());
            switch (devicePOJO.getDeviceDetailPOJO().getDevice().toLowerCase()) {
                case "vmt":
                    setEngineon(devicePOJO.getImei(), "1");
                    break;
                case "vbt+":
                    setEngineon(devicePOJO.getImei(), "2");
                    break;
                case "vbtplus":
                    setEngineon(devicePOJO.getImei(), "2");
                    break;
                case "vbt +":
                    setEngineon(devicePOJO.getImei(), "2");
                    break;
                case "vss03":
                    setEngineon(devicePOJO.getImei(), "3");
                    break;
                case "vss06":
                    setEngineon(devicePOJO.getImei(), "4");
                    break;
                case "ev02":
                    setEngineon(devicePOJO.getImei(), "5");
                    break;
                default:
//                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Specify gps type");
                    setEngineon(devicePOJO.getImei(), "2");
                    break;
            }
        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select Vehicle");
        }
    }

    public void animateFrame(FrameLayout frameLayout) {
        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        frameLayout.startAnimation(myAnim);
    }

    public void offDisclaimer(final boolean is_stop) {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog1.setContentView(R.layout.dialog_off_disclaimer);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView iv_close = dialog1.findViewById(R.id.iv_close);
        TextView tv_ok = dialog1.findViewById(R.id.tv_ok);

        FrameLayout frame_dialog = dialog1.findViewById(R.id.frame_dialog);
        animateFrame(frame_dialog);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                dialogShutDown(is_stop);
            }
        });
    }

    public void dialogShutDown(final boolean status) {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog1.setContentView(R.layout.dialog_shut_down);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView iv_ok = dialog1.findViewById(R.id.iv_ok);
        final ImageView iv_cancel = dialog1.findViewById(R.id.iv_cancel);
        ImageView iv_close = dialog1.findViewById(R.id.iv_close);
        TextView tv_shut_down = dialog1.findViewById(R.id.tv_shut_down);

        if (status) {
            tv_shut_down.setText("Are you sure to restart your vehicle ?");
        } else {
            tv_shut_down.setText("Are you sure to shut down vehicle ?");
        }

        FrameLayout frame_dialog = dialog1.findViewById(R.id.frame_dialog);
        animateFrame(frame_dialog);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        iv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status) {
                    engineON();
                } else {
                    engineOFF();
                }
                dialog1.dismiss();
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_cancel.callOnClick();
            }
        });

    }


    public void setEngineoff(final String imei, String device_type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", "1"));
        nameValuePairs.add(new BasicNameValuePair("dt_cmd", UtilityFunction.parseISTToUTC(simpleDateFormat.format(new Date()))));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        nameValuePairs.add(new BasicNameValuePair("device_type", device_type));

        new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                    try{
                        String time=jsonObject.optString("result");
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");



                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Exception in response");
                }
                checkVehicleLastTime(imei);
            }
        }, "TURN OFF", true).execute("http://platform.voxtrail.com/gps/index.php/ObjectControl/engineOff");
    }
    public void setEngineon(final String imei, String device_type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", "1"));
        nameValuePairs.add(new BasicNameValuePair("dt_cmd", UtilityFunction.parseISTToUTC(simpleDateFormat.format(new Date()))));
        nameValuePairs.add(new BasicNameValuePair("imei", imei));
        nameValuePairs.add(new BasicNameValuePair("device_type", device_type));

        new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Exception in response");
                }
                checkVehicleLastTime(imei);
            }
        }, "TURN OFF", true).execute("http://platform.voxtrail.com/gps/index.php/ObjectControl/engineON");
    }

    public void getCompleteAddress(String latitude, String longitude, final TextView textView) {
        try {
//            showProgressBar();
            String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true&key=" + getResources().getString(R.string.google_map_api_key);

//            StringRequest req = new StringRequest(url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
////                            dismissProgressBar();
//                            try {
////                                Log.d(TagUtils.getTag(), "address response:-" + response);
//                                JSONObject jsonObject = new JSONObject(response);
//                                 JSONArray jsonArray = jsonObject.optJSONArray("results");
//                                JSONObject jsonObject1 = jsonArray.optJSONObject(0);
//                                String formatted_address = jsonObject1.optString("formatted_address");
//                                textView.setText(formatted_address);
//                            } catch (Exception e8-) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
////                    dismissProgressBar();
//                    Log.d(TagUtils.getTag(), "api error:-" + error.toString());
//                }
//            });
//            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//            queue.add(req);

            new GetWebServices(getActivity(), null, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try {
//                                Log.d(TagUtils.getTag(), "address response:-" + response);
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.optJSONArray("results");
                        JSONObject jsonObject1 = jsonArray.optJSONObject(0);
                        String formatted_address = jsonObject1.optString("formatted_address");
                        textView.setText(formatted_address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "GET_ROUTE", true).execute(url);
        } catch (Exception e) {
//            dismissProgressBar();
            e.printStackTrace();
        }
    }
}

