package com.voxtrail.voxtrail.pojo.travelsheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TravelSheetPOJO implements Serializable {
    @SerializedName("vehicle_data")
    List<TravelSheetDataPOJO> travelSheetDataPOJOS;
    @Expose
    @SerializedName("engine_idle")
    private String engineIdle;
    @Expose
    @SerializedName("engine_work")
    private String engineWork;
    @Expose
    @SerializedName("engine_idle_time")
    private String engineIdleTime;
    @Expose
    @SerializedName("engine_work_time")
    private String engineWorkTime;
    @Expose
    @SerializedName("drives_duration_time")
    private String drivesDurationTime;
    @Expose
    @SerializedName("stops_duration_time")
    private String stopsDurationTime;
    @Expose
    @SerializedName("avg_speed")
    private String avgSpeed;
    @Expose
    @SerializedName("top_speed")
    private String topSpeed;
    @Expose
    @SerializedName("fuel_cost")
    private String fuelCost;
    @Expose
    @SerializedName("fuel_consumption")
    private String fuelConsumption;
    @Expose
    @SerializedName("route_length")
    private String routeLength;


    public List<TravelSheetDataPOJO> getTravelSheetDataPOJOS() {
        return travelSheetDataPOJOS;
    }

    public void setTravelSheetDataPOJOS(List<TravelSheetDataPOJO> travelSheetDataPOJOS) {
        this.travelSheetDataPOJOS = travelSheetDataPOJOS;
    }

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

    public String getEngineIdleTime() {
        return engineIdleTime;
    }

    public void setEngineIdleTime(String engineIdleTime) {
        this.engineIdleTime = engineIdleTime;
    }

    public String getEngineWorkTime() {
        return engineWorkTime;
    }

    public void setEngineWorkTime(String engineWorkTime) {
        this.engineWorkTime = engineWorkTime;
    }

    public String getDrivesDurationTime() {
        return drivesDurationTime;
    }

    public void setDrivesDurationTime(String drivesDurationTime) {
        this.drivesDurationTime = drivesDurationTime;
    }

    public String getStopsDurationTime() {
        return stopsDurationTime;
    }

    public void setStopsDurationTime(String stopsDurationTime) {
        this.stopsDurationTime = stopsDurationTime;
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

    public String getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(String routeLength) {
        this.routeLength = routeLength;
    }
}
