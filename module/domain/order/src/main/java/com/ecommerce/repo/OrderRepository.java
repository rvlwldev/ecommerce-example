package com.ecommerce.repo;

import com.ecommerce.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> findById(UUID orderId);
    List<Order> findByMemberId(UUID memberId);
    Order save(Order order);
}
