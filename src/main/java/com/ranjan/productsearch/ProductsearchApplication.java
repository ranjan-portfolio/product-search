package com.ranjan.productsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class ProductsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsearchApplication.class, args);
	}

}
