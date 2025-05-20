package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductCreateDTO;
import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductDTO;
import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductCreateDTO;
import com.ecommerce.ecommerce_backend.entities.Category;
import com.ecommerce.ecommerce_backend.entities.Product;
import com.ecommerce.ecommerce_backend.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce_backend.mappers.ProductMapper;
import com.ecommerce.ecommerce_backend.repositories.CategoryRepository;
import com.ecommerce.ecommerce_backend.repositories.ProductRepository;
import com.ecommerce.ecommerce_backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }
    @Override
    @Transactional
    public ProductDTO createProduct(ProductCreateDTO dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setStockQuantity(dto.getStockQuantity());
        product.setSpecification(dto.getSpecification());

        // ✅ On récupère la catégorie depuis son ID
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct); // ou une réponse manuelle
    }


    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductCreateDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
            product.setCategory(category);
        }

        productMapper.updateFromDto(dto, product);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(String query, Pageable pageable) {
        return productRepository.searchProducts(query, pageable)
                .map(productMapper::toDTO);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsByCategory(String categoryName, Pageable pageable) {
        return productRepository.findByCategoryNameIgnoreCase(categoryName, pageable)
                .map(productMapper::toDTO);
    }

}