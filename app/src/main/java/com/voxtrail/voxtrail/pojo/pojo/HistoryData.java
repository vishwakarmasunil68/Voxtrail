package com.voxtrail.voxtrail.pojo.pojo;

import java.io.Serializable;

public class HistoryData implements Serializable{
    String data;
    String type;

    public HistoryData(String data, String type) {
        this.data = data;
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HistoryData{" +
                "data='" + data + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
