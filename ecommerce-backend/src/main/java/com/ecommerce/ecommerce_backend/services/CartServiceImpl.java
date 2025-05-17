package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.CartDtos.CartDTO;
import com.ecommerce.ecommerce_backend.dtos.CartDtos.CartItemDTO;
import com.ecommerce.ecommerce_backend.entities.*;
import com.ecommerce.ecommerce_backend.exceptions.BusinessException;
import com.ecommerce.ecommerce_backend.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce_backend.mappers.CartMapper;
import com.ecommerce.ecommerce_backend.repositories.CartItemRepository;
import com.ecommerce.ecommerce_backend.repositories.CartRepository;
import com.ecommerce.ecommerce_backend.repositories.ProductRepository;
import com.ecommerce.ecommerce_backend.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository,
                           CartItemRepository cartItemRepository,
                           CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserIdWithItems(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO addItemToCart(Long userId, CartItemDTO dto) {
        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + dto.getId()));

        if (product.getStockQuantity() < dto.getQuantity()) {
            throw new BusinessException("Insufficient stock for product: " + product.getName());
        }

        Cart cart = getOrCreateCart(userId);
        updateOrAddCartItem(cart, product, dto.getQuantity());

        cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long userId, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + itemId));

        if (!item.getCart().getUser().getId().equals(userId)) {
            throw new BusinessException("Cart item does not belong to user");
        }

        cartItemRepository.delete(item);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserIdAndActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        cartItemRepository.deleteAllByCartId(cart.getId());
    }

    @Override
    @Transactional
    public CartDTO updateCartItemQuantity(Long userId, Long itemId, int quantity) {
        if (quantity <= 0) {
            throw new BusinessException("Quantity must be positive");
        }

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + itemId));

        if (!item.getCart().getUser().getId().equals(userId)) {
            throw new BusinessException("Cart item does not belong to user");
        }

        if (item.getProduct().getStockQuantity() < quantity) {
            throw new BusinessException("Insufficient stock for product: " + item.getProduct().getName());
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        return getCartByUserId(userId);
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserIdAndActiveTrue(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(new Customer());
                    newCart.setActive(true);
                    return cartRepository.save(newCart);
                });
    }

    private void updateOrAddCartItem(Cart cart, Product product, int quantity) {
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getPrice());
            cart.getCartItems().add(newItem);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Page<CartDTO> getAbandonedCarts(Pageable pageable) {
        LocalDateTime threshold = LocalDateTime.now().minusDays(2); // Carts inactive for 2 days
        return cartRepository.findByUpdatedAtBeforeAndActiveTrue(threshold, pageable)
                .map(cartMapper::toDTO);
    }
}