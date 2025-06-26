package com.ecommerce

interface OrderCommandUseCase {

    fun createOrder()
    fun proceedOrder()
    fun cancelOrder()

}