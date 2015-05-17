package com.walkingfood.weather;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.walkingfood.utils.CommonUtils;
import com.walkingfood.weather.cli.WeatherCLParser;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
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
            // No args found - request info on current location
            requestWeather();
        }
        else {
            // At least one arg was found, so let's see which arg it was
            WeatherCLParser clParser = new WeatherCLParser();
            new JCommander(clParser, args);

            // If there was a -loc arg...
            if (clParser.getLocation() != null){
                // There is no validation on the content of the location
                // String, so we need to do a little cleaning here
                // TODO Provide better input cleaning
                requestWeather(
                        clParser.getLocation().replaceAll(
                            "[|&;:]", ""
                ));
            }

            // If there was a -lat arg...
            else if (clParser.getLatitude() != null){

                // If there was also a -lon arg...
                if (clParser.getLongitude() != null){
                    requestWeather(clParser.getLatitude(), clParser.getLongitude());
                }

                // If there was no -lon arg...
                else {
                    throw new ParameterException("Parameter " + PARAM_LON + " should be included when using parameter " + PARAM_LAT);
                }
            }

            // If there was a -lon arg with no -lat arg...
            else if (clParser.getLongitude() != null){
                throw new ParameterException("Parameter " + PARAM_LAT + " should be included when using parameter " + PARAM_LON);
            }

            // If the args weren't recognized...
            else {
                System.err.println("Parameters not recognized: returning weather for current location");
            }
        }
    }

    /**
     * Request weather information for the current location
     */
    public void requestWeather(){
        requestWeather(null);
    }

    /**
     * Request weather information for the given lat and lon coordinates
     *
     * @param lat - the provided latitude (must be a valid Double)
     * @param lon - the provided longitude (must be a valid Double)
     */
    public void requestWeather(String lat, String lon){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0&lat=");
        stringBuilder.append(lat);
        stringBuilder.append("&lon=");
        stringBuilder.append(lon);
        requestWeather(stringBuilder.toString());
    }

    /**
     * Request weather information for a given location String. This
     * String can be either a plain-text "City, Country" name or it
     * can be a formatted lat/lon coordinate input.
     *
     * @param location - the provided location String.
     */
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
            apiKey = CommonUtils.getLocalProperty("weather.apiKey");
        }
        return apiKey;
    }
}
