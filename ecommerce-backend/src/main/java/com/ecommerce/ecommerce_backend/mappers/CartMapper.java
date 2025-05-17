package com.ecommerce.ecommerce_backend.mappers;

import com.ecommerce.ecommerce_backend.dtos.CartDtos.CartDTO;
import com.ecommerce.ecommerce_backend.dtos.CartDtos.CartItemDTO;
import com.ecommerce.ecommerce_backend.entities.Cart;
import com.ecommerce.ecommerce_backend.entities.CartItem;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class CartMapper {
    private final ProductMapper productMapper;

    public CartMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CartDTO toDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setTotal(cart.getTotal());
        dto.setItems(cart.getCartItems().stream()
                .map(this::toCartItemDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    public CartItemDTO toCartItemDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setProduct(productMapper.toDTO(item.getProduct())); // Use injected mapper
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        return dto;
    }

}
