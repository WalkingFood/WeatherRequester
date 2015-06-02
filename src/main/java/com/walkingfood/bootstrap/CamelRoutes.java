package com.walkingfood.bootstrap;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Andrew Fooden on 5/13/2015.
 * Part of SpringBootTemplate.
 * Modified in WeatherRequester.
 */
@Component
public class CamelRoutes extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

        //Building a route from explicitly declared Strings
        from("seda:beginning").to("seda:end").end();

        //Building a route from properties extracted from application.yml
        from("{{routes.from.start}}")
                .to("{{routes.to.finish}}");


        //For getting information from the Camel Weather endpoint
        from("seda:request/weather")
                .choice()
                    .when(header("period").isEqualTo(5))
                        .to("weather:foo?period=5")
                    .when(header("period").isEqualTo(7))
                        .to("weather:foo?period=7")
                    .when(header("period").isEqualTo(14))
                        .to("weather:foo?period=14")
                    .otherwise()
                        .to("weather:foo")
                .end()
                .multicast()
                .to(
                        // Send to the Camel logger
//                        "log:com.walkingfood?showAll=true",

                        // Send to an internal consumer
                        "seda:consume/weather"
                )
                .end();
    }
}
