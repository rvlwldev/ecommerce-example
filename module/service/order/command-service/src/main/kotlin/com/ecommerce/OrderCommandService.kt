package com.ecommerce

import com.ecommerce.OrderCommand.CancelOrderCommand
import com.ecommerce.OrderCommand.CreateOrderCommand
import com.ecommerce.OrderCommand.ProceedOrderCommand
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
) : OrderCommandUseCase {

    @Transactional
    override fun createOrder(command: CreateOrderCommand): Order {
        val (userId, items) = command
        userQuery.isAvailableOrThrow(userId)

        val products = productQuery.findAllOnSales(items.keys.toList())
        val orderItems: List<OrderItem> = products.map { product ->
            val quantity = items[product.id] ?: throw CommonException(ORDERED_QUANTITY_GTE_ONE)
            return@map try {
                val item = with(product) {
                    OrderItem(
                        productId = id,
                        productName = name,
                        price = price,
                        quantity = quantity
                    )
                }

                product.decreaseQuantity(quantity)

                item
            } catch (e: IllegalArgumentException) {
                throw OrderCommandError.toCommonException(e.message)
            } catch (e: Exception) {
                throw e
            }
        }

        val order = Order(userId, orderItems)
        return repo.save(order)
    }


    @Transactional
    override fun proceedOrder(command: ProceedOrderCommand): Order {
        val (userId, orderId) = command
        userQuery.isAvailableOrThrow(userId)

        val order = repo.findByOrderNumber(orderId) ?: throw CommonException(ORDER_NOT_FOUND)
        port.proceedPayment(userId, order.id)
        pointCommand.use(userId, order.totalAmount)
        order.proceedPay()

        return repo.save(order)
    }

    @Transactional
    override fun cancelOrder(command: CancelOrderCommand): Order {
        val (userId, orderId) = command
        userQuery.isAvailableOrThrow(userId)

        val order = repo.findByOrderNumber(orderId) ?: throw CommonException(ORDER_NOT_FOUND)
        port.cancel(userId, order.id)
        order.cancel()
        pointCommand.charge(userId, order.totalAmount)

        return repo.save(order)
    }

}