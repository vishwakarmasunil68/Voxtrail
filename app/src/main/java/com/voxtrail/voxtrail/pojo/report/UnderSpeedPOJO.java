package com.voxtrail.voxtrail.pojo.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UnderSpeedPOJO implements Serializable {

    @Expose
    @SerializedName("underspeed_position")
    private String underspeedPosition;
    @Expose
    @SerializedName("avg_speed")
    private String avgSpeed;
    @Expose
    @SerializedName("top_speed")
    private String topSpeed;
    @Expose
    @SerializedName("duration")
    private String duration;
    @Expose
    @SerializedName("end")
    private String end;
    @Expose
    @SerializedName("start")
    private String start;

    public String getUnderspeedPosition() {
        return underspeedPosition;
    }

    public void setUnderspeedPosition(String underspeedPosition) {
        this.underspeedPosition = underspeedPosition;
    }

    public String getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(String avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public String getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(String topSpeed) {
        this.topSpeed = topSpeed;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
