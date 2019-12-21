package com.voxtrail.voxtrail.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragmentcontroller.ActivityManager;
import com.voxtrail.voxtrail.pojo.user.UserDetail;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.TagUtils;
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

public class LoginActivity extends ActivityManager {

    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_signup)
    TextView tv_signup;
    @BindView(R.id.ll_help)
    LinearLayout ll_help;
    @BindView(R.id.iv_password_visible)
    ImageView iv_password_visible;

    boolean password_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_user_name.getText().toString().length() > 0
                        && et_password.getText().toString().length() > 0) {
                    callLoginAPI();
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Fields Properly");
                }
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        ll_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HelpActivity.class));
            }
        });
        checkfordeeplink();

        iv_password_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password_visible) {
                    iv_password_visible.setImageResource(R.drawable.ic_pass_not_visible);
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password_visible = false;
                    et_password.setSelection(et_password.getText().length());
                } else {
                    iv_password_visible.setImageResource(R.drawable.ic_pass_visible);
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password_visible = true;
                    et_password.setSelection(et_password.getText().length());
                }
            }
        });
    }

    public void checkfordeeplink() {
        try {
            Intent in = getIntent();
            Uri data = in.getData();

            Log.d(TagUtils.getTag(), "data to get:-" + data.toString());
//            String url = "http://www.geetuttrakahand.com/album_id=133/image=singer_25_387893574.png";
            String url = data.toString();
            Log.d(TagUtils.getTag(), "url:-" + url);
            String unique_key = url.replace("http://www.voxtrail.com/", "");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callLoginAPI() {

        showProgressBar();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("username", et_user_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", et_password.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("device_token", Pref.GetDeviceToken(getApplicationContext(), "")));
        nameValuePairs.add(new BasicNameValuePair("device_type", "0"));
        new WebServiceBase(nameValuePairs, null, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                dismissProgressBar();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("1")) {
                        JSONObject resultObject = jsonObject.optJSONObject("result");
                        Pref.SetIntPref(getApplicationContext(), StringUtils.NOTIFICATION_COUNT, 0);
                        Pref.SetStringPref(getApplicationContext(), StringUtils.USER_DETAILS, resultObject.toString());
                        Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, true);
                        Pref.SetBooleanPref(getApplicationContext(), StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION, true);
                        Constants.userDetail = new Gson().fromJson(resultObject.toString(), UserDetail.class);
                        Pref.SetStringPref(getApplicationContext(), StringUtils.MONITOR_REFRESH_RATE, StringUtils.REF_30_SEC);
                        Pref.SetStringPref(getApplicationContext(), StringUtils.TRACKING_REFRESH_RATE, StringUtils.REF_05_SEC);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finishAffinity();
                    }
                    ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "LOGIN_API", false).execute(WebServicesUrls.LOGIN);
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
