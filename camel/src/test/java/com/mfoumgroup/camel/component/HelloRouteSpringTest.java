package com.mfoumgroup.camel.component;

import com.mfoumgroup.camel.CamelApplication;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CamelApplication.class,
properties = {"${mfoumgroup.camel.hello.enabled}=true"}) //very important
public class HelloRouteSpringTest {

    @Autowired
    ProducerTemplate template;

    @Test
    void testMocksAreValid(){
        System.out.println("Sending 1");
        template.sendBody("direct:greeting", "Team");
       System.out.println("Sending 2");
        template.sendBody("direct:greeting", "Me");
    }
}
