package com.ecommerce.ecommerce_backend.dtos.CategoryDtos;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private int productCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getName() {
        return name;
    }

    public int getProductCount() {
        return productCount;
    }
}
