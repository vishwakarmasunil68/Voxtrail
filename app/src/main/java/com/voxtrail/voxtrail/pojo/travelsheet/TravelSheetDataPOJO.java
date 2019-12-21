package com.voxtrail.voxtrail.pojo.travelsheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TravelSheetDataPOJO implements Serializable{

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
    @SerializedName("duration")
    private String duration;
    @Expose
    @SerializedName("position_b")
    private String positionB;
    @Expose
    @SerializedName("time_b")
    private String timeB;
    @Expose
    @SerializedName("position_a")
    private String positionA;
    @Expose
    @SerializedName("time_a")
    private String timeA;

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPositionB() {
        return positionB;
    }

    public void setPositionB(String positionB) {
        this.positionB = positionB;
    }

    public String getTimeB() {
        return timeB;
    }

    public void setTimeB(String timeB) {
        this.timeB = timeB;
    }

    public String getPositionA() {
        return positionA;
    }

    public void setPositionA(String positionA) {
        this.positionA = positionA;
    }

    public String getTimeA() {
        return timeA;
    }

    public void setTimeA(String timeA) {
        this.timeA = timeA;
    }
}
