package com.voxtrail.voxtrail.fragment.sharing;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.ShareDeviceSelectAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

import static android.content.Context.CLIPBOARD_SERVICE;

public class SelectObjectForSharingFragment extends FragmentController implements DatePickerDialog.OnDateSetListener, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rv_device_list)
    RecyclerView rv_device_list;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.tv_share)
    TextView tv_share;

    DevicePOJO devicePOJO;

    public SelectObjectForSharingFragment(DevicePOJO devicePOJO) {
        this.devicePOJO = devicePOJO;
    }

    public SelectObjectForSharingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_objects, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        attachAdapter();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_search.getText().toString().length() > 0) {
                    filterDevice();
                } else {
                    if (Constants.devicePOJOS != null && Constants.devicePOJOS.size() > 0) {
                        deviceStrings.addAll(Constants.devicePOJOS);
                        deviceListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareObject();
            }
        });
    }

    public void shareObject() {
        if (deviceStrings.size() > 0) {

            String devices = "";
            List<DevicePOJO> devicePOJOS = new ArrayList<>();
            for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
                if (devicePOJO.isIs_selected()) {
                    devicePOJOS.add(devicePOJO);
                }
            }

            if (devicePOJOS.size() > 0) {
                for (int i = 0; i < devicePOJOS.size(); i++) {
                    if ((i + 1) == devicePOJOS.size()) {
                        devices += devicePOJOS.get(i).getImei();
                    } else {
                        devices += devicePOJOS.get(i).getImei() + ",";
                    }
                }

                this.devices = devices;
                shareDialog(devices);
//                openDialog(devices);
            } else {
                ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select atleast one object");
            }


        } else {
            ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select atleast one object");
        }
    }

    String devices = "";


    public void filterDevice() {
        if (Constants.devicePOJOS != null && Constants.devicePOJOS.size() > 0) {
            List<DevicePOJO> devicePOJOS = new ArrayList<>();
            for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
                if (devicePOJO.getImei().toLowerCase().contains(et_search.getText().toString().toLowerCase())
                        || devicePOJO.getDeviceDetailPOJO().getName().toLowerCase().contains(et_search.getText().toString().toLowerCase())
                        || devicePOJO.getDeviceDetailPOJO().getPlateNumber().toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                    devicePOJOS.add(devicePOJO);
                }
            }
            if (devicePOJOS.size() > 0) {
                deviceStrings.clear();
                deviceStrings.addAll(devicePOJOS);
                deviceListAdapter.notifyDataSetChanged();
            }
        }
    }

    ShareDeviceSelectAdapter deviceListAdapter;
    List<DevicePOJO> deviceStrings = new ArrayList<>();

    public void attachAdapter() {

        if (Constants.devicePOJOS != null && Constants.devicePOJOS.size() > 0) {
            for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
                devicePOJO.setIs_selected(false);
                deviceStrings.add(devicePOJO);
                if(this.devicePOJO!=null&&this.devicePOJO.getImei().equals(devicePOJO.getImei())){
                    devicePOJO.setIs_selected(true);
                }
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv_device_list.setHasFixedSize(true);
            rv_device_list.setLayoutManager(linearLayoutManager);
            deviceListAdapter = new ShareDeviceSelectAdapter(getActivity(), null, deviceStrings);
            rv_device_list.setAdapter(deviceListAdapter);
            rv_device_list.setNestedScrollingEnabled(false);
            rv_device_list.setItemAnimator(new DefaultItemAnimator());

            deviceListAdapter.setAdapterItemAdapter(new AdapterListener() {
                @Override
                public void onAdapterItemClickListener(int position) {

                }
            });


        }
    }

    String selected_date = "";

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

        String date = year + "-" + month + "-" + day;
        selected_date = date;
        openTimePicker();
    }

    public void openTimePicker() {
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                SelectObjectForSharingFragment.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.setAccentColor(Color.parseColor("#656363"));
        tpd.show(getActivity().getFragmentManager(), "Select Time");
    }

    @Override
    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = hourString + ":" + minuteString + ":" + secondString;
        String dt = selected_date + " " + time;
//        openDialog(devices, dt);
        tv_share_dt.setText(dt);
    }

    public void openDialog(String imeis, String dt) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("imeis", imeis));
        nameValuePairs.add(new BasicNameValuePair("valid_to", dt));

        new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("1")) {
                        String unique_key = jsonObject.optJSONObject("result").optString("unique_key");
                        shareDialog(unique_key);
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Object shared");
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "SHARE_OBJECT", true).execute(WebServicesUrls.SHARE_OBJECTS);
    }

    TextView tv_share_dt;
    String unique_key="";

    public void shareDialog(final String imeis) {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setContentView(R.layout.dialog_object_share);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_share = dialog1.findViewById(R.id.btn_share);
        final TextView tv_dt = dialog1.findViewById(R.id.tv_dt);
        LinearLayout ll_dt = dialog1.findViewById(R.id.ll_dt);
        final Button btn_copy = dialog1.findViewById(R.id.btn_copy);
        TextView tv_link = dialog1.findViewById(R.id.tv_link);
        final LinearLayout ll_generate = dialog1.findViewById(R.id.ll_generate);
        final LinearLayout ll_copy = dialog1.findViewById(R.id.ll_copy);
        Button btn_generate = dialog1.findViewById(R.id.btn_generate);
        ImageView iv_close = dialog1.findViewById(R.id.iv_close);

        tv_dt.setText(getRange());

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        ll_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_share_dt=tv_dt;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SelectObjectForSharingFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(Color.parseColor("#656363"));
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
                nameValuePairs.add(new BasicNameValuePair("imeis", imeis));
                nameValuePairs.add(new BasicNameValuePair("valid_to", tv_dt.getText().toString()));

                new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("status").equalsIgnoreCase("1")) {
                                String unique_key = jsonObject.optJSONObject("result").optString("unique_key");
                                SelectObjectForSharingFragment.this.unique_key=unique_key;
//                                shareDialog(unique_key);
                                ll_generate.setVisibility(View.GONE);
                                ll_copy.setVisibility(View.VISIBLE);
                                ToastClass.showShortToast(getActivity().getApplicationContext(), "Object shared");
                            } else {
                                ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "SHARE_OBJECT", true).execute(WebServicesUrls.SHARE_OBJECTS);
            }
        });

//        tv_link.setText(unique_key);

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Source Text", "http://www.voxtrail.com/share.php?token=" + unique_key);
                clipboardManager.setPrimaryClip(clipData);
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Link");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "http://www.voxtrail.com/share.php?token=" + unique_key);
                startActivity(Intent.createChooser(sharingIntent, "Share Using"));
            }
        });

        tv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_copy.callOnClick();
            }
        });

    }


    public String getRange() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 3);
        String dt=dateFormat.format(cal.getTime());
        return dt;
    }
}
