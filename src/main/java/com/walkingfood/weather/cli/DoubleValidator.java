package com.walkingfood.weather.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;


/**
 * Created by Andrew Fooden on 5/17/2015.
 * Part of WeatherRequester.
 */
public class DoubleValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!checkStringForDouble(value)){
            throw new ParameterException("Parameter " + name + " should be a Double (found " + value +")");
        }
    }

    private boolean checkStringForDouble(String value){
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
