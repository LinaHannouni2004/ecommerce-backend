package com.ecommerce.ecommerce_backend.mappers;

import com.ecommerce.ecommerce_backend.dtos.OrderDtos.OrderDTO;
import com.ecommerce.ecommerce_backend.dtos.OrderDtos.OrderItemCreateDTO;
import com.ecommerce.ecommerce_backend.dtos.OrderDtos.OrderItemDTO;
import com.ecommerce.ecommerce_backend.entities.Order;
import com.ecommerce.ecommerce_backend.entities.OrderItem;
import com.ecommerce.ecommerce_backend.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class OrderMapper {

    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    // Constructor injection

    public OrderMapper(ProductMapper productMapper, UserMapper userMapper) {
        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(String.valueOf(order.getStatus()));
        dto.setItems(order.getItems().stream()
                .map(this::toOrderItemDTO)  // Instance method reference
                .collect(Collectors.toList()));
        dto.setUser(userMapper.toBaseDTO(order.getUser()));  // Use injected mapper
        return dto;
    }

    public OrderItemDTO toOrderItemDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setProduct(productMapper.toDTO(item.getProduct()));  // Use injected mapper
        return dto;
    }

    public OrderItem toEntity(OrderItemCreateDTO dto) {
        OrderItem item = new OrderItem();
        item.setProduct(new Product());
        item.setQuantity(dto.getQuantity());
        return item;
    }
}
