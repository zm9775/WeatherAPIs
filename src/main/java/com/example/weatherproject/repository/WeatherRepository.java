package com.example.weatherproject.repository;

import com.example.weatherproject.entity.Weather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Integer> {
    Page<Weather> findAllByCityName(String cityName, Pageable pageable);
    @Transactional
    void deleteAllByCityName(String cityName);
    @Query("select distinct w.cityName from Weather as w")
    ArrayList<String> findDistinctCityName();
}
