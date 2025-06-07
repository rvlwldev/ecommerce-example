package com.ecommerce

import java.util.UUID

class Point(
    val userId: UUID = UUID.fromString(""),
    amount: Long = 0
) {
    var amount = amount
        private set

    fun validateAmount(amount: Long) =
        require(amount > 0L && amount % 10L == 0L) { PointError.INVALID_CASH_AMOUNT }

    fun increaseCash(amount: Long) {
        validateAmount(amount)

        this.amount += amount
    }

    fun decreaseCash(amount: Long) {
        validateAmount(amount)
        require(amount >= this.amount) { PointError.NOT_ENOUGH_CASH }

        this.amount -= amount
    }
}