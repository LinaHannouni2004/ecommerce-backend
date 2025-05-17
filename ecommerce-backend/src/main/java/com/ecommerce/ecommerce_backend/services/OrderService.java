package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.OrderDtos.OrderCreateDTO;
import com.ecommerce.ecommerce_backend.dtos.OrderDtos.OrderDTO;
import com.ecommerce.ecommerce_backend.enums.StatusOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDTO createOrder(Long userId, OrderCreateDTO dto);
    OrderDTO getOrderById(Long id);
    Page<OrderDTO> getUserOrders(Long userId, Pageable pageable);
    OrderDTO updateOrderStatus(Long id, StatusOrder status);
}