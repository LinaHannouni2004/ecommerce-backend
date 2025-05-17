package com.ecommerce.ecommerce_backend;

import com.ecommerce.ecommerce_backend.entities.Category;
import com.ecommerce.ecommerce_backend.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryService categoryService;

    public DataInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Ajoute les catégories par défaut
        if (categoryService.addCategories(Arrays.asList(
                new Category("Smartwatch", null),   // Pas besoin de null au début
                new Category("Home Appliances", null),
                new Category("Pc&Laptop", null),
                new Category("Headphones&Airpods", null),
                new Category("Phones&Tablets", null),
                new Category("Accessories", null)
        )) != null) {
            System.out.println("Categories added successfully!");
        }
    }
}
