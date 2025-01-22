package com.mindhub.productMicroservice.controllers;

import com.mindhub.productMicroservice.dtos.NewProduct;
import com.mindhub.productMicroservice.dtos.ProductDTO;
import com.mindhub.productMicroservice.exceptions.ProductNotFoundException;
import com.mindhub.productMicroservice.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<Object> getAllProducts() {
        try {
            List<ProductDTO> productsList = productService.getAllProducts();
            return new ResponseEntity<>(productsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public  ResponseEntity<Object> createProduct(@Valid @RequestBody NewProduct newProductData) {
        try {
            ProductDTO createdProduct = productService.createProduct(newProductData);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(
            @Valid @RequestBody NewProduct updateProductData,
            @PathVariable Long id) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(updateProductData, id);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
