package com.walkingfood.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by Andrew Fooden on 5/13/2015.
 * Part of SpringBootTemplate.
 */
public class CommonUtils {

    static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static final String PROPERTIES_FILE = "local.properties";

    private static CommonUtils self;

    private static CommonUtils getSelf(){
        if (self == null){
            self = new CommonUtils();
        }
        return self;
    }

    private static final String spacing = "    ";
    private static JsonNode parserNode;
    private static String parserName;
    private static StringBuilder stringBuilder;
    private static StringBuilder spacer;

    private static StringBuilder getStringBuilder(){
        if (stringBuilder == null){
            stringBuilder = new StringBuilder();
        }
        return stringBuilder;
    }

    private static StringBuilder getSpacer(){
        if (spacer == null){
            spacer = new StringBuilder();
        }
        return spacer;
    }

    /**
     * For getting properties stored in local.properties.
     * In general, local.properties should be used to store
     * sensitive, project-specific information, such as API keys.
     *
     * @param propKey - The name of the property to retrieve
     * @return - The value of the property, or null if no value found
     */
    public static String getLocalProperty(String propKey){
        logger.debug("Searching for local property " + propKey);
        Properties properties;
        String returnString = null;
        InputStream stream = getSelf().getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (stream != null){
            try {
                properties = new Properties();
                properties.load(stream);
                returnString = properties.getProperty(propKey);
            } catch (IOException e) {
                logger.error("IOException encountered", e);
                return null;
            }
        }
        else {
            logger.error("No local.properties file found: please create this file before trying to retrieve properties");
            return null;
        }

        return returnString;
    }

    /**
     * Formatting and printing from a JsonNode with recursion
     * (in case you don't like to use PrettyPrint).
     *
     * @param jsonNode - the JSON node to read
     */
    public static String getParsedJsonNode(JsonNode jsonNode){
        getStringBuilder().setLength(0);
        printFromJSON(jsonNode, 0);
        return getStringBuilder().toString();
    }

    /**
     * Adds to the static StringBuilder to recursively parse a JsonNode.
     *
     * @param jsonNode - the JSON node to read
     * @param depth - the depth of the input node from the root node
     */
    private static void printFromJSON(JsonNode jsonNode, int depth){

        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()){

            getSpacer().setLength(0);
            for (int i = 0; i < depth; i++){
                spacer.append(spacing);
            }

            parserName = fieldNames.next();
            parserNode = jsonNode.get(parserName);
            if (parserNode.isValueNode()){
                getStringBuilder().append(spacer.toString() + "{ " + parserName + " : " + parserNode.asText() + " }\r\n");
            }
            else {
                getStringBuilder().append(spacer.toString() + "{ " + parserName + " }\r\n");
                printFromJSON(parserNode, depth + 1);
            }
        }
    }
}
