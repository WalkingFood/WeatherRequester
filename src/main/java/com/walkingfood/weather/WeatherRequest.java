package com.walkingfood.weather;

import com.walkingfood.utils.CommonUtils;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Andrew Fooden on 5/13/2015.
 * Part of WeatherRequester.
 */
@Service
public class WeatherRequest {

    static Logger logger = LoggerFactory.getLogger(WeatherRequest.class);

    private static String apiKey = null;

    @EndpointInject
    ProducerTemplate template;

    private static final String ROUTE = "seda:request/weather";

    private static final String HEADER_NAME = "CamelWeatherLocation";
    private static final String EMPTY_BODY = "";


    /**
     * Request weather information for the current location
     */
    public String requestWeather(){
        return requestWeather(null);
    }

    /**
     * Request weather information for the given lat and lon coordinates
     *
     * @param lat - the provided latitude (must be a valid Double)
     * @param lon - the provided longitude (must be a valid Double)
     */
    public String requestWeather(String lat, String lon){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0&lat=");
        stringBuilder.append(lat);
        stringBuilder.append("&lon=");
        stringBuilder.append(lon);
        return requestWeather(stringBuilder.toString());
    }

    /**
     * Request weather information for a given location String. This
     * String can be either a plain-text "City, Country" name or it
     * can be a formatted lat/lon coordinate input.
     *
     * @param location - the provided location String.
     */
    public String requestWeather(String location){

        StringBuilder stringBuilder = new StringBuilder();

        if (location != null) {
            logger.info("Looking up weather information for " + location);
            stringBuilder.append(location);
        }
        else {
            logger.info("Looking up weather information for your current location");
        }

        if (getAPIkey() != null){
            if (stringBuilder.length() > 1){
                stringBuilder.append("&");
                stringBuilder.append(apiKey);
            }
        }

        if (stringBuilder.length() > 1) {
            return template.requestBodyAndHeader(
                    ROUTE,
                    EMPTY_BODY,
                    HEADER_NAME,
                    stringBuilder,
                    String.class);
        }
        else {
            return template.requestBody(ROUTE, EMPTY_BODY, String.class);
        }
    }

    private String getAPIkey(){
        if (apiKey == null) {
            apiKey = CommonUtils.getLocalProperty("weather.apiKey");
        }
        return apiKey;
    }
}
