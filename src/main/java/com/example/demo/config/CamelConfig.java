package com.example.demo.config;

import jakarta.jms.ConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQJMSConnectionFactory("vm://0");
        getContext().addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        from("timer://foo?period=10000")
                .setBody(constant("Mensagem de Teste"))
                .log("Sending message to testQueue: ${body}")
                .to("activemq:queue:testQueue");

        from("activemq:queue:testQueue")
                .log("Received message in testQueue: ${body}")
                .to("log:receivedMessage?showAll=true");
    }
}
