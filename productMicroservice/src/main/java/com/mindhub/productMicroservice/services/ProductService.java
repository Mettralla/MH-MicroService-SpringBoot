package com.mindhub.productMicroservice.services;

import com.mindhub.productMicroservice.dtos.NewProduct;
import com.mindhub.productMicroservice.dtos.ProductDTO;
import com.mindhub.productMicroservice.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(NewProduct newProductData);
    ProductDTO updateProduct(NewProduct updateProductData, Long id) throws ProductNotFoundException;
    ProductDTO showProduct(Long id) throws ProductNotFoundException;
    ProductDTO updateProductStock(Long id, int newStock) throws ProductNotFoundException;
}
