package com.restaurant.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.springdata.SpringDataDialect;


@SpringBootApplication
public class RestaurantAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantAppApplication.class, args);
	}
	@Bean
	public SpringDataDialect springDataDialect() {
		return new SpringDataDialect();
	}
}
