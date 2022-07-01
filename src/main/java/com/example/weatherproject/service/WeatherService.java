package com.example.weatherproject.service;

import com.example.weatherproject.entity.Weather;
import com.example.weatherproject.service.logic.WeatherLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/weather")
public class WeatherService {
    @Autowired
    private WeatherLogic weatherLogic;

    @GetMapping(value = "/current/{city}", produces = "application/json")
    public Weather getCurrentWeatherStatusForecast(
            @PathVariable(value = "city") String city
    ) {
        return weatherLogic.getCurrentWeatherStatusForecast(city);
    }

    @GetMapping(value = "/history/{city}", produces = "application/json")
    public ArrayList<Weather> getWeatherStatusHistory(
            @PathVariable(value = "city") String city
    ) {
        return weatherLogic.getWeatherStatusHistory(city);
    }

    @DeleteMapping(value = "/delete/{city}", produces = "application/json")
    public ResponseEntity<Boolean> deleteWeatherHistory(
            @PathVariable(value = "city") String city
    ) {
        return weatherLogic.deleteWeatherHistory(city);
    }
}
