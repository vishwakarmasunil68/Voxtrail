package com.voxtrail.voxtrail.pojo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.voxtrail.voxtrail.pojo.device.DeviceParamPOJO;
import com.voxtrail.voxtrail.util.UtilityFunction;

import java.io.Serializable;

public class DeviceDetailPOJO implements Serializable {
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("protocol")
    @Expose
    private String protocol;
    @SerializedName("net_protocol")
    @Expose
    private String netProtocol;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("port")
    @Expose
    private String port;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("object_expire")
    @Expose
    private String objectExpire;
    @SerializedName("object_expire_dt")
    @Expose
    private String objectExpireDt;
    @SerializedName("manager_id")
    @Expose
    private String managerId;
    @SerializedName("dt_server")
    @Expose
    private String dtServer;
    @SerializedName("dt_tracker")
    @Expose
    private String dtTracker;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("altitude")
    @Expose
    private String altitude;
    @SerializedName("angle")
    @Expose
    private String angle;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("loc_valid")
    @Expose
    private String locValid;
    @SerializedName("params")
    @Expose
    private String params;
    @SerializedName("dt_last_stop")
    @Expose
    private String dtLastStop;
    @SerializedName("dt_last_idle")
    @Expose
    private String dtLastIdle;
    @SerializedName("dt_last_move")
    @Expose
    private String dtLastMove;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("map_arrows")
    @Expose
    private String mapArrows;
    @SerializedName("map_icon")
    @Expose
    private String mapIcon;
    @SerializedName("tail_color")
    @Expose
    private String tailColor;
    @SerializedName("tail_points")
    @Expose
    private String tailPoints;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("sim_number")
    @Expose
    private String simNumber;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("vin")
    @Expose
    private String vin;
    @SerializedName("plate_number")
    @Expose
    private String plateNumber;
    @SerializedName("odometer_type")
    @Expose
    private String odometerType;
    @SerializedName("engine_hours_type")
    @Expose
    private String engineHoursType;
    @SerializedName("odometer")
    @Expose
    private String odometer;
    @SerializedName("engine_hours")
    @Expose
    private String engineHours;
    @SerializedName("fcr")
    @Expose
    private String fcr;
    @SerializedName("time_adj")
    @Expose
    private String timeAdj;
    @SerializedName("accuracy")
    @Expose
    private String accuracy;
    @SerializedName("dt_chat")
    @Expose
    private String dtChat;
    @SerializedName("device_address")
    @Expose
    private String device_address;
    @SerializedName("object_status")
    @Expose
    private ObjectStatusPOJO objectStatusPOJO;
    @SerializedName("cn")
    @Expose
    private Integer cn;
    @SerializedName("st")
    @Expose
    private String st;
    @SerializedName("object_type")
    @Expose
    private String object_type;
    private DeviceParamPOJO deviceParamPOJO;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getNetProtocol() {
        return netProtocol;
    }

    public void setNetProtocol(String netProtocol) {
        this.netProtocol = netProtocol;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getObjectExpire() {
        return objectExpire;
    }

    public void setObjectExpire(String objectExpire) {
        this.objectExpire = objectExpire;
    }

    public String getObjectExpireDt() {
        return objectExpireDt;
    }

    public void setObjectExpireDt(String objectExpireDt) {
        this.objectExpireDt = objectExpireDt;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getDtServer() {
        return dtServer;
    }

    public void setDtServer(String dtServer) {
        this.dtServer = dtServer;
    }

    public String getDtTracker() {
//        if (dtTracker != null) {
//            return UtilityFunction.parseUTCToIST(dtTracker);
//        }
        return dtTracker;
    }

    public String getNormalDtrackerTime() {
        return dtTracker;
    }

    public void setDtTracker(String dtTracker) {
        this.dtTracker = dtTracker;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLocValid() {
        return locValid;
    }

    public void setLocValid(String locValid) {
        this.locValid = locValid;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getDtLastStop() {
        return dtLastStop;
    }

    public void setDtLastStop(String dtLastStop) {
        this.dtLastStop = dtLastStop;
    }

    public String getDtLastIdle() {
        return dtLastIdle;
    }

    public void setDtLastIdle(String dtLastIdle) {
        this.dtLastIdle = dtLastIdle;
    }

    public String getDtLastMove() {
        return dtLastMove;
    }

    public void setDtLastMove(String dtLastMove) {
        this.dtLastMove = dtLastMove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMapArrows() {
        return mapArrows;
    }

    public void setMapArrows(String mapArrows) {
        this.mapArrows = mapArrows;
    }

    public String getMapIcon() {
        return mapIcon;
    }

    public void setMapIcon(String mapIcon) {
        this.mapIcon = mapIcon;
    }

    public String getTailColor() {
        return tailColor;
    }

    public void setTailColor(String tailColor) {
        this.tailColor = tailColor;
    }

    public String getTailPoints() {
        return tailPoints;
    }

    public void setTailPoints(String tailPoints) {
        this.tailPoints = tailPoints;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getOdometerType() {
        return odometerType;
    }

    public void setOdometerType(String odometerType) {
        this.odometerType = odometerType;
    }

    public String getEngineHoursType() {
        return engineHoursType;
    }

    public void setEngineHoursType(String engineHoursType) {
        this.engineHoursType = engineHoursType;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(String engineHours) {
        this.engineHours = engineHours;
    }

    public String getFcr() {
        return fcr;
    }

    public void setFcr(String fcr) {
        this.fcr = fcr;
    }

    public String getTimeAdj() {
        return timeAdj;
    }

    public void setTimeAdj(String timeAdj) {
        this.timeAdj = timeAdj;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getDtChat() {
        return dtChat;
    }

    public void setDtChat(String dtChat) {
        this.dtChat = dtChat;
    }

    public String getDevice_address() {
        return device_address;
    }

    public void setDevice_address(String device_address) {
        this.device_address = device_address;
    }

//    public ObjectStatusPOJO getObjectStatusPOJO() {
//        return objectStatusPOJO;
//    }
//
//    public void setObjectStatusPOJO(ObjectStatusPOJO objectStatusPOJO) {
//        this.objectStatusPOJO = objectStatusPOJO;
//    }

    public Integer getCn() {
        return cn;
    }

    public void setCn(Integer cn) {
        this.cn = cn;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public DeviceParamPOJO getDeviceParamPOJO() {
        return deviceParamPOJO;
    }

    public void setDeviceParamPOJO(DeviceParamPOJO deviceParamPOJO) {
        this.deviceParamPOJO = deviceParamPOJO;
    }

    public ObjectStatusPOJO getObjectStatusPOJO() {
        return objectStatusPOJO;
    }

    public void setObjectStatusPOJO(ObjectStatusPOJO objectStatusPOJO) {
        this.objectStatusPOJO = objectStatusPOJO;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }
}
