package com.voxtrail.voxtrail.adapter;

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
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DistanceMetricesFragment extends FragmentController {

    @BindView(R.id.rv_language)
    RecyclerView rv_language;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_distance_metrices, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }


    public static DistanceMetricesFragment newInstance() {
        Bundle args = new Bundle();
        DistanceMetricesFragment fragment = new DistanceMetricesFragment();
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

        deviceStrings.add(StringUtils.KILOMETER);
        deviceStrings.add(StringUtils.MILE);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_language.setHasFixedSize(true);
        rv_language.setLayoutManager(linearLayoutManager);
        languageSettingAdapter = new LanguageSettingAdapter(getActivity(), null, deviceStrings);
        rv_language.setAdapter(languageSettingAdapter);
        rv_language.setNestedScrollingEnabled(false);
        rv_language.setItemAnimator(new DefaultItemAnimator());

        languageSettingAdapter.setPreferenceString(StringUtils.DISTANCE_METRICES);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                languageSettingAdapter.setPreCheck(Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.DISTANCE_METRICES, ""));
            }
        }, 100);

    }
}
