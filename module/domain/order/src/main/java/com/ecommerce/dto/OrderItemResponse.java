package com.ecommerce.dto;

import com.ecommerce.entity.OrderItem;

import java.util.UUID;

public record OrderItemResponse(
        UUID productId,
        int quantity,
        int price
) {
    public static OrderItemResponse from(OrderItem item) {
        return new OrderItemResponse(item.getProductId(), item.getQuantity(), item.getPrice());
    }
}
