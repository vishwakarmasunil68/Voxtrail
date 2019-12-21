package com.voxtrail.voxtrail.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.SplashActivity;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.TagUtils;

import org.json.JSONObject;

/**
 * Created by sunil on 18-08-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            String notification = remoteMessage.getData().toString();
            String success = remoteMessage.getData().get("success");
            String result = remoteMessage.getData().get("result");
            String title = remoteMessage.getData().get("title");
            String description = remoteMessage.getData().get("description");
            String type = remoteMessage.getData().get("type");

            Log.d(TagUtils.getTag(), "notification:-" + notification);
            Log.d(TagUtils.getTag(), "success:-" + success);
            Log.d(TagUtils.getTag(), "result:-" + result);
            Log.d(TagUtils.getTag(), "type:-" + type);
            if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
                checkType(type, result);
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            try {
                Log.d(TAG, "From: " + remoteMessage.getFrom());
                Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
            }
        }
//        }
    }


    public void checkType(String type, String result) {
        try {
            Log.d(TagUtils.getTag(), "type:-" + type);
            Log.d(TagUtils.getTag(), "result:-" + result);

            if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
                if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION, false)) {
                    if (type.equalsIgnoreCase("alert")) {
                        sendAlertNotification(type, result);
                    } else {
                        sendPostNotification(type, result);
                    }
                }
                Pref.SetIntPref(getApplicationContext(),StringUtils.NOTIFICATION_COUNT,(Pref.GetIntPref(getApplicationContext(),StringUtils.NOTIFICATION_COUNT,0)+1));
                updateAlert(getApplicationContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendPostNotification(String type, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);

            Log.d(TagUtils.getTag(), "notification message:-" + jsonObject.optString("msg"));
            Log.d(TagUtils.getTag(), "notification type:-" + type);

            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("type", type);
            intent.putExtra("data", data);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationId = 1;
            String channelId = "channel-01";
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(type)
                    .setContentText(jsonObject.optString("msg"));

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAlertNotification(String type, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);

            Log.d(TagUtils.getTag(), "notification message:-" + jsonObject.optString("msg"));
            Log.d(TagUtils.getTag(), "notification type:-" + type);

            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("type", jsonObject.optString("name") + " " + type);
//            intent.putExtra("data", jsonObject.optString("event_desc"));
            intent.putExtra("data", data);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationId = 1;
            String channelId = "channel-01";
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(jsonObject.optJSONObject("data").optString("name") + " " + type)
                    .setContentText(jsonObject.optJSONObject("data").optString("event_desc"));

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAlert(Context context) {
        Intent intent = new Intent(StringUtils.UPDATE_ALERT);
        //send broadcast
        context.sendBroadcast(intent);
    }


}