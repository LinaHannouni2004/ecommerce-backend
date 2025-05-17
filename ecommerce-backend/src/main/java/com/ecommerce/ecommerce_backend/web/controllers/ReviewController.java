package com.ecommerce.ecommerce_backend.web.controllers;



import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewCreateDTO;
import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewDTO;
import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewModerationDTO;
import com.ecommerce.ecommerce_backend.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;

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
}