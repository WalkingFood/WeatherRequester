package com.walkingfood.weather.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Andrew Fooden on 6/1/2015.
 * Part of WeatherRequester.
 */
@Entity
public class WeatherData {

    @GeneratedValue
    @Id
    @Column(nullable = false)
    public Long id;

    @Column
    public Date timeGenerated;

    @Column
    public Date timeRequested;

    @Column
    public Double latitude;

    @Column
    public Double longitude;
}
