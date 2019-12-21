package com.voxtrail.voxtrail.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.ActivityManager;
import com.voxtrail.voxtrail.pojo.user.UserDetail;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends ActivityManager {

    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_signup)
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_user_name.getText().toString().length()>0
                        &&et_email.getText().toString().length()>0
                        &&et_password.getText().toString().length()>0){
                    registerUser();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please Enter Fields Properly");
                }
            }
        });
    }

    public void registerUser(){
        showProgressBar();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("username", et_user_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("email", et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", et_password.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("device_token", Pref.GetDeviceToken(getApplicationContext(),"")));
        nameValuePairs.add(new BasicNameValuePair("device_type", "0"));
        new WebServiceBase(nameValuePairs,null, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                dismissProgressBar();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equals("1")){
                        JSONObject resultObject=jsonObject.optJSONObject("result");
                        Pref.SetStringPref(getApplicationContext(), StringUtils.USER_DETAILS,resultObject.toString());
                        Pref.SetBooleanPref(getApplicationContext(),StringUtils.IS_LOGIN,true);
                        Constants.userDetail=new Gson().fromJson(resultObject.toString(), UserDetail.class);
                        startActivity(new Intent(SignupActivity.this,HomeActivity.class));
                        finishAffinity();

                    }
                    ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "REGISTER_USER", false).execute(WebServicesUrls.SIGNUP_USER);
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
