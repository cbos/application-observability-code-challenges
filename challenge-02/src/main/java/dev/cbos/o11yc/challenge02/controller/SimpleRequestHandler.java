package dev.cbos.o11yc.challenge02.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class SimpleRequestHandler {

    private final Logger logger = LoggerFactory.getLogger(SimpleRequestHandler.class);

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/")
    public String index() {
        logger.info("In index handler");
        return String.format("Greetings from Observability demo app '%s'!", appName);
    }

    @GetMapping("/random/{id}")
    public String randomBehaviour(@PathVariable String id) throws InterruptedException {
        int parsedId = Integer.parseInt(id);

        logger.info("In random behavior handler for {}", id);

        if (!handleRequest(parsedId)) {
            logger.error("Failed request");
            throw new RuntimeException("Something went terribly wrong");
        }

        return String.format("Random greetings from Observability demo app '%s' with input %s!", appName, id);
    }

    private boolean handleRequest(int id) throws InterruptedException {
        Thread.sleep((long) (Math.abs((random.nextGaussian() + 1.0) * 300.0)));
        // mark fine when id is bigger then 3
        return id > 3;
    }
}
