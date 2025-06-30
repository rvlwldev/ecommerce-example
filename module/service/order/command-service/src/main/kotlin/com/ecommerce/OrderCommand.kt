package com.ecommerce

import java.util.UUID

class OrderCommand {

    data class CreateOrderCommand(val userId: UUID, val items: Map<UUID, Long>)
    data class ProceedOrderCommand(val userId: UUID, val orderNumber: String)
    data class CancelOrderCommand(val userId: UUID, val orderNumber: String)

}