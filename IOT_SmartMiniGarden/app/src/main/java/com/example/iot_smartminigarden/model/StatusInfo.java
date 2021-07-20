package com.example.iot_smartminigarden.model;

import com.google.gson.annotations.SerializedName;

public class StatusInfo {
    @SerializedName("id")
    private int id;
    @SerializedName("statusLed")
    private String statusLed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusLed() {
        return statusLed;
    }

    public void setStatusLed(String statusLed) {
        this.statusLed = statusLed;
    }
}
