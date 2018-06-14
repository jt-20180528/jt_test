package com.jt.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class WebSocketApplication {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketApplication.class);

    public static void main(String[] args) {
        logger.info("WebSocket Service Running...");
        SpringApplication.run(WebSocketApplication.class, args);
    }

}
