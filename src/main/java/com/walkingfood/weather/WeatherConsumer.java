package com.walkingfood.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walkingfood.utils.CommonUtils;
import org.apache.camel.Consume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Andrew Fooden on 5/17/2015.
 * Part of WeatherRequester.
 */
@Service
public class WeatherConsumer {

    static Logger logger = LoggerFactory.getLogger(WeatherConsumer.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String spacing = "    ";

    @Consume(uri = "seda:consume/weather")
    public void weatherResponseConsumer(String jsonResponse){

        JsonNode json = null;
        try {
            json = mapper.readTree(jsonResponse);
        } catch (IOException e) {
            logger.error("IOException while parsing the JSON response from the weather service.", e);
        }

        if (json != null){

            // To print nicely to log without using PrettyPrint
            logger.debug( "\r\n" +
                CommonUtils.getParsedJsonNode(json)
            );

            /*
            // To print using PrettyPrint
            try {
                logger.debug(
                        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json)
                );
            } catch (JsonProcessingException e) {
                logger.error("Could not read JSON node when using PrettyPrint", e);
            }
            */
        }
    }
}
