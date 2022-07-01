package com.example.weatherproject;

import com.example.weatherproject.dao.WeatherDao;
import com.example.weatherproject.service.WeatherService;
import com.example.weatherproject.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
@EnableScheduling
public class WeatherProjectApplication {
    @Autowired
    WeatherService weatherService;
    @Autowired
    WeatherDao weatherDao;

    public static void main(String[] args) {
        SpringApplication.run(WeatherProjectApplication.class, args);
        System.out.println("Every 5 minutes you can:");
        System.out.println("To use save the current weather status service for a city, enter (cws)");
        System.out.println("To use get weather status history service for a city, enter (wsh)");
        System.out.println("To use the delete weather status history service for a city, enter (dwsh)");
        System.out.println("If you don't want to use any service, enter (esc)");
    }

    @Scheduled(cron = "0 0/5 * * * *")
    private void getCurrentWeatherStatusAndHistory() {
        ArrayList<String> cityNames = weatherDao.findCityNames();
        if (!cityNames.isEmpty()) {
            for (String cityName : cityNames) {
                weatherService.getCurrentWeatherStatusForecast(cityName);
            }
        } else {
            weatherService.getCurrentWeatherStatusForecast("Tehran");
        }

        System.out.println("We saved received weather data from weatherapi' service in our database");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            switch (userInput) {
                case ("cws"): {
                    System.out.println("Enter city name to save weather status:");
                    String cityNameForSave = scanner.nextLine();
                    weatherService.getCurrentWeatherStatusForecast(cityNameForSave);
                    break;
                }
                case ("wsh"): {
                    System.out.println("Enter city name to get weather status history:");
                    String cityName = scanner.nextLine();
                    ArrayList<Weather> weatherHistory = weatherService.getWeatherStatusHistory(cityName);
                    for (Weather weather : weatherHistory) {
                        String cityWeatherStatus = weather.getCityName() + "\n" + weather.getLocalTime() + "\n"
                                + weather.getLastUpdated() + "\n" + weather.getTempC() + "\n" + weather.getTempF();
                        System.out.println(cityWeatherStatus);
                        System.out.println();
                    }
                    break;
                }
                case ("dwsh"): {
                    System.out.println("Enter city name to delete its weather status history:");
                    String cityNameToDelete = scanner.nextLine();
                    if (!cityNameToDelete.isEmpty()) {
                        ResponseEntity<Boolean> deleted = weatherService.deleteWeatherHistory(cityNameToDelete);
                        System.out.println(deleted.getBody() + " " + deleted.getStatusCode());
                    }
                    break;
                }
                case ("esc"):
                    System.out.println("you didn't use any service");
                    break;
                default:
                    System.out.println("Invalid user input");
                    break;
            }
        }
    }
}
