package com.walkingfood.weather;

/**
 * Created by Andrew Fooden on 6/2/2015.
 * Part of WeatherRequester.
 */
public class WeatherRequestDTOBuilder {

    public WeatherRequestDTOBuilder(){}

    private String location = null;
    private String latitude = null;
    private String longitude = null;
    private int period = 5;

    public WeatherRequestDTOBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public WeatherRequestDTOBuilder setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public WeatherRequestDTOBuilder setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public WeatherRequestDTOBuilder setPeriod(Integer period) {
        this.period = period;
        return this;
    }

    public WeatherRequestDTO build(){
        return WeatherRequestDTO.getInstance(
                this.location,
                this.latitude,
                this.longitude,
                this.period
        );
    }
}
