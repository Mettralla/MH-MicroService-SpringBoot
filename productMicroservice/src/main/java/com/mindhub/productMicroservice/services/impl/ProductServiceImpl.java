package com.mindhub.productMicroservice.services.impl;

import com.mindhub.productMicroservice.dtos.NewProduct;
import com.mindhub.productMicroservice.dtos.ProductDTO;
import com.mindhub.productMicroservice.exceptions.ProductNotFoundException;
import com.mindhub.productMicroservice.models.Product;
import com.mindhub.productMicroservice.repositories.ProductRepository;
import com.mindhub.productMicroservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

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
}
