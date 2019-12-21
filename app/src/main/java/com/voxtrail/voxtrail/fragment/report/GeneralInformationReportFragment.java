package com.voxtrail.voxtrail.fragment.report;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.pojo.report.GeneralInformationPOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class GeneralInformationReportFragment extends FragmentController implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ic_back)
    ImageView iv_back;
    DevicePOJO devicePOJO;

    @BindView(R.id.tv_route_start)
    TextView tv_route_start;
    @BindView(R.id.tv_route_end)
    TextView tv_route_end;
    @BindView(R.id.tv_route_length)
    TextView tv_route_length;
    @BindView(R.id.tv_move_duration)
    TextView tv_move_duration;
    @BindView(R.id.tv_stop_duration)
    TextView tv_stop_duration;
    @BindView(R.id.tv_stop_count)
    TextView tv_stop_count;
    @BindView(R.id.tv_top_speed)
    TextView tv_top_speed;
    @BindView(R.id.tv_average_speed)
    TextView tv_average_speed;
    @BindView(R.id.tv_overspeed_count)
    TextView tv_overspeed_count;
    @BindView(R.id.tv_fuel_consumption)
    TextView tv_fuel_consumption;
    @BindView(R.id.tv_fuel_cost)
    TextView tv_fuel_cost;
    @BindView(R.id.tv_engine_work)
    TextView tv_engine_work;
    @BindView(R.id.tv_engine_idle)
    TextView tv_engine_idle;
    @BindView(R.id.tv_odometer)
    TextView tv_odometer;
    @BindView(R.id.tv_engine_hour)
    TextView tv_engine_hour;
    @BindView(R.id.tv_driver)
    TextView tv_driver;
    @BindView(R.id.tv_trailer)
    TextView tv_trailer;
    @BindView(R.id.tv_dt)
    TextView tv_dt;

    @BindView(R.id.check_today)
    CheckBox check_today;
    @BindView(R.id.check_yesterday)
    CheckBox check_yesterday;
    @BindView(R.id.check_hour)
    CheckBox check_hour;
    @BindView(R.id.check_user_defined)
    CheckBox check_user_defined;
    @BindView(R.id.ll_custom)
    LinearLayout ll_custom;
    @BindView(R.id.tv_start_datetime)
    TextView tv_start_datetime;
    @BindView(R.id.ll_start_datetime)
    LinearLayout ll_start_datetime;
    @BindView(R.id.ll_end_datetime)
    LinearLayout ll_end_datetime;
    @BindView(R.id.tv_end_datetime)
    TextView tv_end_datetime;
    @BindView(R.id.et_speed_limit)
    EditText et_speed_limit;
    @BindView(R.id.et_stop_duration)
    EditText et_stop_duration;
    @BindView(R.id.btn_ok)
    Button btn_ok;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;
    @BindView(R.id.iv_drop)
    ImageView iv_drop;
    @BindView(R.id.tv_object_name)
    TextView tv_object_name;
    @BindView(R.id.tv_period)
    TextView tv_period;

    List<CheckBox> checkBoxes = new ArrayList<>();

    public GeneralInformationReportFragment(DevicePOJO devicePOJO) {
        this.devicePOJO = devicePOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_general_information, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI();
            }
        });
        setPlayBackLogic();
        callAPI();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sliding_layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState.name().equals("EXPANDED")) {
                    iv_drop.setImageResource(R.drawable.ic_down);
                } else {
                    iv_drop.setImageResource(R.drawable.ic_up);
                }
            }
        });
    }

    public void callAPI() {
        swipeRefreshLayout.setRefreshing(true);

        Log.d(TagUtils.getTag(), "start DT:-" + startdatetime);
        Log.d(TagUtils.getTag(), "end DT:-" + enddatetime);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("dtf", UtilityFunction.parseISTToUTC(UtilityFunction.convertHistoryserverDateTime(startdatetime))));
        nameValuePairs.add(new BasicNameValuePair("dtt", UtilityFunction.parseISTToUTC(UtilityFunction.convertHistoryserverDateTime(enddatetime))));
        nameValuePairs.add(new BasicNameValuePair("data_items", "['route_start','route_end','route_length','move_duration','stop_duration','stop_count','top_speed','avg_speed','overspeed_count','fuel_consumption','fuel_cost','engine_work','engine_idle','odometer','engine_hours','driver','trailer']"));
        nameValuePairs.add(new BasicNameValuePair("imei", devicePOJO.getImei()));
        if (et_stop_duration.getText().toString().trim().equalsIgnoreCase("")) {
            try {
                int stop_duration = Integer.parseInt(et_stop_duration.getText().toString());
                nameValuePairs.add(new BasicNameValuePair("stop_duration", et_stop_duration.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
                nameValuePairs.add(new BasicNameValuePair("stop_duration", "1"));
            }
        } else {
            nameValuePairs.add(new BasicNameValuePair("stop_duration", "1"));
        }
        if (et_speed_limit.getText().toString().trim().equalsIgnoreCase("0")) {
            try {
                int speed_limit = Integer.parseInt(et_speed_limit.getText().toString());
                nameValuePairs.add(new BasicNameValuePair("speed_limit", et_speed_limit.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
                nameValuePairs.add(new BasicNameValuePair("speed_limit", "0"));
            }
        } else {
            nameValuePairs.add(new BasicNameValuePair("speed_limit", "0"));
        }

        activityManager.showProgressBar();

        new WebServiceBaseResponse<GeneralInformationPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<GeneralInformationPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<GeneralInformationPOJO> responsePOJO) {
                swipeRefreshLayout.setRefreshing(false);
                activityManager.dismissProgressBar();
                try {
                    if (responsePOJO.isSuccess()) {
                        tv_object_name.setText(devicePOJO.getDeviceDetailPOJO().getName());
                        tv_period.setText(UtilityFunction.convertHistoryserverDateTime(startdatetime) + " - " + UtilityFunction.convertHistoryserverDateTime(enddatetime));
                        tv_route_start.setText(String.valueOf(responsePOJO.getResult().getRouteStart()));
                        tv_route_end.setText(String.valueOf(responsePOJO.getResult().getRouteEnd()));
                        tv_route_length.setText(String.valueOf(responsePOJO.getResult().getRouteLength()) + " km");
                        String move_duration = UtilityFunction.parseDuration(String.valueOf(responsePOJO.getResult().getDrivesDuration()));
                        Log.d(TagUtils.getTag(), "move_duration:-" + move_duration);
                        tv_move_duration.setText(move_duration);
                        tv_stop_duration.setText(UtilityFunction.parseDuration(String.valueOf(responsePOJO.getResult().getStopsDuration())));
                        tv_stop_count.setText(String.valueOf(responsePOJO.getResult().getStops()));
                        tv_top_speed.setText(String.valueOf(responsePOJO.getResult().getTopSpeed()) + " km/h");
                        tv_average_speed.setText(String.valueOf(responsePOJO.getResult().getAvgSpeed()) + " km/h");
                        tv_overspeed_count.setText(String.valueOf(responsePOJO.getResult().getOverspeedCount()));
                        tv_fuel_consumption.setText(String.valueOf(responsePOJO.getResult().getFuelConsumption()) + " L");
                        tv_fuel_cost.setText(String.valueOf(responsePOJO.getResult().getFuelCost()));
                        tv_engine_work.setText(UtilityFunction.parseDuration(String.valueOf(responsePOJO.getResult().getEngineWork())));
                        tv_engine_idle.setText(UtilityFunction.parseDuration(String.valueOf(responsePOJO.getResult().getEngineIdle())));
                        tv_odometer.setText(String.valueOf(responsePOJO.getResult().getOdometer() + " km"));
                        tv_engine_hour.setText(String.valueOf(UtilityFunction.parseDuration(responsePOJO.getResult().getEngineHours())));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, GeneralInformationPOJO.class, "GET_GENERAL_INFORMATION", false).execute(WebServicesUrls.GENERAL_INFORMATION_API);
    }

    String startdatetime = "";
    String enddatetime = "";
    TextView tv_date_time;
    boolean is_custom_DT = false;

    boolean is_stating_dt = false;

    public void setPlayBackLogic() {
        checkBoxes.add(check_today);
        checkBoxes.add(check_yesterday);
        checkBoxes.add(check_hour);
        checkBoxes.add(check_user_defined);

        check_user_defined.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    is_custom_DT = true;
                    ll_custom.setVisibility(View.VISIBLE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_user_defined)) {
                            checkBox.setChecked(false);
                        }
                    }
                } else {
                    ll_custom.setVisibility(View.GONE);
                }
            }
        });

        check_hour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.GONE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_hour)) {
                            checkBox.setChecked(false);
                        }
                        getHourAgo();
                    }
                }
            }
        });

        check_today.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.GONE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_today)) {
                            checkBox.setChecked(false);
                        }
                    }
                    getTodayRange();
                }
            }
        });

        check_yesterday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.GONE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_yesterday)) {
                            checkBox.setChecked(false);
                        }
                    }
                    getYesterdayRange();
                }
            }
        });

        ll_start_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_date_time = tv_start_datetime;
                is_stating_dt = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        GeneralInformationReportFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        ll_end_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_date_time = tv_end_datetime;
                is_stating_dt = false;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        GeneralInformationReportFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        check_today.setChecked(true);
        tv_start_datetime.setText(startdatetime);
        tv_end_datetime.setText(enddatetime);
        slidingLogic();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingLogic();
                callAPI();
            }
        });

    }

    public void getTodayRange() {
        is_custom_DT = false;
        startdatetime = UtilityFunction.getCurrentDate() + " 00:00:00";
        enddatetime = UtilityFunction.getCurrentDate() + " 23:59:00";
        setDateTime();
    }

    public void getYesterdayRange() {
        is_custom_DT = false;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        startdatetime = dateFormat.format(cal.getTime()) + " 00:00:00";
        enddatetime = dateFormat.format(cal.getTime()) + " 23:59:00";
        setDateTime();
    }

    public void getHourAgo() {
        is_custom_DT = false;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        startdatetime = dateFormat.format(cal.getTime());
        enddatetime = UtilityFunction.getCurrentDateTime();
        setDateTime();
    }


    public void slidingLogic() {
        Log.d(TagUtils.getTag(), "sliding logic");
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }
    }

    String selected_date = "";

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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

        String date = day + "-" + month + "-" + year;
        selected_date = date;
        openTimePicker();
    }

    public void openTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                GeneralInformationReportFragment.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getActivity().getFragmentManager(), "Select Time");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        is_custom_DT = true;
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = hourString + ":" + minuteString + ":" + secondString;
        tv_date_time.setText(selected_date + " " + time);
        if (is_stating_dt) {
            startdatetime = selected_date + " " + time;
        } else {
            enddatetime = selected_date + " " + time;
        }
        setDateTime();
    }

    public void setDateTime() {
        tv_dt.setText(startdatetime + "  -  " + enddatetime);
    }

}
