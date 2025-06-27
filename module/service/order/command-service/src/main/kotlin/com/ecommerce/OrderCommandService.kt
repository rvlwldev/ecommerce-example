package com.ecommerce

import OrderCommand.CancelOrderCommand
import OrderCommand.CreateOrderCommand
import OrderCommand.ProceedOrderCommand
import com.ecommerce.OrderCommandError.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderCommandService(
    private val repo: OrderRepository,
    private val port: SaveOrderPort,
    private val userQuery: UserQueryUseCase,
    private val productQuery: ProductQueryUseCase,
    private val pointCommand: PointCommandUseCase
) {

    @Transactional
    fun createOrder(command: CreateOrderCommand): Order {
        val (userId, items) = command
        userQuery.isAvailableOrThrow(userId)

        val products = productQuery.findAllOnSales(items.keys.toList())
        val orderItems = products.map { product ->
            val quantity = items[product.id] ?: throw CommonException(ORDERED_QUANTITY_GTE_ONE)
            product.decreaseQuantity(quantity)
            return@map runCatching { OrderItem(product.id, product.price, quantity) }
                .getOrElse { e -> throw OrderCommandError.toCommonException(e.message) }
        }.apply { require(size == items.size) { throw CommonException(ORDERED_PRODUCT_NOT_ORDERABLE) } }
        val order = Order(userId, orderItems)

        return repo.save(order)
    }

    @Transactional
    fun proceedOrder(command: ProceedOrderCommand): Order {
        val (userId, orderId) = command
        userQuery.isAvailableOrThrow(userId)

        val order = repo.find(orderId) ?: throw CommonException(ORDER_NOT_FOUND)
        port.proceedPayment(userId, order.id)
        pointCommand.use(userId, order.totalAmount)
        order.proceedPay()

        return repo.save(order)
    }

    @Transactional
    fun cancelOrder(command: CancelOrderCommand): Order {
        val (userId, orderId) = command
        userQuery.isAvailableOrThrow(userId)

        val order = repo.find(orderId) ?: throw CommonException(ORDER_NOT_FOUND)
        port.cancel(userId, order.id)
        order.cancel()
        pointCommand.charge(userId, order.totalAmount)

        return repo.save(order)
    }

}