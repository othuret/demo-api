package org.othuret.api.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class Application {

    @Value("${spring.data.mongodb.collection-base-name}")
    private String dbCollectionName;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public String dbCollectionName() {
        return dbCollectionName + "_products";
    }
}
