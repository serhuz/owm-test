package com.example.owmtest.retrofit.entities;

import com.google.gson.annotations.Expose;

import java.util.List;

public class OwmResponse {

    @Expose
    private Coord coord;

    @Expose
    private List<Weather> weather;

    /**
     * Internal parameter
     */
    @Expose
    private String base;

    @Expose
    private Main main;

    @Expose
    private Wind wind;

    @Expose
    private Clouds clouds;

    @Expose
    private Precipitation rain;

    @Expose
    private Precipitation snow;

    @Expose
    private Sys sys;

    /**
     * Internal parameter
     */
    @Expose
    private Integer cod;

    /**
     * City name
     */
    @Expose
    private String name;

    /**
     * City ID
     */
    @Expose
    private String id;

    /**
     * Time of data calculation, UNIX, UTC
     */
    @Expose
    private Long dt;

    public OwmResponse() {
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Precipitation getRain() {
        return rain;
    }

    public void setRain(Precipitation rain) {
        this.rain = rain;
    }

    public Precipitation getSnow() {
        return snow;
    }

    public void setSnow(Precipitation snow) {
        this.snow = snow;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }
}
