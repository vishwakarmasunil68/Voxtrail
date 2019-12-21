package com.voxtrail.voxtrail.pojo.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ObjectParamPOJO implements Serializable {
    @SerializedName("pump")
    @Expose
    private String pump;
    @SerializedName("track")
    @Expose
    private String track;
    @SerializedName("bats")
    @Expose
    private String bats;
    @SerializedName("acc")
    @Expose
    private String acc;
    @SerializedName("defense")
    @Expose
    private String defense;
    @SerializedName("batl")
    @Expose
    private String batl;
    @SerializedName("gpslev")
    @Expose
    private String gpslev;
    @SerializedName("mcc")
    @Expose
    private String mcc;
    @SerializedName("mnc")
    @Expose
    private String mnc;
    @SerializedName("lac")
    @Expose
    private String lac;
    @SerializedName("cellid")
    @Expose
    private String cellid;
    @SerializedName("gsmlev")
    @Expose
    private String gsmlev;
    @SerializedName("accv")
    @Expose
    private String accv;


    public String getPump() {
        return pump;
    }

    public void setPump(String pump) {
        this.pump = pump;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getBats() {
        return bats;
    }

    public void setBats(String bats) {
        this.bats = bats;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getBatl() {
        return batl;
    }

    public void setBatl(String batl) {
        this.batl = batl;
    }

    public String getGpslev() {
        return gpslev;
    }

    public void setGpslev(String gpslev) {
        this.gpslev = gpslev;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getGsmlev() {
        return gsmlev;
    }

    public void setGsmlev(String gsmlev) {
        this.gsmlev = gsmlev;
    }

    public String getAccv() {
        return accv;
    }

    public void setAccv(String accv) {
        this.accv = accv;
    }
}
