package com.example.weatherproject.service.logic;

import com.example.weatherproject.dao.WeatherDao;
import com.example.weatherproject.entity.Weather;
import com.example.weatherproject.service.RestException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import static org.springframework.http.HttpStatus.*;

@Service
public class WeatherLogic {
    @Autowired
    private WeatherDao weatherDao;

    public Weather getCurrentWeatherStatusForecast(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.weatherapi.com/v1/current.json?key=ba8ba53f3e37464c86f140028222906&q=" + city +
                "&aqi=no";

        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(
                    url, Object.class
            );


            JSONObject jsonObject = new JSONObject(response);
            Object bodyObj = jsonObject.toMap().get("body");
            Object location = ((HashMap) bodyObj).get("location");
            Object current = ((HashMap) bodyObj).get("current");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime localTime = LocalDateTime.parse(((HashMap) location).get("localtime").toString(), formatter);
            LocalDateTime lastUpdated = LocalDateTime.parse(((HashMap) current).get("last_updated").toString(), formatter);

            Weather weather = new Weather(
                    ((HashMap) location).get("name").toString(),
                    localTime,
                    lastUpdated,
                    (Double) ((HashMap) current).get("temp_c"),
                    (Double) ((HashMap) current).get("temp_f")
            );
            return weatherDao.saveOrUpdate(weather);
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new RestException("No matching location found.", BAD_REQUEST);
        }

    }

    public ArrayList<Weather> getWeatherStatusHistory(String city) {
        Page<Weather> weathers = weatherDao.getWeatherHistoryByCityName(city, PageRequest.of(0, 10));
        if (weathers.isEmpty())
            throw new RestException("Data doesn't exists.", NOT_FOUND);
        return new ArrayList<>(weathers.getContent());
    }

    public ResponseEntity<Boolean> deleteWeatherHistory(String city) {
        Page<Weather> weathersBeforeDelete = weatherDao.getWeatherHistoryByCityName(city, PageRequest.of(0, 1));
        if (weathersBeforeDelete.isEmpty())
            return new ResponseEntity<>(false, NOT_FOUND);

        weatherDao.deleteWeatherHistoryByCityName(city);
        Page<Weather> weathersAfterDelete = weatherDao.getWeatherHistoryByCityName(city, PageRequest.of(0, 1));
        if (weathersAfterDelete.isEmpty())
            return new ResponseEntity<>(true, OK);
        else return new ResponseEntity<>(false, NOT_MODIFIED);
    }
}
