package com.voxtrail.voxtrail.fragment.driver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.device.DriverPOJO;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;

public class DriverEditFragment extends FragmentController {

    @BindView(R.id.et_drive_name)
    EditText et_drive_name;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_description)
    EditText et_description;
    @BindView(R.id.tv_update)
    TextView tv_update;
    @BindView(R.id.ic_back)
    ImageView ic_back;

    DriverPOJO driverPOJO;

    public DriverEditFragment(DriverPOJO driverPOJO){
        this.driverPOJO=driverPOJO;
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_driver_detail, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDriver(driverPOJO);

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void callAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("name",et_drive_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("address",et_address.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("phone",et_phone.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("email",et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("description",et_description.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("driver_id",driverPOJO.getDriverId()));

        new WebServiceBaseResponse<DriverPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<DriverPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<DriverPOJO> responsePOJO) {
                try{
                    if(responsePOJO.isSuccess()) {
                        ToastClass.showShortToast(getActivity().getApplicationContext(),responsePOJO.getMessage());
                        setDriver(responsePOJO.getResult());
                    }else{
                        ToastClass.showShortToast(getActivity().getApplicationContext(),responsePOJO.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },DriverPOJO.class,"UPDATE_DEVICE",true).execute(WebServicesUrls.UPDATE_DRIVER);
    }

    public void setDriver(DriverPOJO driverPOJO){
        if(driverPOJO!=null){
            et_description.setText(driverPOJO.getDriverDesc());
            et_email.setText(driverPOJO.getDriverEmail());
            et_phone.setText(driverPOJO.getDriverPhone());
            et_address.setText(driverPOJO.getDriverAddress());
            et_drive_name.setText(driverPOJO.getDriverName());
        }
    }

}
