package com.example.weatherproject.repository;

import com.example.weatherproject.entity.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Integer> {
    ArrayList<Weather> findAllByCityName(String cityName);
    @Transactional
    void deleteAllByCityName(String cityName);
}
