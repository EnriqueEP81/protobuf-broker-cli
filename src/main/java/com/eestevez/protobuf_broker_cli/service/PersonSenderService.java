package com.eestevez.protobuf_broker_cli.service;

import com.eestevez.protobuf_broker_cli.config.RabbitConfig;
import com.eestevez.protobuf_broker_cli.proto.PersonOuterClass;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.amqp.core.Message;
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

    public PersonOuterClass.Person consumePerson() {
        Message message = rabbitTemplate.receive(RabbitConfig.QUEUE_NAME, 5000);
        if (message !=null) {
            try {
                return PersonOuterClass.Person.parseFrom(message.getBody());
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException("Failed to parse Protobuf message", e);
            }
        } else {
            return null;
        }
    }
}
