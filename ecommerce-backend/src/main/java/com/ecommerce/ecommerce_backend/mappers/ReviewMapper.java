package com.ecommerce.ecommerce_backend.mappers;

import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewCreateDTO;
import com.ecommerce.ecommerce_backend.dtos.ReviewDtos.ReviewDTO;
import com.ecommerce.ecommerce_backend.entities.Customer;
import com.ecommerce.ecommerce_backend.entities.Product;
import com.ecommerce.ecommerce_backend.entities.Review;
import com.ecommerce.ecommerce_backend.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public  ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setUserId(review.getUser().getId());
        dto.setUserName(review.getUser().getName());
        dto.setProductId(review.getProduct().getId());
        dto.setProductName(review.getProduct().getName());
        dto.setSentiment(String.valueOf(review.getSentiment()));
        dto.setSentimentScore(review.getSentimentScore());
        dto.setStatus(String.valueOf(review.getStatus()));
        dto.setAdminComment(review.getAdminComment());
        dto.setCreatedAt(review.getCreatedAt().toString());
        dto.setUpdatedAt(review.getUpdatedAt().toString());
        return dto;
    }

    public  Review toEntity(ReviewCreateDTO dto) {
        Review review = new Review();
        review.setUser(new Customer());
        review.setProduct(new Product());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return review;
    }
}

