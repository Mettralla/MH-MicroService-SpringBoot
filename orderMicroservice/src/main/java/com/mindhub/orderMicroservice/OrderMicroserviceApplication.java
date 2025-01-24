package com.mindhub.orderMicroservice;

import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.OrderItem;
import com.mindhub.orderMicroservice.models.Status;
import com.mindhub.orderMicroservice.repositories.OrderEntityRepository;
import com.mindhub.orderMicroservice.repositories.OrderItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderMicroserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(OrderEntityRepository orderRepository, OrderItemRepository orderItemRepository) {
		return args -> {
			OrderEntity order1 = new OrderEntity(1L, Status.PENDING);
			OrderEntity order2 = new OrderEntity(2L, Status.COMPLETED);
			OrderEntity order3 = new OrderEntity(3L, Status.PENDING);

			OrderItem orderItem1 = new OrderItem(2L, 300);
			OrderItem orderItem2 = new OrderItem(3L, 100);

			orderRepository.save(order1);
			orderRepository.save(order2);
			orderRepository.save(order3);

			order3.addOrderItem(orderItem1);
			order3.addOrderItem(orderItem2);

			orderItemRepository.save(orderItem1);
			orderItemRepository.save(orderItem2);

		};
	}
}
