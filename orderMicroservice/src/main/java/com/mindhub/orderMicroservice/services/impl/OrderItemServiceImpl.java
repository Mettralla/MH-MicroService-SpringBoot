package com.mindhub.orderMicroservice.services.impl;

import com.mindhub.orderMicroservice.config.RestTemplateConfig;
import com.mindhub.orderMicroservice.dtos.*;
import com.mindhub.orderMicroservice.exceptions.InsufficientStockException;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.exceptions.ProductNotFoundException;
import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.OrderItem;
import com.mindhub.orderMicroservice.repositories.OrderEntityRepository;
import com.mindhub.orderMicroservice.repositories.OrderItemRepository;
import com.mindhub.orderMicroservice.services.OrderItemService;
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

        Map<String, Object> productData = getProductEntity(newOrderItem.productId());

        ProductEntityData productEntityData = new ProductEntityData(
                ((Integer) productData.get("id")),
                ((String) productData.get("name")),
                ((String) productData.get("description")),
                ((Double) productData.get("price"))
        );

        int stock = ((Number) productData.get("stock")).intValue();

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

    public Map<String, Object> getProductEntity(Long productId) throws ProductNotFoundException {
        String productServiceUrl = productServiceBaseUrl + "/" + productId;

        ResponseEntity<Object> response = restTemplate.getForEntity(productServiceUrl, Object.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> productData = (Map<String, Object>) response.getBody();
        return productData;
    }

    public void updateProductStock(Long productId, int updatedStock) {
        String patchStockUrl = productServiceBaseUrl + "/" + productId + "/stock?stock=" + updatedStock;
        try {
            restTemplate.put(patchStockUrl, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update stock for product ID " + productId + ": " + e.getMessage());
        }
    }
}
