package com.example.iot_smartminigarden.model;

import com.google.gson.annotations.SerializedName;

public class Led {
    @SerializedName("device_id")
    private int id;
    @SerializedName("value")
    private String value;

    public Led(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public Led() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Led{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}

