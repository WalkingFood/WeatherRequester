package com.walkingfood.weather;

/**
 * Created by Andrew Fooden on 6/2/2015.
 * Part of WeatherRequester.
 */
public class WeatherRequestDTO {

    private WeatherRequestDTO(
            String location,
            String latitude,
            String longitude,
            int period ){
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.period = period;
    }

    public static WeatherRequestDTO getInstance(
            String location,
            String latitude,
            String longitude,
            int period ){
        return new WeatherRequestDTO(
                location,
                latitude,
                longitude,
                period );
    }

    private final String location;
    private final String latitude;
    private final String longitude;
    private final int period;

    public String getLocation() {
        return this.location;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public int getPeriod() {
        return this.period;
    }
}
