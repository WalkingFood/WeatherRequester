package com.walkingfood.bootstrap;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Andrew Fooden on 5/12/2015.
 * Part of SpringBootTemplate.
 */
@SpringBootApplication
@ComponentScan("com.walkingfood")
@EnableScheduling
public class Application {

    /*
    *** Bean definitions go here ***
     */



    public static void main(String[] args){
        ApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
