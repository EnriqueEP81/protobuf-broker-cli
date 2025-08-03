package com.eestevez.protobuf_broker_cli.service;

import com.eestevez.protobuf_broker_cli.config.RabbitConfig;
import com.eestevez.protobuf_broker_cli.proto.PersonOuterClass;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonSenderService {

    private final RabbitTemplate rabbitTemplate;

    public PersonSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPerson(PersonOuterClass.Person person) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, person.toByteArray());
    }
}
