package com.voxtrail.voxtrail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.HomeActivity;
import com.voxtrail.voxtrail.activity.LoginActivity;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.pojo.ResponsePOJO;
import com.voxtrail.voxtrail.pojo.user.UserDetail;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.webservice.ResponseCallBack;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServiceBaseResponse;
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

public class ProfileEditFragment extends FragmentController {


    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_company)
    EditText et_company;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_postal_code)
    EditText et_postal_code;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.tv_update)
    TextView tv_update;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile_edit, container, false);
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

        setData();

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileManager();
            }
        });

        Picasso.get()
                .load(WebServicesUrls.IMAGE_BASE_URL+"/gps/uploads"+Constants.userDetail.getProfile_pic())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .into(cv_profile_pic);
    }

    public void openFileManager(){
        new ImagePicker.Builder(getActivity())
                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }

    public void updateProfile(){
        activityManager.showProgressBar();
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_name",et_user_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("email",et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("info",getUserData()));

        new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {

                activityManager.dismissProgressBar();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("1")) {
                        JSONObject resultObject = jsonObject.optJSONObject("result");
                        Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.USER_DETAILS, resultObject.toString());
                        Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.IS_LOGIN, true);
                        Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION, true);
                        Constants.userDetail = new Gson().fromJson(resultObject.toString(), UserDetail.class);
                        Pref.SetStringPref(getActivity().getApplicationContext(),StringUtils.MONITOR_REFRESH_RATE,StringUtils.REF_05_SEC);
                        Pref.SetStringPref(getActivity().getApplicationContext(),StringUtils.TRACKING_REFRESH_RATE,StringUtils.REF_30_SEC);
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                        getActivity().finishAffinity();
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "UPDATE_USER_PROFILE", false).execute(WebServicesUrls.UPDATE_PROFILE);

    }

    public String getUserData(){
        try{
            JSONObject jsonObject=new JSONObject(Constants.userDetail.getInfo());
            jsonObject.put("company",et_company.getText().toString());
            jsonObject.put("address",et_address.getText().toString());
            jsonObject.put("post_code",et_postal_code.getText().toString());
            jsonObject.put("city",et_city.getText().toString());
            jsonObject.put("country",et_country.getText().toString());

            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public void setData(){
        try {
            JSONObject jsonObject=new JSONObject(Constants.userDetail.getInfo());
            et_user_name.setText(Constants.userDetail.getUsername());
            et_email.setText(Constants.userDetail.getEmail());
            if(jsonObject.has("company")) {
                et_company.setText(jsonObject.optString("company"));
            }
            if(jsonObject.has("address")) {
                et_address.setText(jsonObject.optString("address"));
            }
            if(jsonObject.has("post_code")) {
                et_postal_code.setText(jsonObject.optString("post_code"));
            }
            if(jsonObject.has("city")) {
                et_city.setText(jsonObject.optString("city"));
            }
            if(jsonObject.has("country")) {
                et_country.setText(jsonObject.optString("country"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateProfilePic(String file_path){
        if(new File(file_path).exists()) {
            try {
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("id", new StringBody(Constants.userDetail.getId()));
                if (new File(file_path).exists()) {
                    reqEntity.addPart("profile_pic", new FileBody(new File(file_path)));
                } else {
                    reqEntity.addPart("profile_pic", new StringBody(""));
                }

                activityManager.showProgressBar();
                new WebUploadService(reqEntity, getActivity(),null, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        activityManager.dismissProgressBar();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("status").equals("1")) {
                                JSONObject resultObject = jsonObject.optJSONObject("result");
                                Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.USER_DETAILS, resultObject.toString());
                                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.IS_LOGIN, true);
                                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION, true);
                                Constants.userDetail = new Gson().fromJson(resultObject.toString(), UserDetail.class);
                                Pref.SetStringPref(getActivity().getApplicationContext(),StringUtils.MONITOR_REFRESH_RATE,StringUtils.REF_05_SEC);
                                Pref.SetStringPref(getActivity().getApplicationContext(),StringUtils.TRACKING_REFRESH_RATE,StringUtils.REF_30_SEC);
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                                getActivity().finishAffinity();
                            }
                            ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "UPDATE PROFILE PIC", false).execute(WebServicesUrls.UPLOAD_PROFILE_PIC);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            //Your Code
            if(mPaths.size()>0){
                updateProfilePic(mPaths.get(0));
                Picasso.get()
                        .load(mPaths.get(0))
                        .into(cv_profile_pic);
            }
        }
    }
}
