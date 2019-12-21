package com.voxtrail.voxtrail.pojo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ObjectStatusPOJO implements Serializable{
    @SerializedName("status_str")
    @Expose
    private String statusStr;
    @SerializedName("status_type")
    @Expose
    private String statusType;
    @SerializedName("odometer")
    @Expose
    private String odometer;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }
}
