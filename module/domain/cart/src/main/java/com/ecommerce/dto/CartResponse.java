package com.ecommerce.dto;

import com.ecommerce.entity.Cart;

import java.util.List;
import java.util.UUID;

public record CartResponse(UUID memberId, List<CartItemResponse> items) {
    public static CartResponse from(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(CartItemResponse::from)
                .toList();

        return new CartResponse(cart.getMemberId(), itemResponses);
    }
}
