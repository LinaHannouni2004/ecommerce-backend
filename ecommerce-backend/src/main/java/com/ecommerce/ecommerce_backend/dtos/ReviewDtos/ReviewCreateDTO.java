package com.ecommerce.ecommerce_backend.dtos.ReviewDtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewCreateDTO {
    @NotNull
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    @NotNull
    private Long productId;

    @Min(1) @Max(5)
    private Integer rating;

    @NotBlank
    @Size(max = 2000)
    private String comment;
}