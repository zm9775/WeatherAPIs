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

    @Autowired WeatherDao weatherDao;

    @BeforeEach
    void setUp() {
        sampleCenter.weatherSamples.setUp();
    }

    @Test
    void getWeatherStatusHistoryByCityName() {
        Page<Weather> cityWeatherHistory = weatherDao.getWeatherHistoryByCityName("Ahvaz", PageRequest.of(0, 5));
        ArrayList<Weather> weatherHistory = new ArrayList<>(cityWeatherHistory.getContent());
        Assertions.assertSame("Ahvaz", weatherHistory.get(0).getCityName());
        Assertions.assertSame("Ahvaz", weatherHistory.get(1).getCityName());
    }

    @Test
    void deleteAllWeatherStatusByCityName() {
        weatherDao.deleteWeatherHistoryByCityName("Tabriz");
        Page<Weather> cityWeatherHistory = weatherDao.getWeatherHistoryByCityName("Tabriz", PageRequest.of(0, 5));
        Assertions.assertTrue(cityWeatherHistory.isEmpty());
    }

    @Test
    void findCityNames(){
        ArrayList<String> cityNames = weatherDao.findCityNames();
        Assertions.assertEquals(3, cityNames.size());
        Assertions.assertTrue(cityNames.contains("Tabriz"));
        Assertions.assertTrue(cityNames.contains("Yazd"));
        Assertions.assertTrue(cityNames.contains("Ahvaz"));
    }

    @AfterEach
    void teardown() {
        sampleCenter.weatherSamples.clear();
    }
}
