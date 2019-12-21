package com.voxtrail.voxtrail.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.pojo.user.UserDetail;
import com.voxtrail.voxtrail.testing.GraphTestingActivity;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.TagUtils;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_splash);

        checkfordeeplink();
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d(TagUtils.getTag(), "on new Intent");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String type = extras.getString("type");
            String data = extras.getString("data");
            Log.d(TagUtils.getTag(), "type:-" + type);
            Log.d(TagUtils.getTag(), "data:-" + data);

            Constants.userDetail = new Gson().fromJson(Pref.GetStringPref(getApplicationContext(), StringUtils.USER_DETAILS, ""), UserDetail.class);
            Intent intent1 = new Intent(SplashActivity.this, HomeActivity.class);
            intent1.putExtra("type", type);
            intent1.putExtra("data", data);
            startActivity(intent1);
            finishAffinity();
        } else {
            Constants.userDetail = new Gson().fromJson(Pref.GetStringPref(getApplicationContext(), StringUtils.USER_DETAILS, ""), UserDetail.class);
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//            startActivity(new Intent(SplashActivity.this, GraphTestingActivity.class));
            finishAffinity();
        }
    }

    public void checkfordeeplink() {
        try {
            Intent in = getIntent();
            Uri data = in.getData();

            Log.d(TagUtils.getTag(), "data to get:-" + data.toString());
            String url = data.toString();
            Log.d(TagUtils.getTag(), "url:-" + url);
            String unique_key = url.replace("http://www.voxtrail.com/share.php?token=", "");

            startActivity(new Intent(SplashActivity.this, DeepLinkingObjectList.class).putExtra("unique_key", unique_key));
            finishAffinity();

        } catch (Exception e) {
            Log.d(TagUtils.getTag(), "No Deep Linking:-" + e.toString());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TagUtils.getTag(), "device_token:-" + Pref.GetDeviceToken(getApplicationContext(), ""));
                    Log.d(TagUtils.getTag(), "login:-" + Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false));
                    if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
                        onNewIntent(getIntent());
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 2000);
        }
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
