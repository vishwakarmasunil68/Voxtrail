package com.voxtrail.voxtrail.pojo.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DriveStopResultPOJO implements Serializable {

    @Expose
    @SerializedName("engine_idle")
    private String engineIdle;
    @Expose
    @SerializedName("fuel_cost")
    private String fuelCost;
    @Expose
    @SerializedName("fuel_consumption")
    private String fuelConsumption;
    @Expose
    @SerializedName("stop_position")
    private String stopPosition;
    @Expose
    @SerializedName("avg_speed")
    private String avgSpeed;
    @Expose
    @SerializedName("top_speed")
    private String topSpeed;
    @Expose
    @SerializedName("length")
    private String length;
    @Expose
    @SerializedName("duration")
    private String duration;
    @Expose
    @SerializedName("end_time")
    private String endTime;
    @Expose
    @SerializedName("start_time")
    private String startTime;
    @Expose
    @SerializedName("status")
    private String status;

    public String getEngineIdle() {
        return engineIdle;
    }

    public void setEngineIdle(String engineIdle) {
        this.engineIdle = engineIdle;
    }

    public String getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(String fuelCost) {
        this.fuelCost = fuelCost;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getStopPosition() {
        return stopPosition;
    }

    public void setStopPosition(String stopPosition) {
        this.stopPosition = stopPosition;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
