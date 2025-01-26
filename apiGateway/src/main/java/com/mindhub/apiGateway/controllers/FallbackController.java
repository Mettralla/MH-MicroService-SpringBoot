package com.mindhub.apiGateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/users")
    public ResponseEntity<String> fallbackForUsers() {
        return ResponseEntity.status(503).body("Servicio de usuarios no disponible");
    }

    @RequestMapping("/products")
    public ResponseEntity<String> fallbackForProducts() {
        return ResponseEntity.status(503).body("Servicio de productos no disponible");
    }

    @RequestMapping("/orders")
    public ResponseEntity<String> fallbackForOrders() {
        return ResponseEntity.status(503).body("Servicio de órdenes no disponible");
    }
}
