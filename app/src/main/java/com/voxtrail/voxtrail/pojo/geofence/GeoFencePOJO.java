package com.voxtrail.voxtrail.pojo.geofence;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeoFencePOJO implements Serializable{
    @SerializedName("zone_id")
    @Expose
    private String zoneId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("zone_name")
    @Expose
    private String zoneName;
    @SerializedName("zone_color")
    @Expose
    private String zoneColor;
    @SerializedName("zone_visible")
    @Expose
    private String zoneVisible;
    @SerializedName("zone_name_visible")
    @Expose
    private String zoneNameVisible;
    @SerializedName("zone_area")
    @Expose
    private String zoneArea;
    @SerializedName("zone_vertices")
    @Expose
    private String zoneVertices;
    private boolean checked;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneColor() {
        return zoneColor;
    }

    public void setZoneColor(String zoneColor) {
        this.zoneColor = zoneColor;
    }

    public String getZoneVisible() {
        return zoneVisible;
    }

    public void setZoneVisible(String zoneVisible) {
        this.zoneVisible = zoneVisible;
    }

    public String getZoneNameVisible() {
        return zoneNameVisible;
    }

    public void setZoneNameVisible(String zoneNameVisible) {
        this.zoneNameVisible = zoneNameVisible;
    }

    public String getZoneArea() {
        return zoneArea;
    }

    public void setZoneArea(String zoneArea) {
        this.zoneArea = zoneArea;
    }

    public String getZoneVertices() {
        return zoneVertices;
    }

    public void setZoneVertices(String zoneVertices) {
        this.zoneVertices = zoneVertices;
    }

    @Override
    public String toString() {
        return "GeoFencePOJO{" +
                "zoneId='" + zoneId + '\'' +
                "checked='" + checked+ '\'' +
                '}';
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
