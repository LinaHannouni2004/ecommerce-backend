package com.ecommerce.ecommerce_backend.repositories;

import com.ecommerce.ecommerce_backend.entities.Review;
import com.ecommerce.ecommerce_backend.enums.ReviewStatus;
import com.ecommerce.ecommerce_backend.enums.SentimentLabel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Avis par produit avec analyse sentimentale
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.sentiment = :sentiment")
    Page<Review> findByProductAndSentiment(
            @Param("productId") Long productId,
            @Param("sentiment") SentimentLabel sentiment,
            Pageable pageable
    );
    Page<Review> findByProductIdAndStatus(Long productId, ReviewStatus status, Pageable pageable);
    // Avis suspects (pour modération)
    @Query("SELECT r FROM Review r WHERE r.rating <= 2 AND r.sentimentScore > 0.7")
    List<Review> findSuspiciousReviews();

    // Moyenne des notes par produit
    @Query("SELECT r.product.id, AVG(r.rating) FROM Review r GROUP BY r.product.id")
    List<Object[]> getAverageRatings();

    // Derniers avis analysés par l'IA
    List<Review> findTop5ByOrderByUpdatedAtDesc();
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    List<Review> findBySentimentIsNull();
    List<Review> findByStatus(ReviewStatus status);
}