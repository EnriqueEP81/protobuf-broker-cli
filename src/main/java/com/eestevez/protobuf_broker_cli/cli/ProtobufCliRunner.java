package com.eestevez.protobuf_broker_cli.cli;

import com.eestevez.protobuf_broker_cli.proto.PersonOuterClass;
import com.eestevez.protobuf_broker_cli.service.PersonSenderService;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.util.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ProtobufCliRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ProtobufCliRunner.class);
    private final PersonSenderService personSenderService;
    private final ApplicationContext context;

    public ProtobufCliRunner(PersonSenderService personSenderService, ApplicationContext context){
        this.personSenderService = personSenderService;
        this.context = context;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("file")) {
            String filePath = args.getOptionValues("file").get(0);
            log.info("Processing JSON file: {}", filePath);

            try {
                String json = Files.readString(Paths.get(filePath));
                log.info("file contents:\n{}", json);
                PersonOuterClass.Person.Builder builder = PersonOuterClass.Person.newBuilder();
                JsonFormat.parser().ignoringUnknownFields().merge(json,builder);
                PersonOuterClass.Person person = builder.build();
                log.info("Person object: "+ person);
                personSenderService.sendPerson(person);

            } catch (Exception e) {
                log.error("Error reading file {}", filePath, e);
            }
        }
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
}
