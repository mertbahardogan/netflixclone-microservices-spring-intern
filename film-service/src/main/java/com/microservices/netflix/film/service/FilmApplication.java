package com.microservices.netflix.film.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Component
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class FilmApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FilmApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application run from film service.");

    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.microservices.netflix.film.service")).build();
    }
}
