package com.voxtrail.voxtrail.pojo.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("account_expire")
    @Expose
    private String accountExpire;
    @SerializedName("account_expire_dt")
    @Expose
    private String accountExpireDt;
    @SerializedName("privileges")
    @Expose
    private String privileges;
    @SerializedName("manager_id")
    @Expose
    private String managerId;
    @SerializedName("manager_billing")
    @Expose
    private String managerBilling;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("sess_hash")
    @Expose
    private String sessHash;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("api")
    @Expose
    private String api;
    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("dt_reg")
    @Expose
    private String dtReg;
    @SerializedName("dt_login")
    @Expose
    private String dtLogin;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("notify_account_expire")
    @Expose
    private String notifyAccountExpire;
    @SerializedName("notify_object_expire")
    @Expose
    private String notifyObjectExpire;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("obj_add")
    @Expose
    private String objAdd;
    @SerializedName("obj_limit")
    @Expose
    private String objLimit;
    @SerializedName("obj_limit_num")
    @Expose
    private String objLimitNum;
    @SerializedName("obj_days")
    @Expose
    private String objDays;
    @SerializedName("obj_days_dt")
    @Expose
    private String objDaysDt;
    @SerializedName("obj_edit")
    @Expose
    private String objEdit;
    @SerializedName("obj_history_clear")
    @Expose
    private String objHistoryClear;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("dst")
    @Expose
    private String dst;
    @SerializedName("dst_start")
    @Expose
    private String dstStart;
    @SerializedName("dst_end")
    @Expose
    private String dstEnd;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("map_sp")
    @Expose
    private String mapSp;
    @SerializedName("map_is")
    @Expose
    private String mapIs;
    @SerializedName("map_rc")
    @Expose
    private String mapRc;
    @SerializedName("map_rhc")
    @Expose
    private String mapRhc;
    @SerializedName("groups_collapsed")
    @Expose
    private String groupsCollapsed;
    @SerializedName("od")
    @Expose
    private String od;
    @SerializedName("ohc")
    @Expose
    private String ohc;
    @SerializedName("chat_notify")
    @Expose
    private String chatNotify;
    @SerializedName("sms_gateway_server")
    @Expose
    private String smsGatewayServer;
    @SerializedName("sms_gateway")
    @Expose
    private String smsGateway;
    @SerializedName("sms_gateway_type")
    @Expose
    private String smsGatewayType;
    @SerializedName("sms_gateway_url")
    @Expose
    private String smsGatewayUrl;
    @SerializedName("sms_gateway_identifier")
    @Expose
    private String smsGatewayIdentifier;
    @SerializedName("places_markers")
    @Expose
    private String placesMarkers;
    @SerializedName("places_routes")
    @Expose
    private String placesRoutes;
    @SerializedName("places_zones")
    @Expose
    private String placesZones;
    @SerializedName("usage_email_daily")
    @Expose
    private String usageEmailDaily;
    @SerializedName("usage_sms_daily")
    @Expose
    private String usageSmsDaily;
    @SerializedName("usage_api_daily")
    @Expose
    private String usageApiDaily;
    @SerializedName("usage_email_daily_cnt")
    @Expose
    private String usageEmailDailyCnt;
    @SerializedName("usage_sms_daily_cnt")
    @Expose
    private String usageSmsDailyCnt;
    @SerializedName("usage_api_daily_cnt")
    @Expose
    private String usageApiDailyCnt;
    @SerializedName("dt_usage_d")
    @Expose
    private String dtUsageD;
    @SerializedName("profile_pic")
    @Expose
    private String profile_pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAccountExpire() {
        return accountExpire;
    }

    public void setAccountExpire(String accountExpire) {
        this.accountExpire = accountExpire;
    }

    public String getAccountExpireDt() {
        return accountExpireDt;
    }

    public void setAccountExpireDt(String accountExpireDt) {
        this.accountExpireDt = accountExpireDt;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerBilling() {
        return managerBilling;
    }

    public void setManagerBilling(String managerBilling) {
        this.managerBilling = managerBilling;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessHash() {
        return sessHash;
    }

    public void setSessHash(String sessHash) {
        this.sessHash = sessHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDtReg() {
        return dtReg;
    }

    public void setDtReg(String dtReg) {
        this.dtReg = dtReg;
    }

    public String getDtLogin() {
        return dtLogin;
    }

    public void setDtLogin(String dtLogin) {
        this.dtLogin = dtLogin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNotifyAccountExpire() {
        return notifyAccountExpire;
    }

    public void setNotifyAccountExpire(String notifyAccountExpire) {
        this.notifyAccountExpire = notifyAccountExpire;
    }

    public String getNotifyObjectExpire() {
        return notifyObjectExpire;
    }

    public void setNotifyObjectExpire(String notifyObjectExpire) {
        this.notifyObjectExpire = notifyObjectExpire;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getObjAdd() {
        return objAdd;
    }

    public void setObjAdd(String objAdd) {
        this.objAdd = objAdd;
    }

    public String getObjLimit() {
        return objLimit;
    }

    public void setObjLimit(String objLimit) {
        this.objLimit = objLimit;
    }

    public String getObjLimitNum() {
        return objLimitNum;
    }

    public void setObjLimitNum(String objLimitNum) {
        this.objLimitNum = objLimitNum;
    }

    public String getObjDays() {
        return objDays;
    }

    public void setObjDays(String objDays) {
        this.objDays = objDays;
    }

    public String getObjDaysDt() {
        return objDaysDt;
    }

    public void setObjDaysDt(String objDaysDt) {
        this.objDaysDt = objDaysDt;
    }

    public String getObjEdit() {
        return objEdit;
    }

    public void setObjEdit(String objEdit) {
        this.objEdit = objEdit;
    }

    public String getObjHistoryClear() {
        return objHistoryClear;
    }

    public void setObjHistoryClear(String objHistoryClear) {
        this.objHistoryClear = objHistoryClear;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getDstStart() {
        return dstStart;
    }

    public void setDstStart(String dstStart) {
        this.dstStart = dstStart;
    }

    public String getDstEnd() {
        return dstEnd;
    }

    public void setDstEnd(String dstEnd) {
        this.dstEnd = dstEnd;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getMapSp() {
        return mapSp;
    }

    public void setMapSp(String mapSp) {
        this.mapSp = mapSp;
    }

    public String getMapIs() {
        return mapIs;
    }

    public void setMapIs(String mapIs) {
        this.mapIs = mapIs;
    }

    public String getMapRc() {
        return mapRc;
    }

    public void setMapRc(String mapRc) {
        this.mapRc = mapRc;
    }

    public String getMapRhc() {
        return mapRhc;
    }

    public void setMapRhc(String mapRhc) {
        this.mapRhc = mapRhc;
    }

    public String getGroupsCollapsed() {
        return groupsCollapsed;
    }

    public void setGroupsCollapsed(String groupsCollapsed) {
        this.groupsCollapsed = groupsCollapsed;
    }

    public String getOd() {
        return od;
    }

    public void setOd(String od) {
        this.od = od;
    }

    public String getOhc() {
        return ohc;
    }

    public void setOhc(String ohc) {
        this.ohc = ohc;
    }

    public String getChatNotify() {
        return chatNotify;
    }

    public void setChatNotify(String chatNotify) {
        this.chatNotify = chatNotify;
    }

    public String getSmsGatewayServer() {
        return smsGatewayServer;
    }

    public void setSmsGatewayServer(String smsGatewayServer) {
        this.smsGatewayServer = smsGatewayServer;
    }

    public String getSmsGateway() {
        return smsGateway;
    }

    public void setSmsGateway(String smsGateway) {
        this.smsGateway = smsGateway;
    }

    public String getSmsGatewayType() {
        return smsGatewayType;
    }

    public void setSmsGatewayType(String smsGatewayType) {
        this.smsGatewayType = smsGatewayType;
    }

    public String getSmsGatewayUrl() {
        return smsGatewayUrl;
    }

    public void setSmsGatewayUrl(String smsGatewayUrl) {
        this.smsGatewayUrl = smsGatewayUrl;
    }

    public String getSmsGatewayIdentifier() {
        return smsGatewayIdentifier;
    }

    public void setSmsGatewayIdentifier(String smsGatewayIdentifier) {
        this.smsGatewayIdentifier = smsGatewayIdentifier;
    }

    public String getPlacesMarkers() {
        return placesMarkers;
    }

    public void setPlacesMarkers(String placesMarkers) {
        this.placesMarkers = placesMarkers;
    }

    public String getPlacesRoutes() {
        return placesRoutes;
    }

    public void setPlacesRoutes(String placesRoutes) {
        this.placesRoutes = placesRoutes;
    }

    public String getPlacesZones() {
        return placesZones;
    }

    public void setPlacesZones(String placesZones) {
        this.placesZones = placesZones;
    }

    public String getUsageEmailDaily() {
        return usageEmailDaily;
    }

    public void setUsageEmailDaily(String usageEmailDaily) {
        this.usageEmailDaily = usageEmailDaily;
    }

    public String getUsageSmsDaily() {
        return usageSmsDaily;
    }

    public void setUsageSmsDaily(String usageSmsDaily) {
        this.usageSmsDaily = usageSmsDaily;
    }

    public String getUsageApiDaily() {
        return usageApiDaily;
    }

    public void setUsageApiDaily(String usageApiDaily) {
        this.usageApiDaily = usageApiDaily;
    }

    public String getUsageEmailDailyCnt() {
        return usageEmailDailyCnt;
    }

    public void setUsageEmailDailyCnt(String usageEmailDailyCnt) {
        this.usageEmailDailyCnt = usageEmailDailyCnt;
    }

    public String getUsageSmsDailyCnt() {
        return usageSmsDailyCnt;
    }

    public void setUsageSmsDailyCnt(String usageSmsDailyCnt) {
        this.usageSmsDailyCnt = usageSmsDailyCnt;
    }

    public String getUsageApiDailyCnt() {
        return usageApiDailyCnt;
    }

    public void setUsageApiDailyCnt(String usageApiDailyCnt) {
        this.usageApiDailyCnt = usageApiDailyCnt;
    }

    public String getDtUsageD() {
        return dtUsageD;
    }

    public void setDtUsageD(String dtUsageD) {
        this.dtUsageD = dtUsageD;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
