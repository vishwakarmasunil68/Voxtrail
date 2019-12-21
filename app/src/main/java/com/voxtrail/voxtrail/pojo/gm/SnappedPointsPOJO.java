package com.voxtrail.voxtrail.pojo.gm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SnappedPointsPOJO {
    @SerializedName("location")
    @Expose
    private LocationPOJO locationPOJO;
    @SerializedName("originalIndex")
    @Expose
    private Integer originalIndex;
    @SerializedName("placeId")
    @Expose
    private String placeId;
    private String date;
    private String speed;
    private String mileage;

    public LocationPOJO getLocationPOJO() {
        return locationPOJO;
    }

    public void setLocationPOJO(LocationPOJO locationPOJO) {
        this.locationPOJO = locationPOJO;
    }

    public Integer getOriginalIndex() {
        return originalIndex;
    }

    public void setOriginalIndex(Integer originalIndex) {
        this.originalIndex = originalIndex;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
}
