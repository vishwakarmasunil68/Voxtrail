package com.voxtrail.voxtrail.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImagesPOJO implements Serializable {

    @Expose
    @SerializedName("params")
    private String params;
    @Expose
    @SerializedName("speed")
    private String speed;
    @Expose
    @SerializedName("angle")
    private String angle;
    @Expose
    @SerializedName("altitude")
    private String altitude;
    @Expose
    @SerializedName("lng")
    private String lng;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("dt_tracker")
    private String dtTracker;
    @Expose
    @SerializedName("dt_server")
    private String dtServer;
    @Expose
    @SerializedName("imei")
    private String imei;
    @Expose
    @SerializedName("img_file")
    private String imgFile;
    @Expose
    @SerializedName("img_id")
    private String imgId;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getDtTracker() {
        return dtTracker;
    }

    public void setDtTracker(String dtTracker) {
        this.dtTracker = dtTracker;
    }

    public String getDtServer() {
        return dtServer;
    }

    public void setDtServer(String dtServer) {
        this.dtServer = dtServer;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
}
