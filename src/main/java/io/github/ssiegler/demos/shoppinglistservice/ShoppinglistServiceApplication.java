package io.github.ssiegler.demos.shoppinglistservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShoppinglistServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppinglistServiceApplication.class, args);
    }

    @Bean
    public ShoppinglistService shoppinglistService() {
        return new ShoppinglistService();
    }

}
