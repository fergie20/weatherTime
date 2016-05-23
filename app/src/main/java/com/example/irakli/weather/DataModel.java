package com.example.irakli.weather;

import java.util.Date;

/**
 * Created by Irakli on 21.05.2016.
 */
public class DataModel {

    private int dayTemperature;
    private int nightTemperature;
    private String weatherDescription;
    private String icon;
    private String dt;

    public DataModel(int dayTemperature, int nightTemperature, String weatherDescription, String icon, String dt) {
        this.dayTemperature = dayTemperature;
        this.nightTemperature = nightTemperature;
        this.weatherDescription = weatherDescription;
        this.icon = icon;
        this.dt = dt;
    }

    public int getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(int dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public int getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(int nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }
}
