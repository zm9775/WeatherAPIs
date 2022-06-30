package com.example.weatherproject;

import com.example.weatherproject.controller.WeatherController;
import com.example.weatherproject.entity.Weather;
import com.example.weatherproject.repository.WeatherRepository;
import com.example.weatherproject.service.WeatherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@SpringBootTest
class WeatherProjectApplicationTests {

    @Autowired
    WeatherService weatherService;

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    WeatherController weatherController;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    ArrayList<Weather> data = new ArrayList<>();
    boolean isInitialized = false;

    @BeforeEach
    void setUp() {
        if (isInitialized)
            return;
        isInitialized = true;
        generateData();
    }
    private void generateData(){
        Weather weather1 = new Weather(
                "Tabriz",
                LocalDateTime.parse("2022-06-30 17:20", formatter),
                LocalDateTime.parse("2022-06-30 16:20", formatter),
                40.0,
                104.9
        );
        Weather weather2 = new Weather(
                "Yazd",
                LocalDateTime.parse("2022-06-30 17:20", formatter),
                LocalDateTime.parse("2022-06-30 16:20", formatter),
                39.5,
                99.9
        );
        Weather weather3 = new Weather(
                "Ahvaz",
                LocalDateTime.parse("2022-06-30 17:20", formatter),
                LocalDateTime.parse("2022-06-30 16:20", formatter),
                41.0,
                107.9
        );
        data.add(weather1);
        data.add(weather2);
        data.add(weather3);
        weatherRepository.saveAll(data);
    }

    @Test
    void getAllWeatherByCityName() {
        ArrayList<Weather> tabriz = weatherController.getWeatherHistory("Tabriz");
        Assertions.assertSame("Tabriz", tabriz.get(0).getCityName());
    }

    @Test
    void deleteAllWeatherByCityName() {
        Assertions.assertEquals(true, weatherController.deleteWeatherHistory("Tabriz").getBody());
    }

    @Test
    void getCurrentWeather() {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8080/api/weather/current/Tahran";
//
//        ResponseEntity<Weather> response = restTemplate.getForEntity(
//                url, Weather.class
//        );
        Weather response = weatherController.getCurrentWeatherForecast("Tehran");
        Assertions.assertNotNull(response);
    }

    @AfterEach
    void teardown() {
        isInitialized = false;
        weatherRepository.deleteAll(data);
    }
}
