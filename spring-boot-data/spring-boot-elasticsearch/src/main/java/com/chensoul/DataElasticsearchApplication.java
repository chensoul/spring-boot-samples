package com.chensoul;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
@SpringBootApplication
public class DataElasticsearchApplication {

    @Bean
    public InitializingBean seedDatabase(CarRepository repository) {
        return () -> {
            repository.deleteAll();
            repository.save(new Car("Honda", "Civic", 1997));
            repository.save(new Car("Honda", "Accord", 2003));
            repository.save(new Car("Ford", "Escort", 1985));
        };
    }

    @Bean
    public CommandLineRunner example(CarRepository repository, ElasticsearchTemplate template) {
        return (args) -> {
            System.err.println("From the repository...");
            repository.findByMakeIgnoringCase("fOrD").forEach(System.err::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DataElasticsearchApplication.class, args);
        System.exit(0);
    }

}
