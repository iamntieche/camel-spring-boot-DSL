package com.mfoumgroup.camel.component.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.ERROR;

@Component
@ConditionalOnProperty(name = "${mfoumgroup.camel.hello.enabled}", havingValue = "true") //ConditionalOnProperty verifie si la condition est remplie avant de charger le bean
public class HelloRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:greeting").id("greeting")
                .log(ERROR, "Hello ${body}")
                .choice()
                .when().simple("${body} contains 'Team'")
                     .log(ERROR, "I like working with Teams")
                .otherwise()
                     .log(ERROR, "Solo fighter :)")
                .end()
                .to("direct:finishGreeting");

        from("direct:finishGreeting")
                .log(ERROR, "Bye ${body}");

    }
}
