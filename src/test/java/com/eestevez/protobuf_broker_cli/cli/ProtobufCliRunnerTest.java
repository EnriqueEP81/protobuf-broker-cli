package com.eestevez.protobuf_broker_cli.cli;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProtobufCliRunnerTest {
    @Autowired
    private ProtobufCliRunner runner;
    @Test
    void runnerTest() throws Exception {
        String filePath = "C:/Projects/capitole/example.json";
        String[] args = { "--file=" + filePath };
        ApplicationArguments appArgs = new DefaultApplicationArguments(args);
        runner.run(appArgs);
    }
}
