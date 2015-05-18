package com.walkingfood.weather.mvc;

import com.walkingfood.weather.WeatherRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;

/**
 * Created by Andrew Fooden on 5/18/2015.
 * Part of WeatherRequester.
 */
@Component
public class WeatherController implements IWeatherController{

    static Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    WeatherRequest weatherRequest;

    @Override
    public String getWeather(){
        return weatherRequest.requestWeather();
    }

    @Override
    public String getWeatherAtLocation(@PathVariable String location) {

        // Because Spring encodes path variables in a weird-ass format,
        // we're going to re-code them into UTF-8 before dealing with them.
        byte[] bytes = null;
        String utfLocation = null;

        try {
            // Re-coding location
            bytes = location.getBytes("ISO-8859-1");
            utfLocation = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Failed to handle byte encoding of url parameter", e);
        }

        if (utfLocation != null) {
            return weatherRequest.requestWeather(utfLocation);
        }
        else {
            return "Error: please try your request again!";
        }
    }

    @Override
    public String getWeatherAtCoords(@PathVariable String lat, @PathVariable String lon) {

        // Because Spring encodes path variables in a weird-ass format,
        // we're going to re-code them into UTF-8 before dealing with them.
        byte[] bytes = null;
        String utfLat = null;
        String utfLon = null;

        try {
            // First recode lat
            bytes = lat.getBytes("ISO-8859-1");
            utfLat = new String(bytes, "UTF-8");

            // Now recode lon
            bytes = lon.getBytes("ISO-8859-1");
            utfLon = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Failed to handle byte encoding of url parameter", e);
        }

        if (utfLat != null && utfLon != null) {
            return weatherRequest.requestWeather(utfLat, utfLon);
        }
        else {
            return "Error: please try your request again!";
        }
    }
}
