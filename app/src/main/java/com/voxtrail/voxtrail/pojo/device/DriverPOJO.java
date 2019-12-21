package com.voxtrail.voxtrail.pojo.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverPOJO {
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("driver_assign_id")
    @Expose
    private String driverAssignId;
    @SerializedName("driver_idn")
    @Expose
    private String driverIdn;
    @SerializedName("driver_address")
    @Expose
    private String driverAddress;
    @SerializedName("driver_phone")
    @Expose
    private String driverPhone;
    @SerializedName("driver_email")
    @Expose
    private String driverEmail;
    @SerializedName("driver_desc")
    @Expose
    private String driverDesc;
    @SerializedName("driver_img_file")
    @Expose
    private String driverImgFile;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverAssignId() {
        return driverAssignId;
    }

    public void setDriverAssignId(String driverAssignId) {
        this.driverAssignId = driverAssignId;
    }

    public String getDriverIdn() {
        return driverIdn;
    }

    public void setDriverIdn(String driverIdn) {
        this.driverIdn = driverIdn;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDriverDesc() {
        return driverDesc;
    }

    public void setDriverDesc(String driverDesc) {
        this.driverDesc = driverDesc;
    }

    public String getDriverImgFile() {
        return driverImgFile;
    }

    public void setDriverImgFile(String driverImgFile) {
        this.driverImgFile = driverImgFile;
    }
}
