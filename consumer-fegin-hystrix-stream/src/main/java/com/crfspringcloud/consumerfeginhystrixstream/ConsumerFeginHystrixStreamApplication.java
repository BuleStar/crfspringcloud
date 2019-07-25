package com.crfspringcloud.consumerfeginhystrixstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableCircuitBreaker
public class ConsumerFeginHystrixStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeginHystrixStreamApplication.class, args);
    }

}
