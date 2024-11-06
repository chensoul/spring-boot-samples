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
public class SpringBootMonolithApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMonolithApplication.class, args);
    }
}