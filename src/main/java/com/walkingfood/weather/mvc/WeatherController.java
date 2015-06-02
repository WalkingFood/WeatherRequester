package com.walkingfood.weather.mvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walkingfood.utils.CommonUtils;
import com.walkingfood.weather.WeatherRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
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

    private static final ObjectMapper mapper = new ObjectMapper();
    private static JsonNode json = null;

    @Override
    public String getWeather(@RequestParam(value = "period", defaultValue = "0") int period) {
        return getParsedJsonString(weatherRequest.requestWeather(period));
    }

    @Override
    public String getWeatherAtLocation(@PathVariable("location") String location, @RequestParam(value = "period", defaultValue = "0") int period) {
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
            return getParsedJsonString(weatherRequest.requestWeather(utfLocation, period));
        }
        else {
            return "Error: please try your request again!";
        }
    }

    @Override
    public String getWeatherAtCoords(@PathVariable("lat") String lat, @PathVariable("lon") String lon, @RequestParam(value = "period", defaultValue = "0") int period) {
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
            return getParsedJsonString(weatherRequest.requestWeather(utfLat, utfLon, period));
        }
        else {
            return "Error: please try your request again!";
        }
    }

    private String getParsedJsonString(String input){
        try {
            json = mapper.readTree(input);
        } catch (IOException e) {
            logger.error("IOException while parsing the JSON response from the weather service.", e);
        }

        if (json != null){
            return CommonUtils.getParsedJsonNode(json);
        }
        else {
            return "Error: please try your request again!";
        }
    }
}
