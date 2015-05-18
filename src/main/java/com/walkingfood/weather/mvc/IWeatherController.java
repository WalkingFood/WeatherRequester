package com.walkingfood.weather.mvc;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Andrew Fooden on 5/18/2015.
 * Part of WeatherRequester.
 */
@Controller
@RequestMapping("/weather/")
public interface IWeatherController {

    @RequestMapping(
            value = "get/serverloc",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody String getWeather();

    @RequestMapping(
            value = "get/{location}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody String getWeatherAtLocation(@PathVariable String location);

    @RequestMapping(
            value = "get/coords/{lat}/{lon}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody String getWeatherAtCoords(@PathVariable String lat, @PathVariable String lon);
}
