package com.ecommerce.controller;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.service.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        Order order = orderService.placeOrder(
                UUID.randomUUID(),
                request.getMemberId(),
                request.getAddressId(),
                request.getOrderItems()
        );

        return ResponseEntity.ok(order);
    }
}
