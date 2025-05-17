package com.ecommerce.ecommerce_backend.dtos.CartDtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDTO {
    private Long id;
    private List<CartItemDTO> items;

    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
