package com.walkingfood.bootstrap;

import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by Andrew Fooden on 5/13/2015.
 * Part of SpringBootTemplate.
 */
public class CamelRoutes extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("{{routes.from.x}}")
                .to("{{routes.to.y}}");
    }
}
