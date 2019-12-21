package com.voxtrail.voxtrail.fragment.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentContants;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.ResponseListPOJO;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.device.DriverPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.ResponseListCallback;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponseList;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;
import com.voxtrail.voxtrail.webservice.WebUploadService;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class DeviceEditFragment extends FragmentController {

    @BindView(R.id.et_device_name)
    EditText et_device_name;
    @BindView(R.id.et_imei)
    EditText et_imei;
    @BindView(R.id.et_vin)
    EditText et_vin;
    @BindView(R.id.et_plate_number)
    EditText et_plate_number;
    @BindView(R.id.et_sim_card)
    EditText et_sim_card;
    @BindView(R.id.et_odometer)
    EditText et_odometer;
    @BindView(R.id.spinner_driver)
    Spinner spinner_driver;
    @BindView(R.id.tv_update)
    TextView tv_update;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.et_gps_device)
    EditText et_gps_device;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.spinner_vehicle_type)
    Spinner spinner_vehicle_type;

    DevicePOJO devicePOJO;

    public DeviceEditFragment(DevicePOJO devicePOJO) {
        this.devicePOJO = devicePOJO;
    }

    List<String> vehicleTypeList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_device_edit,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vehicleTypeList.add("Select");
        vehicleTypeList.add("Bike");
        vehicleTypeList.add("Car");
        vehicleTypeList.add("Truck");
        vehicleTypeList.add("Bus");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, vehicleTypeList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_vehicle_type.setAdapter(spinnerArrayAdapter);

        getDrivers();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_device_name.getText().toString().length() > 0) {
                    if (spinner_vehicle_type.getSelectedItemPosition() != 0) {
                        updateDevice();
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Select Vehicle type");
                    }
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Enter Device Name");
                }
            }
        });

        spinner_driver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedDriverPOJO = driverPOJOS.get(position - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileManager();
            }
        });


        if ((WebServicesUrls.IMAGE_BASE_URL + devicePOJO.getDeviceDetailPOJO().getIcon()).contains(".svg")) {
            String url = (WebServicesUrls.IMAGE_BASE_URL + devicePOJO.getDeviceDetailPOJO().getIcon()).replace(".svg", ".png");
            Log.d(TagUtils.getTag(), "icon web url:-" + url);
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_car)
                    .error(R.drawable.ic_car)
                    .into(cv_profile_pic);
        } else {
            String icon_url = WebServicesUrls.IMAGE_BASE_URL + devicePOJO.getDeviceDetailPOJO().getIcon();
            Log.d(TagUtils.getTag(), "icon url:-" + icon_url);
            Glide.with(getActivity().getApplicationContext())
                    .load(icon_url)
                    .placeholder(R.drawable.ic_car)
                    .error(R.drawable.ic_car)
                    .dontAnimate()
                    .into(cv_profile_pic);
        }


    }

    public void openFileManager() {
        new ImagePicker.Builder(getActivity())
                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("devicePOJO", devicePOJO);
        activityManager.popBackResultFragment(startingFragment, 101, FragmentContants.RESULT_OK, bundle);
    }

    public void updateDevice() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("name", et_device_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("vin", et_vin.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("plate_number", et_plate_number.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("sim_number", et_sim_card.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("odometer", et_odometer.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("device", et_gps_device.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("object_type", String.valueOf(spinner_vehicle_type.getSelectedItemPosition())));
        if (selectedDriverPOJO != null) {
            nameValuePairs.add(new BasicNameValuePair("driver_id", selectedDriverPOJO.getDriverId()));
        } else {
            nameValuePairs.add(new BasicNameValuePair("driver_id", ""));
        }
        nameValuePairs.add(new BasicNameValuePair("imei", devicePOJO.getImei()));
        nameValuePairs.add(new BasicNameValuePair("object_id", devicePOJO.getObjectId()));

        new WebServiceBaseResponse<DevicePOJO>(nameValuePairs, getActivity(), new ResponseCallBack<DevicePOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<DevicePOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Object Updated");
                        setDeviceData(responsePOJO.getResult());
                        updateDeviceList(responsePOJO.getResult());
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, DevicePOJO.class, "UPDATE_DEVICE", true).execute(WebServicesUrls.UPDATE_OBJECTS);
    }


    public void updateDeviceList(DevicePOJO updatedDevicePOJO) {
        if (updatedDevicePOJO != null) {
            for (DevicePOJO devicePOJO : Constants.devicePOJOS) {
                if (devicePOJO.getImei().equals(updatedDevicePOJO.getImei())) {
                    devicePOJO.getDeviceDetailPOJO().setName(updatedDevicePOJO.getDeviceDetailPOJO().getName());
                    devicePOJO.getDeviceDetailPOJO().setVin(updatedDevicePOJO.getDeviceDetailPOJO().getVin());
                    devicePOJO.getDeviceDetailPOJO().setPlateNumber(updatedDevicePOJO.getDeviceDetailPOJO().getPlateNumber());
                    devicePOJO.getDeviceDetailPOJO().setSimNumber(updatedDevicePOJO.getDeviceDetailPOJO().getSimNumber());
                    devicePOJO.getDeviceDetailPOJO().setOdometer(updatedDevicePOJO.getDeviceDetailPOJO().getOdometer());
                    devicePOJO.getDeviceDetailPOJO().setDevice(updatedDevicePOJO.getDeviceDetailPOJO().getDevice());
                }
            }
        }
    }


    List<DriverPOJO> driverPOJOS = new ArrayList<>();

    public void getDrivers() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("imei", devicePOJO.getImei()));

        new WebServiceBaseResponseList<DriverPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<DriverPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<DriverPOJO> responseListPOJO) {
                try {
                    driverPOJOS.clear();
                    driverPOJOS.addAll(responseListPOJO.getResultList());

                    List<String> driverString = new ArrayList<>();
                    driverString.add("Select Driver");
                    for (DriverPOJO driverPOJO : driverPOJOS) {
                        driverString.add(driverPOJO.getDriverName());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, driverString); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_driver.setAdapter(spinnerArrayAdapter);
                    callAPI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, DriverPOJO.class, "GET_DEVICE_DATA", true).execute(WebServicesUrls.GET_DRIVERS);
    }

    DriverPOJO selectedDriverPOJO;

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("imei", devicePOJO.getImei()));

        new WebServiceBaseResponse<DevicePOJO>(nameValuePairs, getActivity(), new ResponseCallBack<DevicePOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<DevicePOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        setDeviceData(responsePOJO.getResult());
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, DevicePOJO.class, "GET_DEVICE_DATA", true).execute(WebServicesUrls.GET_OBJECT_WITH_DRIVER);
    }

    public void setDeviceData(DevicePOJO devicePOJO) {
        this.devicePOJO = devicePOJO;
        et_device_name.setText(devicePOJO.getDeviceDetailPOJO().getName());
        try {
            String imei = devicePOJO.getDeviceDetailPOJO().getImei();
//            Log.d(TagUtils.getTag(),"imei:-"+imei);
            String firstFourChars = imei.substring(0, 4);
            String lastFourChars = imei.substring(imei.length() - 4, imei.length());
            et_imei.setText(firstFourChars + lastFourChars);
        } catch (Exception e) {
            e.printStackTrace();
        }
        et_vin.setText(devicePOJO.getDeviceDetailPOJO().getVin());
        et_plate_number.setText(devicePOJO.getDeviceDetailPOJO().getPlateNumber());
        et_sim_card.setText(devicePOJO.getDeviceDetailPOJO().getSimNumber());
        et_odometer.setText(devicePOJO.getDeviceDetailPOJO().getOdometer());
        et_gps_device.setText(devicePOJO.getDeviceDetailPOJO().getDevice());
        selectedDriverPOJO = devicePOJO.getDriverPOJO();
        switch (devicePOJO.getDeviceDetailPOJO().getObject_type()) {
            case "1":
                spinner_vehicle_type.setSelection(1);
                break;
            case "2":
                spinner_vehicle_type.setSelection(2);
                break;
            case "3":
                spinner_vehicle_type.setSelection(3);
                break;
            case "4":
                spinner_vehicle_type.setSelection(4);
                break;
            default:
                spinner_vehicle_type.setSelection(0);
                break;
        }
        setDriverSpinner(devicePOJO.getDriverPOJO());
    }

    public void setDriverSpinner(DriverPOJO driverPOJO) {
        if (driverPOJO != null) {
            List<String> driverString = new ArrayList<>();
            int index = -1;
            for (int i = 0; i < driverPOJOS.size(); i++) {
                DriverPOJO driverPOJO1 = driverPOJOS.get(i);
                driverString.add(driverPOJO1.getDriverName());
                if (driverPOJO.getDriverId().equals(driverPOJO1.getDriverId())) {
                    index = i;
                }
            }
            if (index != -1) {
                spinner_driver.setSelection((index + 1));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            //Your Code
            if (mPaths.size() > 0) {
//                updateProfilePic(mPaths.get(0));
//                Picasso.get()
//                        .load(mPaths.get(0))
//                        .into(cv_profile_pic);
                Glide.with(getActivity().getApplicationContext())
                        .load(mPaths.get(0))
                        .placeholder(R.drawable.ic_default_profile_pic)
                        .error(R.drawable.ic_default_profile_pic)
                        .dontAnimate()
                        .into(cv_profile_pic);

                updateImage(mPaths.get(0));
            }
        }
    }

    public void updateImage(String image_path) {
        if (new File(image_path).exists()) {
            try {
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("imei", new StringBody(devicePOJO.getImei()));
                reqEntity.addPart("user_id", new StringBody(Constants.userDetail.getId()));
                reqEntity.addPart("image", new FileBody(new File(image_path)));

                activityManager.showProgressBar();
                new WebUploadService(reqEntity, getActivity(), null, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        activityManager.dismissProgressBar();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("status").equals("1")) {
                                JSONObject resultObject = jsonObject.optJSONObject("result");
                                DevicePOJO devicePOJO = new Gson().fromJson(resultObject.toString(), DevicePOJO.class);
                                DeviceEditFragment.this.devicePOJO = devicePOJO;
                                for (DevicePOJO devicePOJO1 : Constants.devicePOJOS) {
                                    if (devicePOJO.getImei().equals(devicePOJO1.getImei())) {
                                        devicePOJO1.getDeviceDetailPOJO().setIcon(devicePOJO.getDeviceDetailPOJO().getIcon());
                                    }
                                }
                            }
                            ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "UPDATE PROFILE PIC", false).execute(WebServicesUrls.UPDATE_OBJECT_IMAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

