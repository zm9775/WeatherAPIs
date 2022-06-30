package com.example.weatherproject;

import com.example.weatherproject.controller.WeatherController;
import com.example.weatherproject.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
public class WeatherProjectApplication {
    @Autowired
    WeatherController weatherController;

    public static void main(String[] args) {
        SpringApplication.run(WeatherProjectApplication.class, args);
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void checkApi() {
        weatherController.getCurrentWeatherForecast("Tehran");
        ArrayList<Weather> tehranWeatherHistory = weatherController.getWeatherHistory("Tehran");
        for (Weather weather : tehranWeatherHistory) {
            String tehranWeather = weather.getCityName()+"\n"+weather.getLocalTime()+"\n"
                    +weather.getLastUpdated()+"\n"+weather.getTempC()+"\n"+weather.getTempF();
            System.out.println(tehranWeather);
            System.out.println();
        }
        
        weatherController.getCurrentWeatherForecast("Shiraz");
        ArrayList<Weather> shirazWeatherHistory = weatherController.getWeatherHistory("Shiraz");
        for (Weather weather : shirazWeatherHistory) {
            String shirazWeather = weather.getCityName()+"\n"+weather.getLocalTime()+"\n"
                    +weather.getLastUpdated()+"\n"+weather.getTempC()+"\n"+weather.getTempF();
            System.out.println(shirazWeather);
            System.out.println();
        }
        System.out.println("**********************");
    }
}
