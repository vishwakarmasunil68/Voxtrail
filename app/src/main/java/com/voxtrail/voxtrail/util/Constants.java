package com.voxtrail.voxtrail.util;


import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.pojo.user.UserDetail;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    // Access permission
    public static final int ACCESS_CAMERA = 2001;
    public static final int READ_CONTACTS = 2002;
    public static final int ACCESS_STORAGE = 2003;
    public static final int ACCESS_LOCATION = 2004;
    public static final int READ_SMS = 2005;
    public static final int SELECT_DEVICE = 2006;
    public static final int SELECT_DEVICE_FOR_HISTORY = 2007;
    public static final int SELECT_DEVICE_FOR_REPORT= 2008;
    public static final int SELECT_DEVICE_FOR_GENERAL_REPORT= 2009;
    public static final int SELECT_DEVICE_FOR_ZONE_INOUT_REPORT= 2010;
    public static final int SELECT_DEVICE_FOR_DRIVE_STOP= 2011;
    public static final int SELECT_DEVICE_FOR_OVERSPEED= 2012;
    public static final int SELECT_DEVICE_FOR_UNDERSPEED= 2013;
    public static final int SELECT_DEVICE_FOR_DRIVER_SCORE= 2014;

    public static String RUNNING_TYPE="running_type";
    public static String STOPPED_TYPE="stopped_type";

    public static UserDetail userDetail;
    public static List<DevicePOJO> devicePOJOS=new ArrayList<>();
}
