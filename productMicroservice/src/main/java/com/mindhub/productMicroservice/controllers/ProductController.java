package com.mindhub.productMicroservice.controllers;

import com.mindhub.productMicroservice.dtos.NewProduct;
import com.mindhub.productMicroservice.dtos.ProductDTO;
import com.mindhub.productMicroservice.exceptions.ProductNotFoundException;
import com.mindhub.productMicroservice.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Product Entity", description = "Operations related to Product Entity")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    @Operation(summary = "Get All Products", description = "Retrieve all Products entities.")
    public ResponseEntity<Object> getAllProducts() {
        try {
            List<ProductDTO> productsList = productService.getAllProducts();
            return new ResponseEntity<>(productsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    @Operation(summary = "Create a Product", description = "Create a new Product entity with the provided details.")
    public  ResponseEntity<Object> createProduct(@Valid @RequestBody NewProduct newProductData) {
        try {
            ProductDTO createdProduct = productService.createProduct(newProductData);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Product", description = "Update the details of an existing Product entity by ID.")
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

    @GetMapping("/{id}")
    @Operation(summary = "Get a Product", description = "Retrieve the details of a specific Product entity by ID.")
    public ResponseEntity<Object> showProduct(
            @PathVariable Long id) {
        try {
            ProductDTO foundProduct = productService.showProduct(id);
            return new ResponseEntity<>(foundProduct, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/stock")
    @Operation(summary = "Update Product Stock", description = "Update the stock quantity of a specific Product entity by ID.")
    public ResponseEntity<Object> updateProductStock(
            @PathVariable Long id,
            @RequestParam int stock) {
        try {
            ProductDTO updatedProduct = productService.updateProductStock(id, stock);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
