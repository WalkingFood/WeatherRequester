package com.walkingfood.weathertests;

import com.beust.jcommander.ParameterException;
import com.walkingfood.weather.cli.DoubleValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.springframework.test.util.AssertionErrors.fail;


/**
 * Created by Andrew Fooden on 5/17/2015.
 * Part of WeatherRequester.
 */
public class TestCLIParser {

    DoubleValidator validator = new DoubleValidator();

    @Before
    public void setUp(){}

    @Test
    public void testDoubleValidatorPassing(){
        validator.validate("-lat","30.423");
    }

    @Test
    public void testIntegerValidatorPassing(){
        validator.validate("-lat", "66");
    }

    @Test(expected = ParameterException.class)
    public void testEmptyStringValidationFail(){
        validator.validate("-lat","");
        fail("Should not complete with empty string");
    }

    @Test(expected = ParameterException.class)
    public void testMalformedDoubleValidationFail(){
        validator.validate("-lat","42.4f3");
        fail("Should not complete with malformed string");
    }

    @Test(expected = ParameterException.class)
    public void testMalformedDoubleValidationFail2(){
        validator.validate("-lat","42. 43");
        fail("Should not complete with malformed string");
    }

    @After
    public void tearDown(){}
}
