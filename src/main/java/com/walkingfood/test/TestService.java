package com.walkingfood.test;

import org.apache.camel.Consume;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by Andrew Fooden on 5/13/2015.
 * Part of SpringBootTemplate.
 */
@Service
public class TestService {

    @EndpointInject
    ProducerTemplate template;

    //If we'd like to run this test at start=up, uncomment the @Scheduled annotation
//    @Scheduled(initialDelay = 100, fixedDelay = 2000)
    public void runTest(){
        System.err.println("Sending messages");
        template.sendBody("seda:start", "Plain route test worked");
        template.sendBody("seda:beginning", "Properties-set route test worked");
    }

    @Consume(uri = "seda:finish")
    public void consumeTest1(String message){
        System.err.println(message);
    }

    @Consume(uri = "seda:end")
    public void consumeTest2(String message){
        System.err.println(message);
    }
}
