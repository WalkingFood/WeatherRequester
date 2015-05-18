package com.walkingfood.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            printFromJSON(json, 0);
        }
    }

    private void printFromJSON(JsonNode jsonNode, int depth){
        JsonNode node;
        String name;
        final StringBuilder spacer = new StringBuilder();
        for (int i = 0; i < depth; i++){
            spacer.append(spacing);
        }

        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()){
            name = fieldNames.next();
            node = jsonNode.get(name);
            if (node.isValueNode()){
                logger.debug(spacer.toString() + "{ " + name + " : " + node.asText() + " }");
            }
            else {
                logger.debug(spacer.toString() + "{ " + name + " }");
                printFromJSON(node, depth + 1);
            }
        }
    }
}
