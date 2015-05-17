package com.walkingfood.weather;

import org.apache.camel.Consume;
import org.springframework.stereotype.Service;

/**
 * Created by Andrew Fooden on 5/17/2015.
 * Part of WeatherRequester.
 */
@Service
public class WeatherConsumer {

    @Consume(uri = "seda:consume/weather")
    public void weatherResponseConsumer(String jsonResponse){

    }
}
