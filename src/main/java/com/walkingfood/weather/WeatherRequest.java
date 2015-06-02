package com.walkingfood.weather;

import com.walkingfood.utils.CommonUtils;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    private static final String LOCATION_HEADER_NAME = "CamelWeatherLocation";
    private static final String PERIOD_HEADER_NAME = "period";
    private static final String EMPTY_BODY = "";


    /**
     * Request weather information for the current location
     */
    public String requestWeather(){
        return requestWeather(
                new WeatherRequestDTOBuilder()
                        .build()
        );
    }

    /**
     * Request weather information for the given lat and lon coordinates
     *
     * @param lat - the provided latitude (must be a valid Double)
     * @param lon - the provided longitude (must be a valid Double)
     */
    public String requestWeather(String lat, String lon){
        return requestWeather(
                new WeatherRequestDTOBuilder()
                        .setLatitude(lat)
                        .setLongitude(lon)
                        .build()
        );
    }

    /**
     * Request weather information for a given location String. This
     * String can be a plain-text "City, Country" name
     *
     * @param location - the location String
     */
    public String requestWeather(String location){
        return requestWeather(
                new WeatherRequestDTOBuilder()
                        .setLocation(location)
                        .build()
        );
    }

    /**
     * Request weather information for a given location String. This
     * String can be either a plain-text "City, Country" name or it
     * can be a formatted lat/lon coordinate input.
     *
     * @param dto - a data transfer object containing the request parameters
     */
    public String requestWeather(WeatherRequestDTO dto) {

        // We need a StringBuilder to construct text that will be used as a value to the
        // CamelWeatherLocation header
        StringBuilder stringBuilder = new StringBuilder();

        if (dto.getLocation() != null) {
            logger.info("Looking up weather information for " + dto.getLocation());
            stringBuilder.append(dto.getLocation());
        } else if (dto.getLatitude() != null && dto.getLongitude() != null) {
            logger.info("Looking up weather information for lat " + dto.getLatitude() +
                    " and lon " + dto.getLongitude() + " coordinates");
            stringBuilder.append("0&lat=");
            stringBuilder.append(dto.getLatitude());
            stringBuilder.append("&lon=");
            stringBuilder.append(dto.getLongitude());
        } else {
            logger.info("Looking up weather information for your current location");
        }

        if (getAPIkey() != null) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append("&");
                stringBuilder.append(apiKey);
            }
        }

        Map<String, Object> headers = new HashMap<>();
        if (stringBuilder.length() > 1){
            headers.put(LOCATION_HEADER_NAME, stringBuilder);
        }
        headers.put(PERIOD_HEADER_NAME, dto.getPeriod());

        return template.requestBodyAndHeaders(
                ROUTE,
                EMPTY_BODY,
                headers,
                String.class);
    }

    private String getAPIkey(){
        if (apiKey == null) {
            apiKey = CommonUtils.getLocalProperty("weather.apiKey");
        }
        return apiKey;
    }
}
