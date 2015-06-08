package com.walkingfood.weather.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * © emozia, inc 2015
 * Created by Andrew on 6/8/2015.
 */
@Controller
@RequestMapping("/weather/pretty/")
public interface IPrettyWeatherController extends IWeatherController{
}
