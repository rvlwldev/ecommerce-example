package com.ecommerce.repo;

import com.ecommerce.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpa;

    @Override
    public Optional<Order> findById(UUID orderId) {
        return Optional.empty();
    }

    @Override
    public List<Order> findByMemberId(UUID memberId) {
        return List.of();
    }

    @Override
    public Order save(Order order) {
        return jpa.save(order);
    }
}
