package com.voxtrail.voxtrail.webservice;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sunil on 20-01-2017.
 */

public class WebServicesUrls {

    public static final String BASE_IP = "http://182.73.163.242";
    //    public static final String BASE_IP = "http://192.168.1.63";
    public static final String BASE_IP1 = "http://platform.voxtrail.com/";
//        public static final String BASE_IP1 = "http://192.168.1.63/voxtrail/";
    public static final String BASE_URL = BASE_IP1 + "gps/index.php/";
    public static final String IMAGE_BASE_URL = "http://platform.voxtrail.com/";

    public static final String DEVICES_IMAGE_BASE_URL = "http://platform.voxtrail.com/data/img/";

    public static final String LOGIN = BASE_URL + "User/loginUser";
    public static final String LOGOUT = BASE_URL + "User/logoutUser";
    public static final String UPDATE_PROFILE = BASE_URL + "User/updateProfile";
    public static final String UPLOAD_PROFILE_PIC = BASE_URL + "User/updateProfilePic";
    public static final String GET_DEVICES = BASE_URL + "Objects/getUserObjects";
    public static final String DASHBOARD_DATA = BASE_URL + "Dashboard/getDashBoardData";
    public static final String SIGNUP_USER = BASE_URL + "User/insertUserDetail";
    public static final String DEVICE_LAST_LOCATION = BASE_URL + "Objects/getObjectLastLocation";
    public static final String GET_OBJECT_HISTORY = BASE_URL + "Objects/getObjectHistory";
    public static final String GET_APP_INFO = BASE_URL + "AppInfo/getAppInfo";
    public static final String SAVE_OBJECT = BASE_URL + "Objects/addObject";
    public static final String GET_OBJECT = BASE_URL + "Objects/getObject";
    public static final String SAVE_ZONE = BASE_URL + "Zone/saveZone";
    public static final String deleteZone = BASE_URL + "Zone/deleteZone";
    public static final String updateZone = BASE_URL + "Zone/updateZone";
    public static final String GET_USER_ZONES = BASE_URL + "Zone/getUserZones";
    public static final String GET_ALERTS = BASE_URL + "Alerts/getUserAlerts";
    public static final String GET_DEVICE_ALERT = BASE_URL + "Alerts/getDeviceAlerts";
    public static final String OBJECT_UPDATE = BASE_URL + "Objects/getdevicesUpdate";
    public static final String HELP = BASE_URL + "Help/getHelp";
    public static final String UPDATE_PASSWORD= BASE_URL + "User/changePassword";
    public static final String SHARE_OBJECTS = BASE_URL + "ObjectSharing/shareObject";
    public static final String GET_SHARED_OBJECTS = BASE_URL + "ObjectSharing/getSharedObject";

    public static final String DAHSBOARD_API = BASE_IP1 + "report/report/dashboard.php";
    public static final String VOXTRAIL_DAHSBOARD_API = BASE_IP1 + "report/report/voxtraildashboardapi.php";
    public static final String HOME_TRAVEL_SHEET_REPORT_API = BASE_IP1 + "func/hometravelsheetreport.php";
    public static final String TRAVEL_SHEET_REPORT_API = BASE_IP1 + "func/travelsheetreport.php";
    public static final String GENERAL_INFORMATION_API = BASE_IP1 + "func/generalinformationreport.php";
    public static final String ZONE_IN_OUT_REPORT = BASE_IP1 + "func/zoneinoutreport.php";
    public static final String IMAGES_API = BASE_URL + "Images/getDeviceImages";
    public static final String DRIVE_STOP_API = BASE_IP1 + "report/report/driveandstopreport.php";
    public static final String OVERSPEED_API = BASE_IP1 + "report/report/overspeedreport.php";
    public static final String UNDERSPEED_API = BASE_IP1 + "report/report/underspeedreport.php";
    public static final String DRIVER_SCORE_API = BASE_IP1 + "report/report/driverscorereport.php";
    public static final String MILEAGE_REPORT_API = BASE_IP1 + "report/report/mileagedailyreport.php";
//    public static final String PLATFORM_DEVICE_LIST_API = "http://platform.voxtrail.com/api/get_user_objects.php";
    public static final String PLATFORM_DEVICE_LIST_API = "http://platform.voxtrail.com/api/getUser_objects_new.php";
    public static final String GET_ALERTS_IN_RANGE = BASE_URL+"Alerts/getAlertsinRange";
    public static final String INSERT_EVENT = BASE_URL+"Event/insertEvent";
    public static final String UPDATE_EVENT = BASE_URL+"Event/updateEvent";
    public static final String GET_ALL_EVENTS = BASE_URL+"Event/getAllEvents";
    public static final String DELETE_USER_EVENTS = BASE_URL+"Event/deleteUserEvent";
    public static final String DEVICE_ALERTS = BASE_URL+"Alerts/getDeviceAlerts";
    public static final String GET_OBJECT_WITH_DRIVER = BASE_URL + "Objects/getObjectWithDriver";
    public static final String UPDATE_OBJECTS = BASE_URL + "Objects/updateObject";
    public static final String UPDATE_OBJECT_IMAGE = BASE_URL + "Objects/updateObjectImage";
    public static final String UPDATE_DRIVER = BASE_URL + "Drivers/updateDriver";
    public static final String GET_DRIVERS = BASE_URL + "Drivers/getDrivers";
    public static final String ADD_DRIVER = BASE_URL + "Drivers/addDriver";
    public static final String GET_LAST_DT_DEVICE = BASE_URL + "ObjectControl/getDtCMD";

    public static String getMapsApiDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+"&key=AIzaSyC-L6KkHvJLkqTASANbRZC3gvAQExPFx24";


        return url;

    }


}
