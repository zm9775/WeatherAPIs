package com.example.weatherproject.service;

import com.example.weatherproject.sample.SampleCenter;
import com.example.weatherproject.entity.Weather;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@SpringBootTest
class WeatherServiceTests {
    @Autowired
    WeatherService weatherService;

    @Autowired
    SampleCenter sampleCenter;

    @BeforeEach
    void setUp() {
        sampleCenter.weatherSamples.setUp();
    }

    @Test
    void getAllWeatherStatusByCityName() {
        Weather sampleWeather = sampleCenter.weatherSamples.data.get(2);
        ArrayList<Weather> weatherStatusHistory = weatherService.getWeatherStatusHistory(sampleWeather.getCityName());
        Assertions.assertSame(sampleWeather.getCityName(), weatherStatusHistory.get(0).getCityName());
        Assertions.assertSame(sampleWeather.getCityName(), weatherStatusHistory.get(1).getCityName());
    }

    @Test
    void deleteAllWeatherStatusByCityName() {
        Weather sampleWeather = sampleCenter.weatherSamples.data.get(0);
        Assertions.assertEquals(true, weatherService.deleteWeatherHistory(sampleWeather.getCityName()).getBody());
    }

    @Test
    void getCurrentWeatherStatus() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/weather/current/Tahran";

        ResponseEntity<Weather> response = restTemplate.getForEntity(
                url, Weather.class
        );
        Assertions.assertNotNull(response);
    }

    @AfterEach
    void teardown() {
        sampleCenter.weatherSamples.clear();
    }
}
