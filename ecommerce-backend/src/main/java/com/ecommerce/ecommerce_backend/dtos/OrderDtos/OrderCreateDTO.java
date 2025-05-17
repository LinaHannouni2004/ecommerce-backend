package com.ecommerce.ecommerce_backend.dtos.OrderDtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDTO {

    @NotNull
    private Long userId;

    @NotEmpty
    private List<OrderItemCreateDTO> items;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setItems(List<OrderItemCreateDTO> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public List<OrderItemCreateDTO> getItems() {
        return items;
    }
}