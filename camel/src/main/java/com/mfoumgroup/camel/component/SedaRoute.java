package com.mfoumgroup.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class SedaRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:ping?period=200")
                .process(this::currentDate)
                //blocking queue with seda
                .to("seda:weightLifter?multipleConsumers=true");
        from("seda:weightLifter?multipleConsumers=true")
                .to("direct:complexProcess");

        from("direct:complexProcess")
                .log(LoggingLevel.DEBUG, "${body}")
                .process(exchange -> SECONDS.sleep(2))
                .end();
    }

    public void currentDate(Exchange exchange){
        Message message = new DefaultMessage(exchange);
        message.setBody(LocalDateTime.now());
        exchange.setMessage(message);
    }
}
