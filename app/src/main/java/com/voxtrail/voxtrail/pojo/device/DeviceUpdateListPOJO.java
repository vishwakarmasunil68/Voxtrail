package com.voxtrail.voxtrail.pojo.device;

import com.google.gson.annotations.SerializedName;
import com.voxtrail.voxtrail.pojo.DeviceUpdatedPOJO;

import java.util.List;

public class DeviceUpdateListPOJO {
    @SerializedName("message")
    List<DeviceUpdatedPOJO> deviceUpdatedPOJOS;

    public List<DeviceUpdatedPOJO> getDeviceUpdatedPOJOS() {
        return deviceUpdatedPOJOS;
    }

    public void setDeviceUpdatedPOJOS(List<DeviceUpdatedPOJO> deviceUpdatedPOJOS) {
        this.deviceUpdatedPOJOS = deviceUpdatedPOJOS;
    }
}
