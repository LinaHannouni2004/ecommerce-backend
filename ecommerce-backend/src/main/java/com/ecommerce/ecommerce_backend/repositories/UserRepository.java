package com.ecommerce.ecommerce_backend.repositories;

import com.ecommerce.ecommerce_backend.entities.Admin;
import com.ecommerce.ecommerce_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // Pour la modération (récupère seulement les admins)
    @Query("SELECT u FROM Admin u")
    List<Admin> findAllAdmins();

    @Query("SELECT u FROM Customer u LEFT JOIN FETCH u.reviews WHERE u.id = :id")
    Optional<User> findByIdWithReviews(@Param("id") Long id);

    boolean existsByEmail(String email);
}
