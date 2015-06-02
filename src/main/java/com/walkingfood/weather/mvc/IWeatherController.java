package com.walkingfood.weather.mvc;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody String getWeather(@RequestParam(value = "period", defaultValue = "0") int period);

    @RequestMapping(
            value = "get/location/{location}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody String getWeatherAtLocation(@PathVariable("location") String location,
                                              @RequestParam(value = "period", defaultValue = "0") int period);

    @RequestMapping(
            value = "get/coords/lat/{lat}/lon/{lon}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody String getWeatherAtCoords(@PathVariable("lat") String lat,
                                            @PathVariable("lon") String lon,
                                            @RequestParam(value = "period", defaultValue = "0") int period);
}
