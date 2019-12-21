package com.voxtrail.voxtrail.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.voxtrail.voxtrail.pojo.gm.ObjectHistoryPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class UtilityFunction {
    public static double[] getLocation(Context context) {
        GPSTracker gps;
        gps = new GPSTracker(context);
        double latitude = 0.00;
        double longitude = 0.00;

        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Log.d(TagUtils.getTag(), "location:-latitude:-" + latitude);
            Log.d(TagUtils.getTag(), "location:-longitude:-" + longitude);

            Pref.SetStringPref(context, StringUtils.CURRENT_LATITUDE, String.valueOf(latitude));
            Pref.SetStringPref(context, StringUtils.CURRENT_LONGITUDE, String.valueOf(longitude));
        } else {
//            gps.showSettingsAlert();
        }

        double[] loc = new double[]{latitude, longitude};
        return loc;
    }

    public static double convertStringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public static void printAllValues(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // do stuff
            Log.d(TagUtils.getTag(), key + " : " + value);
        }
    }


    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }

    public static String getMMddYYYY() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }


    public static String getCurrentDateTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }

    public static String getMMddyyyyDT() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }

    public static String converttoserverDateTime(String datetime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date givenDate = sdf.parse(datetime);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            return simpleDateFormat.format(givenDate);
        } catch (Exception e) {
            e.printStackTrace();
            return datetime;
        }
    }

    public static String convertDateTimeToAgo(String dt) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = inputFormat.parse(dt);
            String niceDateStr = String.valueOf(DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));
            return niceDateStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static String getServerTimeCurrentTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }

    public static String getServerTimeCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }

    public static String convertTrackerDTtoTime(String dt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date givenDate = sdf.parse(dt);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return simpleDateFormat.format(givenDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertToMMMDate(String dt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date givenDate = sdf.parse(dt);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd, MMM");
            return simpleDateFormat.format(givenDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertHistoryserverDateTime(String datetime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date givenDate = sdf.parse(datetime);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(givenDate);
        } catch (Exception e) {
            e.printStackTrace();
            return datetime;
        }
    }

    public static boolean checkValidDateTime(String datetime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            Date givenDate = sdf.parse(datetime);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setPreValuesChecked(Context context) {
        Pref.SetBooleanPref(context, StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION, true);
        Pref.SetBooleanPref(context, StringUtils.NOTIFICATION_VIBRATION, true);
        Pref.SetBooleanPref(context, StringUtils.NOTIFICATION_ALL_DAY, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_SOS, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_LOW_BATTERY, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_EXTERNAL_POWER_DISCONNECT, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_VIBRATION, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_GEOFENCE_IN, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_GEOFENCE_OUT, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_SPEEDING, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_TOWING, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_ENGINE_ON, true);
        Pref.SetBooleanPref(context, StringUtils.ALERT_ENGINE_OFF, true);
        Pref.SetStringPref(context, StringUtils.MONITOR_REFRESH_RATE, StringUtils.REF_10_SEC);
        Pref.SetStringPref(context, StringUtils.TRACKING_REFRESH_RATE, StringUtils.REF_10_SEC);
        Pref.SetStringPref(context, StringUtils.DISTANCE_METRICES, StringUtils.KILOMETER);
        Pref.SetStringPref(context, StringUtils.TEMPERATURE_METRICES, StringUtils.CELCIUS);
    }


    public static String getJSONString(Context context) {
        String str = "";
        try {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open("json.txt");
            InputStreamReader isr = new InputStreamReader(in);
            char[] inputBuffer = new char[100];

            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return str;
    }

    public static void writeToFile(String data, Context context) {
        try {
            String base_path = Environment.getExternalStorageDirectory() + File.separator + "output";
            File f = new File(base_path);
            f.mkdirs();

            String file_path = base_path + File.separator + System.currentTimeMillis() + "_oup.txt";

            FileWriter out = new FileWriter(new File(file_path));
            out.write(data);
            out.close();

            Log.d(TagUtils.getTag(), "file written to :-" + file_path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static float getBearing(LatLng begin, LatLng end) {

//        Log.d(TagUtils.getTag(), "begin:-" + begin.latitude + "," + begin.longitude);
//        Log.d(TagUtils.getTag(), "end:-" + end.latitude + "," + end.longitude);

        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

    public static long getCalculatedTime(String server_time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(server_time);
            Date d2 = format.parse(UtilityFunction.getServerTimeCurrentTime());
            long diff = d2.getTime() - d1.getTime();

//            long diffSeconds = diff / 1000 % 60;
//            long diffMinutes = diff / (60 * 1000) % 60;
            long diffMinutes = diff / (60 * 1000);
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);

            return diffMinutes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static long getDifferenceInMin(List<ObjectHistoryPOJO> objectHistoryPOJOS, String start_time, String end_time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(objectHistoryPOJOS.get(Integer.parseInt(start_time)).getDtTracker());
            Date d2 = format.parse(objectHistoryPOJOS.get(Integer.parseInt(end_time)).getDtTracker());
            long diff = d2.getTime() - d1.getTime();

//            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);

            return diffMinutes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDifferenceInMinInt(List<ObjectHistoryPOJO> objectHistoryPOJOS, int starting_index, int end_index) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(objectHistoryPOJOS.get(starting_index).getDtTracker());
            Date d2 = format.parse(objectHistoryPOJOS.get(end_index).getDtTracker());
            long diff = d2.getTime() - d1.getTime();

//            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000);
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);

            return diffMinutes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDifferenceInHour(String start_time, String end_time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(start_time);
            Date d2 = format.parse(end_time);
            long diff = d2.getTime() - d1.getTime();

//            long diffSeconds = diff / 1000 % 60;
//            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);

            return diffHours;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDifferenceInSec(List<ObjectHistoryPOJO> objectHistoryPOJOS, String start_time, String end_time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d(TagUtils.getTag(), "start index:-" + start_time + "\nstart date:-" + objectHistoryPOJOS.get(Integer.parseInt(start_time)).getDtTracker());
            Log.d(TagUtils.getTag(), "end index:-" + end_time + "\nend date:-" + objectHistoryPOJOS.get(Integer.parseInt(end_time)).getDtTracker());
            Date d1 = format.parse(objectHistoryPOJOS.get(Integer.parseInt(start_time)).getDtTracker());
            Date d2 = format.parse(objectHistoryPOJOS.get(Integer.parseInt(end_time)).getDtTracker());
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
//            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);

            return diffSeconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getDifferenceBtwTwoTimeInSec(String start_time, String end_time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(start_time);
            Date d2 = format.parse(end_time);
            long diff = d2.getTime() - d1.getTime();
//            Log.d(TagUtils.getTag(),"start date:-"+start_time+",end date:-"+end_time);
//            Log.d(TagUtils.getTag(),"difference:-"+diff);
            long diffSeconds = diff / 1000;
//            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);

            return diffSeconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getTimeDiffInMin(String start_time, String end_time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(start_time);
            Date d2 = format.parse(end_time);
            long diff = d2.getTime() - d1.getTime();
            long diffMinutes = diff / (60 * 1000) ;

            return diffMinutes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDurationString(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(Math.abs(hours)) + " : " + twoDigitString(Math.abs(minutes)) + " : " + twoDigitString(Math.abs(seconds));
    }

    public static String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public static final String vehicle_list = "[{\"VehicleID\":81,\"VehicleActive\":false,\"VehicleMade\":null,\"VehicleType\":\"Bike\",\"Latitude\":28.168121,\"Longitude\":77.151561,\"Altitude\":null,\"Time\":\"0001-01-01T00:00:00\",\"Speed\":0,\"BatteryLevel\":0,\"FuelLevel\":0,\"Direction\":null,\"Signal\":null,\"DriverName\":null,\"ServiceDueDate\":\"0001-01-01T00:00:00\",\"LastServiceDate\":\"0001-01-01T00:00:00\",\"VehicleNumber\":\"DL3SDC5507\",\"VehicleModel\":null,\"Address\":null,\"Mileage\":55.0,\"TodaysMileage\":0.0,\"ExpiryDate\":\"0001-01-01T00:00:00\",\"MaxSpeed\":0,\"AccidentSensor\":false,\"Fast\":false,\"FuelSensor\":false,\"GeoOnlineFence\":false,\"OfflineGeoFence\":false,\"IgnitionWire\":false,\"MovementAlerts\":false,\"PanicButton\":false,\"Relay\":false,\"SpeedLimit\":0,\"SpeedWarnings\":false,\"StopLimit\":0,\"StopTimeGapMinutes\":0,\"TemperatureSensor\":false,\"Owner\":null,\"DeviceID\":null}]";

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public static String parseUTCToIST(String time) {
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsed = sourceFormat.parse(time); // => Date is in UTC now

            TimeZone tz = TimeZone.getTimeZone("Asia/Kolkata");
            SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            destFormat.setTimeZone(tz);

            String result = destFormat.format(parsed);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String parseISTToUTC(String time) {
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            Date parsed = sourceFormat.parse(time); // => Date is in UTC now

            TimeZone tz = TimeZone.getTimeZone("UTC");
            SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            destFormat.setTimeZone(tz);

            String result = destFormat.format(parsed);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String parseDuration(String duration){
        try {
            duration = duration.trim();
            String[] str=duration.split("  ");
//            for(String s:str){
//                Log.d(TagUtils.getTag(),"move_duration:-"+s);
//            }
            if(str.length==3){
                return str[0]+"h "+str[1]+"m "+str[2]+"s";
            }else if(str.length==2){
                return str[0]+"m "+str[1]+"s";
            }else if(str.length==1){
                return str[0]+"s";
            }
        }catch (Exception e){
            Log.d(TagUtils.getTag(),"move_duration:-"+e.toString());
            e.printStackTrace();
        }
        return duration;
    }

    public static DevicePOJO getDeviceFromList(List<DevicePOJO> devicePOJOS,String imei){
        if(devicePOJOS!=null&&imei!=null&&devicePOJOS.size()>0){
            for(DevicePOJO devicePOJO:devicePOJOS){
                if(devicePOJO.getImei().equals(imei)){
                    return devicePOJO;
                }
            }
        }
        return null;
    }

    public static int getIndexFromList(List<DevicePOJO> devicePOJOS,String imei){
        if(devicePOJOS!=null&&imei!=null&&devicePOJOS.size()>0){
            for(int i=0;i<devicePOJOS.size();i++){
                if(devicePOJOS.get(i).getImei().equals(imei)){
                    return i;
                }
            }
        }
        return -1;
    }

    public static String getGeoAddress(Context activity, double lat, double lng) {
        String address = "";
        if (lat != 0 && lng != 0 && String.valueOf(lat) != null && String.valueOf(lng) != null && activity != null) {
            Geocoder geocoder = new Geocoder(activity);
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                address = addresses.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return address;
    }

}
