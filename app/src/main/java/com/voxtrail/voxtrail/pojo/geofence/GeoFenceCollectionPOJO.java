package com.voxtrail.voxtrail.pojo.geofence;

import java.io.Serializable;
import java.util.List;

public class GeoFenceCollectionPOJO implements Serializable {
    List<GeoFencePOJO> geoFencePOJOS;

    public List<GeoFencePOJO> getGeoFencePOJOS() {
        return geoFencePOJOS;
    }

    public void setGeoFencePOJOS(List<GeoFencePOJO> geoFencePOJOS) {
        this.geoFencePOJOS = geoFencePOJOS;
    }
}
