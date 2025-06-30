package com.ecommerce

import com.ecommerce.OrderError.*
import java.time.Instant
import java.util.UUID

class Order(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val orderNumber: String = "",
    val items: MutableList<OrderItem> = mutableListOf(),
    totalAmount: Long = 0
) {

    var totalAmount = totalAmount
        private set

    var status: OrderStatus = OrderStatus.READY
        private set

    var paidDateUTC: Instant? = null
        private set

    var cancelDateUTC: Instant? = null
        private set

    constructor(userId: UUID, items: List<OrderItem>) : this(
        userId = userId,
        items = items.toMutableList(),
        totalAmount = items.map { it.price * it.quantity }.sum()
    )

    fun addOrderItem(item: OrderItem) {
        require(items.none { it.productId == item.productId }) { ORDERED_PRODUCT_DUPLICATED }

        items.add(item)
        totalAmount += item.price * item.quantity
    }

    fun proceedPay() {
        require(status == OrderStatus.READY) { ALREADY_PAID_ORDER }

        status = OrderStatus.PAID
        paidDateUTC = Instant.now()
    }

    fun cancel() {
        require(status == OrderStatus.READY) { ORDER_NOT_CANCELABLE }

        status = OrderStatus.CANCELLED
        cancelDateUTC = Instant.now()
    }

}


