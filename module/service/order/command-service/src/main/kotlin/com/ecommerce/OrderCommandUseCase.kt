package com.ecommerce

import com.ecommerce.OrderCommand.CancelOrderCommand
import com.ecommerce.OrderCommand.CreateOrderCommand
import com.ecommerce.OrderCommand.ProceedOrderCommand

interface OrderCommandUseCase {

    fun createOrder(command: CreateOrderCommand): Order
    fun proceedOrder(command: ProceedOrderCommand): Order
    fun cancelOrder(command: CancelOrderCommand): Order

}