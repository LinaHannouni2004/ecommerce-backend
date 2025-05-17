package com.ecommerce.ecommerce_backend.repositories;

import com.ecommerce.ecommerce_backend.entities.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndActiveTrue(Long userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems WHERE c.user.id = :userId AND c.active = true")
    Optional<Cart> findByUserIdWithItems(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Cart c SET c.active = false WHERE c.user.id = :userId AND c.active = true")
    void deactivateUserCart(@Param("userId") Long userId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cart c WHERE c.user.id = :userId AND c.active = true")
    boolean existsActiveCartByUserId(@Param("userId") Long userId);
    Page<Cart> findByUpdatedAtBeforeAndActiveTrue(LocalDateTime date, Pageable pageable);
}