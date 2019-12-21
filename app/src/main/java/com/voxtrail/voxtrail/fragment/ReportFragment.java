package com.voxtrail.voxtrail.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.DeviceSelectActivity;
import com.voxtrail.voxtrail.activity.PlayBackActivity;
import com.voxtrail.voxtrail.fragment.report.DriveStopReportFragment;
import com.voxtrail.voxtrail.fragment.report.DriverScoreFragment;
import com.voxtrail.voxtrail.fragment.report.GeneralInformationReportFragment;
import com.voxtrail.voxtrail.fragment.report.OverSpeedReportFragment;
import com.voxtrail.voxtrail.fragment.report.UnderSpeedFragment;
import com.voxtrail.voxtrail.fragment.report.ZoneInOutReportFragment;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;

import butterknife.BindView;

public class ReportFragment extends FragmentController {

    @BindView(R.id.cv_vehicle_status)
    CardView cv_vehicle_status;
    @BindView(R.id.cv_playback)
    CardView cv_playback;
    @BindView(R.id.cv_general_information)
    CardView cv_general_information;
    @BindView(R.id.cv_zone_in_out)
    CardView cv_zone_in_out;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.cv_drive_stop)
    CardView cv_drive_stop;
    @BindView(R.id.cv_overspeed)
    CardView cv_overspeed;
    @BindView(R.id.cv_underspeed)
    CardView cv_underspeed;
    @BindView(R.id.cv_driver_score)
    CardView cv_driver_score;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_report, container, false);
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

        cv_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), DeviceSelectActivity.class), Constants.SELECT_DEVICE_FOR_HISTORY);
            }
        });

        cv_vehicle_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), DeviceSelectActivity.class), Constants.SELECT_DEVICE_FOR_REPORT);
            }
        });

        cv_general_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), DeviceSelectActivity.class), Constants.SELECT_DEVICE_FOR_GENERAL_REPORT);
            }
        });

        cv_zone_in_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), DeviceSelectActivity.class), Constants.SELECT_DEVICE_FOR_ZONE_INOUT_REPORT);
            }
        });

        cv_drive_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), DeviceSelectActivity.class), Constants.SELECT_DEVICE_FOR_DRIVE_STOP);
            }
        });

        cv_overspeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), DeviceSelectActivity.class), Constants.SELECT_DEVICE_FOR_OVERSPEED);
            }
        });

        cv_underspeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), DeviceSelectActivity.class), Constants.SELECT_DEVICE_FOR_UNDERSPEED);
            }
        });

        cv_driver_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), DeviceSelectActivity.class), Constants.SELECT_DEVICE_FOR_DRIVER_SCORE);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SELECT_DEVICE_FOR_HISTORY) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    Intent intent = new Intent(getActivity(), PlayBackActivity.class);
                    intent.putExtra("devicePOJO", devicePOJO);
                    startActivity(intent);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == Constants.SELECT_DEVICE_FOR_REPORT) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    activityManager.startFragment(R.id.frame_home, new VehicleStatusReportFragment(devicePOJO));
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == Constants.SELECT_DEVICE_FOR_GENERAL_REPORT) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    activityManager.startFragment(R.id.frame_home, new GeneralInformationReportFragment(devicePOJO));
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == Constants.SELECT_DEVICE_FOR_ZONE_INOUT_REPORT) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    activityManager.startFragment(R.id.frame_home, new ZoneInOutReportFragment(devicePOJO));
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == Constants.SELECT_DEVICE_FOR_DRIVE_STOP) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    activityManager.startFragment(R.id.frame_home, new DriveStopReportFragment(devicePOJO));
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == Constants.SELECT_DEVICE_FOR_OVERSPEED) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    activityManager.startFragment(R.id.frame_home, new OverSpeedReportFragment(devicePOJO));
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == Constants.SELECT_DEVICE_FOR_UNDERSPEED) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    activityManager.startFragment(R.id.frame_home, new UnderSpeedFragment(devicePOJO));
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == Constants.SELECT_DEVICE_FOR_DRIVER_SCORE) {
            if (resultCode == Activity.RESULT_OK) {
                DevicePOJO devicePOJO = (DevicePOJO) data.getSerializableExtra("devicePOJO");
                if (devicePOJO != null) {
                    activityManager.startFragment(R.id.frame_home, new DriverScoreFragment(devicePOJO));
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
