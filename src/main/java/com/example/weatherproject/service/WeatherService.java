package com.example.weatherproject.service;

import com.example.weatherproject.entity.Weather;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface WeatherService {
    Weather saveOrUpdate(Weather weather);
    ArrayList<Weather> getWeatherHistoryByCityName(String city);
    void deleteWeatherHistoryByCityName(String cityName);
}
