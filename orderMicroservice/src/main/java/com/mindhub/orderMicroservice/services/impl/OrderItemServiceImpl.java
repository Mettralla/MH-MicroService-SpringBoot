package com.mindhub.orderMicroservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.orderMicroservice.config.RestTemplateConfig;
import com.mindhub.orderMicroservice.dtos.*;
import com.mindhub.orderMicroservice.exceptions.InsufficientStockException;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.exceptions.ProductNotFoundException;
import com.mindhub.orderMicroservice.exceptions.UserEntityNotFoundException;
import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.OrderItem;
import com.mindhub.orderMicroservice.repositories.OrderEntityRepository;
import com.mindhub.orderMicroservice.repositories.OrderItemRepository;
import com.mindhub.orderMicroservice.services.OrderEntityService;
import com.mindhub.orderMicroservice.services.OrderItemService;
import com.mindhub.orderMicroservice.services.RabbitMQProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    private RestTemplate restTemplate;

    @Autowired
    private  RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${productservice.baseurl}")
    private String productServiceBaseUrl;

    @Autowired
    public OrderItemServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public OrderItemDTO createOrderItem(NewOrderItem newOrderItem, Long orderId) throws OrderNotFoundException, InsufficientStockException {
        OrderEntity itemOwner = orderEntityRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found"));

        ProductEntityEvent productData = getProductEntity(newOrderItem.productId());

        ProductEntityData productEntityData = new ProductEntityData(
                Math.toIntExact(productData.id()),
                productData.name(),
                productData.description(),
                productData.price()
        );

        int stock = productData.stock();

        validateStock(newOrderItem, stock);

        OrderItem orderItem = new OrderItem(newOrderItem.productId(), newOrderItem.quantity());
        OrderItem createdOrderItem = saveOrderItem(orderItem, itemOwner);

        int updatedStock = stock - newOrderItem.quantity();
        updateProductStock(newOrderItem.productId(), updatedStock);

        return new OrderItemDTO(createdOrderItem, productEntityData);
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem, OrderEntity itemOwner) {
        itemOwner.addOrderItem(orderItem);
        orderItemRepository.save(orderItem);

        return orderItem;
    }

    // ======================== ORDER ITEM UTILS =======================

    public void validateStock(NewOrderItem newOrderItem, int stock) throws InsufficientStockException {
        if (newOrderItem.quantity() > stock) {
            throw new InsufficientStockException(
                    "Insufficient stock for product ID " + newOrderItem.productId() +
                            ". Requested: " + newOrderItem.quantity() + ", Available: " + stock
            );
        }
    }

    public ProductEntityEvent getProductEntity(Long productId) throws ProductNotFoundException {
        try {
            String response = (String) rabbitTemplate.convertSendAndReceive("product.request.exchange", "product.request", productId);

            if (response == null) {
                throw new ProductNotFoundException("Product with ID " + productId + " not found");
            }

            return objectMapper.readValue(response, ProductEntityEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error connecting to ProductService: " + e.getMessage());
        }
    }

    public void updateProductStock(Long productId, int updatedStock) {
        try {
            StockUpdateData stockUpdateData = new StockUpdateData(productId, updatedStock);

            String jsonPayload = objectMapper.writeValueAsString(stockUpdateData);

            String response = (String) rabbitTemplate.convertSendAndReceive(
                    "product.request.exchange",
                    "product.stock.routingkey",
                    jsonPayload
            );


            if (response == null) {
                throw new RuntimeException("Failed to update stock for product ID " + productId);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting stock update data to JSON: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error updating stock for product ID " + productId + ": " + e.getMessage());
        }
    }
}
