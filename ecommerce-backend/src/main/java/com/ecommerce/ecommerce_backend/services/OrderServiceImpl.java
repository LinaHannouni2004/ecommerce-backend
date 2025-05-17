package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.OrderDtos.*;
import com.ecommerce.ecommerce_backend.entities.*;
import com.ecommerce.ecommerce_backend.enums.StatusOrder;
import com.ecommerce.ecommerce_backend.exceptions.*;
import com.ecommerce.ecommerce_backend.mappers.*;
import com.ecommerce.ecommerce_backend.repositories.*;
import com.ecommerce.ecommerce_backend.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,ProductRepository productRepository,CartService cartService, OrderMapper orderMapper, OrderItemRepository orderItemRepository) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.orderMapper = orderMapper;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(Long userId, OrderCreateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(StatusOrder.PENDING);

        List<OrderItem> items = createOrderItems(dto.getItems());
        order.getItems();

        calculateOrderTotal(order);
        validateStock(items);

        Order savedOrder = orderRepository.save(order);
        updateProductStock(items);
        cartService.clearCart(userId);

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findByIdWithItems(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> getUserOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId,pageable)
                .map(orderMapper::toDTO);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(Long id, StatusOrder status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == StatusOrder.CANCELLED) {
            throw new BusinessException("Cannot update a cancelled order");
        }

        order.setStatus(status);
        return orderMapper.toDTO(orderRepository.save(order));
    }

    private List<OrderItem> createOrderItems(List<OrderItemCreateDTO> items) {
        return items.stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + item.getProductId()));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(product.getPrice());
                    return orderItem;
                })
                .toList();
    }

    private void calculateOrderTotal(Order order) {
        BigDecimal total = order.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(total);
    }

    private void validateStock(List<OrderItem> items) {
        items.forEach(item -> {
            if (item.getProduct().getStockQuantity() < item.getQuantity()) {
                throw new BusinessException("Insufficient stock for product: " + item.getProduct().getName());
            }
        });
    }

    private void updateProductStock(List<OrderItem> items) {
        items.forEach(item -> {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
        });
    }
}