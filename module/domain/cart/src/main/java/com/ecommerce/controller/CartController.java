package com.ecommerce.controller;

import com.ecommerce.dto.CartResponse;
import com.ecommerce.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.service.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{memberId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable UUID memberId) {
        Cart cart = cartService.getCart(memberId);
        return ResponseEntity.ok(CartResponse.from(cart));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody AddToCartRequest request) {
        cartService.addProductToCart(request.memberId(), request.productId(), request.amount());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> clearCart(@PathVariable String memberId) {
        System.out.println(UUID.randomUUID());
        cartService.clearCart(UUID.fromString(memberId));
        return ResponseEntity.ok().build();
    }

    // record : Java 14 , 필드, 생성자, Getter, toString() 등등을 알아서 만들어줌
    public record AddToCartRequest(UUID memberId, UUID productId, int amount) {}
}
