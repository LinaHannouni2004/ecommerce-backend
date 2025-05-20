package com.ecommerce.ecommerce_backend.web.controllers;

import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductCreateDTO;
import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductDTO;
import com.ecommerce.ecommerce_backend.entities.Product;
import com.ecommerce.ecommerce_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAllProducts(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // New POST endpoint to create a product


    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductCreateDTO dto) {
        System.out.println("Received: " + dto); // Vérifie la réception
        return new ResponseEntity<>(productService.createProduct(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductCreateDTO product) {
        ProductDTO updated = productService.updateProduct(id, product);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // ✅ DELETE : Supprimer un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryName, PageRequest.of(page, size)));
    }
}
