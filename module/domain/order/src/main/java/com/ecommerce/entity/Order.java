package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID memberId;

    @Column(nullable = false)
    private UUID addressId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(UUID memberId, UUID addressId, OrderStatus status, Address address, List<OrderItem> orderItems) {
        this.memberId = memberId;
        this.addressId = addressId;
        this.status = status;
        this.address = address;
        this.orderItems = orderItems;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }
}
