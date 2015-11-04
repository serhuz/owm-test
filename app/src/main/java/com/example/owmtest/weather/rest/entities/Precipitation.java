package com.example.owmtest.weather.rest.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Precipitation {

    @SerializedName("3h")
    @Expose
    private Double volume;

    public Precipitation() {
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
