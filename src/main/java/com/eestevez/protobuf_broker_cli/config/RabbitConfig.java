package com.eestevez.protobuf_broker_cli.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Bean
    public Queue personQueue() {
        return new Queue(queueName, false);
    }
}
