package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.CartDtos.CartDTO;
import com.ecommerce.ecommerce_backend.dtos.CartDtos.CartItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {
    CartDTO getCartByUserId(Long userId);
    CartDTO addItemToCart(Long userId, CartItemDTO dto);
    void removeItemFromCart(Long userId, Long itemId);
    void clearCart(Long userId);
    CartDTO updateCartItemQuantity(Long userId, Long itemId, int quantity);
    Page<CartDTO> getAbandonedCarts(Pageable pageable);
}
