package com.ecommerce

import java.util.UUID

class Product(
    val id: UUID = UUID.randomUUID(),
    val category: ProductCategory,
    val name: String,
    price: Long,
    quantity: Long,
    status: ProductStatus = ProductStatus.AVAILABLE,
) {
    var price = price
        private set

    var quantity = quantity
        private set

    var status = status
        private set

    fun isSellable() = status == ProductStatus.AVAILABLE

    fun decreaseQuantity() {
        require(quantity > 0) { ProductError.NOT_ENOUGH_QUANTITY }
        quantity--
    }

    fun decreaseQuantity(amount: Long) {
        require(quantity - amount >= 0) { ProductError.NOT_ENOUGH_QUANTITY }
        quantity -= amount
    }

    fun increaseQuantity(amount: Long) {
        quantity += amount
    }

    fun changePrice(price: Long) {
        require(price > 0) { ProductError.NOT_ENOUGH_QUANTITY }
        this.price = price
    }

}