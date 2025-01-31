package com.mindhub.productMicroservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.productMicroservice.dtos.StockUpdateData;
import com.mindhub.productMicroservice.events.ProductUpdatedEvent;
import com.mindhub.productMicroservice.models.Product;
import com.mindhub.productMicroservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductRequestListener {
    @Autowired
    private ProductRepository productRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Autowired
    public ProductRequestListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "product.request.queue")
    public String getProduct(Long productId) throws Exception {
        Product product = productRepository.findById(productId)
                .orElse(null);

        if (product == null) {
            return null;
        }

        return objectMapper.writeValueAsString(product);
    }

    @RabbitListener(queues = "product.stock.update.queue")
    public String updateProductStock(String jsonPayload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StockUpdateData stockUpdateData = objectMapper.readValue(jsonPayload, StockUpdateData.class);

            Long productId = stockUpdateData.productId();
            int updatedStock = stockUpdateData.updatedStock();

            Product product = productRepository.findById(productId)
                    .orElse(null);

            if (product == null) {
                return "Product not found";
            }

            product.setStock(updatedStock);
            productRepository.save(product);

            ProductUpdatedEvent event = new ProductUpdatedEvent(
                    product.getId(),
                    product.getName(),
                    product.getStock(),
                    product.getPrice()
            );
            rabbitMQProducer.sendProductUpdatedEvent(event);

            return "Stock updated successfully";

        } catch (JsonProcessingException e) {
            return "Error processing JSON payload: " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected error updating stock: " + e.getMessage();
        }
    }
}
