package com.voxtrail.voxtrail.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.voxtrail.voxtrail.pojo.device.DeviceParamPOJO;
import com.voxtrail.voxtrail.util.UtilityFunction;

import java.io.Serializable;

public class DeviceUpdatedPOJO implements Serializable{
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("dt_tracker")
    @Expose
    private String dtTracker;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("altitude")
    @Expose
    private String altitude;
    @SerializedName("angle")
    @Expose
    private String angle;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("cn")
    @Expose
    private Integer cn;
    @SerializedName("st")
    @Expose
    private String st;
    private DeviceParamPOJO deviceParamPOJO;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDtTracker() {
//        try{
//            return UtilityFunction.parseUTCToIST(dtTracker);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return dtTracker;
    }

    public String getNormalDttracker() {
        return dtTracker;
    }

    public void setDtTracker(String dtTracker) {
        this.dtTracker = dtTracker;
    }

    @Override
    public String toString() {
        return "DeviceUpdatedPOJO{" +
                "imei='" + imei + '\'' +
                ", dtTracker='" + dtTracker + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", altitude='" + altitude + '\'' +
                ", angle='" + angle + '\'' +
                ", speed='" + speed + '\'' +
                '}';
    }

    public Integer getCn() {
        return cn;
    }

    public void setCn(Integer cn) {
        this.cn = cn;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public DeviceParamPOJO getDeviceParamPOJO() {
        return deviceParamPOJO;
    }

    public void setDeviceParamPOJO(DeviceParamPOJO deviceParamPOJO) {
        this.deviceParamPOJO = deviceParamPOJO;
    }
}
