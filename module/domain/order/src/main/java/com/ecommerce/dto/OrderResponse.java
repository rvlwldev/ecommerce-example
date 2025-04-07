package com.ecommerce.dto;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.service.Address;

import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID memberId,
        Address address,
        OrderStatus status,
        List<OrderItemResponse> orderItems
) {
    public static OrderResponse from(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(OrderItemResponse::from)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getMemberId(),
                order.getAddress(),
                order.getStatus(),
                itemResponses
        );
    }
}
