package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductCreateDTO;
import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDTO createProduct(ProductCreateDTO dto);
    ProductDTO getProductById(Long id);
    Page<ProductDTO> getAllProducts(Pageable pageable);
    ProductDTO updateProduct(Long id, ProductCreateDTO dto);
    void deleteProduct(Long id);
    Page<ProductDTO> searchProducts(String query, Pageable pageable);
}