package com.exchange.rate.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExchangeRateFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateFeignApplication.class, args);
    }

}
