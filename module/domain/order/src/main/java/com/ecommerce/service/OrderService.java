package com.ecommerce.service;

import com.ecommerce.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ecommerce.repo.CartRepository;
import com.ecommerce.repo.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;
    private final AddressRepository addressRepo;

    public Order placeOrder(UUID memberId, UUID addressId) {
        // 1. 장바구니 가져오기
        Cart cart = cartRepo.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 비어 있습니다."));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("장바구니에 담긴 상품이 없습니다.");
        }

        // 2.주소 정보 가져오기
        //TODO : AddressDTO를 만들어야하나? 봐야할듯
        Address address = addressRepo.find(addressId);

        // 3.주문 아이템 만들기
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            //TODO : Product가 있어야 로직 작성 가능할듯
        }

        // 4.주문 생성 및 저장
        Order order = new Order(memberId, addressId, OrderStatus.PENDING, address, orderItems);
        Order saved = orderRepo.save(order);

        // 5. 장바구니 비우기
        cart.getItems().clear();
        cartRepo.save(cart);

        return saved;
    }

    public Order getOrder(UUID orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }

    public List<Order> getOrdersByMember(UUID memberId) {
        return orderRepo.findByMemberId(memberId);
    }

    public void changeOrderStatus(UUID orderId, OrderStatus status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        order.changeStatus(status);
        orderRepo.save(order);
    }
}
