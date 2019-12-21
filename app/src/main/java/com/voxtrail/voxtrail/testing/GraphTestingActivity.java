package com.voxtrail.voxtrail.testing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.report.MileageReportPOJO;
import com.voxtrail.voxtrail.pojo.report.MileageReportResPOJO;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphTestingActivity extends AppCompatActivity {
    private final LineChart[] charts = new LineChart[4];
    List<Integer> lineColors = new ArrayList<>();

    @BindView(R.id.chart_distance)
    LineChart chart_distance;
    @BindView(R.id.chart_fuel_consumption)
    LineChart chart_fuel_consumption;
    @BindView(R.id.chart_fuel_cost)
    LineChart chart_fuel_cost;
    @BindView(R.id.chart_engine_hour)
    LineChart chart_engine_hour;
    @BindView(R.id.tv_min_distance)
    TextView tv_min_distance;
    @BindView(R.id.tv_max_distance)
    TextView tv_max_distance;
    @BindView(R.id.tv_min_fuel_consumption)
    TextView tv_min_fuel_consumption;
    @BindView(R.id.tv_max_fuel_conumption)
    TextView tv_max_fuel_conumption;
    @BindView(R.id.tv_min_fuel_cost)
    TextView tv_min_fuel_cost;
    @BindView(R.id.tv_max_fuel_cost)
    TextView tv_max_fuel_cost;
    @BindView(R.id.tv_min_engine_hours)
    TextView tv_min_engine_hours;
    @BindView(R.id.tv_max_engine_hour)
    TextView tv_max_engine_hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_testing);
        ButterKnife.bind(this);

        lineColors.add(Color.rgb(137, 230, 81));
        lineColors.add(Color.rgb(240, 240, 30));
        lineColors.add(Color.rgb(89, 199, 250));
        lineColors.add(Color.rgb(250, 104, 104));

        getWeekDates();
        getMileageReport();
    }

    public void getMileageReport() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("dtt", UtilityFunction.convertHistoryserverDateTime(dateList.get(0) + " 00:00:00")));
        nameValuePairs.add(new BasicNameValuePair("dtf", UtilityFunction.convertHistoryserverDateTime(dateList.get(dateList.size() - 1) + " 00:00:00")));
        nameValuePairs.add(new BasicNameValuePair("imei", "860016020767146"));
        nameValuePairs.add(new BasicNameValuePair("user_id", "1"));
        new WebServiceBaseResponse<MileageReportResPOJO>(nameValuePairs, this, new ResponseCallBack<MileageReportResPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<MileageReportResPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        List<String> routeLength = new ArrayList<>();
                        for (MileageReportPOJO mileageReportPOJO : responsePOJO.getResult().getMileageReportPOJOS()) {
                            routeLength.add(mileageReportPOJO.getRouteLength());
                        }
                        setUpChart(chart_distance, routeLength,tv_min_distance,tv_max_distance);
                        List<String> fuelConsumption = new ArrayList<>();
                        for (MileageReportPOJO mileageReportPOJO : responsePOJO.getResult().getMileageReportPOJOS()) {
                            fuelConsumption.add(mileageReportPOJO.getFuelConsumption());
                        }
                        setUpChart(chart_fuel_consumption, fuelConsumption,tv_min_fuel_consumption,tv_max_fuel_conumption);
                        List<String> fuel_cost_list = new ArrayList<>();
                        for (MileageReportPOJO mileageReportPOJO : responsePOJO.getResult().getMileageReportPOJOS()) {
                            fuel_cost_list.add(mileageReportPOJO.getFuelCost());
                        }
                        setUpChart(chart_fuel_cost, fuel_cost_list,tv_min_fuel_cost,tv_max_fuel_cost);
                        List<String> engine_hour_list = new ArrayList<>();
                        for (MileageReportPOJO mileageReportPOJO : responsePOJO.getResult().getMileageReportPOJOS()) {
                            engine_hour_list.add(mileageReportPOJO.getEngineHours());
                        }
                        setUpChart(chart_engine_hour, engine_hour_list,tv_min_engine_hours,tv_max_engine_hour);
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responsePOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, MileageReportResPOJO.class, "GET_MILEAGE_REPORT", true).execute(WebServicesUrls.MILEAGE_REPORT_API);
    }

    public List<String> getMaxMin(List<String> rawData) {
        double max = 0;
        double min = 0;
        for (int i = 0; i < rawData.size(); i++) {
            try {
                double val = Double.parseDouble(rawData.get(i));
                if (i == 0) {
                    max = val;
                    min = val;
                } else {
                    if (val > max) {
                        max = val;
                    }
                    if (val < min) {
                        min = val;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<String> returnList = new ArrayList<>();
        returnList.add(String.valueOf(min));
        returnList.add(String.valueOf(max));
        return returnList;
    }

    List<String> dateList = new ArrayList<>();

    public void getWeekDates() {

        dateList.clear();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        Log.d(TagUtils.getTag(), "today:-" + dateFormat.format(cal.getTime()));
        dateList.add(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        Log.d(TagUtils.getTag(), "today -1:-" + dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        dateList.add(dateFormat.format(cal.getTime()));
        Log.d(TagUtils.getTag(), "today -1:-" + dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        dateList.add(dateFormat.format(cal.getTime()));
        Log.d(TagUtils.getTag(), "today -1:-" + dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        dateList.add(dateFormat.format(cal.getTime()));
        Log.d(TagUtils.getTag(), "today -1:-" + dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        dateList.add(dateFormat.format(cal.getTime()));
        Log.d(TagUtils.getTag(), "today -1:-" + dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        dateList.add(dateFormat.format(cal.getTime()));
        Log.d(TagUtils.getTag(), "today -1:-" + dateFormat.format(cal.getTime()));

    }

    private final int[] colors = new int[]{
            Color.rgb(137, 230, 81),
            Color.rgb(240, 240, 30),
            Color.rgb(89, 199, 250),
            Color.rgb(250, 104, 104)
    };

    private void setUpChart(LineChart chart, List<String> rawData,TextView tv_min,TextView tv_max) {

        LineData data = getLineData(rawData);

        ((LineDataSet) data.getDataSetByIndex(0)).setCircleColorHole(Color.parseColor("#1e90ff"));
        ((LineDataSet) data.getDataSetByIndex(0)).setCircleColorHole(Color.parseColor("#1e90ff"));
        ((LineDataSet) data.getDataSetByIndex(0)).setColor(Color.parseColor("#1e90ff"));

        chart.getDescription().setEnabled(false);

        chart.setDrawGridBackground(false);

        chart.setTouchEnabled(true);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        chart.setPinchZoom(false);

        chart.setXAxisRenderer(new ColoredLabelXAxisRenderer(chart.getViewPortHandler(), chart.getXAxis(), chart.getTransformer(YAxis.AxisDependency.LEFT), lineColors));

        chart.setData(data);

        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setEnabled(false);

        chart.animateX(2500);


        List<String> minMaxList=getMaxMin(rawData);
        tv_min.setText(minMaxList.get(0));
        tv_max.setText(minMaxList.get(1));
    }

    private LineData getLineData(List<String> rawData) {
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < rawData.size(); i++) {
            try {
                values.add(new Entry(i, Float.parseFloat(rawData.get(i))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LineDataSet set1 = new LineDataSet(values, "DataSet 1");

        set1.setLineWidth(1.75f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);

        // create a data object with the data sets
        return new LineData(set1);
    }
}
