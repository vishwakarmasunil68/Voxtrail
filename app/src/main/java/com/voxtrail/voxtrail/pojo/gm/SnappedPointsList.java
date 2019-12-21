package com.voxtrail.voxtrail.pojo.gm;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SnappedPointsList {
    @SerializedName("snappedPoints")
    List<SnappedPointsPOJO> snappedPointsPOJOS;

    public List<SnappedPointsPOJO> getSnappedPointsPOJOS() {
        return snappedPointsPOJOS;
    }

    public void setSnappedPointsPOJOS(List<SnappedPointsPOJO> snappedPointsPOJOS) {
        this.snappedPointsPOJOS = snappedPointsPOJOS;
    }
}
