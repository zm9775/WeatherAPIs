package com.example.weatherproject.dao;

import com.example.weatherproject.entity.Weather;
import com.example.weatherproject.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WeatherDaoImpl implements WeatherDao {
    @Autowired
    WeatherRepository weatherRepository;

    @Override
    public Weather saveOrUpdate(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public Page<Weather> getWeatherHistoryByCityName(String city, Pageable pageable) {
        return weatherRepository.findAllByCityName(city, pageable);
    }

    @Override
    public void deleteWeatherHistoryByCityName(String cityName) {
        weatherRepository.deleteAllByCityName(cityName);
    }

    @Override
    public ArrayList<String> findCityNames() {
        return weatherRepository.findDistinctCityName();
    }

}
