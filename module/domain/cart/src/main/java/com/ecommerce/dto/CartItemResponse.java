package com.ecommerce.dto;

import com.ecommerce.entity.CartItem;

import java.util.UUID;

public record CartItemResponse(UUID productId, int amount) {
    public static CartItemResponse from(CartItem item) {
        return new CartItemResponse(item.getProductId(), item.getAmount());
    }
}
