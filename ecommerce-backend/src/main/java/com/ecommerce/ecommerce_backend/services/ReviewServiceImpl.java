package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.*;
import com.ecommerce.ecommerce_backend.dtos.SentimentAnalysisResult;
import com.ecommerce.ecommerce_backend.entities.*;
import com.ecommerce.ecommerce_backend.enums.*;
import com.ecommerce.ecommerce_backend.exceptions.*;
import com.ecommerce.ecommerce_backend.mappers.*;
import com.ecommerce.ecommerce_backend.repositories.*;
import com.ecommerce.ecommerce_backend.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;
    private final SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,ProductRepository productRepository,UserRepository userRepository, SentimentAnalysisService sentimentAnalysisService) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.reviewMapper = new ReviewMapper();
    }

    @Override
    @Transactional
    public ReviewDTO createReview(ReviewCreateDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (reviewRepository.existsByUserIdAndProductId(dto.getUserId(), dto.getProductId())) {
            throw new BusinessException("You have already reviewed this product");
        }

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setStatus(ReviewStatus.PENDING_AI_ANALYSIS);

        Review savedReview = reviewRepository.save(review);

        // Analyse asynchrone (pourrait être déplacé dans un @EventListener)
        analyzeReviewAsync(savedReview);

        return reviewMapper.toDTO(savedReview);
    }

    private void analyzeReviewAsync(Review review) {
        SentimentAnalysisResult result = sentimentAnalysisService.analyzeText(review.getComment());

        review.setSentiment(result.getSentiment());
        review.setSentimentScore(result.getScore());

        // Déterminer le statut automatiquement
        if (result.getSentiment() == SentimentLabel.POSITIVE && review.getRating() >= 4) {
            review.setStatus(ReviewStatus.AUTO_APPROVED);
        } else if (result.getSentiment() == SentimentLabel.NEGATIVE && review.getRating() <= 2) {
            review.setStatus(ReviewStatus.AUTO_REJECTED);
        } else {
            review.setStatus(ReviewStatus.NEEDS_MANUAL_REVIEW);
        }

        reviewRepository.save(review);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> getProductReviews(Long productId, Pageable pageable) {
        return reviewRepository.findByProductIdAndStatus(
                productId,
                ReviewStatus.AUTO_APPROVED,
                pageable
        ).map(reviewMapper::toDTO);
    }

    @Override
    @Transactional
    public ReviewDTO moderateReview(Long id, ReviewModerationDTO dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        review.setStatus(dto.getStatus());
        review.setAdminComment(dto.getAdminComment());

        return reviewMapper.toDTO(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public List<ReviewDTO> analyzeReviewsForSentiment() {
        return reviewRepository.findByStatus(ReviewStatus.PENDING_AI_ANALYSIS).stream()
                .map(review -> {
                    analyzeReviewAsync(review);
                    return reviewMapper.toDTO(review);
                })
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsNeedingModeration() {
        return reviewRepository.findByStatus(ReviewStatus.NEEDS_MANUAL_REVIEW).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }
}