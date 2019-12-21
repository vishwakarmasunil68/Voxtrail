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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.squareup.picasso.Picasso;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.fragment.sharing.SelectObjectForSharingFragment;
import com.voxtrail.voxtrail.fragmentcontroller.FragmentController;
import com.voxtrail.voxtrail.util.Constants;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import java.net.URLEncoder;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends FragmentController {

    @BindView(R.id.iv_facebook)
    ImageView iv_facebook;
    @BindView(R.id.iv_twitter)
    ImageView iv_twitter;
    @BindView(R.id.iv_youtube)
    ImageView iv_youtube;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.ll_object)
    LinearLayout ll_object;
    @BindView(R.id.ll_share_object)
    LinearLayout ll_share_object;
    @BindView(R.id.ll_whatsapp)
    LinearLayout ll_whatsapp;
    @BindView(R.id.ll_app_share)
    LinearLayout ll_app_share;
    @BindView(R.id.ll_call)
    LinearLayout ll_call;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.tv_change_password)
    TextView tv_change_password;
    @BindView(R.id.iv_profile_edit)
    ImageView iv_profile_edit;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_user_name.setText(Constants.userDetail.getUsername());

        iv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    startActivity(getOpenFacebookIntent());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
            }
        });

        iv_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openTwitterPage();
            }
        });

        iv_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openYoutubepage("UCgmNhXApt1d8uG_Dm_tPeqg");
            }
        });

        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new SettingFragment());
            }
        });

        ll_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWhatsApp();
            }
        });

        ll_app_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareAPP();
            }
        });

        ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Permissions.check(getActivity()/*context*/, Manifest.permission.CALL_PHONE, null, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        // do your task.
                        callToVoxtrail();
                    }
                });
            }
        });

        ll_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new ObjectListFragment());
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new ChangePasswordFragment());
            }
        });
        ll_share_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new SelectObjectForSharingFragment());
            }
        });

        iv_profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new ProfileEditFragment());
            }
        });

        Picasso.get()
                .load(WebServicesUrls.IMAGE_BASE_URL+"/gps/uploads/"+Constants.userDetail.getProfile_pic())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .into(cv_profile_pic);
    }

    public void sendWhatsApp(){
        try{
            PackageManager packageManager = getActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);

            try {
                String url = "https://api.whatsapp.com/send?phone=7009549511&text=" + URLEncoder.encode("", "UTF-8");
                i.setPackage("com.whatsapp");
                i.setData(Uri.parse(url));
                if (i.resolveActivity(packageManager) != null) {
                    startActivity(i);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callToVoxtrail() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "+91-1244072192"));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareAPP() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Voxtrail");
            String sAux = "\nwe secure your each step\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.voxtrail.voxtrail \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public Intent getOpenFacebookIntent() {

        try {
            getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1719364511634251"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/1719364511634251"));
        }
    }

    public void openTwitterPage() {
        Intent intent = null;
//        try {
//            // get the Twitter app if possible
//            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=voxtrail"));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/voxtrail"));
//        }
        this.startActivity(intent);
    }

    public void openYoutubepage(String youtube_id) {
        try {
            String urlStr = "http://www.youtube.com/user/channel/UCgmNhXApt1d8uG_Dm_tPeqg";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(urlStr));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
