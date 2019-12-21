package com.voxtrail.voxtrail.pojo.pojo;

import com.google.gson.annotations.SerializedName;
import com.voxtrail.voxtrail.pojo.device.DeviceLocationPOJO;
import com.voxtrail.voxtrail.pojo.gm.ObjectHistoryPOJO;
import com.voxtrail.voxtrail.pojo.travelsheet.TravelSheetPOJO;

import java.io.Serializable;
import java.util.List;

public class VehicleStatusReportPOJO implements Serializable {
    @SerializedName("travel_history")
    List<ObjectHistoryPOJO> objectHistoryPOJOS;
    @SerializedName("travel_sheet_report")
    TravelSheetPOJO travelSheetPOJO;


    public List<ObjectHistoryPOJO> getObjectHistoryPOJOS() {
        return objectHistoryPOJOS;
    }

    public void setObjectHistoryPOJOS(List<ObjectHistoryPOJO> objectHistoryPOJOS) {
        this.objectHistoryPOJOS = objectHistoryPOJOS;
    }

    public TravelSheetPOJO getTravelSheetPOJO() {
        return travelSheetPOJO;
    }

    public void setTravelSheetPOJO(TravelSheetPOJO travelSheetPOJO) {
        this.travelSheetPOJO = travelSheetPOJO;
    }
}
