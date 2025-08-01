package com.example.weather.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ForecastDay {
    private String date;
    private double temperature;
    private double feelsLike;
    private double tempMin;
    private double tempMax;
    private String description;
    private int humidity;
    
    public ForecastDay(String date, double temperature, double feelsLike, 
                      double tempMin, double tempMax, String description, int humidity) {
        this.date = date;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.description = description;
        this.humidity = humidity;
    }
    
    // Getters
    public String getDate() { return date; }
    public double getTemperature() { return temperature; }
    public double getFeelsLike() { return feelsLike; }
    public double getTempMin() { return tempMin; }
    public double getTempMax() { return tempMax; }
    public String getDescription() { return description; }
    public int getHumidity() { return humidity; }
}