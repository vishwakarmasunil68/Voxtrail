package com.voxtrail.voxtrail.fragment.report;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.util.ToastClass;

import butterknife.BindView;

public class ImageViewFragment extends FragmentController {


    @BindView(R.id.ic_back)
    ImageView iv_back;
    @BindView(R.id.iv_image)
    ImageView iv_image;

    String title;
    String image_url;

    public ImageViewFragment(String title, String image_url) {
        this.title = title;
        this.image_url = image_url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_image_view, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activityManager.showProgressBar();
        Picasso.get()
                .load(image_url)
                .into(iv_image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        activityManager.dismissProgressBar();
                    }

                    @Override
                    public void onError(Exception ex) {
                        activityManager.dismissProgressBar();
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
                    }
                });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}