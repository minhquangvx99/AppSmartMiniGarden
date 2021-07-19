package com.example.iot_smartminigarden.model;

import com.google.gson.annotations.SerializedName;

public class WeatherInfo {
    @SerializedName("id")
    private float id;
    @SerializedName("time")
    private String time;
    @SerializedName("temperature")
    private float temperature;
    @SerializedName("humidity")
    private float humidity;

    public float getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}
