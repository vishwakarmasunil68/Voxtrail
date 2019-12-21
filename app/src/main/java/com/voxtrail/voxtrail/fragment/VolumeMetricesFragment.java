package com.voxtrail.voxtrail.fragment;

import android.os.Bundle;
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
import com.voxtrail.voxtrail.adapter.LanguageSettingAdapter;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VolumeMetricesFragment extends FragmentController {

    @BindView(R.id.rv_language)
    RecyclerView rv_language;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_volume_metrices, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }


    public static VolumeMetricesFragment newInstance() {
        Bundle args = new Bundle();
        VolumeMetricesFragment fragment = new VolumeMetricesFragment();
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

        deviceStrings.add("Litres");
        deviceStrings.add("Gallons (US)");
        deviceStrings.add("Gallons (UK)");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_language.setHasFixedSize(true);
        rv_language.setLayoutManager(linearLayoutManager);
        languageSettingAdapter = new LanguageSettingAdapter(getActivity(), null, deviceStrings);
        rv_language.setAdapter(languageSettingAdapter);
        rv_language.setNestedScrollingEnabled(false);
        rv_language.setItemAnimator(new DefaultItemAnimator());
    }
}
