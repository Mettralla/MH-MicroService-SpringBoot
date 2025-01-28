package com.mindhub.productMicroservice.services.impl;

import com.mindhub.productMicroservice.dtos.NewProduct;
import com.mindhub.productMicroservice.dtos.ProductDTO;
import com.mindhub.productMicroservice.events.ProductUpdatedEvent;
import com.mindhub.productMicroservice.exceptions.ProductNotFoundException;
import com.mindhub.productMicroservice.models.Product;
import com.mindhub.productMicroservice.repositories.ProductRepository;
import com.mindhub.productMicroservice.services.ProductService;
import com.mindhub.productMicroservice.services.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                                .stream()
                                .map(product -> new ProductDTO(product))
                                .toList();
    }

    @Override
    public ProductDTO createProduct(NewProduct newProductData) {
        Product newProduct = new Product(
          newProductData.name(),
          newProductData.description(),
          newProductData.price(),
          newProductData.stock()
        );

        Product createdProduct = productRepository.save(newProduct);
        return new ProductDTO(createdProduct);
    }

    @Override
    public ProductDTO updateProduct(NewProduct updateProductData, Long id) throws ProductNotFoundException {
        Product preUpdateProduct = productRepository.findById(id).orElseThrow( () -> new ProductNotFoundException(
                "Product Not Found"));

        preUpdateProduct.setName(updateProductData.name());
        preUpdateProduct.setDescription(updateProductData.description());
        preUpdateProduct.setPrice(updateProductData.price());
        preUpdateProduct.setStock(updateProductData.stock());

        Product postUpdateProduct = productRepository.save(preUpdateProduct);
        return new ProductDTO(postUpdateProduct);
    }

    @Override
    public ProductDTO showProduct(Long id) throws ProductNotFoundException {
        Product productEntity = productRepository.findById(id).orElseThrow( () ->
                new ProductNotFoundException("Product Not Found"));
        return new ProductDTO(productEntity);
    }

    @Override
    public ProductDTO updateProductStock(Long id, int newStock) throws ProductNotFoundException {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        productToUpdate.setStock(newStock);

        Product updatedProduct = productRepository.save(productToUpdate);

        ProductUpdatedEvent event = new ProductUpdatedEvent(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getStock(),
                updatedProduct.getPrice()
        );
        rabbitMQProducer.sendProductUpdatedEvent(event);

        return new ProductDTO(updatedProduct);
    }
}
