package com.ecommerce.repo;

import com.ecommerce.entity.Cart;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository {
    Optional<Cart> findByMemberId(UUID memberId);
    Cart save(Cart cart);
}
