package com.mfoumgroup.camel.component.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
//@ConditionalOnProperty(name = "com.mfoumgroup.camel.seda.enabled", havingValue = "true")
public class SedaRoute extends RouteBuilder {

    static AtomicInteger counter = new AtomicInteger();
    @Override
    public void configure() {
        from("timer:ping?period=1000")
                .routeId("Timer")
                .process(this::currentDate)
                //blocking queue with seda
               .to("seda:weightLifter?multipleConsumers=true");

       from("seda:weightLifter?multipleConsumers=true")
               .routeId("Seda-WeightLifter")
                .to("direct:complexProcess");

        from("direct:complexProcess")
                .routeId("Direct-ComplexProcess")
                //.log(LoggingLevel.DEBUG, "${body}")
                .process(exchange -> {
                    if (new Random().nextInt(20) < 5) {
                      System.err.println("error");
                    }
                })
                .process(exchange -> {
                    SECONDS.sleep(5);
                    int total = counter.decrementAndGet();
                    System.out.println("Counter: " + total);
                })
                .end();
    }

    public void currentDate(Exchange exchange){
        Message message = new DefaultMessage(exchange);
        message.setBody(LocalDateTime.now());
        exchange.setMessage(message);
    }
}
