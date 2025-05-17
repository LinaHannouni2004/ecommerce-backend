package com.ecommerce.ecommerce_backend.entities;

import com.ecommerce.ecommerce_backend.enums.ReviewStatus;
import com.ecommerce.ecommerce_backend.enums.SentimentLabel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews", indexes = {
        @Index(name = "idx_review_product", columnList = "product_id"),
        @Index(name = "idx_review_user", columnList = "user_id"),
        @Index(name = "idx_review_status", columnList = "status"),
        @Index(name = "idx_review_sentiment", columnList = "sentiment") // Bonus pour l'IA
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1) @Max(5)
    @Column(nullable = false)
    private Integer rating;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Size(max = 2000)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;
    /* Analyse IA */
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private SentimentLabel sentiment; // POSITIVE/NEUTRAL/NEGATIVE

    @Column(name = "sentiment_score") // -1.00 à 1.00
    private Double sentimentScore;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status; // Si true → à vérifier par l'admin
    @Column(name = "admin_comment")
    private String adminComment;
    /* Audit */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime  createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin reviewedBy;

    @UpdateTimestamp
    private LocalDateTime  updatedAt;

    public void setUser(User user) {
        this.user = user;
        if (user instanceof Customer) {
            ((Customer) user).getReviews().add(this);
        }
    }
    // Méthode utilitaire pour l'admin
    public boolean isSuspicious() {
        return (sentiment == SentimentLabel.NEGATIVE && sentimentScore > 0.7)
                || (rating <= 2 && sentimentScore < 0.3);
    }

    public Long getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public String getComment() {
        return comment;
    }

    public SentimentLabel getSentiment() {
        return sentiment;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Admin getReviewedBy() {
        return reviewedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSentiment(SentimentLabel sentiment) {
        this.sentiment = sentiment;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setReviewedBy(Admin reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}