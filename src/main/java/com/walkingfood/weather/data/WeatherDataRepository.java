package com.walkingfood.weather.data;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Andrew Fooden on 6/1/2015.
 * Part of WeatherRequester.
 */
public interface WeatherDataRepository extends CrudRepository<WeatherData, Long> {
}
