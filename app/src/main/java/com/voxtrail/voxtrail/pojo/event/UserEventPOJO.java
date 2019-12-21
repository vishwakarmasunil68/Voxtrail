package com.voxtrail.voxtrail.pojo.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserEventPOJO implements Serializable {
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("duration_from_last_event")
    @Expose
    private String durationFromLastEvent;
    @SerializedName("duration_from_last_event_minutes")
    @Expose
    private String durationFromLastEventMinutes;
    @SerializedName("week_days")
    @Expose
    private String weekDays;
    @SerializedName("day_time")
    @Expose
    private String dayTime;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("checked_value")
    @Expose
    private String checkedValue;
    @SerializedName("route_trigger")
    @Expose
    private String routeTrigger;
    @SerializedName("zone_trigger")
    @Expose
    private String zoneTrigger;
    @SerializedName("routes")
    @Expose
    private String routes;
    @SerializedName("zones")
    @Expose
    private String zones;
    @SerializedName("notify_system")
    @Expose
    private String notifySystem;
    @SerializedName("notify_email")
    @Expose
    private String notifyEmail;
    @SerializedName("notify_email_address")
    @Expose
    private String notifyEmailAddress;
    @SerializedName("notify_sms")
    @Expose
    private String notifySms;
    @SerializedName("notify_sms_number")
    @Expose
    private String notifySmsNumber;
    @SerializedName("notify_arrow")
    @Expose
    private String notifyArrow;
    @SerializedName("notify_arrow_color")
    @Expose
    private String notifyArrowColor;
    @SerializedName("notify_ohc")
    @Expose
    private String notifyOhc;
    @SerializedName("notify_ohc_color")
    @Expose
    private String notifyOhcColor;
    @SerializedName("email_template_id")
    @Expose
    private String emailTemplateId;
    @SerializedName("sms_template_id")
    @Expose
    private String smsTemplateId;
    @SerializedName("cmd_send")
    @Expose
    private String cmdSend;
    @SerializedName("cmd_gateway")
    @Expose
    private String cmdGateway;
    @SerializedName("cmd_type")
    @Expose
    private String cmdType;
    @SerializedName("cmd_string")
    @Expose
    private String cmdString;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDurationFromLastEvent() {
        return durationFromLastEvent;
    }

    public void setDurationFromLastEvent(String durationFromLastEvent) {
        this.durationFromLastEvent = durationFromLastEvent;
    }

    public String getDurationFromLastEventMinutes() {
        return durationFromLastEventMinutes;
    }

    public void setDurationFromLastEventMinutes(String durationFromLastEventMinutes) {
        this.durationFromLastEventMinutes = durationFromLastEventMinutes;
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCheckedValue() {
        return checkedValue;
    }

    public void setCheckedValue(String checkedValue) {
        this.checkedValue = checkedValue;
    }

    public String getRouteTrigger() {
        return routeTrigger;
    }

    public void setRouteTrigger(String routeTrigger) {
        this.routeTrigger = routeTrigger;
    }

    public String getZoneTrigger() {
        return zoneTrigger;
    }

    public void setZoneTrigger(String zoneTrigger) {
        this.zoneTrigger = zoneTrigger;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public String getZones() {
        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }

    public String getNotifySystem() {
        return notifySystem;
    }

    public void setNotifySystem(String notifySystem) {
        this.notifySystem = notifySystem;
    }

    public String getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    public String getNotifyEmailAddress() {
        return notifyEmailAddress;
    }

    public void setNotifyEmailAddress(String notifyEmailAddress) {
        this.notifyEmailAddress = notifyEmailAddress;
    }

    public String getNotifySms() {
        return notifySms;
    }

    public void setNotifySms(String notifySms) {
        this.notifySms = notifySms;
    }

    public String getNotifySmsNumber() {
        return notifySmsNumber;
    }

    public void setNotifySmsNumber(String notifySmsNumber) {
        this.notifySmsNumber = notifySmsNumber;
    }

    public String getNotifyArrow() {
        return notifyArrow;
    }

    public void setNotifyArrow(String notifyArrow) {
        this.notifyArrow = notifyArrow;
    }

    public String getNotifyArrowColor() {
        return notifyArrowColor;
    }

    public void setNotifyArrowColor(String notifyArrowColor) {
        this.notifyArrowColor = notifyArrowColor;
    }

    public String getNotifyOhc() {
        return notifyOhc;
    }

    public void setNotifyOhc(String notifyOhc) {
        this.notifyOhc = notifyOhc;
    }

    public String getNotifyOhcColor() {
        return notifyOhcColor;
    }

    public void setNotifyOhcColor(String notifyOhcColor) {
        this.notifyOhcColor = notifyOhcColor;
    }

    public String getEmailTemplateId() {
        return emailTemplateId;
    }

    public void setEmailTemplateId(String emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
    }

    public String getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(String smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    public String getCmdSend() {
        return cmdSend;
    }

    public void setCmdSend(String cmdSend) {
        this.cmdSend = cmdSend;
    }

    public String getCmdGateway() {
        return cmdGateway;
    }

    public void setCmdGateway(String cmdGateway) {
        this.cmdGateway = cmdGateway;
    }

    public String getCmdType() {
        return cmdType;
    }

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public String getCmdString() {
        return cmdString;
    }

    public void setCmdString(String cmdString) {
        this.cmdString = cmdString;
    }
}
