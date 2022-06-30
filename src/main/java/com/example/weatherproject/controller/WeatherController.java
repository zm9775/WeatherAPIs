package com.example.weatherproject.controller;

import com.example.weatherproject.entity.Weather;
import com.example.weatherproject.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = "/current/{city}", produces = "application/json")
    public Weather getCurrentWeatherForecast(
            @PathVariable(value = "city") String city
    ) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.weatherapi.com/v1/current.json?key=ba8ba53f3e37464c86f140028222906&q=" + city +
                "&aqi=no";

        ResponseEntity<Object> response = restTemplate.getForEntity(
                url, Object.class
        );

        JSONObject jsonObject = new JSONObject(response);
        Object bodyObj = jsonObject.toMap().get("body");
        Object location = ((HashMap) bodyObj).get("location");
        Object current = ((HashMap) bodyObj).get("current");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localTime = LocalDateTime.parse(((HashMap) location).get("localtime").toString(), formatter);
        LocalDateTime lastUpdated = LocalDateTime.parse(((HashMap) current).get("last_updated").toString(), formatter);

        Weather weather = new Weather(
                ((HashMap) location).get("name").toString(),
                localTime,
                lastUpdated,
                (Double) ((HashMap) current).get("temp_c"),
                (Double) ((HashMap) current).get("temp_f")
        );
        return weatherService.saveOrUpdate(weather);
    }

    @GetMapping(value = "/history/{city}", produces = "application/json")
    public ArrayList<Weather> getWeatherHistory(
            @PathVariable(value = "city") String city
    ) {
        ArrayList<Weather> weathers = weatherService.getWeatherHistoryByCityName(city);
        if (weathers.isEmpty())
            throw new RestException("data doesn't exists", NOT_FOUND);
        return weatherService.getWeatherHistoryByCityName(city);
    }

    @DeleteMapping(value = "/delete/{city}", produces = "application/json")
    public ResponseEntity<Boolean> deleteWeatherHistory(
            @PathVariable(value = "city") String city
    ) {
        ArrayList<Weather> weathersBeforeDelete = weatherService.getWeatherHistoryByCityName(city);
        if (weathersBeforeDelete.isEmpty())
            return new ResponseEntity<>(false, NOT_FOUND);

        weatherService.deleteWeatherHistoryByCityName(city);
        ArrayList<Weather> weathersAfterDelete = weatherService.getWeatherHistoryByCityName(city);
        if (weathersAfterDelete.isEmpty())
            return new ResponseEntity<>(true, OK);
        else return new ResponseEntity<>(false, NOT_MODIFIED);
    }
}
