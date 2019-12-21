package com.voxtrail.voxtrail.pojo.sharing;

import com.google.gson.annotations.SerializedName;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;

import java.util.List;

public class ObjectSharingPOJO {
    @SerializedName("sharing_data")
    SharingPOJO sharingPOJO;
    @SerializedName("res")
    List<DevicePOJO> devicePOJOS;

    public SharingPOJO getSharingPOJO() {
        return sharingPOJO;
    }

    public void setSharingPOJO(SharingPOJO sharingPOJO) {
        this.sharingPOJO = sharingPOJO;
    }

    public List<DevicePOJO> getDevicePOJOS() {
        return devicePOJOS;
    }

    public void setDevicePOJOS(List<DevicePOJO> devicePOJOS) {
        this.devicePOJOS = devicePOJOS;
    }
}
