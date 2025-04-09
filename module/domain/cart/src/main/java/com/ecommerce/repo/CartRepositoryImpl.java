package com.ecommerce.repo;

import com.ecommerce.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {
    private final CartJpaRepository jpa;

    @Override
    public Optional<Cart> findByMemberId(UUID memberId) {
        return jpa.findByMemberId(memberId);
    }

    @Override
    public Cart save(Cart cart) {
        return jpa.save(cart);
    }
}
