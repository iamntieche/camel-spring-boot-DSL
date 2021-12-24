package com.mfoumgroup.camel.component;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConditionalOnProperty(name = "${mfoumgroup.camel.file.enabled}", havingValue = "true")
public class FileHandlerRoute extends RouteBuilder {

    public static final String FROM_DIR = "/Users/issofa.ntiechmfoum/inputFolder/?noop=true&";
    public static final String TO_DIR = "/Users/issofa.ntiechmfoum/outputFolder/?";
    public static final String APPEND = "&fileExist=Append";

    @Override
    public void configure() throws Exception {
        System.out.println("In file");
        /**
         * Copy data from one file to another.
         * Default behaviour Overwrite
         */
        from("file://"+ FROM_DIR + "fileName=camel-demo-in.txt")
                .to("file://" + TO_DIR + "fileName=camel-demo-out.txt");

        /**
         * Append data to an existing file...
         */
        from("direct:appendToFile")
                .process(Exchange::getMessage)
                .to("file://" + TO_DIR + "fileName=camel-demo-appends.txt" + APPEND);
    }



}
