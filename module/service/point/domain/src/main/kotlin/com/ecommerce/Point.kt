package com.ecommerce

import java.util.UUID

class Point(
    val userId: UUID,
    amount: Long = 0
) {
    var amount = amount
        private set

    val chargeUnit = 10L

    fun validateAmount(amount: Long) {
        require(amount > 0L) { PointError.INVALID_POINT_AMOUNT }
    }

    fun charge(amount: Long) {
        validateAmount(amount)

        require(amount % chargeUnit == 0L) {
            String.format(PointError.INVALID_CHARGE_AMOUNT_UNIT, chargeUnit)
        }

        this.amount += amount
    }

    fun use(amount: Long) {
        validateAmount(amount)

        require(this.amount >= amount) { PointError.NOT_ENOUGH_POINT }

        this.amount -= amount
    }

}