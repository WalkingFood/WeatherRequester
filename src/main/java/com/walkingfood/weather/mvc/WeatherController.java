package com.walkingfood.weather.mvc;

import com.walkingfood.weather.WeatherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Andrew Fooden on 5/18/2015.
 * Part of WeatherRequester.
 */
@Component
public class WeatherController implements IWeatherController{

    @Override
    public String getWeather(){
        return null;
    }

    @Override
    public String getWeatherAtLocation(@PathVariable String location) {
        return null;
    }

    @Override
    public String getWeatherAtCoords(@PathVariable String lat, @PathVariable String lon) {
        return null;
    }
}
