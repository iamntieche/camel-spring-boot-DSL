package com.mfoumgroup.camel.component.rest;

import com.mfoumgroup.camel.dto.WeatherDataProvider;
import com.mfoumgroup.camel.dto.WeatherDto;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.support.DefaultMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;

//@Component
//@ConditionalOnProperty(name = "${mfoumgroup.camel.rest.enabled}", havingValue = "true")
public class RestJavaDsl extends RouteBuilder {

    private final WeatherDataProvider weatherDataProvider;

    public RestJavaDsl(WeatherDataProvider weatherDataProvider) {
        this.weatherDataProvider = weatherDataProvider;
    }

    //http://localhost:8080/services/javadsl/weather/{city}
    @Override
    public void configure() throws Exception {


        from("rest:get:javadsl/weather/{city}?produces=application/json")
                .outputType(WeatherDto.class) //spécification type retour des données
                .process(this::getWeatherData);
    }

    private void getWeatherData(Exchange exchange) {
       String city = exchange.getMessage().getHeader("city", String.class);
       WeatherDto currentWeather = weatherDataProvider.getCurrentWeather(city);

       if(Objects.nonNull(currentWeather)){
           Message message = new DefaultMessage(exchange.getContext());
           message.setBody(currentWeather);
           exchange.setMessage(message);
       }else{
           exchange.getMessage().setHeader(HTTP_RESPONSE_CODE, HttpStatus.NOT_FOUND.value());
       }

    }
}
