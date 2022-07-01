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
        ArrayList<Weather> tabriz = weatherService.getWeatherStatusHistory("Ahvaz");
        Assertions.assertSame("Ahvaz", tabriz.get(0).getCityName());
        Assertions.assertSame("Ahvaz", tabriz.get(1).getCityName());
    }

    @Test
    void deleteAllWeatherStatusByCityName() {
        Assertions.assertEquals(true, weatherService.deleteWeatherHistory("Tabriz").getBody());
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
