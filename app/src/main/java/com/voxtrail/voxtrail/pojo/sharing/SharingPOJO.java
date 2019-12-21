package com.voxtrail.voxtrail.pojo.sharing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SharingPOJO {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("unique_key")
    @Expose
    private String uniqueKey;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("imeis")
    @Expose
    private String imeis;
    @SerializedName("valid_to")
    @Expose
    private String validTo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImeis() {
        return imeis;
    }

    public void setImeis(String imeis) {
        this.imeis = imeis;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }
}
