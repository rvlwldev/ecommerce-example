package com.ecommerce

import java.util.UUID

interface OrderRepository {

    fun find(id: UUID): Order?
    fun findByOrderNumber(number: String): Order?
    fun save(order: Order): Order

}