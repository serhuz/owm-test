package com.example.owmtest.weather.retrofit.entities;

import com.google.gson.annotations.Expose;

public class Weather {

    /**
     * Weather condition id
     */
    @Expose
    private Integer id;

    /**
     * Group of weather parameters (Rain, Snow, Extreme etc.)
     */
    @Expose
    private String main;

    /**
     * Weather condition within the group
     */
    @Expose
    private String description;

    /**
     * Weather icon id
     */
    @Expose
    private String icon;

    public Weather() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
