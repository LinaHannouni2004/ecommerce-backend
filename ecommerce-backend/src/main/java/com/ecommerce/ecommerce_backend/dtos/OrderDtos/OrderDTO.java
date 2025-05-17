package com.ecommerce.ecommerce_backend.dtos.OrderDtos;

import com.ecommerce.ecommerce_backend.dtos.UserDtos.UserBaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String status;
    private List<OrderItemDTO> items;
    private UserBaseDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public void setUser(UserBaseDTO user) {
        this.user = user;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public UserBaseDTO getUser() {
        return user;
    }
}
