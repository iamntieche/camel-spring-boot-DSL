package com.mfoumgroup.camel.component;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class HelloRouteJUnitTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new HelloRoute();
    }

    @Test
    void testMocksAreValid() throws InterruptedException{
        System.out.println("Sending 1");
        template.sendBody("direct:greeting","Team");
        System.out.println("Sending 2");
        template.sendBody("direct:greeting", "Me");
    }
}
