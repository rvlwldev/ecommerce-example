package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ecommerce.repo.CartRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    // 장바구니 조회
    public Cart getCart(UUID memberId) {
        return cartRepository.findByMemberId(memberId)
                .orElseGet(() -> cartRepository.save(new Cart(memberId)));
    }

    // 장바구니 추가
    public void addProductToCart(UUID memberId, UUID productId, int amount) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseGet(() -> cartRepository.save(new Cart(memberId)));

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.changeAmount(existingItem.getAmount() + amount);
        } else {
            cart.getItems().add(new CartItem(productId, amount));
        }

        cartRepository.save(cart);
    }

    // 장바구니 clear
    public void clearCart(UUID memberId) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
