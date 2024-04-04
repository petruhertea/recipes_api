package com.example.recipeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecipeapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeapiApplication.class, args);

	}

}
