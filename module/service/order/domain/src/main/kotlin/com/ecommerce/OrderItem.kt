package com.ecommerce

import com.ecommerce.OrderError.ORDERED_QUANTITY_GTE_ONE
import java.util.UUID

class OrderItem(
    val id: UUID = UUID.randomUUID(),
    val productId: UUID,
    val productName: String = "",
    val price: Long,
    val quantity: Long = 1L
) {
    init {
        require(quantity > 0) { ORDERED_QUANTITY_GTE_ONE }
    }

    constructor(productId: UUID, price: Long) : this(
        id = UUID.randomUUID(),
        productId = productId,
        price = price
    )

    constructor(productId: UUID, price: Long, quantity: Long) : this(
        id = UUID.randomUUID(),
        productId = productId,
        price = price,
        quantity = quantity
    )

    companion object {
        fun create(productId: UUID, productPrice: Long, orderQuantity: Long) =
            OrderItem(productId, productPrice * orderQuantity, orderQuantity)
    }
}
