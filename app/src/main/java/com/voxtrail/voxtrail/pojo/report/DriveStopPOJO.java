package com.voxtrail.voxtrail.pojo.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriveStopPOJO {
    @Expose
    @SerializedName("engine_idle")
    private String engineIdle;
    @Expose
    @SerializedName("engine_work")
    private String engineWork;
    @Expose
    @SerializedName("fuel_cost")
    private String fuelCost;
    @Expose
    @SerializedName("fuel_consumption")
    private String fuelConsumption;
    @Expose
    @SerializedName("avg_speed")
    private String avgSpeed;
    @Expose
    @SerializedName("top_speed")
    private String topSpeed;
    @Expose
    @SerializedName("route_length")
    private String routeLength;
    @Expose
    @SerializedName("stop_duration")
    private String stopDuration;
    @Expose
    @SerializedName("move_duration")
    private String moveDuration;
    @SerializedName("drive_stop")
    List<DriveStopResultPOJO> driveStopResultPOJOS;

    public String getEngineIdle() {
        return engineIdle;
    }

    public void setEngineIdle(String engineIdle) {
        this.engineIdle = engineIdle;
    }

    public String getEngineWork() {
        return engineWork;
    }

    public void setEngineWork(String engineWork) {
        this.engineWork = engineWork;
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

    public String getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(String routeLength) {
        this.routeLength = routeLength;
    }

    public String getStopDuration() {
        return stopDuration;
    }

    public void setStopDuration(String stopDuration) {
        this.stopDuration = stopDuration;
    }

    public String getMoveDuration() {
        return moveDuration;
    }

    public void setMoveDuration(String moveDuration) {
        this.moveDuration = moveDuration;
    }

    public List<DriveStopResultPOJO> getDriveStopResultPOJOS() {
        return driveStopResultPOJOS;
    }

    public void setDriveStopResultPOJOS(List<DriveStopResultPOJO> driveStopResultPOJOS) {
        this.driveStopResultPOJOS = driveStopResultPOJOS;
    }
}
