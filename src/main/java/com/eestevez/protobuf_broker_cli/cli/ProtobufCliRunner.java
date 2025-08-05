package com.eestevez.protobuf_broker_cli.cli;

import com.eestevez.protobuf_broker_cli.proto.PersonOuterClass;
import com.eestevez.protobuf_broker_cli.service.PersonSenderService;
import com.google.protobuf.util.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Profile("!test")
public class ProtobufCliRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ProtobufCliRunner.class);
    private final String CONSUME = "consume";
    private final String FILE = "file";
    private final PersonSenderService personSenderService;
    private final ApplicationContext context;

    public ProtobufCliRunner(PersonSenderService personSenderService, ApplicationContext context){
        this.personSenderService = personSenderService;
        this.context = context;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption(FILE)) {
            String filePath = args.getOptionValues("file").get(0);
            log.info("Processing JSON file: {}", filePath);

            String json = Files.readString(Paths.get(filePath));
            PersonOuterClass.Person.Builder builder = PersonOuterClass.Person.newBuilder();

            JsonFormat.parser().ignoringUnknownFields().merge(json,builder);
            PersonOuterClass.Person person = builder.build();

            personSenderService.sendPerson(person);
            log.info("Person object sent: {} ", person);

        }
        if (args.containsOption(CONSUME) && "true".equals(args.getOptionValues("consume").get(0)) ) {
            PersonOuterClass.Person person = null;
            do {
                person = personSenderService.consumePerson();
                if (person!=null) {
                    log.info("Person object consumed: {} ", person);
                }
            }
            while (person!=null);
        }
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
}
