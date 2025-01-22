package com.mindhub.productMicroservice;

import com.mindhub.productMicroservice.models.Product;
import com.mindhub.productMicroservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductMicroserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ProductRepository productRepository) {
		return args -> {
			Product product1 = new Product("Smartphone", "Latest model with advanced features", 799.99, 150);
			Product product2 = new Product("Laptop", "High-performance laptop for professionals", 1200.00, 50);
			Product product3 = new Product("Wireless Headphones", "Noise-cancelling headphones with long battery life", 199.99, 300);

			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
		};
	}
}
