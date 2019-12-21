package com.voxtrail.voxtrail.testing;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.pojo.DeviceUpdatedPOJO;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.dashboard.DashBoardPOJO;
import com.voxtrail.voxtrail.pojo.gm.ObjectHistoryPOJO;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.ResponseListCallback;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponseList;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;
import com.xzl.marquee.library.MarqueeView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestingActivity extends AppCompatActivity {

    @BindView(R.id.pointerSpeedometer)
    PointerSpeedometer pointerSpeedometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        ButterKnife.bind(this);
//        callAPI();

//        btn_check_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deviceUpdateAPI();
//            }
//        });
//        tv_address.setText("A scjabsdclkajbsclkajbscljabsdckabsdckjahsvjhevuobvlqjrbvailsdubv");
//        tv_address.start();

        pointerSpeedometer.setMaxSpeed(500);
        pointerSpeedometer.setSpeedAt(250);

    }

    public class MyValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,###"); // use no decimals
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

            // write your logic here
            if(value < 10) return "";

            return ""; // in case you want to add percent
        }
    }


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Total Vehicles\n100");
//        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    public void deviceUpdateAPI(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("imeis", "'352544071456413','860016020892670','860016020859158','352887077956435','352887077956393','355139089121658','352887077955494','860016020856311','860016020904418','352887077956252','352887077954919','860016020856345','860016020886748','352887077956310','860016020856352','352887075108195','352887077537383','352887077537938','352887078553132','352887078553504','352887078557331','860016020887522','352887077955510','352887077955058','863586038749143','863586038790782'"));
        new WebServiceBaseResponseList<DeviceUpdatedPOJO>(nameValuePairs, this, new ResponseListCallback<DeviceUpdatedPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<DeviceUpdatedPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {

                    } else {
//                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, DeviceUpdatedPOJO.class, "GET_DEVICE_LIST", false).execute(WebServicesUrls.OBJECT_UPDATE);
    }

    //    class HistoryPOJO{
//        String
//    }
    Date fromDate;
    Date toDate;

    public void callAPI() {

        try {
//        String url="http://platform.voxtrail.com/gps/index.php/Dashboard/getDashBoardData";
            String url = "http://192.168.1.63/voxtrail/gps/index.php/Dashboard/getDashBoardData";

            String from_dt = "2018-09-19 00:00:00";
            String to_dt = "2018-09-19 23:59:00";

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("imei", "352887077954919"));
            nameValuePairs.add(new BasicNameValuePair("from_dt", from_dt));
            nameValuePairs.add(new BasicNameValuePair("to_dt", to_dt));
            nameValuePairs.add(new BasicNameValuePair("user_id", "3"));
            new WebServiceBaseResponse<DashBoardPOJO>(nameValuePairs, this, new ResponseCallBack<DashBoardPOJO>() {
                @Override
                public void onGetMsg(ResponsePOJO<DashBoardPOJO> responsePOJO) {
                    try {
                        if (responsePOJO.isSuccess()) {
                            Log.d(TagUtils.getTag(), "parsing started");
//                            parseDate(responsePOJO.getResult().getObjectHistoryPOJOS());
//                            parseData(responsePOJO.getResult().getObjectHistoryPOJOS());
                        }
                        ToastClass.showShortToast(getApplicationContext(), responsePOJO.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, DashBoardPOJO.class, "GET_DEVICE_LIST", false).execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseDate(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        try {
            for (ObjectHistoryPOJO objectHistoryPOJO : objectHistoryPOJOS) {
                SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date parsed = sourceFormat.parse(objectHistoryPOJO.getDtTracker()); // => Date is in UTC now

                TimeZone tz = TimeZone.getTimeZone("Asia/Kolkata");
                SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                destFormat.setTimeZone(tz);

                String result = destFormat.format(parsed);
                objectHistoryPOJO.setDtTracker(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void parseData(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        Log.d(TagUtils.getTag(), "objects size:-" + objectHistoryPOJOS.size());
        if (objectHistoryPOJOS.size() > 0) {
            clearLists();
            checkForRunningStatus(objectHistoryPOJOS);
            removeDuplicacy(runningStrings);
            removeDuplicacy(stoppedStrings);

            if (runningStrings.size() > 0) {
                if (objectHistoryPOJOS.get(0).getSpeed().equals("0")) {
                    String first_running_index = runningStrings.get(0).split(",")[0];
                    stoppedStrings.add(0, (0 + "," + first_running_index));
                }
            } else {
                stoppedStrings.clear();
                stoppedStrings.add(0 + "," + (objectHistoryPOJOS.size() - 1));
            }
            double avg_speed = calcAvgSpeed(objectHistoryPOJOS);
            long total_duration = getTotalDuration(objectHistoryPOJOS);
            Log.d(TagUtils.getTag(), "running status:-" + runningStrings);
            Log.d(TagUtils.getTag(), "stopped status:-" + stoppedStrings);
            Log.d(TagUtils.getTag(), "MaxSpeed:-" + calculateMaxSpeed(objectHistoryPOJOS));
            Log.d(TagUtils.getTag(), "AvgSpeed:-" + avg_speed);
            Log.d(TagUtils.getTag(), "totalDuration:-" + total_duration);
            double drive_distance = getDriveDistance(running_duration, avg_speed);
            Log.d(TagUtils.getTag(), "drive distance:-" + drive_distance);
            Log.d(TagUtils.getTag(), "fuel Consumption:-" + (drive_distance / kmInLitre));
            Log.d(TagUtils.getTag(), "Parked Duration:-" + (total_duration - running_duration));
            Log.d(TagUtils.getTag(), "stops:-" + stoppedStrings.size());
            Log.d(TagUtils.getTag(), "parsing ended");
        }
    }

    int maxSpeed = 0;

    public int calculateMaxSpeed(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        maxSpeed = 0;
        for (ObjectHistoryPOJO objectHistoryPOJO : objectHistoryPOJOS) {
            if (!objectHistoryPOJO.getSpeed().equals("0")) {
                if (maxSpeed < (Integer.parseInt(objectHistoryPOJO.getSpeed()))) {
                    maxSpeed = Integer.parseInt(objectHistoryPOJO.getSpeed());
                }
            }
        }

        return maxSpeed;
    }

    long running_duration = 0;
    int kmInLitre = 20;

    public double calcAvgSpeed(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        int total_speed = 0;
        int total_interval = 0;
        running_duration = 0;
        for (String runningString : runningStrings) {
            running_duration += UtilityFunction.getDifferenceBtwTwoTimeInSec(objectHistoryPOJOS.get(Integer.parseInt(runningString.split(",")[1])).getDtTracker(), objectHistoryPOJOS.get(Integer.parseInt(runningString.split(",")[0])).getDtTracker());
            int startIndex = Integer.parseInt(runningString.split(",")[0]);
            int endIndex = Integer.parseInt(runningString.split(",")[1]);
            for (int i = startIndex; i < endIndex; i++) {
                total_speed = total_speed + Integer.parseInt(objectHistoryPOJOS.get(i).getSpeed());
                total_interval++;
            }
        }
        return ((double) total_speed / (double) total_interval);
    }

    public long getTotalDuration(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        if (objectHistoryPOJOS.size() > 0) {
            return (UtilityFunction.getDifferenceBtwTwoTimeInSec(objectHistoryPOJOS.get(objectHistoryPOJOS.size() - 1).getDtTracker(), objectHistoryPOJOS.get(0).getDtTracker()));
        }
        return 0;
    }

    public double getDriveDistance(long timeinSec, double avg_Speed) {
        return avg_Speed * (timeinSec / 3600);
    }


    public void clearLists() {
        runningStrings.clear();
        stoppedStrings.clear();
    }

    public void removeDuplicacy(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {

            for (int j = i + 1; j < strings.size(); j++) {
                if (strings.get(i).equals(strings.get(j))) {
                    strings.remove(j);
                    j--;
                }
            }
        }
    }

    List<String> runningStrings = new ArrayList<>();
    List<String> stoppedStrings = new ArrayList<>();
    int starting_index = -1;

    public void checkForRunningStatus(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        for (int i = 0; i < objectHistoryPOJOS.size(); i++) {
            if (!objectHistoryPOJOS.get(i).getSpeed().equals("0")) {
                if (starting_index == -1) {
                    starting_index = i;
                }
                int next_stop_index = checkForZeroSpeedIndex(objectHistoryPOJOS, i);
                if (next_stop_index != -1) {
                    int next_speed_index = checkForNextSpeed(objectHistoryPOJOS, next_stop_index);
                    if (next_speed_index == -1) {
                        runningStrings.add(starting_index + "," + (next_stop_index - 1));
                        i = objectHistoryPOJOS.size();
                    } else {
                        long timeDiff = UtilityFunction.getDifferenceInMinInt(objectHistoryPOJOS, next_stop_index, next_speed_index);
                        if (timeDiff > 5) {
                            runningStrings.add(starting_index + "," + (next_stop_index - 1));
                            stoppedStrings.add(next_stop_index + "," + next_speed_index);
                            starting_index = -1;
                        } else {

                        }
                        i = next_speed_index;
                    }
                } else {
                    runningStrings.add(starting_index + "," + (objectHistoryPOJOS.size() - 1));
                }
            }
        }
    }

    public int checkForZeroSpeedIndex(List<ObjectHistoryPOJO> objectHistoryPOJOS, int starting_index) {
        if ((starting_index + 1) != objectHistoryPOJOS.size()) {
            for (int i = (starting_index + 1); i < objectHistoryPOJOS.size(); i++) {
                if (objectHistoryPOJOS.get(i).getSpeed().equals("0")) {
//                    int next_speed_index=checkForNextSpeed(objectHistoryPOJOS,i);
                    return i;
                }
            }
        }
        return -1;
    }

    public int checkForNextSpeed(List<ObjectHistoryPOJO> objectHistoryPOJOS, int starting_index) {
        if ((starting_index + 1) != objectHistoryPOJOS.size()) {
            for (int i = (starting_index + 1); i < objectHistoryPOJOS.size(); i++) {
                if (!objectHistoryPOJOS.get(i).getSpeed().equals("0")) {
                    return i;
                }
            }

        }
        return -1;
    }

//    int total_vehicles = 0;
//    int running_vehicles = 0;
//    int idle_vehicles = 0;
//    int offline_vehicles = 0;
//
//    int max_speed = 0;
//    int avg_speed = 0;
//    int total_speed = 0;
//
//
//    public void parseData(String response) {
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            if (jsonObject.optString("status").equals("1")) {
//
//                JSONObject resultObject = jsonObject.optJSONObject("result");
//
//                List<DevicePOJO> devicePOJOS = new ArrayList<>();
//                JSONArray jsonArray = resultObject.optJSONArray("objects");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    DevicePOJO devicePOJO = new Gson().fromJson(jsonArray.optJSONObject(i).toString(), DevicePOJO.class);
//                    devicePOJOS.add(devicePOJO);
//                }
//
//                total_vehicles = devicePOJOS.size();
//                for (int i = 0; i < devicePOJOS.size(); i++) {
//                    try {
//                        JSONObject paramObject = new JSONObject(devicePOJOS.get(i).getDeviceDetailPOJO().getParams());
//                        if (paramObject.has("acc")) {
//                            if (paramObject.optString("acc").equals("0")) {
//                                if (getCalculatedTime(devicePOJOS.get(i).getDeviceDetailPOJO().getDtServer()) > 5) {
//                                    Log.d(TagUtils.getTag(), "offline 1");
//                                    offline_vehicles++;
//                                } else {
//                                    idle_vehicles++;
//                                }
//                            } else {
//                                running_vehicles++;
//                            }
//                        } else {
//                            Log.d(TagUtils.getTag(), "offline 2");
//                            offline_vehicles++;
//                        }
//                    } catch (Exception e) {
//                        offline_vehicles++;
//                        e.printStackTrace();
//                    }
//                }
//
//                Log.d(TagUtils.getTag(), "total vehicles:-" + total_vehicles);
//                Log.d(TagUtils.getTag(), "offline_vehicles:-" + offline_vehicles);
//                Log.d(TagUtils.getTag(), "idle_vehicles:-" + idle_vehicles);
//                Log.d(TagUtils.getTag(), "running_vehicles:-" + running_vehicles);
//
//                JSONArray historyJSONArray = resultObject.optJSONArray("first_object_history");
//                List<ObjectHistoryPOJO> objectHistoryPOJOS = new ArrayList<>();
//                for (int i = 0; i < historyJSONArray.length(); i++) {
//                    ObjectHistoryPOJO objectHistoryPOJO = new Gson().fromJson(historyJSONArray.optJSONObject(i).toString(), ObjectHistoryPOJO.class);
//                    objectHistoryPOJOS.add(objectHistoryPOJO);
//                }
//
//                checkSpeedTimeData(objectHistoryPOJOS);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String RUNNING_TYPE = "running_type";
//    public static String STOPPED_TYPE = "stopped_type";
//
//    class HistoryData {
//        String data;
//        String type;
//
//        public HistoryData(String data, String type) {
//            this.data = data;
//            this.type = type;
//        }
//
//        public String getData() {
//            return data;
//        }
//
//        public void setData(String data) {
//            this.data = data;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        @Override
//        public String toString() {
//            return "HistoryData{" +
//                    "data='" + data + '\'' +
//                    ", type='" + type + '\'' +
//                    '}';
//        }
//    }
//
//    public void checkSpeedTimeData(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
//        total_speed = 0;
//        for (int i = 0; i < objectHistoryPOJOS.size(); i++) {
//            try {
//                int speed = Integer.parseInt(objectHistoryPOJOS.get(i).getSpeed());
//                if (max_speed < speed) {
//                    max_speed = speed;
//                }
//                total_speed += speed;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        Log.d(TagUtils.getTag(), "max speed:-" + max_speed);
//        Log.d(TagUtils.getTag(), "average_speed:-" + total_speed / objectHistoryPOJOS.size());
//
//        running_minutes = 0;
//        stopped_minutes = 0;
//        stop_times = 0;
//        sum_speed = 0;
//        sum_speed_count = 0;
//
//        historyDataArrayList.clear();
//
//        for (int i = 0; i < objectHistoryPOJOS.size(); i++) {
//            ObjectHistoryPOJO objectHistoryPOJO = objectHistoryPOJOS.get(i);
//            if (objectHistoryPOJO.getSpeed().equals("0")) {
//                int next_0_index = checkZeroSpeedIndex(objectHistoryPOJOS, i) - 1;
//                historyDataArrayList.add(new HistoryData((i + "," + next_0_index), STOPPED_TYPE));
//                i = next_0_index;
//            } else {
//                int next_speed_index = checkSpeedIndex(objectHistoryPOJOS, i) - 1;
//                historyDataArrayList.add(new HistoryData((i + "," + next_speed_index), RUNNING_TYPE));
//                i = next_speed_index;
//            }
//            Log.d(TagUtils.getTag(), "i position:-" + i);
//        }
//
//        Log.d(TagUtils.getTag(), "History Data:-" + historyDataArrayList.toString());
////
////        if (historyDataArrayList.size() > 0) {
////            for (HistoryData historyData: historyDataArrayList) {
////                String data1=historyData.getData().split(",")[0];
////                String data2=historyData.getData().split(",")[1];
////                if(historyData.getType().equals(RUNNING_TYPE)) {
////                    running_minutes += getDifferenceInMin(objectHistoryPOJOS, data1, data2);
////                    sum_speed += calcSpeedDiff(objectHistoryPOJOS, data1, data2);
////                }else{
////                    long diff = getDifferenceInMin(objectHistoryPOJOS,data1, data2);
////                    if (diff > 5) {
////                        stopped_minutes += diff;
////                        stop_times++;
////                    } else {
////                        running_minutes += diff;
////                    }
////                }
////            }
////        } else {
////            // set distance duration -
////            running_minutes = 0;
////        }
////
//////        if (stoppedIndexes.size() > 0) {
//////            for (String str : runningIndexes) {
//////                long diff = getDifferenceInMin(objectHistoryPOJOS,str.split(",")[0], str.split(",")[1]);
//////                if (diff > 5) {
//////                    stopped_minutes += diff;
//////                    stop_times++;
//////                } else {
//////                    running_minutes += diff;
//////                }
//////            }
//////        } else {
//////            //set total parked to - and total stop to -
//////            stopped_minutes = 0;
//////        }
////
////        Log.d(TagUtils.getTag(), "stopped minutes:-" + stopped_minutes);
////        Log.d(TagUtils.getTag(), "running minutes:-" + running_minutes);
////        Log.d(TagUtils.getTag(), "stop times:-" + stop_times);
////        double avg_speed=(sum_speed/sum_speed_count);
////        Log.d(TagUtils.getTag(), "avg speed:-" + avg_speed);
////        double distance_travelled=(((double)running_minutes)/60)*avg_speed;
////        Log.d(TagUtils.getTag(), "distance travelled:-" + distance_travelled);
//
//
//        for (int i = 0; i < historyDataArrayList.size(); i++) {
//            if (historyDataArrayList.get(i).getType().equals(Constants.STOPPED_TYPE)) {
//                String stopped_start_time = String.valueOf(Integer.parseInt(historyDataArrayList.get(i).getData().split(",")[0]));
//                String stopped_end_time = String.valueOf(Integer.parseInt(historyDataArrayList.get(i).getData().split(",")[1]));
//                long diff = UtilityFunction.getDifferenceInSec(objectHistoryPOJOS, stopped_start_time, stopped_end_time);
//                Log.d(TagUtils.getTag(), "difference:-" + diff);
//                if (diff >= 1) {
//                    stoppedhistoryData.add(historyDataArrayList.get(i));
//                    stoppedIntIndex.add(i);
//                }
//            }
//        }
//
//        Log.d(TagUtils.getTag(), "stopped Index:-" + stoppedIntIndex);
//
//        if(stoppedIntIndex.size()>0){
//            if(stoppedIntIndex.size()==1){
//                int starting_pos = Integer.parseInt(historyDataArrayList.get(stoppedIntIndex.get(0)).getData().split(",")[1]) + 1;
//                int end_pos = Integer.parseInt(historyDataArrayList.get(stoppedIntIndex.get(1)).getData().split(",")[0]) - 1;
//                HistoryData historyData = new HistoryData(String.valueOf(starting_pos + "," + end_pos), Constants.RUNNING_TYPE);
//                runninghistoryData.add(historyData);
//            }
//        }
//        if(stoppedIntIndex.size()>1){
//            int starting_pos = Integer.parseInt(historyDataArrayList.get(stoppedIntIndex.get(0)).getData().split(",")[1]) + 1;
//            int end_pos = Integer.parseInt(historyDataArrayList.get(stoppedIntIndex.get(1)).getData().split(",")[0]) - 1;
//            HistoryData historyData = new HistoryData(String.valueOf(starting_pos + "," + end_pos), Constants.RUNNING_TYPE);
//            runninghistoryData.add(historyData);
//        }else{
//            if(stoppedIntIndex.size()==1){
//
//            }
//        }
//        for (int i = 0; i < stoppedIntIndex.size(); i++) {
//            if ((i + 1) != stoppedIntIndex.size()) {
//                int starting_pos = Integer.parseInt(historyDataArrayList.get(stoppedIntIndex.get(i)).getData().split(",")[1]) + 1;
//                int end_pos = Integer.parseInt(historyDataArrayList.get(stoppedIntIndex.get(i + 1)).getData().split(",")[0]) - 1;
//                HistoryData historyData = new HistoryData(String.valueOf(starting_pos + "," + end_pos), Constants.RUNNING_TYPE);
//                runninghistoryData.add(historyData);
//            }
//        }
//
//
//        Log.d(TagUtils.getTag(), "running data:-" + runninghistoryData.toString());
//        Log.d(TagUtils.getTag(), "stopped data:-" + stoppedhistoryData.toString());
//
//    }
//
//    List<HistoryData> runninghistoryData = new ArrayList<>();
//    List<HistoryData> stoppedhistoryData = new ArrayList<>();
//    List<Integer> stoppedIntIndex = new ArrayList<>();
//
//    public int calcSpeedDiff(List<ObjectHistoryPOJO> objectHistoryPOJOS, String startIndex, String endIndex) {
//        try {
//            int total_speed = 0;
//            for (int i = Integer.parseInt(startIndex); i < Integer.parseInt(endIndex); i++) {
//                total_speed += Integer.parseInt(objectHistoryPOJOS.get(i).getSpeed());
//            }
//            sum_speed_count++;
//            if ((Integer.parseInt(endIndex) - Integer.parseInt(startIndex)) == 0) {
//                return total_speed;
//            } else {
//                return (total_speed / (Integer.parseInt(endIndex) - Integer.parseInt(startIndex)));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//
//    int running_minutes = 0;
//    int stopped_minutes = 0;
//    int stop_times = 0;
//    int sum_speed = 0;
//    int sum_speed_count = 0;
//
//    List<HistoryData> historyDataArrayList = new ArrayList<>();
//
//    public int checkZeroSpeedIndex(List<ObjectHistoryPOJO> objectHistoryPOJOS, int last_index) {
//        int ind = last_index;
//        if (objectHistoryPOJOS.size() != (last_index + 1)) {
//            for (int i = (last_index + 1); i < objectHistoryPOJOS.size(); i++) {
//                if (!objectHistoryPOJOS.get(i).getSpeed().equals("0")) {
//                    return i;
//                }
//            }
//        }
//        return ind + 1;
//    }
//
//    public int checkSpeedIndex(List<ObjectHistoryPOJO> objectHistoryPOJOS, int last_index) {
//        int ind = last_index;
//        if (objectHistoryPOJOS.size() != (last_index + 1)) {
//            for (int i = (last_index + 1); i < objectHistoryPOJOS.size(); i++) {
//                if (objectHistoryPOJOS.get(i).getSpeed().equals("0")) {
//                    return i;
//                }
//            }
//        }
//        return ind + 1;
//    }
//
//    public long getCalculatedTime(String server_time) {
//        try {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date d1 = format.parse(server_time);
//            Date d2 = format.parse(UtilityFunction.getServerTimeCurrentTime());
//            long diff = d2.getTime() - d1.getTime();
//
//            long diffSeconds = diff / 1000 % 60;
//            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);
//
//            return diffMinutes;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    public long getDifferenceInMin(List<ObjectHistoryPOJO> objectHistoryPOJOS, String start_time, String end_time) {
//        try {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date d1 = format.parse(objectHistoryPOJOS.get(Integer.parseInt(start_time)).getDtTracker());
//            Date d2 = format.parse(objectHistoryPOJOS.get(Integer.parseInt(end_time)).getDtTracker());
//            long diff = d2.getTime() - d1.getTime();
//
////            long diffSeconds = diff / 1000 % 60;
//            long diffMinutes = diff / (60 * 1000) % 60;
////            long diffHours = diff / (60 * 60 * 1000) % 24;
////            long diffDays = diff / (24 * 60 * 60 * 1000);
//
//            return diffMinutes;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

}
