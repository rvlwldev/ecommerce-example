package com.ecommerce

import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class OrderItemTest {

    @Test
    fun `success - from creates item with correct fields`() {
        val productId = UUID.randomUUID()
        val productPrice = 1500L
        val orderQuantity = 3L

        val item = OrderItem.create(productId, productPrice, orderQuantity)

        assertEquals(productId, item.productId)
        assertEquals(productPrice * orderQuantity, item.price)
        assertEquals(orderQuantity, item.quantity)
        assertNotNull(item.id)
    }

    @Test
    fun `failure - from with zero quantity throws IllegalArgumentException`() {
        val productId = UUID.randomUUID()
        val productPrice = 1500L
        val orderQuantity = 0L

        val e = assertThrows<IllegalArgumentException> {
            OrderItem.create(productId, productPrice, orderQuantity)
        }
        assertEquals(OrderError.ORDERED_QUANTITY_GTE_ONE.name, e.message)
    }

    @Test
    fun `success - two-arg constructor sets quantity to one`() {
        val productId = UUID.randomUUID()
        val price = 2000L

        val item = OrderItem(productId, price)

        assertEquals(productId, item.productId)
        assertEquals(price, item.price)
        assertEquals(1L, item.quantity)
        assertNotNull(item.id)
    }

    @Test
    fun `success - three-arg constructor sets fields correctly`() {
        val productId = UUID.randomUUID()
        val price = 2000L
        val quantity = 5L

        val item = OrderItem(productId, price, quantity)

        assertEquals(productId, item.productId)
        assertEquals(price, item.price)
        assertEquals(quantity, item.quantity)
        assertNotNull(item.id)
    }

    @Test
    fun `failure - three-arg constructor with zero quantity throws IllegalArgumentException`() {
        val productId = UUID.randomUUID()
        val price = 2000L
        val quantity = 0L

        val ex = assertThrows<IllegalArgumentException> { OrderItem(productId, price, quantity) }
        assertEquals(OrderError.ORDERED_QUANTITY_GTE_ONE.name, ex.message)
    }

}