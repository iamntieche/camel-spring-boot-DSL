package com.mfoumgroup.camel.component;

import com.mfoumgroup.camel.dto.TransationDto;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.mfoumgroup.camel.config.CamelConfiguration.RABBIT_URI;
import static org.apache.camel.LoggingLevel.ERROR;

//@Component
//@ConditionalOnProperty(name = "mfoumgroup.camel.wiretap.enabled", havingValue = "true")
public class Wiretap extends RouteBuilder {

    public static final String SENDER = "sender";
    public static final String RECEIVER = "receiver";
    public static final String AUDIT_TRANSACTION_ROUTE = "direct:audit-transaction";
    public static final String AUDIT = "audit-transactions";


    @Override
    public void configure() throws Exception {
        /*
          fromF et toF sont dynamique car permettent de construire l'uri dans le traitement
          contrairement à from et to qui prennent directement les liens statiques
         */
        fromF(RABBIT_URI, SENDER, SENDER)
                .unmarshal().json(JsonLibrary.Jackson, TransationDto.class)
                .wireTap(AUDIT_TRANSACTION_ROUTE)
                .process(this::enrichTransactionDto)
                .marshal().json(JsonLibrary.Jackson, TransationDto.class)
                .toF(RABBIT_URI, RECEIVER,RECEIVER)
                .log(ERROR, "Money Transferred: ${body}");

        from(AUDIT_TRANSACTION_ROUTE)
                .process(this::enrichTransactionDto)
                .marshal().json(JsonLibrary.Jackson, TransationDto.class)
                .toF(RABBIT_URI, AUDIT, AUDIT);
    }

    private void enrichTransactionDto(Exchange exchange){

        TransationDto dto = exchange.getMessage().getBody(TransationDto.class);
        dto.setTransactionDate(new Date().toString());

        Message message = new DefaultMessage(exchange);
        message.setBody(dto);
        exchange.setMessage(message);
    }
}
