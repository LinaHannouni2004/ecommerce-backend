package com.ecommerce.ecommerce_backend.web.controllers;

import com.ecommerce.ecommerce_backend.dtos.CategoryDtos.CategoryDTO;
import com.ecommerce.ecommerce_backend.entities.Category;
import com.ecommerce.ecommerce_backend.mappers.CategoryMapper;
import com.ecommerce.ecommerce_backend.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(category -> {
                        CategoryDTO dto = new CategoryDTO();
                        dto.setId(category.getId());
                        dto.setName(category.getName());
                        dto.setProductCount(0); // Don't try to count products
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(categoryDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching categories: " + e.getMessage());
        }
    }
}