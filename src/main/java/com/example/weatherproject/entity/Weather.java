package com.example.weatherproject.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Weather {
    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column
    private String cityName;
    @Column
    private LocalDateTime localTime;
    @Column
    private LocalDateTime lastUpdated;
    @Column
    private double tempC;
    @Column
    private double tempF;

    public Weather() {

    }

    public Weather(String cityName, LocalDateTime localTime, LocalDateTime lastUpdated, double tempC, double tempF) {
        this.cityName = cityName;
        this.localTime = localTime;
        this.lastUpdated = lastUpdated;
        this.tempC = tempC;
        this.tempF = tempF;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public LocalDateTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalDateTime localTime) {
        this.localTime = localTime;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getTempC() {
        return tempC;
    }

    public void setTempC(float tempC) {
        this.tempC = tempC;
    }

    public double getTempF() {
        return tempF;
    }

    public void setTempF(float tempF) {
        this.tempF = tempF;
    }
}
