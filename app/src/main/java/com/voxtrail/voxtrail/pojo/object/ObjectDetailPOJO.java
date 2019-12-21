package com.voxtrail.voxtrail.pojo.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ObjectDetailPOJO implements Serializable {
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
    private Integer speed;
    @SerializedName("params")
    @Expose
    private ObjectParamPOJO objectParamPOJO;

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

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public ObjectParamPOJO getObjectParamPOJO() {
        return objectParamPOJO;
    }

    public void setObjectParamPOJO(ObjectParamPOJO objectParamPOJO) {
        this.objectParamPOJO = objectParamPOJO;
    }
}
