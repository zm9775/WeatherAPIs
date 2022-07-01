package com.example.weatherproject.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleCenter {
    @Autowired public WeatherSamples weatherSamples;
}
