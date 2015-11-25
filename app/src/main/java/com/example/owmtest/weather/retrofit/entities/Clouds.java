package com.example.owmtest.weather.retrofit.entities;

import com.google.gson.annotations.Expose;

public class Clouds {

    /**
     * Cloudiness, %
     */
    @Expose
    private Integer all;

    public Clouds() {
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }
}
