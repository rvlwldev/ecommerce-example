package com.ecommerce

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.util.UUID

class OrderUnitTest {

    private lateinit var sut: Order

    @BeforeEach
    fun setUp() {
        sut = Order(userId = UUID.randomUUID())
    }

    @Test
    fun `failure - adding duplicate product throws`() {
        val pid = UUID.randomUUID()
        val item1 = OrderItem(pid, price = 1000, quantity = 1)
        val item2 = OrderItem(UUID.randomUUID(), price = 1000, quantity = 1)

        sut.addOrderItem(item1)
        assertDoesNotThrow { sut.addOrderItem(item2) }

        val e = assertThrows<IllegalArgumentException> { sut.addOrderItem(item1) }
        assertEquals(OrderError.ORDERED_PRODUCT_DUPLICATED.name, e.message)
    }

    @Test
    fun `success - adding products increases totalAmount`() {
        val item1 = OrderItem(UUID.randomUUID(), price = 1000, quantity = 2)
        val item2 = OrderItem(UUID.randomUUID(), price = 2000, quantity = 3)

        sut.addOrderItem(item1)
        sut.addOrderItem(item2)

        val expected = (item1.price * item1.quantity) + (item2.price * item2.quantity)
        assertEquals(expected, sut.totalAmount)
    }

    @Test
    fun `success - proceedPay sets status to PAID and records timestamp`() {
        sut.proceedPay()

        assertEquals(OrderStatus.PAID, sut.status)
        assertNotNull(sut.paidDateUTC)
        assertTrue(sut.paidDateUTC!! <= Instant.now())
    }

    @Test
    fun `failure - calling proceedPay on already paid order throws`() {
        sut.proceedPay()

        val e = assertThrows<IllegalArgumentException> { sut.proceedPay() }
        assertEquals(OrderError.ALREADY_PAID_ORDER.name, e.message)
    }

    @Test
    fun `success - cancel before payment sets status to CANCELLED and records timestamp`() {
        sut.cancel()

        assertEquals(OrderStatus.CANCELLED, sut.status)
        assertNotNull(sut.cancelDateUTC)
        assertTrue(sut.cancelDateUTC!! <= Instant.now())
    }

    @Test
    fun `failure - cancel after payment throws`() {
        sut.proceedPay()

        val e = assertThrows<IllegalArgumentException> { sut.cancel() }
        assertEquals(OrderError.ORDER_NOT_CANCELABLE.name, e.message)
    }
}