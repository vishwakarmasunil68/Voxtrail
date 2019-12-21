package com.voxtrail.voxtrail.pojo.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ZoneInOutPOJO implements Serializable{
    @Expose
    @SerializedName("zone_position")
    private String zonePosition;
    @Expose
    @SerializedName("zone_name")
    private String zoneName;
    @Expose
    @SerializedName("route_length")
    private String routeLength;
    @Expose
    @SerializedName("duration")
    private String duration;
    @Expose
    @SerializedName("zone_out")
    private String zoneOut;
    @Expose
    @SerializedName("zone_in")
    private String zoneIn;

    public String getZonePosition() {
        return zonePosition;
    }

    public void setZonePosition(String zonePosition) {
        this.zonePosition = zonePosition;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(String routeLength) {
        this.routeLength = routeLength;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getZoneOut() {
        return zoneOut;
    }

    public void setZoneOut(String zoneOut) {
        this.zoneOut = zoneOut;
    }

    public String getZoneIn() {
        return zoneIn;
    }

    public void setZoneIn(String zoneIn) {
        this.zoneIn = zoneIn;
    }
}
