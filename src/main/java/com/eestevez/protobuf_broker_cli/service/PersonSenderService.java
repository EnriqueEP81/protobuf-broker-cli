package com.eestevez.protobuf_broker_cli.service;

import com.eestevez.protobuf_broker_cli.proto.PersonOuterClass;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PersonSenderService {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    private final RabbitTemplate rabbitTemplate;

    public PersonSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPerson(PersonOuterClass.Person person) {
        rabbitTemplate.convertAndSend(queueName, person.toByteArray());
    }

    public PersonOuterClass.Person consumePerson() {
        Message message = rabbitTemplate.receive(queueName, 5000);
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
