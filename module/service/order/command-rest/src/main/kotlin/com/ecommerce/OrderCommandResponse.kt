package com.ecommerce

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

class OrderCommandResponse {

    @Schema(description = "Created New Order")
    data class OrderResponse(
        @Schema(description = "Order Number", example = """ { "number" : "20250101-12345678" } """)
        val number: String,

        @Schema(description = "Order Total Amount, sum of items' price", example = """ { "totalAmount" : "1000" } """)
        val totalAmount: Long,

        @Schema(
            description = "Ordered Products", example = """
                [
                    {
                        "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                        "name": "Product Name1",
                        "price": 1000,
                        "quantity": 1
                    },
                    {
                        "id": "b2c3d4e5-f6a1-7890-abcd-1234567890ab",
                        "name": "Product Name2",
                        "price": 2000,
                        "quantity": 2
                    }
                ]"""
        )
        val items: List<OrderItemResponse>
    ) {
        constructor(order: Order) : this(
            number = order.orderNumber,
            totalAmount = order.totalAmount,
            items = order.items.map { with(it) { OrderItemResponse(productId, productName, price, quantity) } }
        )
    }

    @Schema(description = "Ordered Item")
    data class OrderItemResponse(
        @Schema(description = "Product ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        val id: UUID,

        @Schema(description = "Product Name", example = "Product Name")
        val name: String,

        @Schema(description = "Product Price", example = "1000")
        val price: Long,

        @Schema(description = "Order Quantity", example = "1")
        val quantity: Long
    )

}