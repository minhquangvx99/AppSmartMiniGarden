package com.example.iot_smartminigarden.model;

import com.google.gson.annotations.SerializedName;

public class Led {
    @SerializedName("device_id")
    private int id;
    @SerializedName("value")
    private int value;

    public Led(int id, int value) {
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Led{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

    public static String URL = "http://localhost:8080/openLight?id=16&statusLed=on";
}

