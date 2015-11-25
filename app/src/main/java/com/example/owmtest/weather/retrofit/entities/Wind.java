package com.example.owmtest.weather.retrofit.entities;

import com.google.gson.annotations.Expose;

public class Wind {

    /**
     * Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
     */
    @Expose
    private Double speed;

    /**
     * Wind direction, degrees (meteorological)
     */
    @Expose
    private Double deg;

    public Wind() {
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }
}
