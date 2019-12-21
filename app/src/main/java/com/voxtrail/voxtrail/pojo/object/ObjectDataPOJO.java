package com.voxtrail.voxtrail.pojo.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ObjectDataPOJO implements Serializable {
    @SerializedName("v")
    @Expose
    private Boolean v;
    @SerializedName("f")
    @Expose
    private Boolean f;
    @SerializedName("s")
    @Expose
    private Boolean s;
    @SerializedName("evt")
    @Expose
    private Boolean evt;
    @SerializedName("evtac")
    @Expose
    private Boolean evtac;
    @SerializedName("evtohc")
    @Expose
    private Boolean evtohc;
    @SerializedName("a")
    @Expose
    private String a;
    @SerializedName("l")
    @Expose
    private List<Object> l = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("object_type")
    @Expose
    private String objectType;
    @SerializedName("vehicle_type")
    @Expose
    private Object vehicleType;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("st")
    @Expose
    private String st;
    @SerializedName("ststr")
    @Expose
    private String ststr;
    @SerializedName("p")
    @Expose
    private String p;
    @SerializedName("cn")
    @Expose
    private Integer cn;
    @SerializedName("o")
    @Expose
    private Integer o;
    @SerializedName("eh")
    @Expose
    private String eh;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("plate_number")
    @Expose
    private String plate_number;
    @SerializedName("odometer")
    @Expose
    private String odometer;
    @SerializedName("d")
    @Expose
    private ObjectDetailPOJO objectDetailPOJO;

    public Boolean getV() {
        return v;
    }

    public void setV(Boolean v) {
        this.v = v;
    }

    public Boolean getF() {
        return f;
    }

    public void setF(Boolean f) {
        this.f = f;
    }

    public Boolean getS() {
        return s;
    }

    public void setS(Boolean s) {
        this.s = s;
    }

    public Boolean getEvt() {
        return evt;
    }

    public void setEvt(Boolean evt) {
        this.evt = evt;
    }

    public Boolean getEvtac() {
        return evtac;
    }

    public void setEvtac(Boolean evtac) {
        this.evtac = evtac;
    }

    public Boolean getEvtohc() {
        return evtohc;
    }

    public void setEvtohc(Boolean evtohc) {
        this.evtohc = evtohc;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public List<Object> getL() {
        return l;
    }

    public void setL(List<Object> l) {
        this.l = l;
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

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Object getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Object vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getStstr() {
        return ststr;
    }

    public void setStstr(String ststr) {
        this.ststr = ststr;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public Integer getCn() {
        return cn;
    }

    public void setCn(Integer cn) {
        this.cn = cn;
    }

    public Integer getO() {
        return o;
    }

    public void setO(Integer o) {
        this.o = o;
    }

    public String getEh() {
        return eh;
    }

    public void setEh(String eh) {
        this.eh = eh;
    }

    public ObjectDetailPOJO getObjectDetailPOJO() {
        return objectDetailPOJO;
    }

    public void setObjectDetailPOJO(ObjectDetailPOJO objectDetailPOJO) {
        this.objectDetailPOJO = objectDetailPOJO;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }
}
