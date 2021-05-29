package com.example.iot_smartminigarden.model;

import com.google.gson.annotations.SerializedName;

public class WeatherInfo {
    @SerializedName("id")
    private int id;
    @SerializedName("time")
    private String time;
    @SerializedName("temperature")
    private String temperature;
    @SerializedName("humidity")
    private String humidity;

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

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
