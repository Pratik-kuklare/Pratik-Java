package com.example.weather.controller;

import com.example.weather.service.WeatherService;
import com.example.weather.model.ForecastDay;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Value("${weather.api.key}")
    private String apiKey;

    @GetMapping("/")
    public String getWeather(@RequestParam(required = false, defaultValue = "Mumbai") String city, Model model) {
        JsonNode forecastData = weatherService.getWeather(city, apiKey);

        if (forecastData == null || !forecastData.has("list")) {
            model.addAttribute("error", "Error fetching weather data. City not found or API error.");
            return "weather";
        }

        try {
            List<ForecastDay> forecastDays = new ArrayList<>();
            JsonNode list = forecastData.get("list");
            
            // Get forecast for 5 days (every 8th item represents next day at same time)
            for (int i = 0; i < Math.min(list.size(), 40); i += 8) {
                JsonNode dayData = list.get(i);
                
                long timestamp = dayData.get("dt").asLong();
                LocalDateTime dateTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
                String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("EEEE, MMM dd"));
                
                double temp = dayData.get("main").get("temp").asDouble();
                double feelsLike = dayData.get("main").get("feels_like").asDouble();
                double tempMin = dayData.get("main").get("temp_min").asDouble();
                double tempMax = dayData.get("main").get("temp_max").asDouble();
                String description = dayData.get("weather").get(0).get("description").asText();
                int humidity = dayData.get("main").get("humidity").asInt();
                
                forecastDays.add(new ForecastDay(formattedDate, temp, feelsLike, tempMin, tempMax, description, humidity));
            }
            
            model.addAttribute("forecastDays", forecastDays);
            model.addAttribute("city", city);
            
        } catch (Exception e) {
            model.addAttribute("error", "Error processing weather data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "weather";
    }
}
