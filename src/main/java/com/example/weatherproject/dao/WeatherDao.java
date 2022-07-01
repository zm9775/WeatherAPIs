package com.example.weatherproject.dao;

import com.example.weatherproject.entity.Weather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface WeatherDao {
    Weather saveOrUpdate(Weather weather);
    Page<Weather> getWeatherHistoryByCityName(String city, Pageable pageable);
    void deleteWeatherHistoryByCityName(String cityName);
    ArrayList<String> findCityNames();
}
