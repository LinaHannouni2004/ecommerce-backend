package com.ecommerce.ecommerce_backend.mappers;

import com.ecommerce.ecommerce_backend.dtos.CategoryDtos.CategoryDTO;
import com.ecommerce.ecommerce_backend.dtos.CategoryDtos.CategoryWithProductsDTO;
import com.ecommerce.ecommerce_backend.entities.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    private final ProductMapper productMapper;

    public CategoryMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setProductCount( 0);
        return dto;
    }

    public CategoryWithProductsDTO toWithProductsDTO(Category category) {
        CategoryWithProductsDTO dto = new CategoryWithProductsDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setProducts(category.getProducts().stream()
                .map(productMapper::toDTO)  // instance method reference
                .collect(Collectors.toList()));
        return dto;
    }
}

