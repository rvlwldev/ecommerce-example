package com.ecommerce.dto;

import com.ecommerce.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderRequest {

    private UUID memberId;
    private UUID addressId;
    private List<OrderItem> orderItems;
}
