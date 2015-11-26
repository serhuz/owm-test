package com.example.owmtest.retrofit.entities;

import com.google.gson.annotations.Expose;

public class Coord {

    /**
     * City geo location, latitude
     */
    @Expose
    private Double lat;

    /**
     * City geo location, longitude
     */
    @Expose
    private Double lon;

    public Coord() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
