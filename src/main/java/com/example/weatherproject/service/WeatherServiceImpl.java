package com.example.weatherproject.service;

import com.example.weatherproject.entity.Weather;
import com.example.weatherproject.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    WeatherRepository weatherRepository;

    @Override
    public Weather saveOrUpdate(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public ArrayList<Weather> getWeatherHistoryByCityName(String city) {
        return weatherRepository.findAllByCityName(city);
    }

    @Override
    public void deleteWeatherHistoryByCityName(String cityName) {
        weatherRepository.deleteAllByCityName(cityName);
    }

}
