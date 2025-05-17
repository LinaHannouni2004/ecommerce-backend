package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewCreateDTO;
import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewDTO;
import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewModerationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewCreateDTO dto);
    Page<ReviewDTO> getProductReviews(Long productId, Pageable pageable);
    ReviewDTO moderateReview(Long id, ReviewModerationDTO dto);
    List<ReviewDTO> analyzeReviewsForSentiment();
}
