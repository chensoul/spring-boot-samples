package com.chensoul.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories
@ConfigurationPropertiesScan
@EnableScheduling
public class SpringBootSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSimpleApplication.class, args);
    }
}