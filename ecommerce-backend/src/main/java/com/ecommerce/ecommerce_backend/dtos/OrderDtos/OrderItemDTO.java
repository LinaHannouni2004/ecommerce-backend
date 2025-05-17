package com.ecommerce.ecommerce_backend.dtos.OrderDtos;

import com.ecommerce.ecommerce_backend.dtos.ProductDtos.ProductDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDTO getProduct() {
        return product;
    }
}
