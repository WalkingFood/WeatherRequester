package com.walkingfood.weather.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.walkingfood.weather.WeatherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Created by Andrew Fooden on 5/18/2015.
 * Part of WeatherRequester.
 */
@Service
public class WeatherRunner implements CommandLineRunner {

    private static final String PARAM_LAT = "-lat or -latitude";
    private static final String PARAM_LON = "-lon or -longitude";

    @Autowired
    WeatherRequest weatherRequest;

    @Override
    public void run(String... args) throws Exception {
        /*
            Check for any CL arguments. If none found, go with the default
            and check the weather at the user's location. If a --location arg
            is found, check the weather at that location.
         */

        if (args.length == 0){
            // No args found - request info on current location
            String response = weatherRequest.requestWeather();
            System.err.println(response);
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
                weatherRequest.requestWeather(
                        clParser.getLocation().replaceAll(
                                "[|&;:]", ""
                        ));
            }

            // If there was a -lat arg...
            else if (clParser.getLatitude() != null){

                // If there was also a -lon arg...
                if (clParser.getLongitude() != null){
                    weatherRequest.requestWeather(clParser.getLatitude(), clParser.getLongitude());
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
}
