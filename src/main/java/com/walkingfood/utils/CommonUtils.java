package com.walkingfood.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
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
}
