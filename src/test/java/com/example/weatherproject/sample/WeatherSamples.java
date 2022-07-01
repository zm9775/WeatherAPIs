package com.example.weatherproject.sample;

import com.example.weatherproject.dao.WeatherDao;
import com.example.weatherproject.entity.Weather;
import com.example.weatherproject.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class WeatherSamples {
    @Autowired
    WeatherDao weatherDao;

    @Autowired
    WeatherRepository weatherRepository;

    ArrayList<Weather> data = new ArrayList<>();
    boolean isInitialized = false;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void setUp() {
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
        Weather weather4 = new Weather(
                "Ahvaz",
                LocalDateTime.parse("2022-06-30 18:20", formatter),
                LocalDateTime.parse("2022-06-30 17:20", formatter),
                50.0,
                200.9
        );
        data.add(weather1);
        data.add(weather2);
        data.add(weather3);
        data.add(weather4);
        weatherRepository.saveAll(data);
    }

    public void clear() {
        isInitialized = false;
        weatherRepository.deleteAll(data);
    }
}
