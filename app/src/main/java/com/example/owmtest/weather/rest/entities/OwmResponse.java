package com.example.owmtest.weather.rest.entities;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.owmtest.BR;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class OwmResponse extends BaseObservable {

    @Expose
    private Coord coord;

    @Expose
    private List<Weather> weather = new ArrayList<>();

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

    {
        weather.add(new Weather());
    }

    @Bindable
    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
        notifyPropertyChanged(BR.coord);
    }

    @Bindable
    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
        notifyPropertyChanged(BR.weather);
    }

    @Bindable
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
        notifyPropertyChanged(BR.base);
    }

    @Bindable
    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
        notifyPropertyChanged(BR.main);
    }

    @Bindable
    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
        notifyPropertyChanged(BR.wind);
    }

    @Bindable
    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
        notifyPropertyChanged(BR.clouds);
    }

    @Bindable
    public Precipitation getRain() {
        return rain;
    }

    public void setRain(Precipitation rain) {
        this.rain = rain;
        notifyPropertyChanged(BR.rain);
    }

    @Bindable
    public Precipitation getSnow() {
        return snow;
    }

    public void setSnow(Precipitation snow) {
        this.snow = snow;
        notifyPropertyChanged(BR.snow);
    }

    @Bindable
    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
        notifyPropertyChanged(BR.sys);
    }

    @Bindable
    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
        notifyPropertyChanged(BR.cod);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
        notifyPropertyChanged(BR.dt);
    }
}
