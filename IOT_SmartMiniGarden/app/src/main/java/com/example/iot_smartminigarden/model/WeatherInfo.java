package com.example.iot_smartminigarden.model;

import com.google.gson.annotations.SerializedName;

public class WeatherInfo {
    @SerializedName("id")
    private int id;
    @SerializedName("time")
    private String time;
    @SerializedName("temperature")
    private int temperature;
    @SerializedName("humidity")
    private int humidity;

    public int getId() {
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

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
