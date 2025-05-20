package com.ecommerce.ecommerce_backend.web.controllers;



import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewCreateDTO;
import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewDTO;
import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewModerationDTO;
import com.ecommerce.ecommerce_backend.entities.Review;
import com.ecommerce.ecommerce_backend.mappers.ReviewMapper;
import com.ecommerce.ecommerce_backend.repositories.ReviewRepository;
import com.ecommerce.ecommerce_backend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {


    private final ReviewService reviewService;
    private ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }


    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewCreateDTO dto) {
        return ResponseEntity.ok(reviewService.createReview(dto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<ReviewDTO>> getProductReviews(
            @PathVariable Long productId,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.getProductReviews(productId, pageable));
    }



    @PutMapping("/{id}/moderate")
    public ResponseEntity<ReviewDTO> moderateReview(
            @PathVariable Long id,
            @RequestBody ReviewModerationDTO dto) {
        return ResponseEntity.ok(reviewService.moderateReview(id, dto));
    }
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }






}