package com.eestevez.protobuf_broker_cli.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME = "person-queue";

    @Bean
    public Queue personQueue() {
        return new Queue(QUEUE_NAME, false);
    }
}
