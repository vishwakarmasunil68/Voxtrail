package com.voxtrail.voxtrail.pojo.device;

import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;

import java.io.Serializable;
import java.util.List;

public class DeviceCollectionPOJO implements Serializable {
    List<DevicePOJO> devicePOJOS;

    public List<DevicePOJO> getDevicePOJOS() {
        return devicePOJOS;
    }

    public void setDevicePOJOS(List<DevicePOJO> devicePOJOS) {
        this.devicePOJOS = devicePOJOS;
    }
}
