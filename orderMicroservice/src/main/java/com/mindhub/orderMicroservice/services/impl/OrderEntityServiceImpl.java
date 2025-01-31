package com.mindhub.orderMicroservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.orderMicroservice.dtos.*;
import com.mindhub.orderMicroservice.exceptions.InsufficientStockException;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.exceptions.UserEntityNotFoundException;
import com.mindhub.orderMicroservice.models.OrderEntity;
import com.mindhub.orderMicroservice.models.Status;
import com.mindhub.orderMicroservice.repositories.OrderEntityRepository;
import com.mindhub.orderMicroservice.services.OrderEntityService;
import com.mindhub.orderMicroservice.services.OrderItemService;
import com.mindhub.orderMicroservice.services.RabbitMQProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderEntityServiceImpl implements OrderEntityService {

    @Autowired
    OrderEntityRepository orderRepository;

    private RestTemplate restTemplate;

    @Autowired
    private  RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;

    @Autowired
    public OrderEntityServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public OrderItemService orderItemService;

    @Override
    public OrderDTO createOrder(NewOrder newOrderData) throws UserEntityNotFoundException, JsonProcessingException {
        UserEntityData userEntityData = getUserEntityData(newOrderData.userId());

        OrderEntity newOrder = new OrderEntity(
            newOrderData.userId(),
            newOrderData.status()
        );

        OrderEntity createdOrder = orderRepository.save(newOrder);

        List<OrderItemDTO> orderItemsDTO = createdOrder.getProducts()
                .stream()
                .map(orderItem -> {
                    ProductEntityEvent productData = orderItemService.getProductEntity(orderItem.getProductId());
                    ProductEntityData productEntityData = new ProductEntityData(
                            Math.toIntExact(productData.id()),
                            productData.name(),
                            productData.description(),
                            productData.price()
                    );
                    return new OrderItemDTO(orderItem, productEntityData);
                })
                .toList();

//        OrderDTO createdOrderDTO = new OrderDTO(createdOrder, userEntityData, orderItemsDTO);

//        rabbitMQProducer.sendOrder(createdOrderDTO);

        return new OrderDTO(createdOrder, userEntityData, orderItemsDTO);
    }

    @Override
    public List<OrderDTO> getAllOrders() throws UserEntityNotFoundException {
        return orderRepository.findAll()
                              .stream()
                                .map(order -> {
                                    try {
                                        UserEntityData userEntityData = getUserEntityData(order.getUserId());

                                        List<OrderItemDTO> orderItemsDTO = order.getProducts()
                                                .stream()
                                                .map(orderItem -> {
                                                    ProductEntityEvent productData = orderItemService.getProductEntity(orderItem.getProductId());
                                                    ProductEntityData productEntityData = new ProductEntityData(
                                                            Math.toIntExact(productData.id()),
                                                            productData.name(),
                                                            productData.description(),
                                                            productData.price()
                                                    );
                                                    return new OrderItemDTO(orderItem, productEntityData);
                                                })
                                                .toList();

                                        return new OrderDTO(order, userEntityData, orderItemsDTO);
                                    } catch (UserEntityNotFoundException e) {
                                        System.out.println("Error: User Entity Not Found for order ID: " + order.getId());
                                        return null;
                                    }
                                })
                                .filter(orderDTO -> orderDTO != null)
                                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, NewOrderStatus newStatus) throws OrderNotFoundException, UserEntityNotFoundException {
        OrderEntity preUpdateOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        preUpdateOrder.setStatus(newStatus.status());
        OrderEntity postUpdateOrder = orderRepository.save(preUpdateOrder);

        UserEntityData userEntityData = getUserEntityData(postUpdateOrder.getUserId());

        List<OrderItemDTO> orderItemsDTO = postUpdateOrder.getProducts()
                .stream()
                .map(orderItem -> {
                    ProductEntityEvent productData = orderItemService.getProductEntity(orderItem.getProductId());
                    ProductEntityData productEntityData = new ProductEntityData(
                            Math.toIntExact(productData.id()),
                            productData.name(),
                            productData.description(),
                            productData.price()
                    );
                    return new OrderItemDTO(orderItem, productEntityData);
                })
                .toList();

        return new OrderDTO(postUpdateOrder, userEntityData, orderItemsDTO);
    }

    @Override
    public OrderDTO showOrder(Long orderId) throws OrderNotFoundException, UserEntityNotFoundException {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        UserEntityData userEntityData = getUserEntityData(orderEntity.getUserId());

        List<OrderItemDTO> orderItemsDTO = orderEntity.getProducts()
                .stream()
                .map(orderItem -> {
                    ProductEntityEvent productData = orderItemService.getProductEntity(orderItem.getProductId());
                    ProductEntityData productEntityData = new ProductEntityData(
                            Math.toIntExact(productData.id()),
                            productData.name(),
                            productData.description(),
                            productData.price()
                    );
                    return new OrderItemDTO(orderItem, productEntityData);
                })
                .toList();

        return new OrderDTO(orderEntity, userEntityData, orderItemsDTO);
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));

        orderRepository.delete(orderEntity);
    }

    // ============================== UTILS ===============================

    public UserEntityData getUserEntityData(Long userId) throws UserEntityNotFoundException {
        try {
            String response = (String) rabbitTemplate.convertSendAndReceive("user.request.exchange", "user.request", userId);
            if (response == null) {
                throw new UserEntityNotFoundException("User with ID " + userId + " not found");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, UserEntityData.class);

        } catch (Exception e) {
            throw new RuntimeException("Unexpected error connecting to UserService", e);
        }
    }


}
