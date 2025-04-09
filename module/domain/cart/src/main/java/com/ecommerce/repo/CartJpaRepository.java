package com.ecommerce.repo;

import com.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartJpaRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByMemberId(UUID memberId);
}
