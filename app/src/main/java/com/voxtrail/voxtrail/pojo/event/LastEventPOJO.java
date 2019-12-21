package com.voxtrail.voxtrail.pojo.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LastEventPOJO implements Serializable{
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("event_desc")
    @Expose
    private String eventDesc;
    @SerializedName("notify_system")
    @Expose
    private String notifySystem;
    @SerializedName("notify_arrow")
    @Expose
    private String notifyArrow;
    @SerializedName("notify_arrow_color")
    @Expose
    private String notifyArrowColor;
    @SerializedName("notify_ohc")
    @Expose
    private String notifyOhc;
    @SerializedName("notify_ohc_color")
    @Expose
    private String notifyOhcColor;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dt_server")
    @Expose
    private String dtServer;
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
    @SerializedName("params")
    @Expose
    private String params;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getNotifySystem() {
        return notifySystem;
    }

    public void setNotifySystem(String notifySystem) {
        this.notifySystem = notifySystem;
    }

    public String getNotifyArrow() {
        return notifyArrow;
    }

    public void setNotifyArrow(String notifyArrow) {
        this.notifyArrow = notifyArrow;
    }

    public String getNotifyArrowColor() {
        return notifyArrowColor;
    }

    public void setNotifyArrowColor(String notifyArrowColor) {
        this.notifyArrowColor = notifyArrowColor;
    }

    public String getNotifyOhc() {
        return notifyOhc;
    }

    public void setNotifyOhc(String notifyOhc) {
        this.notifyOhc = notifyOhc;
    }

    public String getNotifyOhcColor() {
        return notifyOhcColor;
    }

    public void setNotifyOhcColor(String notifyOhcColor) {
        this.notifyOhcColor = notifyOhcColor;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDtServer() {
        return dtServer;
    }

    public void setDtServer(String dtServer) {
        this.dtServer = dtServer;
    }

    public String getDtTracker() {
        return dtTracker;
    }

    public void setDtTracker(String dtTracker) {
        this.dtTracker = dtTracker;
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
