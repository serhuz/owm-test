package com.example.owmtest.retrofit.entities;

import com.google.gson.annotations.Expose;

public class Sys {

    /**
     * Internal parameter
     */
    @Expose
    private Integer type;

    /**
     * Internal parameter
     */
    @Expose
    private Integer id;

    /**
     * Internal parameter
     */
    @Expose
    private Double message;

    /**
     * Country code (GB, JP etc.)
     */
    @Expose
    private String country;

    /**
     * Sunrise time, unix, UTC
     */
    @Expose
    private Long sunrise;

    /**
     * Sunset time, unix, UTC
     */
    @Expose
    private Long sunset;

    public Sys() {
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }
}
