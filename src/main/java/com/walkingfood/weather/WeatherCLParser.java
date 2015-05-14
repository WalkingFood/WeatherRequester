package com.walkingfood.weather;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.util.regex.Pattern;

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

    @Parameter(names = {"-lat", "-latitude"},
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

    private class DoubleValidator implements IParameterValidator{

        @Override
        public void validate(String name, String value) throws ParameterException {
            if (!checkStringForDouble(value)){
                throw new ParameterException("Parameter " + name + " should be a Double (found " + value +")");
            }
        }

        /*
            Borrowing this from the Double class
         */
        private boolean checkStringForDouble(String myString){
            final String Digits     = "(\\p{Digit}+)";
            final String HexDigits  = "(\\p{XDigit}+)";
            final String Exp        = "[eE][+-]?"+Digits;
            final String fpRegex    =
                    ("[\\x00-\\x20] "+  // Optional leading "whitespace"
                            "[+-]?(" + // Optional sign character
                            "NaN|" +           // "NaN" string
                            "Infinity|" +      // "Infinity" string

                            // A decimal floating-point string representing a finite positive
                            // number without a leading sign has at most five basic pieces:
                            // Digits . Digits ExponentPart FloatTypeSuffix
                            //
                            // Since this method allows integer-only strings as input
                            // in addition to strings of floating-point literals, the
                            // two sub-patterns below are simplifications of the grammar
                            // productions from section 3.10.2 of
                            // The Java Language Specification.

                            // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                            "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

                            // . Digits ExponentPart_opt FloatTypeSuffix_opt
                            "(\\.("+Digits+")("+Exp+")?)|"+

                            // Hexadecimal strings
                            "((" +
                            // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                            "(0[xX]" + HexDigits + "(\\.)?)|" +

                            // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                            "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                            ")[pP][+-]?" + Digits + "))" +
                            "[fFdD]?))" +
                            "[\\x00-\\x20] ");// Optional trailing "whitespace"

            if (Pattern.matches(fpRegex, myString))
                return true; // Will not throw NumberFormatException
            else {
                return false;
            }
        }
    }
}
