package com.voxtrail.voxtrail.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;

public class ChangePasswordFragment extends FragmentController {


    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.et_old_password)
    EditText et_old_password;
    @BindView(R.id.et_new_password)
    EditText et_new_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    @BindView(R.id.btn_submit)
    Button btn_submit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_change_password, container, false);
        setUpView(getActivity(), this, view);
        return view;
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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_new_password.getText().toString().length()>0
                        &&et_confirm_password.getText().toString().length()>0
                        &&et_old_password.getText().toString().length()>0){
                    if(et_new_password.getText().toString().length()<6){
                        ToastClass.showShortToast(getActivity().getApplicationContext(),"Password length should not be less than 6 digit");
                    }else {
                        if(et_new_password.getText().toString().equals(et_confirm_password.getText().toString())) {
                            changePassword();
                        }else{
                            ToastClass.showShortToast(getActivity().getApplicationContext(),"Password and confirm password do not match");
                        }
                    }
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"Please enter all fields properly");
                }
            }
        });
    }

    public void changePassword(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("old_password",et_old_password.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password",et_new_password.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("id",Constants.userDetail.getId()));
        new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.optString("status").equals("1")){
                            ToastClass.showShortToast(getActivity().getApplicationContext(),"Password changed successfully");
                        }else{
                            ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Enter Correct Password");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"UPDATE_PASSWORD",true).execute(WebServicesUrls.UPDATE_PASSWORD);
    }

}
