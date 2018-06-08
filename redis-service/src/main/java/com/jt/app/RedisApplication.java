package com.jt.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisApplication {

    private static final Logger logger = LoggerFactory.getLogger(RedisApplication.class);

    public static void main(String[] args) {
        logger.info("Redis Service Running...");
        SpringApplication.run(RedisApplication.class, args);
    }
}
