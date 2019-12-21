package com.voxtrail.voxtrail.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.adapter.DeviceListAdapter;
import com.voxtrail.voxtrail.adapter.HelpAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.ActivityManager;
import com.voxtrail.voxtrail.pojo.HelpPOJO;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.dashboard.DashBoardPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.ResponseListCallback;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponseList;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends ActivityManager {

    @BindView(R.id.rv_help)
    RecyclerView rv_help;
    @BindView(R.id.ic_back)
    ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        attachAdapter();
        getHelp();
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void getHelp(){
        showProgressBar();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        new WebServiceBaseResponseList<HelpPOJO>(nameValuePairs, this, new ResponseListCallback<HelpPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<HelpPOJO> responseListPOJO) {
                dismissProgressBar();
                try {
                    if (responseListPOJO.isSuccess()) {

                        if (devicePOJOS.size() == 0) {
                            devicePOJOS.addAll(responseListPOJO.getResultList());
                            helpAdapter.notifyDataSetChanged();
                        }
                    }else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, HelpPOJO.class, "HELP", false).execute(WebServicesUrls.HELP);
    }


    public HelpAdapter helpAdapter;
    public List<HelpPOJO> devicePOJOS = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_help.setHasFixedSize(true);
        rv_help.setLayoutManager(linearLayoutManager);
        helpAdapter = new HelpAdapter(this, null, devicePOJOS);
        rv_help.setAdapter(helpAdapter);
        rv_help.setNestedScrollingEnabled(false);
        rv_help.setItemAnimator(new DefaultItemAnimator());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.ic_header);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }
}
