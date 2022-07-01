package com.example.weatherproject.dao;

import com.example.weatherproject.entity.Weather;
import com.example.weatherproject.sample.SampleCenter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

@SpringBootTest
public class WeatherDaoTests {
    @Autowired
    SampleCenter sampleCenter;

    @Autowired
    WeatherDao weatherDao;

    @BeforeEach
    void setUp() {
        sampleCenter.weatherSamples.setUp();
    }

    @Test
    void getWeatherStatusHistoryByCityName() {
        Weather sampleWeather = sampleCenter.weatherSamples.data.get(2);
        Page<Weather> weatherStatusHistory = weatherDao.getWeatherHistoryByCityName(
                sampleWeather.getCityName(), PageRequest.of(0, 5)
        );
        ArrayList<Weather> weatherArrayList = new ArrayList<>(weatherStatusHistory.getContent());
        Assertions.assertSame(sampleWeather.getCityName(), weatherArrayList.get(0).getCityName());
        Assertions.assertSame(sampleWeather.getCityName(), weatherArrayList.get(1).getCityName());
    }

    @Test
    void deleteAllWeatherStatusByCityName() {
        Weather sampleWeather = sampleCenter.weatherSamples.data.get(0);
        weatherDao.deleteWeatherHistoryByCityName(sampleWeather.getCityName());
        Page<Weather> cityWeatherHistory = weatherDao.getWeatherHistoryByCityName(
                sampleWeather.getCityName(), PageRequest.of(0, 1)
        );
        Assertions.assertTrue(cityWeatherHistory.isEmpty());
    }

    @Test
    void findCityNames() {
        ArrayList<String> cityNames = weatherDao.findCityNames();
        Assertions.assertEquals(3, cityNames.size());
        Assertions.assertTrue(cityNames.contains(sampleCenter.weatherSamples.data.get(0).getCityName()));
        Assertions.assertTrue(cityNames.contains(sampleCenter.weatherSamples.data.get(1).getCityName()));
        Assertions.assertTrue(cityNames.contains(sampleCenter.weatherSamples.data.get(2).getCityName()));
    }

    @AfterEach
    void teardown() {
        sampleCenter.weatherSamples.clear();
    }
}
