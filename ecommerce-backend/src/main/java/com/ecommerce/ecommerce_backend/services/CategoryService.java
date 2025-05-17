package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.entities.Category;
import com.ecommerce.ecommerce_backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> addCategories(List<Category> categories) {
        return categoryRepository.saveAll(categories);
    }
}
