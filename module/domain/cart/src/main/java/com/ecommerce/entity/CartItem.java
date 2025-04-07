package com.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private int amount;

    public CartItem(UUID productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public void changeAmount(int newAmount) {
        this.amount = newAmount;
    }
}
