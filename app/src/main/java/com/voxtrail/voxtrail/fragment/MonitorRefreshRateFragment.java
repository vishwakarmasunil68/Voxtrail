package com.voxtrail.voxtrail.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.HomeActivity;
import com.voxtrail.voxtrail.adapter.LanguageSettingAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MonitorRefreshRateFragment extends FragmentController {

    @BindView(R.id.rv_language)
    RecyclerView rv_language;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_monitor_refresh_rate, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }


    public static MonitorRefreshRateFragment newInstance() {
        Bundle args = new Bundle();
        MonitorRefreshRateFragment fragment = new MonitorRefreshRateFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        attachAdapter();

    }

    LanguageSettingAdapter languageSettingAdapter;
    List<String> deviceStrings = new ArrayList<>();

    public void attachAdapter() {

        deviceStrings.add(StringUtils.REF_DISABLE_AUTO_REFRESH);
        deviceStrings.add(StringUtils.REF_05_SEC);
        deviceStrings.add(StringUtils.REF_10_SEC);
        deviceStrings.add(StringUtils.REF_30_SEC);
        deviceStrings.add(StringUtils.REF_1_MIN);
        deviceStrings.add(StringUtils.REF_3_MIN);
        deviceStrings.add(StringUtils.REF_5_MIN);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_language.setHasFixedSize(true);
        rv_language.setLayoutManager(linearLayoutManager);
        languageSettingAdapter = new LanguageSettingAdapter(getActivity(), null, deviceStrings);
        rv_language.setAdapter(languageSettingAdapter);
        rv_language.setNestedScrollingEnabled(false);
        rv_language.setItemAnimator(new DefaultItemAnimator());

        languageSettingAdapter.setPreferenceString(StringUtils.MONITOR_REFRESH_RATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                languageSettingAdapter.setPreCheck(Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.MONITOR_REFRESH_RATE, ""));
            }
        }, 100);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
