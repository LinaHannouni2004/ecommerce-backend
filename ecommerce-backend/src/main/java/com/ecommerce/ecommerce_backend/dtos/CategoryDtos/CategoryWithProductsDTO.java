package com.ecommerce.ecommerce_backend.dtos.CategoryDtos;

import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryWithProductsDTO {
    private Long id;
    private String name;
    private List<ProductDTO> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }
}
