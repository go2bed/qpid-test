package com.qpid.test;

import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import static org.springframework.util.SocketUtils.findAvailableTcpPort;

public class EmbeddedAMQPBroker {


    public static final int BROKER_PORT = findAvailableTcpPort();
    private final Broker broker = new Broker();

    public EmbeddedAMQPBroker() throws Exception {
        final String configFileName = "qpid-config.json";
        final String passwordFileName = "passwd.properties";
        // prepare options
        final BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("qpid.amqp_port", String.valueOf(BROKER_PORT));
        brokerOptions.setConfigProperty("qpid.pass_file", passwordFileName);
        brokerOptions.setConfigProperty("qpid.work_dir", "work");
        brokerOptions.setInitialConfigurationLocation(configFileName);
        // start broker
        broker.startup(brokerOptions);
    }


    public void createExchange() {
        final CachingConnectionFactory cf = new CachingConnectionFactory(EmbeddedAMQPBroker.BROKER_PORT);
        final RabbitAdmin admin = new RabbitAdmin(cf);
        final Queue queue = new Queue("myQueue", false);
        admin.declareQueue(queue);
        final TopicExchange exchange = new TopicExchange("myExchange");
        admin.declareExchange(exchange);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("#"));
        cf.destroy();
    }
    public void deleteExchange() {
        final CachingConnectionFactory cf = new CachingConnectionFactory(EmbeddedAMQPBroker.BROKER_PORT);
        final RabbitAdmin admin = new RabbitAdmin(cf);
        admin.deleteExchange("myExchange");
        cf.destroy();
    }

}
