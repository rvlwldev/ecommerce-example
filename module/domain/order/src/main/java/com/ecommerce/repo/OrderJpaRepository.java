package com.ecommerce.repo;

import com.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {
    List<Order> findByMemberId(UUID memberId);
}
