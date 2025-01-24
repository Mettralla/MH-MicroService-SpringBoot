package com.mindhub.orderMicroservice.controllers;

import com.mindhub.orderMicroservice.dtos.*;
import com.mindhub.orderMicroservice.exceptions.OrderNotFoundException;
import com.mindhub.orderMicroservice.services.OrderEntityService;
import com.mindhub.orderMicroservice.services.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Order Entity", description = "Operations related to Order Entity")
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderEntityService orderEntityService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping()
    @Operation(summary = "Get All Orders", description = "Retrieve all orders.")
    public ResponseEntity<Object> getAllOrders() {
        try {
            List<OrderDTO> ordersList = orderEntityService.getAllOrders();
            return new ResponseEntity<>(ordersList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Order by ID", description = "Retrieve an order by its ID.")
    public ResponseEntity<Object> getOrder(@PathVariable Long id) {
        try {
            OrderDTO foundOrder = orderEntityService.showOrder(id);
            return new ResponseEntity<>(foundOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    @Operation(summary = "Create Order", description = "Create a new order.")
    public  ResponseEntity<Object> createOrder(@Valid @RequestBody NewOrder newOrderData) {
        try {
            OrderDTO createdOrder = orderEntityService.createOrder(newOrderData);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Order Status", description = "Update the status of an existing order.")
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Order", description = "Delete an order by its ID.")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderEntityService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // =========================== ORDER ITEM =======================

    @PostMapping("/{id}/new-item")
    @Operation(summary = "Create Order Item", description = "Add a new item to an existing order.")
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
