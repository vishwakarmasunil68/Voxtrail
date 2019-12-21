package com.voxtrail.voxtrail.pojo.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DriverScorePOJO implements Serializable {
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("object_name")
    @Expose
    private String objectName;
    @SerializedName("route_length")
    @Expose
    private Double routeLength;
    @SerializedName("overspeed_duration")
    @Expose
    private String overspeedDuration;
    @SerializedName("overspeed_score")
    @Expose
    private String overspeedScore;
    @SerializedName("haccel_count")
    @Expose
    private Integer haccelCount;
    @SerializedName("haccel_score")
    @Expose
    private String haccelScore;
    @SerializedName("hbrake_count")
    @Expose
    private Integer hbrakeCount;
    @SerializedName("hbrake_score")
    @Expose
    private String hbrakeScore;
    @SerializedName("hcorn_count")
    @Expose
    private Integer hcornCount;
    @SerializedName("hcorn_score")
    @Expose
    private String hcornScore;
    @SerializedName("rag_color")
    @Expose
    private String ragColor;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Double getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(Double routeLength) {
        this.routeLength = routeLength;
    }

    public String getOverspeedDuration() {
        return overspeedDuration;
    }

    public void setOverspeedDuration(String overspeedDuration) {
        this.overspeedDuration = overspeedDuration;
    }

    public String getOverspeedScore() {
        return overspeedScore;
    }

    public void setOverspeedScore(String overspeedScore) {
        this.overspeedScore = overspeedScore;
    }

    public Integer getHaccelCount() {
        return haccelCount;
    }

    public void setHaccelCount(Integer haccelCount) {
        this.haccelCount = haccelCount;
    }

    public String getHaccelScore() {
        return haccelScore;
    }

    public void setHaccelScore(String haccelScore) {
        this.haccelScore = haccelScore;
    }

    public Integer getHbrakeCount() {
        return hbrakeCount;
    }

    public void setHbrakeCount(Integer hbrakeCount) {
        this.hbrakeCount = hbrakeCount;
    }

    public String getHbrakeScore() {
        return hbrakeScore;
    }

    public void setHbrakeScore(String hbrakeScore) {
        this.hbrakeScore = hbrakeScore;
    }

    public Integer getHcornCount() {
        return hcornCount;
    }

    public void setHcornCount(Integer hcornCount) {
        this.hcornCount = hcornCount;
    }

    public String getHcornScore() {
        return hcornScore;
    }

    public void setHcornScore(String hcornScore) {
        this.hcornScore = hcornScore;
    }

    public String getRagColor() {
        return ragColor;
    }

    public void setRagColor(String ragColor) {
        this.ragColor = ragColor;
    }
}
