package com.voxtrail.voxtrail.pojo.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MileageReportPOJO {

    @Expose
    @SerializedName("engine_hours")
    private String engineHours;
    @Expose
    @SerializedName("fuel_cost")
    private String fuelCost;
    @Expose
    @SerializedName("fuel_consumption")
    private String fuelConsumption;
    @Expose
    @SerializedName("route_length")
    private String routeLength;
    @Expose
    @SerializedName("end")
    private String end;
    @Expose
    @SerializedName("start")
    private String start;
    @Expose
    @SerializedName("donation")
    private String donation;

    public String getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(String engineHours) {
        this.engineHours = engineHours;
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

    public String getDonation() {
        return donation;
    }

    public void setDonation(String donation) {
        this.donation = donation;
    }
}
