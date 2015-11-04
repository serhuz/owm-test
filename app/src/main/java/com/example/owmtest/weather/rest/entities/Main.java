package com.example.owmtest.weather.rest.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    /**
     * Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     */
    private Double temp;

    /**
     * Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
     */
    private Double pressure;

    /**
     * Humidity, %
     */
    private Integer humidity;

    /**
     * Minimum temperature at the moment.
     * This is deviation from current temp that is possible for large cities and megalopolises
     * geographically expanded (use these parameter optionally).
     * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     */
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;

    /**
     * Maximum temperature at the moment.
     * This is deviation from current temp that is possible for large cities and megalopolises
     * geographically expanded (use these parameter optionally).
     * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     */
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;

    /**
     * Atmospheric pressure on the sea level, hPa
     */
    @SerializedName("sea_level")
    @Expose
    private Double pressureSeaLevel;

    /**
     * Atmospheric pressure on the ground level, hPa
     */
    @SerializedName("grnd_level")
    @Expose
    private Double pressureGroundLevel;

    public Main() {
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getPressureSeaLevel() {
        return pressureSeaLevel;
    }

    public void setPressureSeaLevel(Double pressureSeaLevel) {
        this.pressureSeaLevel = pressureSeaLevel;
    }

    public Double getPressureGroundLevel() {
        return pressureGroundLevel;
    }

    public void setPressureGroundLevel(Double pressureGroundLevel) {
        this.pressureGroundLevel = pressureGroundLevel;
    }
}
