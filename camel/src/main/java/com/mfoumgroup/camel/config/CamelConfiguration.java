package com.mfoumgroup.camel.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfiguration {

    private static final String EX_DIRECT = "amq.direct";
    public static final String RABBIT_URI = "rabbitmq:" + EX_DIRECT + "?queue=%s&routingKey=%s&autoDelete=false";

   @Bean
    public ConnectionFactory connectionFactory(){
        return factory();
    }

    private ConnectionFactory factory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("user");
        factory.setPassword("bitnami");
        return factory;
    }

    /**
     * recupération du metrics des routes apache camel
     * return
     */
   /* @Bean
    public CamelContextConfiguration camelContextConfiguration(){
        return  new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                camelContext.addRoutePolicyFactory(new MicrometerRoutePolicyFactory());
                camelContext.setMessageHistoryFactory(new MicrometerMessageHistoryFactory());
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {

            }
        };
    }*/
}
