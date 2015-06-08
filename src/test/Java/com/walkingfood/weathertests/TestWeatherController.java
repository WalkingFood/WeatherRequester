package com.walkingfood.weathertests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walkingfood.bootstrap.Application;
import com.walkingfood.weather.mvc.WeatherController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;

import static org.junit.Assert.assertTrue;

/**
 * Created by Andrew Fooden on 5/18/2015.
 * Part of WeatherRequester.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MockServletContext.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class TestWeatherController {

    @Autowired
    WebApplicationContext context;

    @Autowired
    WeatherController controller;

    @Value("${local.server.port}")
    private int port;

    private MockMvc mvc;
    private URL base;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        // If you're not doing a full integration test, use the
        // standalone setup instead of the context setup for mvc mocks
//        mvc = MockMvcBuilders.standaloneSetup(new WeatherController()).build();

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        base = new URL("http://localhost:" + port + "/weather/get/serverloc");
        template = new TestRestTemplate();
    }

    @Test
    public void isServerLocValidJSON() throws Exception{
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        JsonNode node = new ObjectMapper().readTree(response.getBody());
        assertTrue(node.has("coord"));
        assertTrue(node.has("sys"));
        assertTrue(node.has("main"));
    }

    @Test
    public void getServerLoc() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/weather/get/serverloc").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
