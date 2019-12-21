package com.voxtrail.voxtrail.pojo.dashboard;

import com.google.gson.annotations.SerializedName;
import com.voxtrail.voxtrail.pojo.gm.ObjectHistoryPOJO;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;
import com.voxtrail.voxtrail.pojo.travelsheet.TravelSheetPOJO;

import java.io.Serializable;
import java.util.List;

public class DashBoardPOJO implements Serializable {
    @SerializedName("objects")
    List<DevicePOJO> devicePOJOS;
//    @SerializedName("first_object_history")
//    List<ObjectHistoryPOJO> objectHistoryPOJOS;
    @SerializedName("travel_sheet")
    TravelSheetPOJO travelSheetPOJO;

    public List<DevicePOJO> getDevicePOJOS() {
        return devicePOJOS;
    }

    public void setDevicePOJOS(List<DevicePOJO> devicePOJOS) {
        this.devicePOJOS = devicePOJOS;
    }

    public TravelSheetPOJO getTravelSheetPOJO() {
        return travelSheetPOJO;
    }

    public void setTravelSheetPOJO(TravelSheetPOJO travelSheetPOJO) {
        this.travelSheetPOJO = travelSheetPOJO;
    }
}
