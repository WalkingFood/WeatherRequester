package com.walkingfood.weather.cli;

import com.beust.jcommander.Parameter;

/**
 * Created by Andrew Fooden on 5/14/2015.
 * Part of WeatherRequester.
 */
public class WeatherCLParser {

    @Parameter(names = {"-lat", "-latitude"},
            validateWith = DoubleValidator.class,
            description = "The latitude of the location you would like to examine. " +
                    "Must be accompanied by a longitude value.")
    private String latitude = null;

    @Parameter(names = {"-lon", "-longitude"},
            validateWith = DoubleValidator.class,
            description = "The longitude of the location you would like to examine. " +
                    "Must be accompanied by a latitude value.")
    private String longitude = null;

    @Parameter(names = {"-loc", "-location"},
    description = "The name of the location you would like to examine. For example, \"Madrid,Spain\"")
    private String location = null;


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return location;
    }
}
