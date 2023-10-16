package com.bbps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class BbpsIncoming {
    public static void main(String[] args) {
        SpringApplication.run(BbpsIncoming.class, args);
    }
}