package com.walkingfood.weathertests;

import com.beust.jcommander.ParameterException;
import com.walkingfood.weather.cli.DoubleValidator;
import org.junit.Test;


import static org.springframework.test.util.AssertionErrors.fail;


/**
 * Created by Andrew Fooden on 5/17/2015.
 * Part of WeatherRequester.
 */
public class TestCLIParser {

    @Test
    public void testDoubleValidatorPassing(){
        DoubleValidator validator = new DoubleValidator();
        validator.validate("-lat","30.423d");
    }

    @Test(expected = ParameterException.class)
    public void testEmptyStringValidationFail(){
        DoubleValidator validator = new DoubleValidator();
        validator.validate("-lat","");
        fail("Should not complete with empty string");
    }
}
