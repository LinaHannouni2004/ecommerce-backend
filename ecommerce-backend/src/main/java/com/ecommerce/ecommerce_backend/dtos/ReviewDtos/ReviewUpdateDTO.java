package com.ecommerce.ecommerce_backend.dtos.ReviewDtos;

import com.ecommerce.ecommerce_backend.enums.ReviewStatus;
import lombok.Data;

@Data
public class ReviewUpdateDTO {
    private ReviewStatus status;
    private String adminComment;

    public ReviewStatus getStatus() {
        return status;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }
}
