package com.voxtrail.voxtrail.testing;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.util.TagUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestingActivity2 extends AppCompatActivity {
    protected HorizontalBarChart mChart;

    @BindView(R.id.web_view)
    WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing2);
        ButterKnife.bind(this);

//        web_view.loadData(htmlData, "text/html; charset=UTF-8", null);
        web_view.getSettings().setJavaScriptEnabled(true); // enable javascript


        web_view.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
                Log.d(TagUtils.getTag(),"error description:-"+description);
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        web_view .loadUrl("http://platform.voxtrail.com/report/polarchart.php?total_vehicles=100&parked_vehicles=50&offline_vehicles=20&running_vehicles=30");
//        web_view.loadData(returnHtmlData(), "text/html; charset=UTF-8", null);
    }
    public String returnHtmlData(){
        String htmlData = "<html>\n" +
                "<head>\n" +
                "\t<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.min.js\"></script>\n" +
                "\t<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
                "\t<!-- <script src=\"Chart.js\"></script> -->\n" +
                "\t<!-- <script src=\"check.js\"></script> -->\n" +
                "\n" +
                "</head>\n" +
                "<style type=\"text/css\">\n" +
                ".container {\n" +
                "\twidth: 80%;\n" +
                "\tmargin: 15px auto;\n" +
                "}\n" +
                "</style>\n" +
                "<script type=\"text/javascript\">\n" +
                "\n" +
                "\t(function(window, document, $, undefined) {\n" +
                "\t\t\"use strict\";\n" +
                "\t\t$(function() {\n" +
                "\n" +
                "\t\t\tif ($('#myChart').length) {\n" +
                "\t\t\t\tvar ctx = document.getElementById(\"myChart\").getContext('2d');\n" +
                "\t\t\t\tvar myChart = new Chart(ctx, {\n" +
                "\t\t\t\t\ttype: 'polarArea',\n" +
                "\t\t\t\t\tdata: {\n" +
                "\t\t\t\t\t\tlabels: [\"Total\", \"Running\", \"Parked\", \"Offline\"],\n" +
                "\t\t\t\t\t\tdatasets: [{\n" +
                "\t\t\t\t\t\t\tbackgroundColor: [\n" +
                "\t\t\t\t\t\t\t\"#5867C3\",\n" +
                "\t\t\t\t\t\t\t\"#1C86BF\",\n" +
                "\t\t\t\t\t\t\t\"#28BEBD\",\n" +
                "\t\t\t\t\t\t\t\"#FEB38D\"\n" +
                "\t\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\tdata: [12, 19, 3, 17]\n" +
                "\t\t\t\t\t\t}]\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t});\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t});\n" +
                "\n" +
                "\t})(window, document, window.jQuery);\n" +
                "\n" +
                "\n" +
                "</script>\n" +
                "<body>\n" +
                "\t<!-- <div class=\"container\">\n" +
                "\t  <div style=\"width: 400px;height: 400px;\">\n" +
                "\t    <canvas id=\"myChart\"></canvas>\n" +
                "\t  </div>\n" +
                "\t</div> -->\n" +
                "\n" +
                "\t\n" +
                "\t\t<canvas id=\"myChart\" width=\"334\" height=\"167\" style=\"display: block; width: 334px; height: 167px;\"></canvas>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n" +
                "\n";

        return htmlData;
    }

//
//        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
//        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
//
//        Polar polar = AnyChart.polar();
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new CustomDataEntry("Total", 15000));
//        data.add(new CustomDataEntry("Running", 12814));
//        data.add(new CustomDataEntry("Parked", 13012));
//        data.add(new CustomDataEntry("Offline", 11624));
//
//
//        Set set = Set.instantiate();
//        set.data(data);
//        Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
//        Mapping series2Data = set.mapAs("{ x: 'x', value: 'value' }");
//        Mapping series3Data = set.mapAs("{ x: 'x', value: 'value' }");
////
//        polar.column(series1Data);
//
//        polar.column(series2Data);
//
//        polar.column(series3Data);
//
//        polar.title("Total Vehicles");
//
//        polar.sortPointsByX(true)
//                .defaultSeriesType(PolarSeriesType.COLUMN)
//                .yAxis(false)
//                .xScale(ScaleTypes.ORDINAL);
//
//        polar.title().margin().bottom(20d);
//
//        ((Linear) polar.yScale(Linear.class)).stackMode(ScaleStackMode.VALUE);
//
////        polar.tooltip()
////                .valuePrefix("")
////                .displayMode(TooltipDisplayMode.UNION);
//
//        anyChartView.setChart(polar);
//
//        polar.tooltip().setOnClickListener(new ListenersInterface.OnClickListener() {
//            @Override
//            public void onClick(Event event) {
//                Log.d(TagUtils.getTag(),"polar clicked");
//            }
//        });
//
////        anyChartView.setFocusable(false);
////        anyChartView.setClickable(false);
//
//        polar.setOnClickListener(new ListenersInterface.OnClickListener() {
//            @Override
//            public void onClick(Event event) {
//                Log.d(TagUtils.getTag(),"polar clicked1");
//            }
//        });
//
//
//
//    }
//
//    private class CustomDataEntry extends ValueDataEntry {
//        CustomDataEntry(String x, Number value) {
//            super(x, value);
//        }
//    }
}