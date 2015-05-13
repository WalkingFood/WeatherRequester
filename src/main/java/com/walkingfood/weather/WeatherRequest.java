package com.walkingfood.weather;

import com.walkingfood.utils.CommonUtils;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(initialDelay = 100, fixedDelay = 20000)
    public void requestWeather(){
        requestWeather(null);
    }

    public void requestWeather(String location){

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
            template.requestBodyAndHeader(
                    ROUTE,
                    EMPTY_BODY,
                    HEADER_NAME,
                    stringBuilder,
                    String.class);
        }
        else {
            template.requestBody(ROUTE, EMPTY_BODY);
        }
    }

    private String getAPIkey(){
        if (apiKey == null) {
            String key = CommonUtils.getLocalProperty("weather.apiKey");
            if (key != null){
                apiKey = key;
            }
        }
        return apiKey;
    }
}
