package com.voxtrail.voxtrail.pojo.report;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MileageReportResPOJO {
    @SerializedName("res")
    List<MileageReportPOJO> mileageReportPOJOS;
    @SerializedName("total")
    MileageTotalPOJO mileageTotalPOJO;

    public List<MileageReportPOJO> getMileageReportPOJOS() {
        return mileageReportPOJOS;
    }

    public void setMileageReportPOJOS(List<MileageReportPOJO> mileageReportPOJOS) {
        this.mileageReportPOJOS = mileageReportPOJOS;
    }
}
