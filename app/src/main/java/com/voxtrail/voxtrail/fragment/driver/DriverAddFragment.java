package com.voxtrail.voxtrail.fragment.driver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.device.DriverPOJO;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;

public class DriverAddFragment extends FragmentController {

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
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.ic_back)
    ImageView ic_back;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_driver_add, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAPI();
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
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));

        new WebServiceBaseResponse<DriverPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<DriverPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<DriverPOJO> responsePOJO) {
                try{
                    if(responsePOJO.isSuccess()) {
                        onBackPressed();
                        ToastClass.showShortToast(getActivity().getApplicationContext(),responsePOJO.getMessage());
                    }else{
                        ToastClass.showShortToast(getActivity().getApplicationContext(),responsePOJO.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },DriverPOJO.class,"UPDATE_DEVICE",true).execute(WebServicesUrls.ADD_DRIVER);
    }


}
