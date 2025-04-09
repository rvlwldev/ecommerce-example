package com.ecommerce.controller;

import com.ecommerce.dto.OrderResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody PlaceOrderRequest request) {
        Order order = orderService.placeOrder(request.memberId, request.addressId);
        return ResponseEntity.ok(OrderResponse.from(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(OrderResponse.from(orderService.getOrder(orderId)));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByMember(@PathVariable UUID memberId) {
        List<OrderResponse> responses = orderService.getOrdersByMember(memberId).stream()
                .map(OrderResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody UpdateStatusRequest request
    ) {
        orderService.changeOrderStatus(orderId, request.status());
        return ResponseEntity.ok().build();
    }

    public record PlaceOrderRequest(UUID memberId, UUID addressId) {}
    public record UpdateStatusRequest(OrderStatus status) {}
}
