package com.walkingfood.weather;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.walkingfood.utils.CommonUtils;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by Andrew Fooden on 5/13/2015.
 * Part of WeatherRequester.
 */
@Service
public class WeatherRequest implements CommandLineRunner{

    static Logger logger = LoggerFactory.getLogger(WeatherRequest.class);

    private static String apiKey = null;

    @EndpointInject
    ProducerTemplate template;

    private static final String ROUTE = "seda:request/weather";

    private static final String HEADER_NAME = "CamelWeatherLocation";
    private static final String EMPTY_BODY = "";

    private static final String PARAM_LAT = "-lat or -latitude";
    private static final String PARAM_LON = "-lon or -longitude";


    @Override
    public void run(String... args) throws Exception {
        /*
            Check for any CL arguments. If none found, go with the default
            and check the weather at the user's location. If a --location arg
            is found, check the weather at that location.
         */

        if (args.length == 0){
            requestWeather();
        }
        else {
            WeatherCLParser clParser = new WeatherCLParser();
            new JCommander(clParser, args);
            if (clParser.getLocation() != null){
                // TODO Provide better input cleaning
                requestWeather(
                        clParser.getLocation().replaceAll(
                            "[|&;:]", ""
                ));
            }
            else if (clParser.getLatitude() != null){
                if (clParser.getLongitude() != null){
                    requestWeather(clParser.getLatitude(), clParser.getLongitude());
                }
                else {
                    throw new ParameterException("Parameter " + PARAM_LON + " should be included when using parameter " + PARAM_LAT);
                }
            }
            else if (clParser.getLongitude() != null){
                throw new ParameterException("Parameter " + PARAM_LAT + " should be included when using parameter " + PARAM_LON);
            }
            else {
                System.err.println("Parameters not recognized: returning weather for current location");
            }
        }
    }

//    @Scheduled(initialDelay = 100, fixedDelay = 20000)
    public void requestWeather(){
        requestWeather(null);
    }

    public void requestWeather(String lat, String lon){

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
