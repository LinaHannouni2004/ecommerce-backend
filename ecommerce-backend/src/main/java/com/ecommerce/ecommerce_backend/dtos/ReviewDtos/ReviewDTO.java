package com.ecommerce.ecommerce_backend.dtos.ReviewDtos;

import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductDTO;
import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserBaseDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private String comment;
    private Integer rating;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
    private String sentiment;
    private Double sentimentScore;
    private String status;
    private String adminComment;
    private String createdAt;
    private String updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getComment() {
        return comment;
    }

    public Integer getRating() {
        return rating;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getSentiment() {
        return sentiment;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public String getStatus() {
        return status;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}