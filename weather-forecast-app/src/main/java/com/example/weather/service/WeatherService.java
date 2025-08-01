package com.example.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class WeatherService {

    public JsonNode getWeather(String city, String apiKey) {
        try {
            String urlString = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric",
                city, apiKey
            );

            System.out.println("Fetching weather data from: " + urlString);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("API Response Code: " + responseCode);

            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(inputStream);
                System.out.println("Weather Data: " + json.toPrettyString());

                return json;
            } else {
                System.err.println("API returned error. Response code: " + responseCode);
            }

        } catch (Exception e) {
            System.err.println("Exception occurred while fetching weather data:");
            e.printStackTrace();
        }

        // Return null on error, so controller handles it gracefully
        return null;
    }
}
