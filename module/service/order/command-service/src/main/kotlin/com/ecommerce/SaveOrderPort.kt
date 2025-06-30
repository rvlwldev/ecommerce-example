package com.ecommerce

import java.util.UUID

interface SaveOrderPort {
    fun proceedPayment(userId: UUID, orderId: UUID): Order
    fun cancel(userId: UUID, orderId: UUID): Order
}