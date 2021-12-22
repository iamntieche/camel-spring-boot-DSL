package com.mfoumgroup.camel.component;

import com.mfoumgroup.camel.CamelApplication;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest(classes = CamelApplication.class, properties = {"${mfoumgroup.camel.file.enabled}=true"})
@CamelSpringBootTest
@MockEndpoints()
public class FileHandlerRouteTest {

    @Autowired
    ProducerTemplate template;

    @Test
    void testCamelFileRoute() throws Exception{
        System.out.println("Sending request to append to existing file...");
        template.sendBody("direct:appendToFile","Hello "+ new Date() + "\n");
        System.out.println("Sent request to append to existing file...");
    }
}
