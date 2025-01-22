package com.mindhub.orderMicroservice.controllers;

import com.mindhub.orderMicroservice.dtos.*;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.services.OrderEntityService;
import com.mindhub.orderMicroservice.services.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderEntityService orderEntityService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping()
    public ResponseEntity<Object> getAllOrders() {
        try {
            List<OrderDTO> ordersList = orderEntityService.getAllOrders();
            return new ResponseEntity<>(ordersList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public  ResponseEntity<Object> createOrder(@Valid @RequestBody NewOrder newOrderData) {
        try {
            OrderDTO createdOrder = orderEntityService.createOrder(newOrderData);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(
            @Valid @RequestBody NewOrderStatus updateStatusData,
            @PathVariable Long id) {
        try {
            OrderDTO updatedOrder = orderEntityService.updateOrderStatus(id, updateStatusData);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // =========================== ORDER ITEM =======================

    @PostMapping("/{id}/new-item")
    public  ResponseEntity<Object> createOrderItem(
            @Valid @RequestBody NewOrderItem newOrderItemData,
            @PathVariable Long id
    ) {
        try {
            OrderItemDTO createdOrderItem = orderItemService.createOrderItem(newOrderItemData, id);
            return new ResponseEntity<>(createdOrderItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
