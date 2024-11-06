package com.chensoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OperationsLoggingApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(
                OperationsLoggingApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

}
