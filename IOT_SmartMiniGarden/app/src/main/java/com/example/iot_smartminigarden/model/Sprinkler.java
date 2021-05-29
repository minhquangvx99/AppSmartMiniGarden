package com.example.iot_smartminigarden.model;

import com.google.gson.annotations.SerializedName;

public class Sprinkler {
    @SerializedName("device_id")
    private int id;
    @SerializedName("value")
    private int value;

    public Sprinkler(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public Sprinkler() {
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
        return "Sprinkler{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

    //public static String URL = "localhost:3000/getStateBulb?id=1";
    public static String URL = "http://localhost:8080/getStateBulb?id=1";
}

