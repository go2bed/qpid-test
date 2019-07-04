package com.qpid.test;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class Sender {


    public void sendMessage() throws Exception {
        final CachingConnectionFactory cf = new CachingConnectionFactory(EmbeddedAMQPBroker.BROKER_PORT);
        final RabbitTemplate template = new RabbitTemplate(cf);
        final String message = "hello world message!";
        template.convertAndSend("myExchange", "#", message);
        cf.destroy();
        // waitForMessageBeConsumed();
    }
}
