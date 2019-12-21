package com.voxtrail.voxtrail.pojo.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.voxtrail.voxtrail.util.UtilityFunction;

public class GeneralInformationPOJO {
    @Expose
    @SerializedName("engine_hours")
    private String engineHours;
    @Expose
    @SerializedName("odometer")
    private String odometer;
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
    @SerializedName("overspeed_count")
    private int overspeedCount;
    @Expose
    @SerializedName("avg_speed")
    private String avgSpeed;
    @Expose
    @SerializedName("top_speed")
    private String topSpeed;
    @Expose
    @SerializedName("stops")
    private int stops;
    @Expose
    @SerializedName("stops_duration")
    private String stopsDuration;
    @Expose
    @SerializedName("drives_duration")
    private String drivesDuration;
    @Expose
    @SerializedName("route_length")
    private String routeLength;
    @Expose
    @SerializedName("route_end")
    private String routeEnd;
    @Expose
    @SerializedName("route_start")
    private String routeStart;

    public String getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(String engineHours) {
        this.engineHours = engineHours;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
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

    public int getOverspeedCount() {
        return overspeedCount;
    }

    public void setOverspeedCount(int overspeedCount) {
        this.overspeedCount = overspeedCount;
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

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public String getStopsDuration() {
        return stopsDuration;
    }

    public void setStopsDuration(String stopsDuration) {
        this.stopsDuration = stopsDuration;
    }

    public String getDrivesDuration() {
        return drivesDuration;
    }

    public void setDrivesDuration(String drivesDuration) {
        this.drivesDuration = drivesDuration;
    }

    public String getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(String routeLength) {
        this.routeLength = routeLength;
    }

    public String getRouteEnd() {
        try{
            return UtilityFunction.parseUTCToIST(routeEnd);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

    public String getRouteStart() {
        try{
            return UtilityFunction.parseUTCToIST(routeStart);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return routeStart;
    }

    public void setRouteStart(String routeStart) {
        this.routeStart = routeStart;
    }
}
